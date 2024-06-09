import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
from matplotlib.font_manager import FontProperties

# coding:utf-8
# plt.rcParams['font.sans-serif'] = ['SimHei'] #用来正常显示中文标签
# plt.rcParams['axes.unicode_minus'] = False #用来正常显示负号

# 准备数据
x_values = [1, 2, 3, 4, 5]
y_values = [10, 15, 7, 12, 9]

# 绘制折线图
plt.plot(x_values, y_values, marker='o', linestyle='-', color='b')
# 设置 x 轴刻度
plt.xticks(np.arange(min(x_values), max(x_values) + 1, 2.0))
# 设置 y 轴刻度
plt.yticks(np.arange(min(y_values), max(y_values) + 0.1, 0.1))

# 添加标题和标签
plt.title('Line Chart Example')
plt.xlabel('xxx')
plt.ylabel('xxx')

# 添加图例
plt.legend()

# 显示图表
plt.show()
