function y = drawUsersPosition(basicPath, fileNumber) 
fig = figure;
hold on;
for i = 1:fileNumber
    tempFile = [basicPath,num2str(i),'.txt'];
    drawOneUserPosition(tempFile);
end
    