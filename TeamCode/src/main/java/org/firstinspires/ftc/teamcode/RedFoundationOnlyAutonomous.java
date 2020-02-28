package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedFoundationOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();


        driveTrain.goToPositions(encodersFromDistance(forward,47*0.0254 - CAR_HEIGHT),drivePower);
        driveTrain.goToPositions(encodersFromDistance(sideways,-(22.75)*0.0254),drivePower);

        moveFoundation(1);

        park(1);
    }
    @Override
    protected void moveFoundation(int direction){
        lowerArm(1);
        driveTrain.goToPositions(encodersFromDistance(forward,-(23*2*0.0254 - CAR_HEIGHT)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-direction*180)),drivePower);


        /*
        //mover.toggle();
        sleep(400);
        driveTrain.setPower(drivePower,0.5,0,0);
        sleep(1000);
        driveTrain.setPower(drivePower,0.5,0,(direction)*0.5);
        sleep(1600);
        driveTrain.setPower(drivePower,0,0,0);
        //mover.toggle();

         */
    }
}
