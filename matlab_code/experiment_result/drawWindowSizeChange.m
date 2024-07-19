function y = drawWindowSizeChange(input_basicPath, output_basicPath)
dataset_dirs = ["1.trajectory_result", "2.checkIn_result", "3.tlns_result", "4.sin_result", "5.log_result"];
output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"];
default_budget = 1.5;
i = 0;
for dir_name = dataset_dirs
    i = i + 1;
    abs_dir = strcat(input_basicPath, dir_name);
    %outputFileName = strcat(output_basicPath, 'fig/', 'experiment_result/', output_dataset_names(i), "_budget_change.eps");
    outputFileName = fullfile(char(output_basicPath), 'fig', 'experiment_result', char(strcat(output_dataset_names(i),"_window_size_change.eps")));
    drawWindowSizeChangeForSingleDatasetExceptDynamic(abs_dir, default_budget, outputFileName);
end