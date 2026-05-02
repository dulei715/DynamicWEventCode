import os
from ftplib import error_perm

import matplotlib.pyplot as plt

def draw_ratio_bar(output_basic_dir):
    # 设置绘图参数
    plt.rcParams.update({
        "text.usetex": False,
        "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
        "mathtext.fontset": "cm",
        "lines.linewidth": 2,
        "font.size": 28,
        "axes.titlesize": 26,
        "axes.labelsize": 26,
        "xtick.labelsize": 26,
        "ytick.labelsize": 26,
        "legend.fontsize": 24
    })

    # 定义标签和颜色
    # labels = ["BD", "BA", "PBD", "PBA"]
    # colors = ["blue", "orange", "green", "red", "purple"]

    # 定义标签和对应样式
    labels_styles = [
        ("BD", 's', '-', 'k'),
        ("BA", 'o', '-', 'm'),
        ("PBD", 's', '--', 'b'),
        ("PBA", 'o', '--', 'g')
    ]

    # 创建一个新的图形
    fig, ax = plt.subplots(figsize=(6.5, 0.9))

    # 对于每个标签和颜色，创建一个空的线条仅用于图例
    for label, marker, linestyle, color in labels_styles:
        ax.plot(
            [], [],
            label=label,
            linestyle=linestyle,
            marker=marker,
            color=color,
            markersize=20,
            markerfacecolor='none',
            markeredgecolor=color,
            markeredgewidth=3,
            linewidth=3
        )

    # 显示图例
    ax.legend(
        loc='center',
        frameon=False,
        ncol=4,
        columnspacing=1.0,
        handletextpad=0.3
    )

    # 隐藏坐标轴
    ax.axis('off')

    # 调整布局
    plt.subplots_adjust(left=0.01, right=0.99, bottom=0.01, top=0.99)

    # 保存图片路径设置
    # output_file_name = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
    # output_basic_dir = "/Users/mac/MainFiles"
    filename = "bar_2.pdf"
    full_path = os.path.join(output_basic_dir, filename)

    # 保存图像
    plt.savefig(full_path, bbox_inches="tight")
    plt.show()
def draw_running_time_bar(output_basic_dir):
    # 设置绘图参数
    plt.rcParams.update({
        "text.usetex": False,
        "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
        "mathtext.fontset": "cm",
        "lines.linewidth": 2,
        "font.size": 28,
        "axes.titlesize": 26,
        "axes.labelsize": 26,
        "xtick.labelsize": 26,
        "ytick.labelsize": 26,
        "legend.fontsize": 24
    })

    # 定义标签和颜色
    # labels = ["BD", "BA", "PBD", "PBA"]
    # colors = ["blue", "orange", "green", "red", "purple"]

    # 定义标签和对应样式
    labels_styles = [
        ("BD", 's', '-', 'k'),
        ("BA", 'o', '-', 'm'),
        ("PBD", 's', '--', 'b'),
        ("PBA", 'o', '--', 'g'),
        ("DPBD", 's', ':', 'c'),
        ("DPBA", 'o', ':', 'r')
    ]

    # 创建一个新的图形
    fig, ax = plt.subplots(figsize=(10.5, 0.9))

    # 对于每个标签和颜色，创建一个空的线条仅用于图例
    for label, marker, linestyle, color in labels_styles:
        ax.plot(
            [], [],
            label=label,
            linestyle=linestyle,
            marker=marker,
            color=color,
            markersize=20,
            markerfacecolor='none',
            markeredgecolor=color,
            markeredgewidth=3,
            linewidth=3
        )

    # 显示图例
    ax.legend(
        loc='center',
        frameon=False,
        ncol=6,
        columnspacing=1.0,
        handletextpad=0.3
    )

    # 隐藏坐标轴
    ax.axis('off')

    # 调整布局
    plt.subplots_adjust(left=0.01, right=0.99, bottom=0.01, top=0.99)

    # 保存图片路径设置
    # output_file_name = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
    # output_basic_dir = "/Users/mac/MainFiles"
    filename = "bar3_time1.pdf"
    full_path = os.path.join(output_basic_dir, filename)

    # 保存图像
    plt.savefig(full_path, bbox_inches="tight")
    plt.show()
