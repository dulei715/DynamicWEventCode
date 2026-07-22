import os

from pydantic.v1.validators import max_str_int

import basic_plot_tools_only_for_error_detals as bp

def plot_budget_change_for_error(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_containing_error_details_result",
        "2.check_in_containing_error_details_result",
        "3.tlns_containing_error_details_result",
        "4.sin_containing_error_details_result",
        "5.log_containing_error_details_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_window_size = 120
    shrink_ratio = 1

    # 创建输出目录 figures/experiment_result_add（如果不存在）
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add5')
    os.makedirs(output_dir, exist_ok=True)

    # metric_col_index = 9
    metric_simple_name = r"error"
    metric_name = r"$\overline{err}_{OBS}$"
    # metric_name = r"$Mean Error Component$"
    metric_whether_log = True

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_budget_change_{metric_simple_name}_containing_error_details"
        )
        bp.plot_budget_change_influence_given_metric_for_single_dataset_all_columns_containing_error_details(
            abs_dir,
            default_window_size,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )






if __name__ == '__main__':
    basic_path = '/Users/admin/MainFiles/1.Research/dataset'
    input_basic_error_containing_error_details_path = basic_path + "/3_stream_dp/8.result_containing_error_details"
    output_basic_path = "/Users/admin/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python"

    plot_budget_change_for_error(input_basic_error_containing_error_details_path, output_basic_path)