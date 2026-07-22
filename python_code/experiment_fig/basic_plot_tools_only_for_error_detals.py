import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
# from matplotlib.pyplot import ylabel

import tools.common_utils as cutils
import tools.special_utils as sutils
# from tools.special_utils import extract_budget_and_window_size_from_dir_name

figure_MarkerSize = 24
figure_FontSize_X = 40
figure_FontSize_Y = 40
font_size = 40

linewidth = 3
markeredgewidth = 3



def plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(basic_path, default_window_size, metric_col_index, metric_name, metric_whether_log, shrink_ratio, output_file_name):

    dir_names = cutils.list_dir_name(basic_path)

    x = []
    # y_bd = []
    y_ba = []
    # y_plbu = []
    # y_pbd = []
    y_pba = []
    # y_pdbd = []
    # y_pdba = []
    # y_spas = []

    for temp_name in dir_names:
        temp_budget, temp_window_size = sutils.extract_budget_and_window_size_from_dir_name(temp_name)
        if temp_window_size != default_window_size:
            continue

        data_path = os.path.join(basic_path, temp_name, 'result.txt')
        try:
            temp_table = pd.read_csv(data_path, sep=',')
        except Exception as e:
            print(f"读取失败: {data_path} -> {e}")
            continue

        x.append(temp_budget)
        getter = np.log if metric_whether_log else lambda v: v
        col = metric_col_index - 1  # Python 索引从0开始

        try:
            # y_bd.append(getter(temp_table.iloc[1, col]) * shrink_ratio)
            y_ba.append(getter(temp_table.iloc[1, col]) * shrink_ratio)
            # y_pbd.append(getter(temp_table.iloc[3, col]) * shrink_ratio)
            y_pba.append(getter(temp_table.iloc[2, col]) * shrink_ratio)
            # y_pdbd.append(getter(temp_table.iloc[5, col]) * shrink_ratio)
            # y_pdba.append(getter(temp_table.iloc[6, col]) * shrink_ratio)
            # y_plbu.append(getter(temp_table.iloc[7, col]) * shrink_ratio)
            # y_spas.append(getter(temp_table.iloc[8, col]) * shrink_ratio)
        except Exception as e:
            print(f"表格读取异常（{data_path}）：{e}")

    # 排序
    x = np.array(x)
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    # y_bd = np.array(y_bd)[sorted_indices]
    y_ba = np.array(y_ba)[sorted_indices]
    # y_pbd = np.array(y_pbd)[sorted_indices]
    y_pba = np.array(y_pba)[sorted_indices]
    # y_pdbd = np.array(y_pdbd)[sorted_indices]
    # y_pdba = np.array(y_pdba)[sorted_indices]
    # y_plbu = np.array(y_plbu)[sorted_indices]
    # y_spas = np.array(y_spas)[sorted_indices]

    # 图像配置
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'
    plt.rcParams['font.size'] = font_size
    # figure_MarkerSize = 24
    # figure_FontSize_X = 28
    # figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    # linewidth = 3
    # markeredgewidth = 3

    # ax.plot(x, y_bd, 'ks-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BD')
    ax.plot(x, y_ba, 'mo-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BA')
    # ax.plot(x, y_plbu, color='teal', linestyle='--', marker='*', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PLBU')
    # ax.plot(x, y_spas, color='limegreen', linestyle='-.', marker='D', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='SPAS')
    # ax.plot(x, y_pbd, 'bs--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBD')
    ax.plot(x, y_pba, 'go--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBA')
    # ax.plot(x, y_pdbd, 'cs:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBD')
    # ax.plot(x, y_pdba, 'ro:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBA')

    ax.set_xlabel(r"$\mathcal{E}$", fontsize=figure_FontSize_X)
    ylabel = f"ln({metric_name})" if metric_whether_log else metric_name
    # y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba, y_plbu, y_spas])
    y_all = np.concatenate([y_ba, y_pba])
    ax.set_ylim(top=np.max(y_all) * 1.07)  # 提高 7% 上限，避免遮挡
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    ax.set_xticks(x)
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)

    ax.tick_params(axis='both', labelsize=figure_FontSize_X)

    # 图例
    # legend_names = ["BD", "BA", "PLBU", "SPAS", "PBD", "PBA", "PDBD", "PDBA"]
    legend_names = ["BA", "PBA"]
    # ax.legend(legend_names, loc='best', fontsize=14, frameon=False)

    plt.tight_layout()

    # plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()
