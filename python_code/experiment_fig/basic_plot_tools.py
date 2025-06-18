import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

import tools.common_utils as cutils
import tools.special_utils as sutils
from tools.special_utils import extract_budget_and_window_size_from_dir_name


def plot_basic_budget_time_cost(basic_path, default_window_size, metric_col_index, metric_name, metric_whether_log, shrink_ratio, output_file_name):
    dir_names = cutils.list_dir_name(basic_path)
    # print("dir_names", dir_names)
    y_bd = []
    y_ba = []
    y_pbd = []
    y_pba = []
    y_pdbd = []
    y_pdba = []

    x = []

    for temp_name in dir_names:
        temp_budget, temp_window_size = extract_budget_and_window_size_from_dir_name(temp_name)
        if temp_window_size != default_window_size:
            continue

        x.append(temp_budget)
        data_path = os.path.join(basic_path, temp_name, 'result.txt')

        try:
            temp_table = pd.read_csv(data_path, sep=',')  # 假设是 tab 分隔的 txt 文件
        except Exception as e:
            print(f"无法读取文件: {data_path}, 错误: {e}")
            continue

        if metric_whether_log:
            y_bd.append(np.log(temp_table.iloc[1, metric_col_index - 1]) * shrink_ratio)
            y_ba.append(np.log(temp_table.iloc[2, metric_col_index - 1]) * shrink_ratio)
            y_pbd.append(np.log(temp_table.iloc[3, metric_col_index - 1]) * shrink_ratio)
            y_pba.append(np.log(temp_table.iloc[4, metric_col_index - 1]) * shrink_ratio)
            y_pdbd.append(np.log(temp_table.iloc[5, metric_col_index - 1]) * shrink_ratio)
            y_pdba.append(np.log(temp_table.iloc[6, metric_col_index - 1]) * shrink_ratio)
        else:
            y_bd.append(temp_table.iloc[1, metric_col_index - 1] * shrink_ratio)
            y_ba.append(temp_table.iloc[2, metric_col_index - 1] * shrink_ratio)
            y_pbd.append(temp_table.iloc[3, metric_col_index - 1] * shrink_ratio)
            y_pba.append(temp_table.iloc[4, metric_col_index - 1] * shrink_ratio)
            y_pdbd.append(temp_table.iloc[5, metric_col_index - 1] * shrink_ratio)
            y_pdba.append(temp_table.iloc[6, metric_col_index - 1] * shrink_ratio)

    # 转换为 numpy 数组便于绘图
    x = np.array(x)
    y_bd = np.array(y_bd)
    y_ba = np.array(y_ba)
    y_pbd = np.array(y_pbd)
    y_pba = np.array(y_pba)
    y_pdbd = np.array(y_pdbd)
    y_pdba = np.array(y_pdba)

    # 排序 x 值以确保折线图顺序正确
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    y_bd = y_bd[sorted_indices]
    y_ba = y_ba[sorted_indices]
    y_pbd = y_pbd[sorted_indices]
    y_pba = y_pba[sorted_indices]
    y_pdbd = y_pdbd[sorted_indices]
    y_pdba = y_pdba[sorted_indices]

    # 设置字体样式
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'  # 使用 Computer Modern 字体渲染 LaTeX
    plt.rcParams['font.size'] = 28
    figure_MarkerSize = 24
    figure_FontSize_X = 28
    figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    linewidth = 3
    markeredgewidth = 3

    ax.plot(x, y_bd, 'ks-', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='BD')
    ax.plot(x, y_ba, 'mo-', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='BA')
    ax.plot(x, y_pbd, 'bs--', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PBD')
    ax.plot(x, y_pba, 'go--', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PBA')
    ax.plot(x, y_pdbd, 'cs:', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PDBD')
    ax.plot(x, y_pdba, 'ro:', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PDBA')

    ax.set_xlabel(r'$\mathcal{E}$', fontsize=figure_FontSize_X)

    if metric_whether_log:
        ylabel = r'ln(' + metric_name + ')'
    else:
        ylabel = metric_name
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba])
    ax.set_ylim(top=np.max(y_all) * 1.05)  # 提高 5% 上限，避免遮挡


    # ax.set_xlim(left=np.round(x[0], 1), right=x[-1])
    # ax.set_xticks(np.round(x, 1))
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)
    ax.tick_params(axis='both', which='major', labelsize=figure_FontSize_X)

    legend_names = ["BD", "BA", "PBD", "PBA", "PDBD", "PDBA"]
    # ax.legend(legend_names, loc='best', fontsize=14, prop={'family': 'Times New Roman'})

    plt.tight_layout()



    # 导出为 PDF（支持透明背景）
    # export_fig(fig, '-pdf', '-r256', '-transparent', outputFileName)
    # fig.savefig(output_file_name + '.pdf', format='pdf', bbox_inches='tight', transparent=True)
    plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()
def plot_basic_window_size_time_cost(basic_path, default_budget, metric_col_index, metric_name, metric_whether_log, shrink_ratio, output_file_name):
    dir_names = cutils.list_dir_name(basic_path)
    # print("dir_names", dir_names)
    y_bd = []
    y_ba = []
    y_pbd = []
    y_pba = []
    y_pdbd = []
    y_pdba = []

    x = []

    for temp_name in dir_names:
        temp_budget, temp_window_size = extract_budget_and_window_size_from_dir_name(temp_name)
        if temp_budget != default_budget:
            continue

        x.append(temp_window_size)
        data_path = os.path.join(basic_path, temp_name, 'result.txt')

        try:
            temp_table = pd.read_csv(data_path, sep=',')  # 假设是 tab 分隔的 txt 文件
        except Exception as e:
            print(f"无法读取文件: {data_path}, 错误: {e}")
            continue

        if metric_whether_log:
            y_bd.append(np.log(temp_table.iloc[1, metric_col_index - 1]) * shrink_ratio)
            y_ba.append(np.log(temp_table.iloc[2, metric_col_index - 1]) * shrink_ratio)
            y_pbd.append(np.log(temp_table.iloc[3, metric_col_index - 1]) * shrink_ratio)
            y_pba.append(np.log(temp_table.iloc[4, metric_col_index - 1]) * shrink_ratio)
            y_pdbd.append(np.log(temp_table.iloc[5, metric_col_index - 1]) * shrink_ratio)
            y_pdba.append(np.log(temp_table.iloc[6, metric_col_index - 1]) * shrink_ratio)
        else:
            y_bd.append(temp_table.iloc[1, metric_col_index - 1] * shrink_ratio)
            y_ba.append(temp_table.iloc[2, metric_col_index - 1] * shrink_ratio)
            y_pbd.append(temp_table.iloc[3, metric_col_index - 1] * shrink_ratio)
            y_pba.append(temp_table.iloc[4, metric_col_index - 1] * shrink_ratio)
            y_pdbd.append(temp_table.iloc[5, metric_col_index - 1] * shrink_ratio)
            y_pdba.append(temp_table.iloc[6, metric_col_index - 1] * shrink_ratio)

    # 转换为 numpy 数组便于绘图
    x = np.array(x)
    y_bd = np.array(y_bd)
    y_ba = np.array(y_ba)
    y_pbd = np.array(y_pbd)
    y_pba = np.array(y_pba)
    y_pdbd = np.array(y_pdbd)
    y_pdba = np.array(y_pdba)

    # 排序 x 值以确保折线图顺序正确
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    y_bd = y_bd[sorted_indices]
    y_ba = y_ba[sorted_indices]
    y_pbd = y_pbd[sorted_indices]
    y_pba = y_pba[sorted_indices]
    y_pdbd = y_pdbd[sorted_indices]
    y_pdba = y_pdba[sorted_indices]

    # 设置字体样式
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'  # 使用 Computer Modern 字体渲染 LaTeX
    plt.rcParams['font.size'] = 28
    figure_MarkerSize = 24
    figure_FontSize_X = 28
    figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    linewidth = 3
    markeredgewidth = 3

    ax.plot(x, y_bd, 'ks-', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='BD')
    ax.plot(x, y_ba, 'mo-', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='BA')
    ax.plot(x, y_pbd, 'bs--', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PBD')
    ax.plot(x, y_pba, 'go--', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PBA')
    ax.plot(x, y_pdbd, 'cs:', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PDBD')
    ax.plot(x, y_pdba, 'ro:', markerfacecolor='none', markeredgewidth=markeredgewidth, linewidth=linewidth, markersize=figure_MarkerSize, label='PDBA')

    ax.set_xlabel(r'$w$', fontsize=figure_FontSize_X)

    if metric_whether_log:
        ylabel = r'ln(' + metric_name + ')'
    else:
        ylabel = metric_name
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba])
    ax.set_ylim(top=np.max(y_all) * 1.05)  # 提高 5% 上限，避免遮挡

    # ax.set_xlim(left=np.round(x[0], 1), right=x[-1])
    # ax.set_xticks(np.round(x, 1))
    ax.set_xticks(x)
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)
    # ax.set_xlim(x[0] - 0.5, x[-1] + 0.5)  # 留一点边距更美观
    ax.tick_params(axis='both', which='major', labelsize=figure_FontSize_X)

    legend_names = ["BD", "BA", "PBD", "PBA", "PDBD", "PDBA"]
    # ax.legend(legend_names, loc='best', fontsize=14, prop={'family': 'Times New Roman'})

    plt.tight_layout()



    # 导出为 PDF（支持透明背景）
    # export_fig(fig, '-pdf', '-r256', '-transparent', outputFileName)
    # fig.savefig(output_file_name + '.pdf', format='pdf', bbox_inches='tight', transparent=True)
    plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()


def plot_budget_change_influence_given_metric_for_single_dataset(basic_path, default_window_size, metric_col_index, metric_name, metric_whether_log, shrink_ratio, output_file_name):

    dir_names = cutils.list_dir_name(basic_path)

    x = []
    y_bd = []
    y_ba = []
    y_plbu = []
    y_pbd = []
    y_pba = []
    y_pdbd = []
    y_pdba = []

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
            y_bd.append(getter(temp_table.iloc[1, col]) * shrink_ratio)
            y_ba.append(getter(temp_table.iloc[2, col]) * shrink_ratio)
            y_pbd.append(getter(temp_table.iloc[3, col]) * shrink_ratio)
            y_pba.append(getter(temp_table.iloc[4, col]) * shrink_ratio)
            y_pdbd.append(getter(temp_table.iloc[5, col]) * shrink_ratio)
            y_pdba.append(getter(temp_table.iloc[6, col]) * shrink_ratio)
            y_plbu.append(getter(temp_table.iloc[7, col]) * shrink_ratio)
        except Exception as e:
            print(f"表格读取异常（{data_path}）：{e}")

    # 排序
    x = np.array(x)
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    y_bd = np.array(y_bd)[sorted_indices]
    y_ba = np.array(y_ba)[sorted_indices]
    y_pbd = np.array(y_pbd)[sorted_indices]
    y_pba = np.array(y_pba)[sorted_indices]
    y_pdbd = np.array(y_pdbd)[sorted_indices]
    y_pdba = np.array(y_pdba)[sorted_indices]
    y_plbu = np.array(y_plbu)[sorted_indices]

    # 图像配置
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'
    plt.rcParams['font.size'] = 28
    figure_MarkerSize = 24
    figure_FontSize_X = 28
    figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    linewidth = 3
    markeredgewidth = 3

    ax.plot(x, y_bd, 'ks-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BD')
    ax.plot(x, y_ba, 'mo-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BA')
    ax.plot(x, y_plbu, 'c*--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PLBU')
    ax.plot(x, y_pbd, 'bs--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBD')
    ax.plot(x, y_pba, 'go--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBA')
    ax.plot(x, y_pdbd, 'cs:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBD')
    ax.plot(x, y_pdba, 'ro:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBA')

    ax.set_xlabel(r"$\mathcal{E}$", fontsize=figure_FontSize_X)
    ylabel = f"ln({metric_name})" if metric_whether_log else metric_name
    y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba, y_plbu])
    ax.set_ylim(top=np.max(y_all) * 1.07)  # 提高 7% 上限，避免遮挡
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    ax.set_xticks(x)
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)

    ax.tick_params(axis='both', labelsize=figure_FontSize_X)

    # 图例
    legend_names = ["BD", "BA", "PLBU", "PBD", "PBA", "PDBD", "PDBA"]
    # ax.legend(legend_names, loc='best', fontsize=14, frameon=False)

    plt.tight_layout()

    plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()
