#!/bin/bash
dataset_dir_name="T-drive_Taxi_Trajectories";
main_java_file_name="Trajectory"
final_output_dir_name="1.trajectory_result"
position_file_name="cell.txt"
basic_path="../0.dataset/$dataset_dir_name";
rm -rf ${basic_path}/basic_info/userTypeID.txt &
rm -rf ${basic_path}/basic_info/user_to_type.txt &
rm -rf ${basic_path}/group_generated_parameters &
rm -rf ${basic_path}/group_output &
rm -rf ${basic_path}/extract_result &
rm -rf ${basic_path}/${final_output_dir_name} &