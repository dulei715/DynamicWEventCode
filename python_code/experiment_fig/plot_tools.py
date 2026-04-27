import os

from pydantic.v1.validators import max_str_int

import basic_plot_tools as bp

def plot_budget_change_for_error(input_basic_path, output_basic_path):
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
            f"{output_dataset_names[i]}_budget_change_{metric_name}_containing_ldp"
        )
        bp.plot_budget_change_influence_given_metric_for_single_dataset(
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
            f"{output_dataset_names[i]}_budget_change_{metric_name}_containing_ldp"
        )
        bp.plot_budget_change_influence_given_metric_for_single_dataset(
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
        "1.trajectory_containing_ldp_result",
        "2.check_in_containing_ldp_result",
        "3.tlns_containing_ldp_result",
        "4.sin_containing_ldp_result",
        "5.log_containing_ldp_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_budget = 0.6
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
            f"{output_dataset_names[i]}_window_size_change_{metric_name}_containing_ldp"
        )
        bp.plot_window_size_change_influence_given_metric_for_single_dataset(
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
            f"{output_dataset_names[i]}_window_size_change_{metric_name}_containing_ldp"
        )
        bp.plot_window_size_change_influence_given_metric_for_single_dataset(
            abs_dir,
            default_budget,
            metric_col_index,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )


def plot_budget_change_for_time(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_time_cost_result",
        "2.check_in_time_cost_result",
        "3.tlns_time_cost_result",
        "4.sin_time_cost_result",
        "5.log_time_cost_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_window_size = 120

    metric_col_index = 4  # metric_name = "Time Cost"
    metric_name = "running time (s)"
    metric_file_name = "running_time"
    shrink_ratio = 0.001
    metric_whether_log = False

    # 创建输出目录
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig')
    os.makedirs(output_dir, exist_ok=True)

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig',
                                        f"{output_dataset_names[i]}_budget_change_{metric_file_name}")

        bp.plot_basic_budget_time_cost(abs_dir, default_window_size, metric_col_index, metric_name, metric_whether_log,
                                shrink_ratio, output_file_name)
def plot_window_size_change_for_time(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_time_cost_result",
        "2.check_in_time_cost_result",
        "3.tlns_time_cost_result",
        "4.sin_time_cost_result",
        "5.log_time_cost_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_budget = 0.6

    metric_col_index = 4  # metric_name = "Time Cost"
    metric_name = "running time (s)"
    metric_file_name = "running_time"
    shrink_ratio = 0.001
    metric_whether_log = False

    # 创建输出目录
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig')
    os.makedirs(output_dir, exist_ok=True)

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig',
                                        f"{output_dataset_names[i]}_window_size_change_{metric_file_name}")

        bp.plot_basic_window_size_time_cost(abs_dir, default_budget, metric_col_index, metric_name, metric_whether_log,
                                shrink_ratio, output_file_name)
def plot_budget_change_for_average_time(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_time_cost_result",
        "2.check_in_time_cost_result",
        "3.tlns_time_cost_result",
        "4.sin_time_cost_result",
        "5.log_time_cost_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_window_size = 120

    metric_col_index = 4  # metric_name = "Time Cost"
    metric_name = "average time (s)"
    metric_file_name = "running_time"
    shrink_ratio = 0.001
    metric_whether_log = False

    # 创建输出目录
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig')
    os.makedirs(output_dir, exist_ok=True)

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig',
                                        f"{output_dataset_names[i]}_budget_change_{metric_file_name}")

        bp.plot_basic_budget_time_cost_average(abs_dir, default_window_size, metric_col_index, metric_name, metric_whether_log,
                                shrink_ratio, output_file_name)
def plot_window_size_change_for_average_time(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_time_cost_result",
        "2.check_in_time_cost_result",
        "3.tlns_time_cost_result",
        "4.sin_time_cost_result",
        "5.log_time_cost_result"
    ]

    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]
    default_budget = 0.6

    metric_col_index = 4  # metric_name = "Time Cost"
    metric_name = "average time (s)"
    metric_file_name = "running_time"
    shrink_ratio = 0.001
    metric_whether_log = False

    # 创建输出目录
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig')
    os.makedirs(output_dir, exist_ok=True)

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(output_basic_path, 'figures', 'experiment_result_add', 'time_cost_fig',
                                        f"{output_dataset_names[i]}_window_size_change_{metric_file_name}")

        bp.plot_basic_window_size_time_cost_average(abs_dir, default_budget, metric_col_index, metric_name, metric_whether_log,
                                shrink_ratio, output_file_name)


def draw_ratio_change_with_two_budget(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_internal_result",
        "2.check_in_internal_result",
        "3.tlns_internal_result",
        "4.sin_internal_result",
        "5.log_internal_result"
    ]
    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)

        # 构建输出文件名
        output_dir = os.path.join(output_basic_path, 'fig', 'experiment_result')
        output_file_name = os.path.join(output_dir, f"{output_dataset_names[i]}_ratio_change_two_budget")

        # 确保输出目录存在
        os.makedirs(output_dir, exist_ok=True)

        # 调用已实现的绘图函数
        bp.draw_ratio_change_with_two_budget_for_single_dataset_except_dynamic(abs_dir, output_file_name)


def draw_ratio_change_with_two_w_size(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_internal_result",
        "2.check_in_internal_result",
        "3.tlns_internal_result",
        "4.sin_internal_result",
        "5.log_internal_result"
    ]
    output_dataset_names = ["trajectory", "check_in", "tlns", "sin", "log"]

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)

        # 构建输出路径和文件名
        output_dir = os.path.join(output_basic_path, 'fig', 'experiment_result')
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_ratio_change_two_w_size"
        )

        # 确保输出目录存在
        os.makedirs(output_dir, exist_ok=True)

        # 调用已实现的绘图函数
        bp.draw_ratio_change_with_two_w_size_for_single_dataset_except_dynamic(abs_dir, output_file_name)

if __name__ == '__main__':
    input_basic_user_ratio = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp/2.result_internal"
    input_basic_time_path = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp/3.result_time_cost"
    input_basic_error_path = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp/4.result_containing_ldp"
    output_basic_path = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python"

    plot_budget_change_for_error(input_basic_error_path, output_basic_path)
    plot_window_size_change_for_error(input_basic_error_path, output_basic_path)
    # plot_budget_change_for_time(input_basic_time_path, output_basic_path)
    # plot_window_size_change_for_time(input_basic_time_path, output_basic_path) # except dynamic
    # plot_budget_change_for_average_time(input_basic_time_path, output_basic_path) # except dynamic
    # plot_window_size_change_for_average_time(input_basic_time_path, output_basic_path)
    # draw_ratio_change_with_two_budget(input_basic_user_ratio, output_basic_path)
    # draw_ratio_change_with_two_w_size(input_basic_user_ratio, output_basic_path)