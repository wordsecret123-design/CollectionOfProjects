Mother Controller runs on a raspberry pi and it communicated via websocket with the ObjectDHandler which runs on a pc that handles the AI object detection, taking in inputs from the camera. The Detection\_Debounce, is used to stabilize the detection. 



TurnVerticalAndHorizontalConveyor runs on an Arduino ESP32, it is used to handle the running of the two stepper which turns the conveyor belt. 

