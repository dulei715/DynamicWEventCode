function y = plotWindowSizeChange(input_basicPath, output_basicPath)
dataset_dirs = ["1.trajectory_containing_ldp_result", "2.check_in_containing_ldp_result", "3.tlns_containing_ldp_result", "4.sin_containing_ldp_result", "5.log_containing_ldp_result"];
output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"];
default_budget = 0.6;

i = 0;
metric_col_index = 9; % 
metric_name = "AMRE";
metric_whether_log = 1;
for dir_name = dataset_dirs
    i = i + 1;
    abs_dir = strcat(input_basicPath, dir_name);
    outputFileName = fullfile(char(output_basicPath), 'figures', 'experiment_result_add', char(strcat(output_dataset_names(i),"_window_size_change_", metric_name,"_containing_ldp.eps")));
    plotWindowSizeChangeInfluenceGivenMetricForSingleDatset(abs_dir, default_budget, metric_col_index, metric_name, metric_whether_log, outputFileName);
end

i = 0;
metric_col_index = 10; % 
metric_name = "AJSD";
metric_whether_log = 1;
for dir_name = dataset_dirs
    i = i + 1;
    abs_dir = strcat(input_basicPath, dir_name);
    outputFileName = fullfile(char(output_basicPath), 'fig', 'experiment_result_add', char(strcat(output_dataset_names(i),"_window_size_change_", metric_name,"_containing_ldp.eps")));
    plotWindowSizeChangeInfluenceGivenMetricForSingleDatset(abs_dir, default_budget, metric_col_index, metric_name, metric_whether_log, outputFileName);
end