#!/bin/bash
main_java_file_name="Trajectory"
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._1_initialize_run.${main_java_file_name}Initialize