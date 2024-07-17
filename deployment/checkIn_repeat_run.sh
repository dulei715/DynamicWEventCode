#!/bin/bash
java -cp DynamicWEventCode-1.0-DMDP-jar-with-dependencies.jar ecnu.dll.run.c_dataset_run.CheckInDataSetRun

for i in {1..10}; do
  echo "hello";
done