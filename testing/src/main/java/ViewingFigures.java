import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;


import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;


public class ViewingFigures
{
    public static void main(String[] args)
    {
        //        System.setProperty("hadoop.home.dir", "C:\\Use11rs\\filip\\Downloads\\hadoop-common-2.2.0-bin-master");
        //        System.setProperty("hadoop.home.dir", "C:\\Users\\filip\\Downloads\\winutil");
        Logger.getLogger("org.apache").setLevel(Level.WARN);


        SparkSession conf = SparkSession.builder().appName("test").master("spark://10.10.1.14:7077").getOrCreate();


        try (JavaSparkContext sc = new JavaSparkContext(conf.sparkContext()))
        {
            Dataset<String> stringDataset = conf.createDataset(Arrays.asList("k","t","a","z", "b", "c", "a"), Encoders.STRING());
            Dataset<String> sortedDataset = stringDataset.sort(stringDataset.col(stringDataset.columns()[0])); //by defualt is ascending order
            List<String> result = sortedDataset.collectAsList();
            result.stream().forEach(i -> System.out.println(i));



//            List<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//            JavaRDD<Integer> rdd = sc.parallelize(collection).sortBy(i -> i, true, 1);
//
//            for (Integer tmp : rdd.collect())
//            {
//                System.out.println(tmp);
//            }


        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
