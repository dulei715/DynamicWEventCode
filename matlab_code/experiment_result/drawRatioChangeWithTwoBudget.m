function y = drawRatioChangeWithTwoBudget(input_basicPath, output_basicPath)
dataset_dirs = ["1.trajectory_internal_result", "2.check_in_internal_result", "3.tlns_internal_result", "4.sin_internal_result", "5.log_internal_result"];
output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"];
i = 0;
for dir_name = dataset_dirs
    i = i + 1;
    abs_dir = strcat(input_basicPath, dir_name);
    %outputFileName = strcat(output_basicPath, 'fig/', 'experiment_result/', output_dataset_names(i), "_budget_change.eps");
    outputFileName = fullfile(char(output_basicPath), 'fig', 'experiment_result', char(strcat(output_dataset_names(i),"_ratio_change_two_budget.eps")));
    %drawBudgetChangeForSingleDatset(abs_dir, default_window_size, outputFileName);
    drawRatioChangeWithTwoBudgetForSingleDatsetExceptDynamic(abs_dir, outputFileName);
end