function y = drawOneUserPositionOfTrajectory(file)
matrix = readtable(file);
if isempty(matrix)
    return;
end
latitudes = matrix(:,4).Var4;
longitudes = matrix(:,3).Var3;
plot(longitudes, latitudes,'.','Color','b');