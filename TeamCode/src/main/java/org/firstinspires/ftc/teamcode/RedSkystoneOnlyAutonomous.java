package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedSkystoneOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();


        lineUp();

        getBlock(1);

        double distance = (22.75 * 3  - 28);
        goBuildSite(distance,1);


        ejectBlock();

        lowerArm(0.5);
        driveTrain.goToPositions(encodersFromDistance(forward,-distance*0.0254),drivePower);

    }
}
