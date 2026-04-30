#!/bin/bash
dataset_dir_name="TLNS";

#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
exp_class_name="Total"
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._4_added_run._3_5_poster_process_run.${dataset_dir_name}Containing${exp_class_name}PosterRun