/*
 * Copyright 2009-2015 DigitalGlobe, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package org.mrgeo.data.rdd

import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.mapreduce.SparkHadoopMapReduceUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{Logging, Partition, TaskContext}

import scala.reflect.ClassTag

class MrGeoRDD[K:ClassTag, V:ClassTag](parent: RDD[(K, V)])  extends RDD[(K, V)](parent) {

  //  printDependencies(this)
//  private def printDependencies(rdd:RDD[_], level:Int = 0) {
//    for (sp <- 0 until level) {
//      print(" ")
//    }
//    print("*** " + rdd.name + ": " + rdd.id + " (" + rdd + ")")
//
//    rdd match {
//    case mrgeo:MrGeoRDD[_,_] =>
//      println(" --- MrGeoRDD ")
//    case _ => println()
//    }
//
//    rdd.dependencies.foreach(dep => {
//      printDependencies(dep.rdd, level + 1)
//    })
//  }

  @DeveloperApi
  override def compute(split: Partition, context: TaskContext): Iterator[(K, V)] = {
    firstParent[(K, V)].iterator(split, context)
  }

  override protected def getPartitions: Array[Partition] = {
    firstParent[(K, V)].partitions
  }


}