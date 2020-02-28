package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class BlueSkystoneOnlyAutonomous extends AutonomousMode {

    @Override
    public void runOpMode(){
        initialize();

        waitForStart();


        lineUp();

        getBlock(-1);

        double distance = (22.75 * 3  - 28);
        goBuildSite(distance,-1);


        ejectBlock();

        driveTrain.goToPositions(encodersFromDistance(forward,-distance*0.0254),drivePower);
    }

    @Override
    protected void goBuildSite(double distance,double direction){
        while(finder.getInfo() == null &&opModeIsActive()){

        }
        double[] data = finder.getInfo();


        double targetAngle =  -(Math.abs(data[1])/data[1])*(90 - Math.abs(data[1]));
        telemetry.addData("Status","Going for foundation" + String.valueOf(data[1]) + "\n"  +String.valueOf(targetAngle));
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(targetAngle)),drivePower);
        double dist = (data[0] - distance)*0.0254;
        driveAndMoveArm(encodersFromDistance(forward,-dist),drivePower,-850);

    }

}