def plot_budget_change_influence_given_metric_for_single_dataset_all_columns_containing_error_details(
        basic_path, default_window_size,
        metric_name, metric_whether_log, shrink_ratio, output_file_name):

    dir_names = cutils.list_dir_name(basic_path)

    x = []
    y_partA_error_dp = []
    y_partB_error_dp = []
    y_partA_error_sample_var = []
    y_partB_error_sample_var = []
    y_partA_count_var = []
    y_partB_count_var = []
    y_partA_bias_square = []
    y_partB_bias_square = []

    for temp_name in dir_names:
        temp_budget, temp_window_size = sutils.extract_budget_and_window_size_from_dir_name(temp_name)
        if temp_window_size != default_window_size:
            continue

        data_path = os.path.join(basic_path, temp_name, 'result.txt')
        try:
            temp_table = pd.read_csv(data_path, sep=',')
        except Exception as e:
            print(f"读取失败: {data_path} -> {e}")
            continue

        metric_col_index_list = list(range(21, 29))

        x.append(temp_budget)
        getter = np.log1p if metric_whether_log else lambda v: v
        # col = metric_col_index - 1  # Python 索引从0开始


        try:
            y_partA_error_dp.append(getter(temp_table.iloc[2, metric_col_index_list[0]]) * shrink_ratio)
            y_partB_error_dp.append(getter(temp_table.iloc[2, metric_col_index_list[1]]) * shrink_ratio)
            y_partA_error_sample_var.append(getter(temp_table.iloc[2, metric_col_index_list[2]]) * shrink_ratio)
            y_partB_error_sample_var.append(getter(temp_table.iloc[2, metric_col_index_list[3]]) * shrink_ratio)
            y_partA_count_var.append(getter(temp_table.iloc[2, metric_col_index_list[4]]) * shrink_ratio)
            y_partB_count_var.append(getter(temp_table.iloc[2, metric_col_index_list[5]]) * shrink_ratio)
            y_partA_bias_square.append(getter(temp_table.iloc[2, metric_col_index_list[6]]) * shrink_ratio)
            y_partB_bias_square.append(getter(temp_table.iloc[2, metric_col_index_list[7]]) * shrink_ratio)
        except Exception as e:
            print(f"表格读取异常（{data_path}）：{e}")

    # 排序
    x = np.array(x)
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    y_partA_error_dp = np.array(y_partA_error_dp)[sorted_indices]
    y_partB_error_dp = np.array(y_partB_error_dp)[sorted_indices]
    y_partA_error_sample_var = np.array(y_partA_error_sample_var)[sorted_indices]
    y_partB_error_sample_var = np.array(y_partB_error_sample_var)[sorted_indices]
    y_partA_count_var = np.array(y_partA_count_var)[sorted_indices]
    y_partB_count_var = np.array(y_partB_count_var)[sorted_indices]
    y_partA_bias_square = np.array(y_partA_bias_square)[sorted_indices]
    y_partB_bias_square = np.array(y_partB_bias_square)[sorted_indices]

    # 图像配置
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'
    plt.rcParams['font.size'] = font_size
    # figure_MarkerSize = 24
    # figure_FontSize_X = 28
    # figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    # linewidth = 3
    # markeredgewidth = 3

    ax.plot(x, y_partA_error_dp, 'ks-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BD')
    ax.plot(x, y_partB_error_dp, 'mo-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BA')
    # ax.plot(x, y_partA_error_sample_var, color='teal', linestyle='-.', marker='*', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PLBU')
    # ax.plot(x, y_partB_error_sample_var, color='limegreen', linestyle='-.', marker='D', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='SPAS')
    ax.plot(x, y_partA_count_var, 'bs--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBD')
    ax.plot(x, y_partB_count_var, 'go--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBA')
    ax.plot(x, y_partA_bias_square, 'cs:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBD')
    ax.plot(x, y_partB_bias_square, 'ro:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBA')

    ax.set_xlabel(r"$\mathcal{E}$", fontsize=figure_FontSize_X)
    ylabel = f"ln(1+{metric_name})" if metric_whether_log else metric_name
    # y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba, y_plbu, y_spas])
    # y_all = np.concatenate([y_partA_error_dp, y_partB_error_dp,
    #                         y_partA_error_sample_var, y_partB_error_sample_var,
    #                         y_partA_count_var, y_partB_count_var,
    #                         y_partA_bias_square, y_partB_bias_square])
    y_all = np.concatenate([y_partA_error_dp, y_partB_error_dp,
                            y_partA_count_var, y_partB_count_var,
                            y_partA_bias_square, y_partB_bias_square])
    ax.set_ylim(top=np.max(y_all) * 1.07)  # 提高 7% 上限，避免遮挡
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    ax.set_xticks(x)
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)

    ax.tick_params(axis='both', labelsize=figure_FontSize_X)

    # 图例
    # legend_names = ["BD", "BA", "PLBU", "SPAS", "PBD", "PBA", "PDBD", "PDBA"]
    legend_names = ["Part-A Error (DP)", "Part-B Error (DP)",
                    "Part-A Sample Var", "Part-B Sample Var",
                    "Part-A Count Var", "Part-B Count Var",
                    "Part-A Bias Square", "Part-B Bias Square"]
    # ax.legend(legend_names, loc='best', fontsize=14, frameon=False)

    plt.tight_layout()

    plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()
