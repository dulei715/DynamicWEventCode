import os
import sys


def list_dir_name(path):
    dirs = []
    # print(f"列出目录：{basic_path}")
    for entry in os.listdir(path):
        full_path = os.path.join(path, entry)
        if os.path.isdir(full_path):
            dirs.append(entry)
            # print(f"目录：{entry}")
    return dirs

def list_file_name(path):
    files = []
    # print(f"列出目录：{basic_path}")
    for entry in os.listdir(path):
        full_path = os.path.join(path, entry)
        if os.path.isfile(full_path):
            files.append(entry)
            # print(f"目录：{entry}")
    return files

def get_parent_and_file_name(path):
    normalized_path = os.path.normpath(path)
    parent_dir = os.path.dirname(normalized_path)
    base_name = os.path.basename(normalized_path)
    return parent_dir, base_name


if __name__ == '__main__':
    basic_path = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp/3.result_time_cost/1.trajectory_time_cost_result"
    # basic_path = "$HOME/MainFiles/1.Research/dataset/3_stream_dp/3.result_time_cost/1.trajectory_time_cost_result"
    result = get_parent_and_file_name(basic_path)
    for element in result:
        print(element)