#!/bin/bash
dataset_dir_name="Log";
final_output_dir_name="5.log_result"
basic_path="../0.dataset/$dataset_dir_name";
basic_output_path="../1.result";
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
#java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.utils.run.CombineForEachRound ${basic_path} ${final_output_dir_name}
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.utils.run.RepeatUtils ${basic_path} ${basic_output_path}