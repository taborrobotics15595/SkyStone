package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedFullAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lowerArm();

        lineUp();

        getBlock();

        goBuildSite(44);

        approachFoundation();

        moveFoundation(1);

        park();
    }
}
