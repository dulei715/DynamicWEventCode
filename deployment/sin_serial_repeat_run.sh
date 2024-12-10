#!/bin/bash
dataset_dir_name="Sin";
final_output_dir_name="4.sin_time_cost_result"
basic_path="../0.dataset/$dataset_dir_name";
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
for i in {1..10}; do
  java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._4_added_run._2_2_time_cost_run.${dataset_dir_name}TimeCostRun
  mkdir -p ${basic_path}/round_${i}_time_cost
  mv ${basic_path}/basic_info ${basic_path}/round_${i}_time_cost &
  mv ${basic_path}/runInput ${basic_path}/round_${i}_time_cost &
  mv ${basic_path}/group_generated_parameters ${basic_path}/round_${i}_time_cost &
  mv ${basic_path}/group_output_time_cost ${basic_path}/round_${i}_time_cost &
  mv ${basic_path}/extract_result ${basic_path}/round_${i}_time_cost &
  mv ${basic_path}/${final_output_dir_name} ${basic_path}/round_${i}_time_cost &
  wait
done