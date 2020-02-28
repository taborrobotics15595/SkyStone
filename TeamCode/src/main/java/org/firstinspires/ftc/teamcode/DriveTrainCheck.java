package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class DriveTrainCheck extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();
        driveTrain.checkEncoders(encodersFromDistance(forward,0.5),drivePower,telemetry);
    }


}
