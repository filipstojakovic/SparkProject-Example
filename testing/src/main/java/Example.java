import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Char;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Example extends Application
{
    @FXML
    private TextArea initialTextArea;
    @FXML
    private TextArea sortedTextArea;
    private Service<String> backgroundTask;
    @FXML
    void startBtnClicked(ActionEvent event)
    {
        backgroundTask= new Service<String>()
        {   @Override
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
            }
        });
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
        List<String> result=null;
        try
        {
//            SparkSession conf = SparkSession.builder().appName("test").master("spark://10.10.1.14:7077").getOrCreate();
            SparkSession conf = SparkSession.builder().appName("test").master("local[*]").getOrCreate();


            result= new ArrayList<>();
            try (JavaSparkContext sc = new JavaSparkContext(conf.sparkContext()))
            {
                char inputChars[] = inputString.toCharArray();
                List<String> tmp = new ArrayList<String>();

                for(int i=0;i<inputChars.length;i++)
                    if(' '!=inputChars[i])
                        tmp.add(String.valueOf(inputChars[i]));


                Dataset<String> stringDataset = conf.createDataset(tmp,Encoders.STRING());
                Dataset<String> sortedDataset = stringDataset.sort(stringDataset.col(stringDataset.columns()[0]));
                result = sortedDataset.collectAsList();
    //            result.stream().forEach(System.out::println);

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }catch (Exception ex)
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
        Parent root = null;
        URL url = null;
        try
        {
            url = getClass().getResource(sceneFile);
            root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Generic Algotithm");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex)
        {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + ex);
        }

    }

}