def draw_error_bar(output_basic_dir):
    # 设置绘图参数
    plt.rcParams.update({
        "text.usetex": False,
        "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
        "mathtext.fontset": "cm",
        "lines.linewidth": 2,
        "font.size": 28,
        "axes.titlesize": 26,
        "axes.labelsize": 26,
        "xtick.labelsize": 26,
        "ytick.labelsize": 26,
        "legend.fontsize": 24
    })

    # 定义标签和颜色
    # labels = ["BD", "BA", "PBD", "PBA"]
    # colors = ["blue", "orange", "green", "red", "purple"]

    # 定义标签和对应样式
    labels_styles = [
        ("BD", 's', '-', 'k'),
        ("BA", 'o', '-', 'm'),
        ("PLBU", '*', '--', 'teal'),
        ("PBD", 's', '--', 'b'),
        ("PBA", 'o', '--', 'g'),
        ("DPBD", 's', ':', 'c'),
        ("DPBA", 'o', ':', 'r')
    ]

    # 创建一个新的图形
    fig, ax = plt.subplots(figsize=(12.5, 0.9))

    # 对于每个标签和颜色，创建一个空的线条仅用于图例
    for label, marker, linestyle, color in labels_styles:
        ax.plot(
            [], [],
            label=label,
            linestyle=linestyle,
            marker=marker,
            color=color,
            markersize=20,
            markerfacecolor='none',
            markeredgecolor=color,
            markeredgewidth=3,
            linewidth=3
        )

    # 显示图例
    ax.legend(
        loc='center',
        frameon=False,
        ncol=7,
        columnspacing=1.0,
        handletextpad=0.3
    )

    # 隐藏坐标轴
    ax.axis('off')

    # 调整布局
    plt.subplots_adjust(left=0.01, right=0.99, bottom=0.01, top=0.99)

    # 保存图片路径设置
    # output_file_name = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
    # output_basic_dir = "/Users/mac/MainFiles"
    filename = "bar3.pdf"
    full_path = os.path.join(output_basic_dir, filename)

    # 保存图像
    plt.savefig(full_path, bbox_inches="tight")
    plt.show()
def draw_error_bar_containing_spas(output_basic_dir):
    # 设置绘图参数
    plt.rcParams.update({
        "text.usetex": False,
        "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
        "mathtext.fontset": "cm",
        "lines.linewidth": 2,
        "font.size": 28,
        "axes.titlesize": 26,
        "axes.labelsize": 26,
        "xtick.labelsize": 26,
        "ytick.labelsize": 26,
        "legend.fontsize": 24
    })

    # 定义标签和颜色
    # labels = ["BD", "BA", "PBD", "PBA"]
    # colors = ["blue", "orange", "green", "red", "purple"]

    # 定义标签和对应样式
    labels_styles = [
        ("BD", 's', '-', 'k'),
        ("BA", 'o', '-', 'm'),
        ("SPAS", 'D', '-.', 'limegreen'),
        ("PBD", 's', '--', 'b'),
        ("PBA", 'o', '--', 'g'),
        ("DPBD", 's', ':', 'c'),
        ("DPBA", 'o', ':', 'r')
    ]

    # 创建一个新的图形
    fig, ax = plt.subplots(figsize=(12.5, 0.9))

    # 对于每个标签和颜色，创建一个空的线条仅用于图例
    for label, marker, linestyle, color in labels_styles:
        ax.plot(
            [], [],
            label=label,
            linestyle=linestyle,
            marker=marker,
            color=color,
            markersize=20,
            markerfacecolor='none',
            markeredgecolor=color,
            markeredgewidth=3,
            linewidth=3
        )

    # 显示图例
    ax.legend(
        loc='center',
        frameon=False,
        ncol=7,
        columnspacing=1.0,
        handletextpad=0.3
    )

    # 隐藏坐标轴
    ax.axis('off')

    # 调整布局
    plt.subplots_adjust(left=0.01, right=0.99, bottom=0.01, top=0.99)

    # 保存图片路径设置
    # output_file_name = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
    # output_basic_dir = "/Users/mac/MainFiles"
    filename = "bar4.pdf"
    full_path = os.path.join(output_basic_dir, filename)

    # 保存图像
    plt.savefig(full_path, bbox_inches="tight")
    plt.show()

