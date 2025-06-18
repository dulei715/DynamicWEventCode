import os

def plotBudgetChangeForError(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_containing_ldp_result",
        "2.check_in_containing_ldp_result",
        "3.tlns_containing_ldp_result",
        "4.sin_containing_ldp_result",
        "5.log_containing_ldp_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_window_size = 120
    shrink_ratio = 1

    # 创建输出目录 figures/experiment_result_add（如果不存在）
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add')
    os.makedirs(output_dir, exist_ok=True)

    # 第一轮：AMRE
    metric_col_index = 9
    metric_name = "AMRE"
    metric_whether_log = True

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_budget_change_{metric_name}_containing_ldp.pdf"
        )
        plotBudgetChangeInfluenceGivenMetricForSingleDatset(
            abs_dir,
            default_window_size,
            metric_col_index,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )

    # 第二轮：AJSD
    metric_col_index = 10
    metric_name = "AJSD"
    metric_whether_log = True

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_budget_change_{metric_name}_containing_ldp.pdf"
        )
        plotBudgetChangeInfluenceGivenMetricForSingleDatset(
            abs_dir,
            default_window_size,
            metric_col_index,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )
