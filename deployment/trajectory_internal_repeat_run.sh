#!/bin/bash
dataset_dir_name="T-drive_Taxi_Trajectories";
main_java_file_name="Trajectory"
final_output_dir_name="1.trajectory_internal_result"
position_file_name="cell.txt"
basic_path="../0.dataset/$dataset_dir_name";
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
for i in {1..10}; do
  java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.e_internal_run._2_main_run.${main_java_file_name}InternalRun
  mkdir -p ${basic_path}/round_${i}_internal
#  mv ${basic_path}/basic_info/userTypeID.txt ${basic_path}/round_${i}/basic_info &
#  mv ${basic_path}/basic_info/user_to_type.txt ${basic_path}/round_${i}/basic_info &
  mv ${basic_path}/group_generated_parameters ${basic_path}/round_${i}_internal &
  mv ${basic_path}/group_output_internal ${basic_path}/round_${i}_internal &
  mv ${basic_path}/extract_internal_result ${basic_path}/round_${i}_internal &
  mv ${basic_path}/${final_output_dir_name} ${basic_path}/round_${i}_internal &
  wait
done