import os
import re

def extract_budget_and_window_size_from_dir_name(pattern):
    match = re.match(r"p_(\d+)(?:-(\d+))?_w_(\d+)", pattern)
    if not match:
        raise ValueError(f"模式不匹配：{pattern}")
    integer_part = match.group(1)
    decimal_part = match.group(2)
    if decimal_part is not None:
        epsilon = float(f"{integer_part}.{decimal_part}")
    else:
        epsilon = int(integer_part)
    window_size = int(match.group(3))
    return epsilon, window_size


def extract_budget_and_window_size_and_dimension_size_from_dir_name(pattern):
    # 匹配格式: p_X-Y_w_Z_d_W 或 p_X-Y_w_Z_p_W
    match = re.match(r"p_(\d+)(?:-(\d+))?_w_(\d+)_(?:d|p)_(\d+)", pattern)
    if not match:
        raise ValueError(f"模式不匹配：{pattern}")

    # 提取 epsilon
    integer_part = match.group(1)
    decimal_part = match.group(2)
    if decimal_part is not None:
        epsilon = float(f"{integer_part}.{decimal_part}")
    else:
        epsilon = int(integer_part)

    # 提取 window_size
    window_size = int(match.group(3))

    # 提取 dimension_size
    dimension_size = int(match.group(4))

    return epsilon, window_size, dimension_size


def extract_ratio_and_others_from_dir_name(pattern):
    # 正则表达式匹配 u_*, p_*, w_*
    pattern_regex = r"^u_(\d+)-(\d+)(?:_p_(\d+)-(\d+)|_w_(\d+))?$"
    match = re.match(pattern_regex, pattern)

    if not match:
        raise ValueError(f"格式不匹配: {pattern}")

    # 提取 user ratio
    user_integer, user_decimal = match.group(1), match.group(2)
    user_ratio = float(f"{user_integer}.{user_decimal}")

    # 初始化 privacy_budget 和 window_size 为 None
    privacy_budget = None
    window_size = None

    # 判断是 _p_ 还是 _w_
    if match.group(3):  # 匹配到 _p_*-* 的情况
        pb_integer, pb_decimal = match.group(3), match.group(4)
        privacy_budget = float(f"{pb_integer}.{pb_decimal}")
    elif match.group(5):  # 匹配到 _w_* 的情况
        window_size = int(match.group(5))

    return user_ratio, privacy_budget, window_size


# 示例调用
if __name__ == '__main__':
    # patterns = ["p_2-0_w_20", "p_3_w_50", "p_4-14_w_60"]
    # for pattern in patterns:
    #     epsilon, window_size = extract_budget_and_window_size_from_dir_name(pattern)
    #     print(f"Pattern: {pattern} --> epsilon: {epsilon}, window_size: {window_size}")
    test_patterns = [
        "u_0-1_p_0-6",
        "u_0-1_w_100",
        "u_1-0_p_2-0",
        "u_3-14_w_500"
    ]

    for pattern in test_patterns:
        ur, pb, ws = extract_ratio_and_others_from_dir_name(pattern)
        print(f"Pattern: {pattern} -> User Ratio: {ur}, Privacy Budget: {pb}, Window Size: {ws}")