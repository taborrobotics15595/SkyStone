package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class Test extends LinearOpMode {

    @Override
    public void runOpMode(){
        DigitalChannel sensor = hardwareMap.get(DigitalChannel.class,"Sensor");
        sensor.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();
        while(opModeIsActive()){
            String message = "Pressed:" + String.valueOf(sensor.getState());

            telemetry.addData("Status:",message);
            telemetry.update();
        }
    }
}
