package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BlueParkAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lowerArm(1);
        driveTrain.goToPositions(encodersFromDistance(forward,25*0.0254),drivePower);


    }
}
