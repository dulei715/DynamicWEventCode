import os

from pydantic.v1.validators import max_str_int

import basic_plot_tools as bp

def plot_dimension_change_for_error(input_basic_path, output_basic_path):
    dataset_dirs = [
        "1.trajectory_dimension_ablation_result",
        "2.check_in_dimension_ablation_result"
    ]

    output_dataset_names = ["trajectory", "check_in"]
    shrink_ratio = 1

    # 创建输出目录 figures/experiment_result_add（如果不存在）
    output_dir = os.path.join(output_basic_path, 'figures', 'experiment_result_add2')
    os.makedirs(output_dir, exist_ok=True)

    # 第一轮：AMRE
    metric_col_index = 9
    metric_name = "AMRE"
    metric_whether_log = True

    for i, dir_name in enumerate(dataset_dirs):
        abs_dir = os.path.join(input_basic_path, dir_name)
        output_file_name = os.path.join(
            output_dir,
            f"{output_dataset_names[i]}_dimension_change_{metric_name}_dimension_ablation"
        )
        bp.plot_dimension_change_influence_given_metric_for_single_dataset(
            abs_dir,
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
            f"{output_dataset_names[i]}_dimension_change_{metric_name}_dimension_ablation"
        )
        bp.plot_dimension_change_influence_given_metric_for_single_dataset(
            abs_dir,
            metric_col_index,
            metric_name,
            metric_whether_log,
            shrink_ratio,
            output_file_name
        )




if __name__ == '__main__':
    input_basic_dimension_error_path = "/Users/mac/MainFiles/1.Research/dataset/3-3_dynamic_stream_pdp/5.result_dimension_ablation"
    output_basic_path = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python"

    plot_dimension_change_for_error(input_basic_dimension_error_path, output_basic_path)