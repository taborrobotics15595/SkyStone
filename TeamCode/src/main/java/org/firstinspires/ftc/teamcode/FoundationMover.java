package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FoundationMover {
    private Servo servo1,servo2;
    private final double[] MAXMIN1 = {0.7,0},MAXMIN2 = {0.3,1};
    private int index = 0;

    public FoundationMover(HardwareMap hardwareMap,String servoName1,String servoName2) {
        servo1 = hardwareMap.get(Servo.class,servoName1);
        servo2 = hardwareMap.get(Servo.class,servoName2);

        setPosition(index);

    }

    private void setPosition(int index)  {
        servo1.setPosition(MAXMIN1[index]);
        servo2.setPosition(MAXMIN2[index]);
    }

    public void toggle(){
        index = (index + 1)%2;
        setPosition(index);
    }

}

