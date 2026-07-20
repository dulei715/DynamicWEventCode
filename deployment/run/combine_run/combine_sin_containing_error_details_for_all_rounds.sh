#!/bin/bash
dataset_dir_name="Sin";

#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._4_added_run._3_6_poster_process_run.${dataset_dir_name}ContainingErrorDetailsPosterRun