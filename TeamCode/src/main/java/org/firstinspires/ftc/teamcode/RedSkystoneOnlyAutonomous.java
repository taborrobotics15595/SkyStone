package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedSkystoneOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lowerArm(1);

        lineUp();

        getBlock();

        double distance = (22.75 * 3  - 20)* 0.0254;
        goBuildSite(distance);

        raiseArm(0.5);

        ejectBlock();

        lowerArm(0.5);
        driveTrain.goToPositions(encodersFromDistance(forward,-distance),drivePower);

    }
}
