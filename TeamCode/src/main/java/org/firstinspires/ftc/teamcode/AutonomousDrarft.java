package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class AutonomousDrarft extends LinearOpMode {
    MecanumDriveTrain driveTrain;
    FoundationMover mover;
    StoneCollector grabber;
    Vision vision;
    ElapsedTime runtime;

    String trackable = "Front Perimeter 1";

    static final int TICKSPERREV = 1120;
    static final double WHEEL_DIAMETER = 0.075;
    final double APPROACH_DISTANCE = 0.42,SEARCH_DISTANCE = 0.3,ATTACK_DISTANCE = 0.42;

    int[] SEARCH = encodersFromDistance(new int[]{1,-1,1,-1},SEARCH_DISTANCE);//{700,-700,700,-700};
    int[] APPROACH_TARGET_VALUES =  encodersFromDistance(new int[]{1,1,-1,-1},APPROACH_DISTANCE);//{2700,2700,-2700,-2700};
    int[] ATTACK_VALUES = encodersFromDistance(new int[]{1,-1,1,-1},ATTACK_DISTANCE);
    int[] ROTATE = {2200,2200,2200,2200};
    int[] FOUNDATION;

    double maxDrivePower = 0.8;
    @Override
    public void runOpMode() throws InterruptedException {

        driveTrain = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mover = new FoundationMover(hardwareMap,"Servo1","Servo2");
        grabber = new StoneCollector(hardwareMap,"Grabber1","Grabber2","Touch_Sensor");

        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        vision = new Vision(hardwareMap);
        vision.activate();

        telemetry.addData("Status","Ready to start");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status","moving to position");
        telemetry.update();
        move(maxDrivePower,APPROACH_TARGET_VALUES); //move up

        int lastDirection = 1;

        double degrees = vision.angleToSkyStone();
        while(Math.abs(degrees) > 3 && opModeIsActive()){
            degrees = vision.angleToSkyStone();
            if (degrees != 390){
                //go towards direction slightly
                int[] search = encodersFromDistance(new int[]{1,-1,1,-1},0.375*Math.tan(Math.toRadians(degrees)));
                telemetry.addData("Status", 0.375*Math.tan(Math.toRadians(degrees)));
                telemetry.update();
                sleep(500);
                move(maxDrivePower,search);


            }
            else{
                telemetry.addData("Status", "not detected");
                telemetry.update();
                find(maxDrivePower,multiply(SEARCH,lastDirection),"Skystone"); //move left and right
                lastDirection *= -1;


            }

            sleep(100);


        }
        telemetry.addData("Status","Rotating");
        telemetry.update();
        sleep(1000);

        //move(maxDrivePower,ROTATE);

        telemetry.addData("Status","Attacking");
        telemetry.update();
        //move(maxDrivePower,ATTACK_VALUES);

        // alignWithTrackable(this.trackable);
        //move(maxDrivePower,FOUNDATION);




        vision.stop();
    }

    private void alignWithTrackable(String trackable){
        double[] info = vision.findTrackableInfo(trackable);
        while(Math.abs(info[0] + 35) > 2 && Math.abs(info[1]) > 4){
            info = vision.findTrackableInfo(trackable);
            int direction = (int) ((info[0] + 35)/Math.abs(info[0] + 35));
            int rotationDirection = (int)(info[1]/Math.abs(info[1]));
            telemetry.addData("Go to:",direction + "Ritate to:" + rotationDirection);
            telemetry.update();
        }


    }

    private int[] encodersFromDistance(int[] direction,double distance){
        int[] positions = new int[direction.length];
        int encoder = (int) (distance*TICKSPERREV/(Math.PI*WHEEL_DIAMETER));
        for(int i = 0;i<positions.length;i++){
            positions[i] = direction[i]*encoder;
        }
        return positions;
    }
    private int[] multiply(int[] matrix,int s){
        int[] newMatrix = new int[matrix.length];
        for(int i = 0;i<matrix.length;i++){
            newMatrix[i] = matrix[i]*s;
        }
        return newMatrix;
    }
    private void move(double power,int[] positions){
        driveTrain.goToPositions(positions,power);

    }

    private void find(double power,int[] positions,String name){
        driveTrain.driveOnTheLookOut(positions,power,vision,name);
    }
}
