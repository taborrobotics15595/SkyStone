package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedFullAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lineUp();

        getBlock(1);

        goBuildSite(44,1);

        approachFoundation();

        moveFoundation(1);

        driveAndMoveArm(encodersFromDistance(forward,-0.2),drivePower,-850);

    }
}
