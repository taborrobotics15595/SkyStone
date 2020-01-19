package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous
public class Autonomous1 extends LinearOpMode {
    Vision finder;
    DcMotor motor;
    MecanumDriveTrain driveTrain;
    StoneCollector grabber;
    FoundationMover mover;
    ElapsedTime runtime;

    int[] forward = {-1,1,-1,1};
    int[] sideways = {1,1,-1,-1};
    int[] rotation = {1,1,1,1};
    int[] diagonal = {1,0,0,-1};
    static final int TICKSPERREV = 1120;
    static final double WHEEL_DIAMETER = 0.075,CAR_WIDTH = 16.5*0.0254,CAR_HEIGHT = 16*0.0254;



    double driveTo = (23.5 - 17.5)*0.0254;
    double drivePower = 1;

    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"Motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        finder = new Vision(hardwareMap,"Dist");
        driveTrain = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grabber = new StoneCollector(hardwareMap,"Grabber1","Grabber2","Touch_Sensor");

        mover  = new FoundationMover(hardwareMap,"Servo1","Servo2");
        runtime = new ElapsedTime();

        finder.activate();

        telemetry.addData("Status","Ready");
        telemetry.update();



        waitForStart();
        motor.setPower(0.5);
        sleep(500);
        motor.setPower(0);


        driveTrain.goToPositions(encodersFromDistance(sideways,driveTo),drivePower);
        double angle = finder.angleToSkyStone();
        while (angle == 390){
            angle = finder.angleToSkyStone();
            telemetry.addData("Status","Looking" + runtime.seconds());
            telemetry.update();
        }
        double distanceX = (23.5*0.0254)*Math.tan(Math.toRadians(angle)) -4*0.0254;// - 4*0.0254;

        telemetry.addData("Distance", distanceX);
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,distanceX),drivePower);

        telemetry.addData("Status", "In Line, rotating");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        grabber.activate(true);
        telemetry.addData("Status","grabbing");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,34*0.0254),drivePower);
        double start = runtime.seconds();
        while(!grabber.checkObtained() && runtime.seconds() < start + 0.2){

        }
        grabber.stop();
        telemetry.addData("Status","getting back");
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(forward,-19*0.0254),drivePower);


        while(finder.getInfo() == null){

        }
        sleep(700);
        double[] data = finder.getInfo();
        double targetAngle = (data[1]/Math.abs(data[1]))*(180 - Math.abs(data[1]));
        telemetry.addData("Status","Going for foundation" + String.valueOf(data[1]) + "\n"  +String.valueOf(targetAngle));
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-(90 - targetAngle))),drivePower);
        double dist = (data[0] - 44)*0.0254;
        driveTrain.goToPositions(encodersFromDistance(forward,-dist),drivePower);
        /*
        motor.setPower(-0.5);
        sleep(500);
        motor.setPower(0);

         */


        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-90)),drivePower);

        while(finder.getInfo() == null){

        }
        sleep(200);
        data = finder.getInfo();
        double y = data[2];
        telemetry.addData("Status","Grabbing " + y);
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(forward,-(-32.6-y)*0.0254),drivePower);
        mover.toggle();
        sleep(400);
        driveTrain.setPower(drivePower,0.5,0,0);
        sleep(1000);
        driveTrain.setPower(drivePower,0.5,0,0.5);
        sleep(1200);
        driveTrain.setPower(drivePower,0,0,0);
        mover.toggle();

        driveTrain.goToPositions(encodersFromDistance(diagonal,-50*0.0254),1);

    }


    private int[] encodersFromDistance(int[] direction,double distance){
        int[] positions = new int[direction.length];
        int encoder = (int) (distance*TICKSPERREV/(Math.PI*WHEEL_DIAMETER));
        for(int i = 0;i<positions.length;i++){
            positions[i] = direction[i]*encoder;
        }
        return positions;
    }

    private double rotationDistance(double theta){
        return  Math.toRadians(theta)*Math.sqrt(Math.pow(CAR_WIDTH/2,2) + Math.pow(CAR_HEIGHT/2,2));

    }
}
