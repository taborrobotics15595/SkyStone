package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BlueFoundationOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){

        initialize();

        waitForStart();

        //lowerArm();
        driveTrain.goToPositions(encodersFromDistance(sideways,(47.25)*0.0254 - CAR_WIDTH),drivePower);
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-90)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(sideways,(4 + (34.5)/2 -2*22.75 - CAR_HEIGHT)*0.0254),drivePower);

        moveFoundation(-1);
    }

}
