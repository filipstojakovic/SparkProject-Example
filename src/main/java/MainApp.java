
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

}