def plot_BA_budget_change_influence_given_metric_for_single_dataset_all_columns_containing_error_details(
        basic_path, default_window_size,
        metric_name, metric_whether_log, shrink_ratio, output_file_name):

    dir_names = cutils.list_dir_name(basic_path)

    x = []
    y_partA_error_dp = []
    y_partB_error_dp = []

    for temp_name in dir_names:
        temp_budget, temp_window_size = sutils.extract_budget_and_window_size_from_dir_name(temp_name)
        if temp_window_size != default_window_size:
            continue

        data_path = os.path.join(basic_path, temp_name, 'result.txt')
        try:
            temp_table = pd.read_csv(data_path, sep=',')
        except Exception as e:
            print(f"读取失败: {data_path} -> {e}")
            continue

        metric_col_index_list = list(range(21, 29))

        x.append(temp_budget)
        getter = np.log1p if metric_whether_log else lambda v: v
        # col = metric_col_index - 1  # Python 索引从0开始


        try:
            y_partA_error_dp.append(getter(temp_table.iloc[1, metric_col_index_list[0]]) * shrink_ratio)
            y_partB_error_dp.append(getter(temp_table.iloc[1, metric_col_index_list[1]]) * shrink_ratio)
        except Exception as e:
            print(f"表格读取异常（{data_path}）：{e}")

    # 排序
    x = np.array(x)
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    y_partA_error_dp = np.array(y_partA_error_dp)[sorted_indices]
    y_partB_error_dp = np.array(y_partB_error_dp)[sorted_indices]

    # 图像配置
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'
    plt.rcParams['font.size'] = font_size
    # figure_MarkerSize = 24
    # figure_FontSize_X = 28
    # figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    # linewidth = 3
    # markeredgewidth = 3

    ax.plot(x, y_partA_error_dp, 'ks-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BD')
    ax.plot(x, y_partB_error_dp, 'mo-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BA')

    ax.set_xlabel(r"$\mathcal{E}$", fontsize=figure_FontSize_X)
    ylabel = f"ln(1+{metric_name})" if metric_whether_log else metric_name
    # y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba, y_plbu, y_spas])
    # y_all = np.concatenate([y_partA_error_dp, y_partB_error_dp,
    #                         y_partA_error_sample_var, y_partB_error_sample_var,
    #                         y_partA_count_var, y_partB_count_var,
    #                         y_partA_bias_square, y_partB_bias_square])
    y_all = np.concatenate([y_partA_error_dp, y_partB_error_dp])
    ax.set_ylim(top=np.max(y_all) * 1.07)  # 提高 7% 上限，避免遮挡
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    ax.set_xticks(x)
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)

    ax.tick_params(axis='both', labelsize=figure_FontSize_X)

    # 图例
    # legend_names = ["BD", "BA", "PLBU", "SPAS", "PBD", "PBA", "PDBD", "PDBA"]
    legend_names = ["Part-A Error (DP)", "Part-B Error (DP)",
                    "Part-A Sample Var", "Part-B Sample Var",
                    "Part-A Count Var", "Part-B Count Var",
                    "Part-A Bias Square", "Part-B Bias Square"]
    # ax.legend(legend_names, loc='best', fontsize=14, frameon=False)

    plt.tight_layout()

    plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()


