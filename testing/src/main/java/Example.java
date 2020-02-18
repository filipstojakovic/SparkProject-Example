import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Example extends Application
{
    @FXML
    private TextArea initialTextArea;
    @FXML
    private TextArea sortedTextArea;
    @FXML
    private Label durationLbl;
    private Service<String> backgroundTask;
    public static final int NUMBER_OF_SLICES = 1000;
    @FXML
    Parent root = null;

    @FXML
    void startBtnClicked(ActionEvent event)
    {
        long startTime = System.currentTimeMillis();

        backgroundTask = new Service<String>()
        {
            @Override
            public Task<String> createTask()
            {
                return new Task<String>()
                {
                    @Override
                    protected String call() throws Exception
                    {
                        return MyExample(initialTextArea.getText());
                    }
                };
            }
        };

        backgroundTask.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent workerStateEvent)
            {
                sortedTextArea.setText(backgroundTask.getValue().trim());
                System.out.println("DONE");
                long endTime = System.currentTimeMillis();
                long duration = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

                if(duration==0)
                    durationLbl.setText((endTime - startTime) + "ms");
                else
                    durationLbl.setText(duration + "s");

            }
        });
        if(!"".equals(initialTextArea.getText().trim()))
            backgroundTask.start();

    }

    public static void main(String[] args)
    {
        //START PROGRAM
        launch(args);
    }

    public static String MyExample(String inputString)
    {

        Logger.getLogger("org.apache").setLevel(Level.WARN);
        List<String> result = null;
        try
        {
//            SparkSession conf = SparkSession.builder().appName("test").master("spark://10.10.1.14:7077").getOrCreate();
            SparkSession conf = SparkSession.builder().appName("test").master("local[*]").getOrCreate();


            result = new ArrayList<>();
            try (JavaSparkContext javaSparkContext = new JavaSparkContext(conf.sparkContext()))
            {
                char inputChars[] = inputString.toCharArray();
                List<String> tmp = new ArrayList<String>();


                for (int i = 0; i < inputChars.length; i++)
                    if (' ' != inputChars[i] && '\n' != inputChars[i])
                        tmp.add(String.valueOf(inputChars[i]));

                JavaRDD<String> rdd = javaSparkContext.parallelize(tmp, NUMBER_OF_SLICES);
                Dataset<String> stringDataset = conf.createDataset(rdd.collect(), Encoders.STRING());
                Dataset<String> sortedDataset = stringDataset.sort(stringDataset.col(stringDataset.columns()[0]));
                result = sortedDataset.collectAsList();
                //            result.stream().forEach(System.out::println);

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        } catch (Exception ex)
        {
            System.err.println("Cluster is probably not running");
            //            ex.printStackTrace();
        }
        return result.toString();
    }

    @FXML
    @Override
    public void start(Stage primaryStage)
    {
        String sceneFile = "fxml/Example.fxml";
//        Parent root = null;
        URL url = null;
        try
        {
            url = getClass().getResource(sceneFile);
            root = FXMLLoader.load(url);
            root.setStyle("fx-border-color:black");
            Scene scene = new Scene(root);
            primaryStage.setTitle("Generic Algotithm");
            primaryStage.setScene(scene);

//            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex)
        {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + ex);
        }

    }

}
