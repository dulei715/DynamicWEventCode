import os

import numpy as np
import matplotlib.pyplot as plt
import pylab as p
import math

plt.rcParams.update({
    "text.usetex": False,
    "font.family": "Times New Roman",   # 可替换为 'Helvetica'、'Times New Roman' 等
    "mathtext.fontset": "cm",
    "lines.linewidth": 3,
    "font.size": 20,
    "axes.titlesize": 22,
    "axes.labelsize": 22,
    "xtick.labelsize": 20,
    "ytick.labelsize": 20,
    "legend.fontsize": 18
})

x = np.arange(0, 50.01, 0.01)
eL = 8
eR = 12
eH = 10
epsilonBoundL = p.full(x.shape, eL)
epsilonBoundR = p.full(x.shape, eR)
epsilonForwardL = []
# aL = 6291456
bLB = 0.1
# bL = -0.2867
tagB = (32 - eR) / 1.2
c = 1
aL = tagB / c + math.log(eR-bLB) / math.log(2)
print(aL)
for xi in x:
    if xi <= tagB:
        val = -1.2 * xi + 32
    else:
        # val = aL * (2**(-xi)) + bL
        val = 2**(-xi/c+aL)+bLB
    epsilonForwardL.append(val)
epsilonForwardL = np.array(epsilonForwardL)
epsilonForwardR = []
# aR = 939524208
# bR = 5
bRD = 3
tagD = (32 - eL) / 0.74074
aL = tagD / c + math.log(eL-bRD) / math.log(2)
for xi in x:
    if xi <= tagD:
        val = (-0.74074) * xi + 32
    else:
        val = 2 ** (-xi/c+aL) + bRD
    epsilonForwardR.append(val)
epsilonForwardR = np.array(epsilonForwardR)

epsilonForwardM = []
# aM = 50331648.375
# bM = 4
bMH = 1.5
tagH = (32 - eH) / 0.95652
aL = tagH / c + math.log(eH-bMH) / math.log(2)
for xi in x:
    if xi <= tagH:
        val = (-0.95652) * xi + 32
    else:
        val = 2**(-xi/c+aL) + bMH
    epsilonForwardM.append(val)
epsilonForwardM = np.array(epsilonForwardM)

# fontSizeMark = 18
# fontSize = 20
# lineWidth = 3

# color_list = ["orange", "red", "green", "blue", "magenta", "cyan"]
color_list = ["blue", "orange", "green", "red", "purple"]

plt.figure()
plt.plot(x, epsilonBoundL, label=r"$\epsilon_{L}^{(B,M)}(i)$", color=color_list[0])
plt.plot(x, epsilonBoundR, label=r"$\epsilon_{R}^{(B,M)}(i)$", color=color_list[1])
plt.plot(x, epsilonForwardL, label=r"$\epsilon_{F,i,t}^{(2,L)}(i)$", color=color_list[2])
plt.plot(x, epsilonForwardR, label=r"$\epsilon_{F,i,t}^{(2,R)}(i)$", color=color_list[3])
plt.plot(x, epsilonForwardM, label=r"$\epsilon_{F,i,t}^{(2)}(i)$", color=color_list[4])
# plt.title("An example")



A_x = 0
A_y = 32
plt.plot(A_x, A_y, 'ko')
plt.text(A_x+1, A_y+0.5, 'A', verticalalignment='center')
B_x = tagB - 0.1
B_y = eR
plt.plot(B_x, B_y, 'ko')
plt.text(B_x - 2, B_y - 1.5, 'B', verticalalignment='center')
C_x = 50
C_y = epsilonForwardL[len(x)-1]
plt.plot(C_x, C_y, 'ko')
plt.text(C_x + 0.7, C_y - 0.5, 'C', verticalalignment='center')
D_x = tagD-0.1
D_y = eL
plt.plot(D_x, D_y, 'ko')
plt.text(D_x + 1, D_y + 1, 'D', verticalalignment='center')
E_x = 50
E_y = epsilonForwardR[len(x)-1]
plt.plot(E_x, E_y, 'ko')
plt.text(E_x + 0.5, E_y + 1, 'E', verticalalignment='center')
F_x = 0
F_y = 12
plt.plot(F_x, F_y, 'ko')
plt.text(F_x + 1, F_y + 1, 'F', verticalalignment='center')
G_x = 50
G_y = 12
plt.plot(G_x, G_y, 'ko')
plt.text(G_x - 1, G_y + 1, 'G', verticalalignment='center')

H_x = 23
H_y = 10
plt.plot(H_x, H_y, 'ko')
plt.text(H_x + 1, H_y, 'H', verticalalignment='center')
I_x = 23+0.6
I_y = 8
plt.plot(I_x, I_y, 'ko')
plt.text(I_x - 1.5, I_y - 1.5, 'I', verticalalignment='center')
J_x = 50
J_y = epsilonForwardM[len(x)-1]
plt.plot(J_x, J_y, 'ko')
plt.text(J_x + 1.0, J_y, 'J', verticalalignment='center')

K_x = 0
K_y = 8
plt.plot(K_x, K_y, 'ko')
plt.text(K_x + 1, K_y - 1.5, 'K', verticalalignment='center')
L_x = 50
L_y = 8
plt.plot(L_x, L_y, 'ko')
plt.text(L_x-0.5, L_y + 1, 'L', verticalalignment='center')


plt.xlabel("non-null publication times")
plt.ylabel("budget values")
# plt.legend(
#     loc="lower center",
#     bbox_to_anchor=(0.5, 1.02),
#     ncol=3,
#     fontsize=fontSizeMark,
#     borderaxespad=0.
# )
# plt.legend()
plt.xticks()
plt.yticks()
plt.tight_layout()
plt.subplots_adjust(left=0.11, right=0.99, bottom=0.14, top=0.99)

filename = "data.pdf"
# basic_path = "/Users/mac/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
basic_path = "E:/MainFiles/5.GitTrans/3.github_file/PaperTrans/paper3_3_dynamic-Extended-W-event-DP/figures/backward_forward_bound"
full_path = os.path.join(basic_path, filename)
# plt.savefig(full_path, bbox_inches="tight")

plt.show()
