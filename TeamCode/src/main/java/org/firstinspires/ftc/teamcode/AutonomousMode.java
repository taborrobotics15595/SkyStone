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
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        finder = new Vision(hardwareMap,"Dist","Dist2");
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
        sleep(900);
        grabber.stop();
    }

    protected void lineUp(){
        grabber.activate(false);
        driveAndMoveArm(encodersFromDistance(sideways,driveTo),drivePower,1700);
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
        String message = "";
        if (!stopped){
            distanceX = (22.75 * 0.0254) * Math.tan(Math.toRadians(angle));
            message = "First: " + distanceX;
            if (distanceX > 0.15){
                distanceX = 0.2;
            }
            else if (distanceX < -0.01){
                distanceX = -0.22;
            }
            else{
                distanceX = 0;
            }

        }

        telemetry.addData("Distance", message + "Second: " + distanceX);
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,distanceX),drivePower);
    }

    protected void getBlock(int direction){
        telemetry.addData("Status", "In Line, rotating");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        grabber.activate(true);
        telemetry.addData("Status","grabbing");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,35*0.0254),drivePower);
        double start = runtime.seconds();
        while(!grabber.checkObtained() && runtime.seconds() < start + 0.5 &&opModeIsActive()){

        }
        grabber.stop();
        telemetry.addData("Status","getting back");
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(forward,-18.5*0.0254),drivePower);
    }

    protected void goBuildSite(double distance,double direction){
        /*
        double d = finder.getDistance();
        double angle = direction*finder.getAngle();
        telemetry.addData("Status:","Angle: " + angle + " Distance: " + d);
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(-direction*(90 - angle))),drivePower);
        driveTrain.goToPositions(encodersFromDistance(forward,direction*((72  + distance)*0.0254 - CAR_WIDTH/2 - d)),drivePower);
        */
        boolean stopped =false;
        double start= runtime.seconds();
        while(finder.getInfo() == null &&opModeIsActive()){
            if (runtime.seconds()>=start + 3){
                stopped = true;
                break;
            }
        }
        sleep(700);
        double[] data = finder.getInfo();

        double targetAngle= 0;
        double dist = 0;
        if(stopped){
            targetAngle = -90;
            dist = -88*0.0254;
        }
        else{
            targetAngle = -90 + (Math.abs(data[1])/data[1])*(data[1]);
            dist = (data[0] - distance)*0.0254;
        }
        telemetry.addData("Status","Going for foundation" + targetAngle);
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(targetAngle)),drivePower);

        driveAndMoveArm(encodersFromDistance(forward,-direction*dist),drivePower,-850);


    }

    protected void approachFoundation(){
        double distance = finder.getDistance();

        double angle = finder.getAngle();


        telemetry.addData("Status:","Angle: " + angle + " Distance: " + distance);
        telemetry.update();

        if (Double.isNaN(angle)){
            angle = 0;
            distance = finder.getOneDistance();
        }
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90 + angle)),drivePower);
        //driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),drivePower);
        driveTrain.goToPositions(encodersFromDistance(forward,distance),drivePower);
        ejectBlock();
    }

    protected void moveFoundation(int direction){
        lowerArm(0.5);
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

    protected void park(int direction){
        driveAndMoveArm(encodersFromDistance(forward,-35*0.0254),drivePower,-1200);


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

    public double rotationDistance(double theta){
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

        String motors = "";
        int busy = 5;
        while(busy > 0 && opModeIsActive()){
            busy = 5;
            motors = "";
            for (DcMotor motor:toMove){
                if (motor.isBusy()){
                    motor.setPower(power);
                    motors += "Motor " +toMove.indexOf(motor) + "is busy";
                }
                else{
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    busy -= 1;
                }
            }
            telemetry.addData("Motors",motors);
            telemetry.update();
        }
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
