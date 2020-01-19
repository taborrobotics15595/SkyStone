package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BlueSkystoneOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lowerArm(1);

        lineUp();

        getBlock();

        double distance = (22.75 * 3  - 20)* 0.0254 - CAR_HEIGHT;
        goBuildSite(distance);

        raiseArm(0.5);

        ejectBlock();

        lowerArm(0.5);
        driveTrain.goToPositions(encodersFromDistance(forward,-distance),drivePower);
    }
    @Override
    protected void goBuildSite(double distance){
        double d = (22.75 * 3 * 0.0254) - finder.getDistance() + distance;
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(forward,d),drivePower);


    }
}
