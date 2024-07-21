#!/bin/bash
dataset_dir_name="CheckIn_dataset_TIST2015";
main_java_file_name="CheckIn"
final_output_dir_name="2.check_in_result"
position_file_name="country.txt"
basic_path="../0.dataset/$dataset_dir_name";
# rm -rf ${basic_path}/basic_info/user.txt &
rm -rf ${basic_path}/basic_info/userTypeID.txt &
rm -rf ${basic_path}/basic_info/user_to_type.txt &
# rm -rf ${basic_path}/runInput &
rm -rf ${basic_path}/group_generated_parameters &
rm -rf ${basic_path}/group_output &
rm -rf ${basic_path}/extract_result &
rm -rf ${basic_path}/${final_output_dir_name} &