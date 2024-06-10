import paperplotlib as ppl
import numpy as np

a = 5
b = 7
y = np.random.randint(10, 100, size=(a, b))

# graph = ppl.BarGraph()
graph = ppl.LineGraph()
x_data = [i ** 2 for i in range(1, b+1)]
group_names = [f"group(i)" for i in range(a)]
column_names = [f"group(i)" for i in range(b)]
# graph.plot_2d(y, group_names, column_names)
graph.plot_2d(x_data, y, column_names)

graph.x_label = "The number of data"
graph.y_label = "Throughpu (Mbps)"

graph.save()
