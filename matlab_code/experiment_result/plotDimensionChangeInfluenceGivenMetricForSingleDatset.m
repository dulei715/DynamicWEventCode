function y = plotDimensionChangeInfluenceGivenMetricForSingleDatset(basic_path, metric_col_index, metric_name, metric_whether_log, shrink_ratio, outputFileName)
dir_names = list_dir_name(basic_path);
y_bd = zeros(1,5);
y_ba = zeros(1,5);
y_pbd = zeros(1,5);
y_pba = zeros(1,5);
y_pdbd = zeros(1,5);
y_pdba = zeros(1,5);

x = zeros(1,5);
i = 0;
for temp_name = dir_names
    temp_name = cell2mat(temp_name);
    [temp_budget, temp_window_size, d_size] = extractBudgetAndWSizeAndDimensionFromDirName(temp_name);
    
    % disp(temp_budget);
    i = i + 1;
    x(i) = d_size;
    data_path = fullfile(char(basic_path), temp_name, 'result.txt');
    temp_table = readtable(data_path);
    if metric_whether_log
            y_bd(i) = log(temp_table{2,metric_col_index})*shrink_ratio;
            y_ba(i) = log(temp_table{3,metric_col_index})*shrink_ratio;
            y_pbd(i) = log(temp_table{4,metric_col_index})*shrink_ratio;
            y_pba(i) = log(temp_table{5,metric_col_index})*shrink_ratio;
            y_pdbd(i) = log(temp_table{6,metric_col_index})*shrink_ratio;
            y_pdba(i) = log(temp_table{7,metric_col_index})*shrink_ratio;
    else
            y_bd(i) = temp_table{2,metric_col_index}*shrink_ratio;
            y_ba(i) = temp_table{3,metric_col_index}*shrink_ratio;
            y_pbd(i) = temp_table{4,metric_col_index}*shrink_ratio;
            y_pba(i) = temp_table{5,metric_col_index}*shrink_ratio;
            y_pdbd(i) = temp_table{6,metric_col_index}*shrink_ratio;
            y_pdba(i) = temp_table{7,metric_col_index}*shrink_ratio;
    end

end


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
xLabelName = "$d$";
if metric_whether_log 
    yLabelName = "ln(" + metric_name + ")";
else
    yLabelName = metric_name;
end

legend_names = ["BD";"BA";"PBD";"PBA";"PDBD";"PDBA"];
%legend_names = ["BD";"BA";"PLBU";"PBD";"PBA"];
%legend_names = ["BD";"BA";"PLBU";"PBD";"PBA";"PDBD";"PDBA"];
figure_MarkerSize = 20;
figure_FontSize = 28;
figure_FontSize_X = 28;
figure_FontSize_Y = 28;

fig = figure;
hold on;

xlabel(xLabelName, 'Interpreter', 'latex');
%yyaxis left
plot(x, y_bd, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_ba, 'mo-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x, y_plbu, 'c*--', 'LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_pbd, 'bs--', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
plot(x, y_pba, 'go--','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_pdbd, 'cs:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_pdba, 'ro:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
ylabel(yLabelName);


xlim([round(x(1),1) x(length(x))]);
set(gca,'XTick',round(x,1));

%figure_FontSize = 18;
%set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
%set(findobj('FontSize',10),'FontSize',figure_FontSize);


set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Time New Roman');
%set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

h = legend(legend_names(1), legend_names(2), legend_names(3), legend_names(4), legend_names(5), legend_names(6), 'Location','Best');
%h = legend(legend_names(1), legend_names(2), legend_names(3), legend_names(4), legend_names(5), legend_names(6), legend_names(7), 'Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');

%saveas(fig,outputFileName,'eps');
%print(fig, outputFileName, '-depsc2');
%saveas(fig,outputFileName,'fig');

export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName); %for windows system

%export_fig(outputFileName, '.eps');
%print(fig, outputFileName,'-dpdf');