def draw_error_bar_containing_all(output_basic_dir):
    # 设置绘图参数
    plt.rcParams.update({
        "text.usetex": False,
        "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
        "mathtext.fontset": "cm",
        "lines.linewidth": 2,
        "font.size": 28,
        "axes.titlesize": 26,
        "axes.labelsize": 26,
        "xtick.labelsize": 26,
        "ytick.labelsize": 26,
        "legend.fontsize": 24
    })

    # 定义标签和颜色
    # labels = ["BD", "BA", "PBD", "PBA"]
    # colors = ["blue", "orange", "green", "red", "purple"]

    # 定义标签和对应样式
    labels_styles = [
        ("BD", 's', '-', 'k'),
        ("BA", 'o', '-', 'm'),
        ("PLBU", '*', '--', 'teal'),
        ("SPAS", 'D', '-.', 'limegreen'),
        ("PBD", 's', '--', 'b'),
        ("PBA", 'o', '--', 'g'),
        ("DPBD", 's', ':', 'c'),
        ("DPBA", 'o', ':', 'r')
    ]

    # 创建一个新的图形
    # fig, ax = plt.subplots(figsize=(12.5, 0.9))
    # fig, ax = plt.subplots(figsize=(15, 0.9))
    fig, ax = plt.subplots(figsize=(20, 0.9))

    # 对于每个标签和颜色，创建一个空的线条仅用于图例
    for label, marker, linestyle, color in labels_styles:
        ax.plot(
            [], [],
            label=label,
            linestyle=linestyle,
            marker=marker,
            color=color,
            markersize=20,
            markerfacecolor='none',
            markeredgecolor=color,
            markeredgewidth=3,
            linewidth=3
        )

    # 显示图例
    ax.legend(
        loc='center',
        frameon=False,
        ncol=len(labels_styles),
        columnspacing=1.0,
        handletextpad=0.3,
    )

    # 隐藏坐标轴
    ax.axis('off')

    # 调整布局
    plt.subplots_adjust(left=0.01, right=0.99, bottom=0.01, top=0.99)

    # 保存图片路径设置
    # output_file_name = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
    # output_basic_dir = "/Users/mac/MainFiles"
    filename = "bar5.pdf"
    full_path = os.path.join(output_basic_dir, filename)

    # 保存图像
    plt.savefig(full_path, bbox_inches="tight")
    plt.show()


if __name__ == '__main__':
    ratio_output_basic_dir = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python/fig/experiment_result"
    error_output_basic_dir = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python/figures/experiment_result_add"
    time_output_basic_dir = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python/figures/experiment_result_add/time_cost_fig"
    containing_spas_error_output_basic_dir = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python/figures/experiment_result_add3"
    containing_all_error_output_basic_dir = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/experiment_result_by_python/figures/experiment_result_add4"
    # draw_ratio_bar(ratio_output_basic_dir)
    # draw_error_bar(error_output_basic_dir)
    # draw_running_time_bar(time_output_basic_dir)
    # draw_error_bar_containing_spas(containing_spas_error_output_basic_dir)
    draw_error_bar_containing_all(containing_all_error_output_basic_dir)
