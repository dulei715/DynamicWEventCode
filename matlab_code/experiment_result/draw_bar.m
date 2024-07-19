function draw_bar_changeB()
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

%a = plot(x_DAM_Crime,y_DAM_Crime, 'rs-','LineWidth',2,'MarkerSize',figure_MarkerSize);
%b = plot(x_DAM_NYC, y_DAM_NYC, 'rx-','LineWidth',2,'MarkerSize',figure_MarkerSize);
%c = plot(x_DAM_Normal, y_DAM_Normal, 'g+:','LineWidth',2,'MarkerSize',figure_MarkerSize);
%d = plot(x_DAM_Zipf, y_DAM_Zipf, 'go:', 'LineWidth', 2,'MarkerSize',figure_MarkerSize);
%e = plot(x_DAM_Zipf, y_DAM_Zipf, 'g*:', 'LineWidth', 2,'MarkerSize',figure_MarkerSize);

a = plot(x, y_bd, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
b = plot(x, y_ba, 'bs--', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
c = plot(x, y_pbd, 'mo-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
d = plot(x, y_pba, 'go--','LineWidth',2, 'MarkerSize',figure_MarkerSize);
e = plot(x, y_pdbd, 'cd-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
f = plot(x, y_pdba, 'rd--','LineWidth',2, 'MarkerSize',figure_MarkerSize);

a.Visible='off';
b.Visible='off';
c.Visible='off';
d.Visible='off';
e.Visible='off';
f.Visible='off';

legend_names = ["BD";"BA";"PBD";"PBA";"PDBD";"PDBA"];

locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
%h = legend('RAM-Crime', 'DAM-Crime','RAM-NYC','DAM-NYC','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
%h = legend('DAM-Crime','DAM-NYC','DAM-Normal','DAM-Zipf','DAM-MC-Normal','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
h = legend(legend_names(1), legend_names(2), legend_names(3), legend_names(4), legend_names(5), legend_names(6), 'Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
axis off;