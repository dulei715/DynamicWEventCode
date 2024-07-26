#!/bin/bash
dataset_dir_name="Sin";
final_output_dir_name="4.sin_internal_result"
basic_path="../0.dataset/$dataset_dir_name";
basic_output_path="../1.result_internal";
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
# java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.utils.run.CombineForEachRoundInternal ${basic_path} ${final_output_dir_name}
# java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.e_internal_run._3_poster_process_run.CombineMultipleInternalRound ${basic_path} ${basic_output_path}
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.e_internal_run._3_poster_process_run.${dataset_dir_name}InternalPosterRun