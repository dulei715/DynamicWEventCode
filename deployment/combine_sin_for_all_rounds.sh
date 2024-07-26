#!/bin/bash
dataset_dir_name="Sin";
final_output_dir_name="4.sin_result"
basic_path="../0.dataset/$dataset_dir_name";
basic_output_path="../1.result";
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
# java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.utils.run.CombineForEachRound ${basic_path} ${final_output_dir_name}
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._3_poster_process_run.CombineMultipleMainRound ${basic_path} ${basic_output_path}