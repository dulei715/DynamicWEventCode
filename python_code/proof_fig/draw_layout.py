import os
import matplotlib.pyplot as plt

plt.rcParams.update({
    "text.usetex": False,
    "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
    "mathtext.fontset": "cm",
    "lines.linewidth": 3,
    "font.size": 20,
    "axes.titlesize": 18,
    "axes.labelsize": 18,
    "xtick.labelsize": 18,
    "ytick.labelsize": 18,
    "legend.fontsize": 20
})
labels = [r"$\epsilon_{L}^{(B,M)}(i)$", r"$\epsilon_{R}^{(B,M)}(i)$", r"$\epsilon_{F,i,t}^{(2,L)}(i)$", r"$\epsilon_{F,i,t}^{(2,R)}(i)$", r"$\epsilon_{F,i,t}^{(2)}(i)$"]
colors = ["blue", "orange", "green", "red", "purple"]
fig, ax = plt.subplots(figsize=(6.50, 1.1))
# fig, ax = plt.subplots()
for label, color in zip(labels, colors):
    ax.plot([], [], color=color, label=label)



# 显示图例
ax.legend(
    loc='center',
    frameon=False,
    # ncol=len(labels)
    ncol=3
)
ax.axis('off')  # 隐藏坐标轴
plt.subplots_adjust(left=0.01, right=0.99, bottom=0.01, top=0.99)
basic_path = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
filename = "bar.pdf"
full_path = os.path.join(basic_path, filename)
plt.savefig(full_path, bbox_inches="tight")
plt.show()