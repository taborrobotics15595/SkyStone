package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BlueFullAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();

        lineUp();


        getBlock(-1);

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        goBuildSite(44,-1);

        approachFoundation();

        moveFoundation(-1);

        driveAndMoveArm(encodersFromDistance(forward,-0.2),drivePower,-850);

    }
    @Override
    protected void goBuildSite(double distance,double direction){
        boolean stopped =false;
        double start= runtime.seconds();
        while(finder.getInfo() == null &&opModeIsActive()){
            if (runtime.seconds()>=start + 0.5){
                stopped = true;
                break;
            }
        }
        double[] data = finder.getInfo();
        double targetAngle = 0;
        double dist =0;
        if(stopped){
            targetAngle = 0;
            dist=-88*0.0254;
        }
        else{
            targetAngle = -90 + (Math.abs(data[1])/data[1])*(data[1]);
            dist = (data[0] - distance)*0.0254;
        }

        telemetry.addData("Status","Going for foundation" + dist + "\n"  +String.valueOf(targetAngle));
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(targetAngle)),drivePower);

        driveAndMoveArm(encodersFromDistance(forward,-dist),drivePower,-850);

    }

    @Override
    protected void approachFoundation(){
        while(finder.getInfo() == null &&opModeIsActive()){

        }

        double[] data = finder.getInfo();
        double distance = (data[2] - 18.5)*0.0254 - CAR_WIDTH/2;
        telemetry.addData("Status:","Turning: " + distance);
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-90)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(forward,distance),drivePower);
    }






}
