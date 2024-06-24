function y = drawOneUserPositionOfTrajectory(file)
matrix = readtable(file);
if isempty(matrix)
    return;
end
latitudes = matrix(:,2).Var2;
longitudes = matrix(:,1).Var1;
plot(longitudes, latitudes,'.','Color','b');