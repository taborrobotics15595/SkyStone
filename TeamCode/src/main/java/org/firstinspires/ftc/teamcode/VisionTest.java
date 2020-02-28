package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VisionTest extends LinearOpMode {
    Vision v;

    @Override
    public void runOpMode(){

        v =  new Vision(hardwareMap,"Dist","Dist2");
        v.activate();
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("Found:","Distance: " + v.distanceToSkyStone() + "Angle: " + v.angleToSkystone());
            telemetry.update();
        }
    }
}
