function y = plotDimensionChangeForError(input_basicPath, output_basicPath)
%dataset_dirs = ["1.trajectory_containing_ldp_result", "2.check_in_containing_ldp_result", "3.tlns_containing_ldp_result", "4.sin_containing_ldp_result", "5.log_containing_ldp_result"];
dataset_dirs = ["1.trajectory_dimension_ablation_result", "2.check_in_dimension_ablation_result"];
%output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"];
output_dataset_names = ["trajectory", "check_in"];

i = 0;
metric_col_index = 9; % metric_name = "AMRE";
metric_name = "AMRE";
shrink_ratio = 1;
metric_whether_log = 1;
for dir_name = dataset_dirs
    i = i + 1;
    abs_dir = strcat(input_basicPath, dir_name);
    outputFileName = fullfile(char(output_basicPath), 'figures', 'experiment_result_add2', char(strcat(output_dataset_names(i),"_dimension_change_", metric_name,"_dimension_ablation.eps")));
    plotDimensionChangeInfluenceGivenMetricForSingleDatset(abs_dir, metric_col_index, metric_name, metric_whether_log, shrink_ratio, outputFileName);
end

i = 0;
metric_col_index = 10; % metric_name = "AJSD";
metric_name = 'AJSD';
metric_whether_log = 1;
for dir_name = dataset_dirs
    i = i + 1;
    abs_dir = strcat(input_basicPath, dir_name);
    outputFileName = fullfile(char(output_basicPath), 'figures', 'experiment_result_add2', char(strcat(output_dataset_names(i),"_dimension_change_", metric_name,"_dimension_ablation.eps")));
    plotDimensionChangeInfluenceGivenMetricForSingleDatset(abs_dir, metric_col_index, metric_name, metric_whether_log, shrink_ratio, outputFileName);
end