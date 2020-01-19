package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class AutonomousMode extends LinearOpMode {
    Vision finder;
    DcMotor motor;
    MecanumDriveTrain driveTrain;
    StoneCollector grabber;
    //FoundationMover mover;
    ElapsedTime runtime;

    int[] forward = {-1,1,-1,1};
    int[] sideways = {1,1,-1,-1};
    int[] rotation = {1,1,1,1};
    int[] diagonal = {1,0,0,-1};
    static final int TICKSPERREV = 1120;
    static final double WHEEL_DIAMETER = 0.075,CAR_WIDTH = 16.5*0.0254,CAR_HEIGHT = 16*0.0254;



    double driveTo = (23.5)*0.0254 - CAR_WIDTH;
    double drivePower = 1;

    protected void initialize(){
        motor = hardwareMap.get(DcMotor.class,"Motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        finder = new Vision(hardwareMap,"Dist");
        driveTrain = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grabber = new StoneCollector(hardwareMap,"Grabber1","Grabber2","Touch_Sensor");

        //mover  = new FoundationMover(hardwareMap,"Servo1","Servo2");
        runtime = new ElapsedTime();

        finder.activate();

        telemetry.addData("Status","Ready");
        telemetry.update();
    }

    protected void lowerArm(double p){
        motor.setTargetPosition((int) (1700*p));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(motor.isBusy()){
            motor.setPower(0.5);
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    protected void raiseArm(double p){
        motor.setTargetPosition((int) (-1700*p));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(motor.isBusy()){
            motor.setPower(0.5);
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    protected void ejectBlock(){
        grabber.activate(false);
        sleep(700);
        grabber.stop();
    }

    protected void lineUp(){
        driveAndMoveArm(encodersFromDistance(sideways,driveTo),drivePower,1700);
        //driveTrain.goToPositions(encodersFromDistance(sideways,driveTo),drivePower);
        double start = runtime.seconds();
        double angle = finder.angleToSkyStone();
        boolean stopped = false;
        while (angle == 390 &&opModeIsActive()){
            angle = finder.angleToSkyStone();
            telemetry.addData("Status","Looking" + runtime.seconds());
            telemetry.update();
            if (runtime.seconds() >= start + 3){
                stopped = true;
                break;
            }
        }
        double distanceX = 0;
        if (!stopped){
            distanceX = (23.5 * 0.0254) * Math.tan(Math.toRadians(angle));
            if (distanceX < 0){
                distanceX -= 3*0.0254;
            }
            else{
                distanceX += 0.0254;
            }

        }


        telemetry.addData("Distance", distanceX);
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,distanceX),drivePower);
    }

    protected void getBlock(){
        telemetry.addData("Status", "In Line, rotating");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        grabber.activate(true);
        telemetry.addData("Status","grabbing");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,33*0.0254),drivePower);
        double start = runtime.seconds();
        while(!grabber.checkObtained() && runtime.seconds() < start + 0.5 &&opModeIsActive()){

        }
        grabber.stop();
        telemetry.addData("Status","getting back");
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(forward,-18*0.0254),drivePower);
    }

    protected void goBuildSite(double distance){
        while(finder.getInfo() == null &&opModeIsActive()){

        }
        sleep(700);
        double[] data = finder.getInfo();
        double targetAngle = (data[1]/Math.abs(data[1]))*(180 - Math.abs(data[1]));
        telemetry.addData("Status","Going for foundation" + String.valueOf(data[1]) + "\n"  +String.valueOf(targetAngle));
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-(90 - targetAngle))),drivePower);
        double dist = (data[0] - distance)*0.0254;
        driveTrain.goToPositions(encodersFromDistance(forward,-dist),drivePower);
    }

    protected void approachFoundation(){
        double distance = finder.getDistance();
        while(distance > 2 &&opModeIsActive()){
            distance = finder.getDistance();
        }

        telemetry.addData("Status","Grabbing " + distance);
        telemetry.update();
        driveAndMoveArm(encodersFromDistance(rotation,rotationDistance(90)),drivePower,-850);
        //driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(forward,distance),drivePower);
    }

    protected void moveFoundation(int direction){
        lowerArm(0.5);
        driveTrain.goToPositions(encodersFromDistance(forward,-(24.5*2*0.0254 - CAR_HEIGHT)),drivePower);
        raiseArm(0.7);
        grabber.activate(false);


        driveTrain.goToPositions(encodersFromDistance(sideways,direction*23*0.0254),drivePower);
        grabber.stop();
        driveTrain.goToPositions(encodersFromDistance(forward,23*0.0254),drivePower);
        driveTrain.goToPositions(encodersFromDistance(sideways,direction*23*0.0254),drivePower);

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

    protected void park(){
        driveTrain.goToPositions(encodersFromDistance(diagonal,-50*0.0254),1);
    }
    @Override
    public void runOpMode(){

    }

    protected int[] encodersFromDistance(int[] direction,double distance){
        int[] positions = new int[direction.length];
        int encoder = (int) (distance*TICKSPERREV/(Math.PI*WHEEL_DIAMETER));
        for(int i = 0;i<positions.length;i++){
            positions[i] = direction[i]*encoder;
        }
        return positions;
    }

    protected double rotationDistance(double theta){
        return  Math.toRadians(theta)*Math.sqrt(Math.pow(CAR_WIDTH/2,2) + Math.pow(CAR_HEIGHT/2,2));

    }

    protected void driveAndMoveArm(int[] targets,double power,int target){
        ArrayList<DcMotor> toMove = new ArrayList<>();
        for(int i = 0;i<driveTrain.motors.size();i++){
            DcMotor m = driveTrain.motors.get(i);
            m.setTargetPosition(m.getCurrentPosition() + targets[i]);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            toMove.add(m);

        }
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        toMove.add(motor);


        int busy = 5;
        while(busy > 0 && opModeIsActive()){
            busy = 5;
            for (DcMotor motor:toMove){
                if (motor.isBusy()){
                    motor.setPower(power);
                }
                else{
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    busy -= 1;
                }
            }
        }
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
