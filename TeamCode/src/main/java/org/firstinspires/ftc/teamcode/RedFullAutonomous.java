package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedFullAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        //lowerArm(1);

        lineUp();

        getBlock();

        goBuildSite(46.5);

        //raiseArm(0.5);

        approachFoundation();

        moveFoundation(1);

        //park();
    }
}
