function bar3()
fig = figure;
hold on;

figure_MarkerSize = 15;
legend_FontSize = 20;

x = [1:0.1:10]; 

y_bd = x * 2;
y_ba = x * 2;
y_pbd = x * 2;
y_pba = x * 2;
y_pdbd = x * 2;
y_pdba = x * 2;
y_plbu = x * 2;
a = plot(x, y_bd, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
b = plot(x, y_ba, 'mo-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
c = plot(x, y_plbu, 'c*--', 'LineWidth',2, 'MarkerSize',figure_MarkerSize);
d = plot(x, y_pbd, 'bs--', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
e = plot(x, y_pba, 'go--','LineWidth',2, 'MarkerSize',figure_MarkerSize);
f = plot(x, y_pdbd, 'cs:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
g = plot(x, y_pdba, 'ro:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
a.Visible='off';
b.Visible='off';
c.Visible='off';
d.Visible='off';
e.Visible='off';
f.Visible='off';
g.Visible='off';
legend_names = ["BD";"BA";"PLBU";"PBD";"PBA";"DPBD";"DPBA"];
locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
h = legend(legend_names, 'Location',locationType,'Orientation',orientationType);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
set(h, 'TextColor',textColor);
h.Box = 'off';
for i = 1:length(h.Children)
    if isfield(h.Children(i), 'String') % 检查是否为文本对象
        set(h.Children(i), 'Color', 'black');
    end
end
axis off;