if __name__ == '__main0__':
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/1.trajectory_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/2.check_in_containing_error_details_result"
    error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/3.tlns_containing_error_details_result"

    default_window_size = 120
    default_privacy_budget = 0.6
    # time_col_index = 4
    # ajsd_col_index = 10

    # time_y_name = "running time (s)"
    # tjsd_y_name = "AJSD"
    partA_error_DP_col_index = 22
    partB_error_DP_col_index = 23
    partA_error_sample_var_col_index = 24
    partB_error_sample_var_col_index = 25
    partA_count_var_col_index = 26
    partB_count_var_col_index = 27
    partA_bias_square_col_index = 28
    partB_bias_square_col_index = 29

    partA_error_dp_y_name = "Part-A Error (DP)"
    partB_error_dp_y_name = "Part-B Error (DP)"
    partA_error_sample_var_y_name = "Part-A Sample Var"
    partB_error_sample_var_y_name = "Part-B Sample Var"
    partA_count_var_y_name = "Part-A Count Var"
    partB_count_var_y_name = "Part-B Count Var"
    partA_bias_square_y_name = "Part-A Bias Square"
    partB_bias_square_y_name = "Part-B Bias Square"

    # metric_whether_log_time = False
    metric_whether_log_error = False
    shrink_ratio_time = 0.001
    shrink_ratio_error = 1
    output_file_name = "/Users/mac/MainFiles/temp"

    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partA_error_DP_col_index, partA_error_dp_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partB_error_DP_col_index, partB_error_dp_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partA_error_sample_var_col_index, partA_error_sample_var_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partA_error_sample_var_col_index, partA_error_sample_var_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partB_error_sample_var_col_index, partB_error_sample_var_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partB_error_sample_var_col_index, partB_error_sample_var_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partA_count_var_col_index, partA_count_var_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partB_count_var_col_index, partB_count_var_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partA_bias_square_col_index, partA_bias_square_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset_single_column_containing_error_details(
        error_details_basic_path, default_window_size,
        partB_bias_square_col_index, partB_bias_square_y_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)

    # plot_basic_budget_time_cost(time_cost_basic_path, default_window_size, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_basic_window_size_time_cost(time_cost_basic_path, default_privacy_budget, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_budget_change_influence_given_metric_for_single_dataset(influence_basic_path, default_window_size, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # plot_window_size_change_influence_given_metric_for_single_dataset(influence_basic_path, default_privacy_budget, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # draw_ratio_change_with_two_budget_for_single_dataset_except_dynamic(internal_basic_path, output_file_name)
    # draw_ratio_change_with_two_w_size_for_single_dataset_except_dynamic(internal_basic_path, output_file_name)
if __name__ == '__main__':
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/1.trajectory_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/2.check_in_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/3.tlns_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/4.sin_containing_error_details_result"
    error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/5.log_containing_error_details_result"

    default_window_size = 120
    default_privacy_budget = 0.6
    # time_col_index = 4
    # ajsd_col_index = 10


    # metric_whether_log_time = False
    # metric_whether_log_error = False
    metric_whether_log_error = True
    col_name = "AMRE"
    shrink_ratio_time = 0.001
    shrink_ratio_error = 1
    output_file_name = "/Users/mac/MainFiles/temp"

    plot_budget_change_influence_given_metric_for_single_dataset_all_columns_containing_error_details(
        error_details_basic_path, default_window_size,
        col_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)

    # plot_basic_budget_time_cost(time_cost_basic_path, default_window_size, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_basic_window_size_time_cost(time_cost_basic_path, default_privacy_budget, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_budget_change_influence_given_metric_for_single_dataset(influence_basic_path, default_window_size, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # plot_window_size_change_influence_given_metric_for_single_dataset(influence_basic_path, default_privacy_budget, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # draw_ratio_change_with_two_budget_for_single_dataset_except_dynamic(internal_basic_path, output_file_name)
    # draw_ratio_change_with_two_w_size_for_single_dataset_except_dynamic(internal_basic_path, output_file_name)
if __name__ == '__main2__':
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/1.trajectory_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/2.check_in_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/3.tlns_containing_error_details_result"
    # error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/4.sin_containing_error_details_result"
    error_details_basic_path = "/Users/admin/MainFiles/1.Research/dataset/3_stream_dp/8.result_containing_error_details/5.log_containing_error_details_result"

    default_window_size = 120
    default_privacy_budget = 0.6
    # time_col_index = 4
    # ajsd_col_index = 10


    # metric_whether_log_time = False
    # metric_whether_log_error = False
    metric_whether_log_error = True
    col_name = "AMRE"
    shrink_ratio_time = 0.001
    shrink_ratio_error = 1
    output_file_name = "/Users/mac/MainFiles/temp"

    plot_BA_budget_change_influence_given_metric_for_single_dataset_all_columns_containing_error_details(
        error_details_basic_path, default_window_size,
        col_name,
        metric_whether_log_error, shrink_ratio_error,
        output_file_name)

    # plot_basic_budget_time_cost(time_cost_basic_path, default_window_size, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_basic_window_size_time_cost(time_cost_basic_path, default_privacy_budget, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_budget_change_influence_given_metric_for_single_dataset(influence_basic_path, default_window_size, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # plot_window_size_change_influence_given_metric_for_single_dataset(influence_basic_path, default_privacy_budget, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # draw_ratio_change_with_two_budget_for_single_dataset_except_dynamic(internal_basic_path, output_file_name)
    # draw_ratio_change_with_two_w_size_for_single_dataset_except_dynamic(internal_basic_path, output_file_name)

