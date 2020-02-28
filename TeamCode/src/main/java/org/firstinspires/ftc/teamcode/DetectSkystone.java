package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class DetectSkystone extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lineUp();
        getBlock(1);
    }

    @Override
    protected void lineUp(){
        driveTrain.goToPositions(encodersFromDistance(sideways,driveTo),drivePower);
        double start = runtime.seconds();
        double angle = finder.angleToSkystone();
        boolean stopped = false;
        while (angle == 390 &&opModeIsActive()){
            angle = finder.angleToSkystone();
            telemetry.addData("Status","Looking" + runtime.seconds());
            telemetry.update();
            if (runtime.seconds() >= start + 3){
                stopped = true;
                break;
            }
        }
        double distanceX = 0;
        if (!stopped){
            distanceX = (22.75 * 0.0254) * Math.tan(Math.toRadians(angle));
            if (distanceX < 0.15){
                distanceX -= 1.5*0.0254;
            }
            else if (distanceX > 0) {
                distanceX = 0;
            }
            else{
                    distanceX += 0.0254;

            }


        }

        telemetry.addData("Distance", distanceX);
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,distanceX),drivePower);
    }

}