def plot_window_size_change_influence_given_metric_for_single_dataset(basic_path, default_budget, metric_col_index, metric_name, metric_whether_log, shrink_ratio, output_file_name):

    dir_names = cutils.list_dir_name(basic_path)

    x = []
    y_bd = []
    y_ba = []
    y_plbu = []
    y_pbd = []
    y_pba = []
    y_pdbd = []
    y_pdba = []

    for temp_name in dir_names:
        temp_budget, temp_window_size = sutils.extract_budget_and_window_size_from_dir_name(temp_name)
        if temp_budget != default_budget:
            continue

        data_path = os.path.join(basic_path, temp_name, 'result.txt')
        try:
            temp_table = pd.read_csv(data_path, sep=',')
        except Exception as e:
            print(f"读取失败: {data_path} -> {e}")
            continue

        x.append(temp_window_size)
        getter = np.log if metric_whether_log else lambda v: v
        col = metric_col_index - 1  # Python 索引从0开始

        try:
            y_bd.append(getter(temp_table.iloc[1, col]) * shrink_ratio)
            y_ba.append(getter(temp_table.iloc[2, col]) * shrink_ratio)
            y_pbd.append(getter(temp_table.iloc[3, col]) * shrink_ratio)
            y_pba.append(getter(temp_table.iloc[4, col]) * shrink_ratio)
            y_pdbd.append(getter(temp_table.iloc[5, col]) * shrink_ratio)
            y_pdba.append(getter(temp_table.iloc[6, col]) * shrink_ratio)
            y_plbu.append(getter(temp_table.iloc[7, col]) * shrink_ratio)
        except Exception as e:
            print(f"表格读取异常（{data_path}）：{e}")

    # 排序
    x = np.array(x)
    sorted_indices = np.argsort(x)
    x = x[sorted_indices]
    y_bd = np.array(y_bd)[sorted_indices]
    y_ba = np.array(y_ba)[sorted_indices]
    y_pbd = np.array(y_pbd)[sorted_indices]
    y_pba = np.array(y_pba)[sorted_indices]
    y_pdbd = np.array(y_pdbd)[sorted_indices]
    y_pdba = np.array(y_pdba)[sorted_indices]
    y_plbu = np.array(y_plbu)[sorted_indices]

    # 图像配置
    plt.rcParams['font.family'] = 'serif'
    plt.rcParams['font.serif'] = ['Times New Roman']
    plt.rcParams['mathtext.fontset'] = 'cm'
    plt.rcParams['font.size'] = 28
    figure_MarkerSize = 24
    figure_FontSize_X = 28
    figure_FontSize_Y = 28

    fig, ax = plt.subplots(figsize=(8, 6))
    linewidth = 3
    markeredgewidth = 3

    ax.plot(x, y_bd, 'ks-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BD')
    ax.plot(x, y_ba, 'mo-', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='BA')
    ax.plot(x, y_plbu, 'c*--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PLBU')
    ax.plot(x, y_pbd, 'bs--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBD')
    ax.plot(x, y_pba, 'go--', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PBA')
    ax.plot(x, y_pdbd, 'cs:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBD')
    ax.plot(x, y_pdba, 'ro:', linewidth=linewidth, markersize=figure_MarkerSize, markerfacecolor='none', markeredgewidth=markeredgewidth, label='PDBA')

    ax.set_xlabel(r"$\mathcal{E}$", fontsize=figure_FontSize_X)
    ylabel = f"ln({metric_name})" if metric_whether_log else metric_name
    y_all = np.concatenate([y_bd, y_ba, y_pbd, y_pba, y_pdbd, y_pdba, y_plbu])
    ax.set_ylim(top=np.max(y_all) * 1.07)  # 提高 7% 上限，避免遮挡
    ax.set_ylabel(ylabel, fontsize=figure_FontSize_Y)

    ax.set_xticks(x)
    padding = (x[-1] - x[0]) * 0.05  # 动态计算边缘留白 5%
    ax.set_xlim(x[0] - padding, x[-1] + padding)

    ax.tick_params(axis='both', labelsize=figure_FontSize_X)

    # 图例
    legend_names = ["BD", "BA", "PLBU", "PBD", "PBA", "PDBD", "PDBA"]
    ax.legend(legend_names, loc='best', fontsize=14, frameon=False)

    plt.tight_layout()

    plt.savefig(output_file_name + '.pdf', format='pdf', bbox_inches="tight")

    plt.show()

    plt.close()



if __name__ == '__main__':
    time_cost_basic_path = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp/3.result_time_cost/1.trajectory_time_cost_result"
    influence_basic_path = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp/4.result_containing_ldp/1.trajectory_containing_ldp_result"
    default_window_size = 120
    default_privacy_budget = 0.6
    time_col_index = 4
    ajsd_col_index = 10
    time_y_name = "running time (s)"
    tjsd_y_name = "AJSD"
    metric_whether_log_time = False
    metric_whether_log_error = True
    shrink_ratio_time = 0.001
    shrink_ratio_error = 1
    output_file_name = "/Users/mac/MainFiles/temp"

    # plot_basic_budget_time_cost(time_cost_basic_path, default_window_size, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    # plot_basic_window_size_time_cost(time_cost_basic_path, default_privacy_budget, time_col_index, time_y_name, metric_whether_log_time, shrink_ratio_time, output_file_name)
    plot_budget_change_influence_given_metric_for_single_dataset(influence_basic_path, default_window_size, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)
    # plot_window_size_change_influence_given_metric_for_single_dataset(influence_basic_path, default_privacy_budget, ajsd_col_index, tjsd_y_name, metric_whether_log_error, shrink_ratio_error, output_file_name)

