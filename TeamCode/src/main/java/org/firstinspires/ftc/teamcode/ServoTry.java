package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ServoTry extends LinearOpMode {
    FoundationMover mover;

    @Override
    public void runOpMode() throws InterruptedException {
        mover = new FoundationMover(hardwareMap,"Servo1","Servo2");
        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                mover.toggle();
                Thread.sleep(200);
            }
        }
    }
}
