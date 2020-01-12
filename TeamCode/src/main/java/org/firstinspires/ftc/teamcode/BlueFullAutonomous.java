package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BlueFullAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        waitForStart();

        lowerArm();

        lineUp();

        getBlock();

        goBuildSite(-44);

        approachFoundation();

        moveFoundation(-1);

        //park();
    }

    @Override
    protected void goBuildSite(double distance){
        double d = (22.75 * 3 * 0.0254) - finder.getDistance() + distance;
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(forward,-d),drivePower);


    }
}
