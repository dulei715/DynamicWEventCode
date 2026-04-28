#!/bin/bash
dataset_dir_name="CheckIn_dataset_TIST2015";
main_java_file_name="CheckIn"
basic_path="../0.dataset/$dataset_dir_name";
fix_tag="dimension_ablation"
#totalMem="-Xms120g"
#maxMem="-Xmx2400g"
for i in {1..10}; do
  java ${totalMem} ${maxMem} -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.d_total_run._5_ablation_run._5_1_1_dim_ablation_run.${main_java_file_name}DimensionAblationRun
  mkdir -p ${basic_path}/round_${i}_${fix_tag}/basic_info
  mv ${basic_path}/basic_info/userTypeID.txt ${basic_path}/round_${i}_${fix_tag}/basic_info &
  mv ${basic_path}/basic_info/user_to_type.txt ${basic_path}/round_${i}_${fix_tag}/basic_info &
  mv ${basic_path}/group_generated_parameters ${basic_path}/round_${i}_${fix_tag} &
  mv ${basic_path}/group_output_${fix_tag} ${basic_path}/round_${i}_${fix_tag} &
  mv ${basic_path}/basic_info/ablation_info ${basic_path}/round_${i}_${fix_tag}/basic_info &
  mv ${basic_path}/runInput_dim_ablation ${basic_path}/round_${i}_${fix_tag}
#  mv ${basic_path}/extract_result ${basic_path}/round_${i} &
#  mv ${basic_path}/${final_output_dir_name} ${basic_path}/round_${i} &
  wait
done