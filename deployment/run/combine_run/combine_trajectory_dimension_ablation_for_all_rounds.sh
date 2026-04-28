#!/bin/bash
class_name="Trajectory"

#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._5_ablation_run._5_2_1_poster_process_run.${class_name}DimensionAblationPosterRun