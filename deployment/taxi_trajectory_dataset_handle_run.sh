#!/bin/bash

# for TaxiTrajectory dataset


# java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.TrajectoryDatasetPreprocessRun
# java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.utils.FormatFileName "../0.dataset/T-drive_Taxi_Trajectories/shuffle_by_time_slot/" "_" "."

#java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run._pre_process.b_parameter_pre_process.version_1_continue.parameter_pre_run.GenerateParametersForTrajectory
java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_pre_run.GenerateDiscreteParametersForTrajectory