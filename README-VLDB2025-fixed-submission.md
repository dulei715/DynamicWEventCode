# Data Sets
## Real data set links
*Taxi* Data set (T-Drive trajectory data sample): [https://www.microsoft.com/en-us/research/publication/t-drive-trajectory-data-sample/](https://www.microsoft.com/en-us/research/publication/t-drive-trajectory-data-sample/).

*Foursquare* Data set (Global-scale Check-in Dataset): [https://sites.google.com/site/yangdingqi/home/foursquare-dataset](https://sites.google.com/site/yangdingqi/home/foursquare-dataset).

# Run Process 

## Pre-process
1. Create paths `${basic_path}/0.dataset/${dataset_name}` where `${basic_path}` is any fixed path setting by yourself, `${dataset_name}` is any element in {`T-drive_Taxi_Trajectories`, `CheckIn_dataset_TIST2015`, `TLNS`, `Sin`, `Log`}. 
2. Download *Taxi* and *Foursquare* data sets into paths `${basic_path}/0.dataset/T-drive_Taxi_Trajectories` and `${basic_path}/0.dataset/CheckIn_dataset_TIST2015`, respectively.
3. Copy files in `deployment` to path `${basic_path/deployment}` and direct to `${basic_path}/deployment`.
4. Pre-handle real data sets (*Taxi* and *Foursquare*) respectively by: 
	+ Run script `initialize_trajectory_run.sh`.
	+ Run script `initialize_check_in_run.sh`.


## Repeat run for real data sets 

* Run for *Taxi*:
	* run script `trajectory_containing_ldp_repeat_run.sh`
	* run script `trajectory_internal_repeat_run.sh`
	* run script `trajectory_serial_repeat_run.sh`
* Run for *Foursquare*: 
	* run script `check_in_containing_ldp_repeat_run.sh`
	* run script `check_in_internal_repeat_run.sh`
	* run script `check_in_serial_repeat_run.sh`

## Repeat initialize and run for synthetic data sets (VLDB 2025 version 1.0)

* Run for *TLns*:
	* run script `tlns_containing_ldp_repeat_run.sh`
	* run script `tlns_internal_repeat_run.sh`
	* run script `tlns_serial_repeat_run.sh`
* Run for *Sin*: 
	* run script `sin_containing_ldp_repeat_run.sh`
	* run script `sin_internal_repeat_run.sh`
	* run script `sin_serial_repeat_run.sh`
* Run for *Log*: 
	* run script `log_containing_ldp_repeat_run.sh`
	* run script `log_internal_repeat_run.sh`
	* run script `log_serial_repeat_run.sh`


## Combine
* Run for *Taxi*:
	* run script `combine_trajectory_containing_ldp_for_all_rounds.sh`
	* run script `combine_trajectory_internal_for_all_rounds.sh`
* Run for *Foursquare*: 
	* run script `combine_check_in_containing_ldp_for_all_rounds.sh`
	* run script `combine_check_in_internal_for_all_rounds.sh`
* Run for *TLns*:
	* run script `combine_tlns_containing_ldp_for_all_rounds.sh`
	* run script `combine_tlns_internal_for_all_rounds.sh`
* Run for *Sin*: 
	* run script `combine_sin_containing_ldp_for_all_rounds.sh`
	* run script `combine_sin_internal_for_all_rounds.sh`
* Run for *Log*: 
	* run script `combine_log_containing_ldp_for_all_rounds.sh`
	* run script `combine_log_internal_for_all_rounds.sh`
	
The final results are record in `${basic_path}/4.result_containing_ldp`, `${basic_path}/2.result_internal` and `${basic_path}/3.result_time_cost`
	

# Draw Results
## Initialize MATLAB (2017a)
1. Direct to `${project_path}/matlab_code/experiment_result`.
2. Run function `init_params()`.
3. Create directory `${output_basic_path}/fig/experiment_result` to record the experiment results where `${output_basic_path}` is  any fixed path setting by yourself.
## Plot Results
* Run function `draw_bar()` to get the bar of all figures for internal parts.
* Run function `drawRatioChangeWithTwoWSize(${basic_path}/1.result_internal,${output_basic_path})`.
* Run function `drawRatioChangeWithTwoBudget(${basic_path}/1.result_internal,${output_basic_path})`.
* Run function `draw_bar2()` to get the bar of all figures.
* Run function `plotBudgetChangeForError(${basic_path}/1.result,${output_basic_path})`.
* Run function `plotWindowSizeChangeForError(${basic_path}/1.result,${output_basic_path})`.
* Run function `plotBudgetChangeForTime(${basic_path}/1.result,${output_basic_path})`.
* Run function `plotWindowSizeChangeForTime(${basic_path}/1.result,${output_basic_path})`.
