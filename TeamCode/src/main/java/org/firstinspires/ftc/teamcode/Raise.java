package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class Raise extends AutonomousMode {

    @Override
    public void runOpMode(){

        initialize();
        waitForStart();

        raiseArm(1);
        stop();


    }


}
