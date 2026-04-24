import os

from pydantic.v1.validators import max_str_int

import basic_plot_tools as bp

def plot_budget_change_for_error(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_containing_spas_result",
        "2.check_in_containing_spas_result",
        "3.tlns_containing_spas_result",
        "4.sin_containing_spas_result",
        "5.log_containing_spas_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_window_size = 120
    shrink_ratio = 1

    # 创建输出目录 figures/experiment_result_add（如果不存在）
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add3')
    os.makedirs(output_dir, exist_ok=True)

    # 第一轮：AMRE
    metric_col_index = 9
    metric_name = "AMRE"
    metric_whether_log = True

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_budget_change_{metric_name}_containing_spas"
        )
        bp.plot_budget_change_influence_given_metric_for_single_dataset_containing_spas(
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
            f"{output_dataset_names[i]}_budget_change_{metric_name}_containing_spas"
        )
        bp.plot_budget_change_influence_given_metric_for_single_dataset_containing_spas(
            abs_dir,
            default_window_size,
            metric_col_index,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )
def plot_window_size_change_for_error(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_containing_spas_result",
        "2.check_in_containing_spas_result",
        "3.tlns_containing_spas_result",
        "4.sin_containing_spas_result",
        "5.log_containing_spas_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_budget = 0.6
    shrink_ratio = 1

    # 创建输出目录 figures/experiment_result_add（如果不存在）
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add3')
    os.makedirs(output_dir, exist_ok=True)

    # 第一轮：AMRE
    metric_col_index = 9
    metric_name = "AMRE"
    metric_whether_log = True

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_window_size_change_{metric_name}_containing_spas"
        )
        bp.plot_window_size_change_influence_given_metric_for_single_dataset_containing_spas(
            abs_dir,
            default_budget,
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
            f"{output_dataset_names[i]}_window_size_change_{metric_name}_containing_spas"
        )
        bp.plot_window_size_change_influence_given_metric_for_single_dataset_containing_spas(
            abs_dir,
            default_budget,
            metric_col_index,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )




if __name__ == '__main__':
    input_basic_error_containing_spas_path = "/Users/mac/MainFiles/1.Research/dataset/3-3_dynamic_stream_pdp/6.result_containing_spas"
    output_basic_path = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python"

    # plot_budget_change_for_error(input_basic_error_containing_spas_path, output_basic_path)
    plot_window_size_change_for_error(input_basic_error_containing_spas_path, output_basic_path)