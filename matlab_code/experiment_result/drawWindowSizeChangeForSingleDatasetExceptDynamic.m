function y = drawWindowSizeChangeForSingleDatasetExceptDynamic(basic_path, default_budget,outputFileName)
% haha = @utils/list_dir_name;
% disp(haha);
dir_names = list_dir_name(basic_path);
y_bd = zeros(1,5);
y_ba = zeros(1,5);
y_pbd = zeros(1,5);
y_pba = zeros(1,5);
%y_pdbd = zeros(1,5);
%y_pdba = zeros(1,5);
x = zeros(1,5);
i = 0;
for temp_name = dir_names
    temp_name = cell2mat(temp_name);
    [temp_budget, temp_window_size] = extractParameterFromDirName(temp_name);
    if temp_budget ~= default_budget
        continue;
    end
    % disp(temp_budget);
    i = i + 1;
    x(i) = temp_window_size;
    data_path = fullfile(char(basic_path), temp_name, 'result.txt');
    temp_table = readtable(data_path);

    y_bd(i) = log(temp_table(2,8).MRE);
    y_ba(i) = log(temp_table(3,8).MRE);
    y_pbd(i) = log(temp_table(4,8).MRE);
    y_pba(i) = log(temp_table(5,8).MRE);
%    y_pdbd(i) = log(temp_table(6,8).MRE);
%    y_pdba(i) = log(temp_table(7,8).MRE);
end


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
xLabelName = "w";
% yLabelName = 'MRE';
yLabelName = "log(MRE)";

legend_names = ["BD";"BA";"PBD";"PBA";"PDBD";"PDBA"];
figure_MarkerSize = 20;
figure_FontSize = 28;
figure_FontSize_X = 28;
figure_FontSize_Y = 28;

fig = figure;
hold on;

xlabel(xLabelName);
%yyaxis left
plot(x, y_bd, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_ba, 'bs--', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
plot(x, y_pbd, 'mo-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_pba, 'go--','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x, y_pdbd, 'cd-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x, y_pdba, 'rd--','LineWidth',2, 'MarkerSize',figure_MarkerSize);
ylabel(yLabelName);


xlim([round(x(1),1) x(length(x))]);
set(gca,'XTick',round(x,1));

%figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
%set(findobj('FontSize',10),'FontSize',figure_FontSize);


set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

%h = legend('SubGeoI_2', 'MDSW','HUEM','DAM','DAMShrink', 'SubGeoI_1', 'RAM','Location','Best');
h = legend(legend_names(1), legend_names(2), legend_names(3), legend_names(4), 'Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);
