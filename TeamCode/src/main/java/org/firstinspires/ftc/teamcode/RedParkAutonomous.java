package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedParkAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lowerArm(1);
        driveTrain.goToPositions(encodersFromDistance(forward,5*0.0254),drivePower);


    }
}
