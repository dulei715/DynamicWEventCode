#!/bin/bash
dataset_dir_name="CheckIn_dataset_TIST2015";
main_java_file_name="CheckIn"
basic_path="../0.dataset/$dataset_dir_name";
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
for i in {1..10}; do
  java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._4_added_run._2_4_containing_spas_run.${main_java_file_name}ContainingSPASRun
  mkdir -p ${basic_path}/round_${i}_containing_spas/basic_info
  mv ${basic_path}/basic_info/userTypeID.txt ${basic_path}/round_${i}_containing_spas/basic_info &
  mv ${basic_path}/basic_info/user_to_type.txt ${basic_path}/round_${i}_containing_spas/basic_info &
  mv ${basic_path}/group_generated_parameters ${basic_path}/round_${i}_containing_spas &
  mv ${basic_path}/group_output_containing_spas ${basic_path}/round_${i}_containing_spas &
#  mv ${basic_path}/extract_result ${basic_path}/round_${i} &
#  mv ${basic_path}/${final_output_dir_name} ${basic_path}/round_${i} &
  wait
done