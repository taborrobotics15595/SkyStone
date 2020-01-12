package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedFoundationOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        driveTrain.goToPositions(encodersFromDistance(forward,(47 - CAR_HEIGHT)*0.0254),drivePower);

        driveTrain.goToPositions(encodersFromDistance(sideways,-(4 + (34.5)/2 -2*22.75 - CAR_WIDTH)*0.0254),drivePower);

        moveFoundation(1);
    }
}
