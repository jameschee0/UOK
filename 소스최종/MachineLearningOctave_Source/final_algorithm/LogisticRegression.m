clear ; close all; clc

cd = '/Users/jameschee/Desktop/octave/test/new challenge';
learnstand = load('standing_data.txt');
learnfall = load('falling_data.txt');
learnrun = load('running_data.txt');

%%===stand vs all=====
X = [learnfall(:,:);learnrun(:,:);learnstand(:,:)];
Y = [zeros(100,1);ones(50,1)];

[m,n]=size(X);
X = [ones(m,1) X];
initial_theta = zeros(n+1,1);

options = optimset('GradObj', 'on', 'MaxIter', 400);

[standtheta, cost] = ...
	fminunc(@(t)(costFunction(t, X, Y)), initial_theta, options);

%%===fall vs all=====
X = [learnrun(:,:);learnstand(:,:);learnfall(:,:)];
Y = [zeros(100,1);ones(50,1)];

[m,n]=size(X);
X = [ones(m,1) X];
initial_theta = zeros(n+1,1);

options = optimset('GradObj', 'on', 'MaxIter', 400);

[falltheta, cost] = ...
	fminunc(@(t)(costFunction(t, X, Y)), initial_theta, options);

%%===running vs all=====
X = [learnfall(:,:);learnstand(:,:);learnrun(:,:)];
Y = [zeros(100,1);ones(50,1)];

[m,n]=size(X);
X = [ones(m,1) X];
initial_theta = zeros(n+1,1);

options = optimset('GradObj', 'on', 'MaxIter', 400);

[runtheta, cost] = ...
	fminunc(@(t)(costFunction(t, X, Y)), initial_theta, options);

%%===Real Testing...Oh!====
testdata = [1 load('fallingtesting.txt')];
standingthetatrans = standtheta';
fallingthetatrans = falltheta';
runningthetatrans = runtheta';

standsum = sum(testdata.*standingthetatrans);
fallsum = sum(testdata.*fallingthetatrans);
runsum = sum(testdata.*runningthetatrans);

stand = sigmoid(standsum);
fall = sigmoid(fallsum);
run = sigmoid(runsum);






