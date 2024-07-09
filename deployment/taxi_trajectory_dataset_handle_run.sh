#!/bin/bash

# for TaxiTrajectory dataset
 java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.c_dataset_run.pre_process.real_dataset.TrajectoryDatasetPreprocessRun
 java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.utils.FormatFileName "../0.dataset/T-drive_Taxi_Trajectories/shuffle_by_time_slot/" "_" "."
