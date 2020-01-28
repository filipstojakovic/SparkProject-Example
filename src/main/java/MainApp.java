
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import javax.swing.*;
import java.util.Scanner;

public class MainApp
{


    public static void main(String[] args)
    {
        JFrame frame = new JFrame("MyApp");
        frame.setContentPane(new MainAppController().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }



        //    public static void main(String[] args)
//    {
//        //  spark://10.10.1.14:7077
//
//        //TODO: change \\ to patseparator
//        System.setProperty("hadoop.home.dir", "C:\\Users\\filip\\Downloads\\hadoop-common-2.2.0-bin-master"); // only for local machine (i think)
//        SparkSession sc = SparkSession.builder().appName("Stupidoooo").master("local[*]").getOrCreate();
//
//
//        try(JavaSparkContext context = new JavaSparkContext(sc.sparkContext()))
//        {
//            //Logger.getLogger("org.apache").getLevel(Level.WARN);
//
//            //List<Integer> list = new ArrayList<>()
//
//            (new Scanner(System.in)).nextInt();
//            context.stop();
//
//
//        }catch(Exception ex)
//        {
//            System.out.println("Hello there");
//        }
//    }

}
