/*
 * (C) Copyright 2017-2019 ElasTest (http://elastest.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package org.sparkexample;
import com.google.common.collect.ImmutableList;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import static org.elasticsearch.spark.rdd.api.java.JavaEsSpark.*;
import scala.Tuple2;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;


public class WordCountTask {
  public static void main(String[] args) {
    //checkArgument(args.length > 0, "Please provide the path of input file as first parameter.");
    //new WordCountTask().run(args[0]);
    new WordCountTask().run();
  }



  public void run(){
    SparkConf conf = new SparkConf()
            .setAppName(WordCountTask.class.getName());
    conf.set("spark.es.index.auto.create", "true");
    conf.set("spark.es.nodes", "elastest_esnode_1");
    JavaSparkContext context = new JavaSparkContext(conf);
    JavaRDD<Map<String, Object>> esRDD = esRDD(context, "2", "?q=me*").values();
    //esRDD.take(100).forEach(System.out::println);
    esRDD.take(100).forEach(p -> System.out.println(p));

    Map<String, Object> myMap = new HashMap<>();
    esRDD.collect().forEach(i -> myMap.putAll(i));

    /*esRDD.collect().forEach(
            (Map<String, Object> i) -> {
                myMap.entrySet().contains("message");

            } //myMap.putAll(i)

    );
*/

    List<String> msg = new ArrayList<>();
//    esRDD.collect().stream().forEach(i-> i.get( i.containsKey("message") ) );


    System.out.println("size: " + myMap.size());

    for(Map.Entry<String, Object>  obj :  myMap.entrySet()){
      if ( obj.getKey().equals("message") ){
        msg.add((String) obj.getValue());
      }
    }
    for (String s: msg){
      System.out.println("message: " + s);
    }
  }

  
//  public void run(String inputFilePath) {
//    SparkConf conf = new SparkConf()
//        .setAppName(WordCountTask.class.getName());
//    conf.set("spark.es.index.auto.create", "true");
//    conf.set("spark.es.nodes", "elastest_esnode_1");
//    JavaSparkContext context = new JavaSparkContext(conf);
////    JavaPairRDD<String, Map<String, Object>> esRDD =
////            JavaEsSpark.esRDD(jsc, "radio/artists");
//
//    JavaRDD<Map<String, Object>> esRDD = esRDD(context, "362", "?q=me*").values();
//    esRDD.take(100).forEach(System.out::println);
//    esRDD.take(100).forEach(p -> System.out.println(p));
//
//    context.textFile(inputFilePath)
//            .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
//            .mapToPair(word -> new Tuple2<>(word, 1))
//            .reduceByKey((a, b) -> a + b); //.saveAsTextFile("/out.txt");
    //JavaRDD<Object> jrdd = context.parallelize(Arrays.asList(context));


    //JavaRDD  javaRDD =
 //   JavaEsSpark.saveToEs(jrdd, "spark/testresult");
//  }




}



















