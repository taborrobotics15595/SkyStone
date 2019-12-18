package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class AutonomousDrarft extends LinearOpMode {
    MecanumDriveTrain driveTrain;
    FoundationMover mover;
    StoneCollector grabber;
    Vision vision;

    String trackable = "Front Perimeter 1";
    int[] SLIDE_TARGET_VALUES;
    int[] APPROACH_TARGET_VALUES;
    int[] ATTACK_VALUES;
    int[] ROTATE;
    int[] FOUNDATION;

    double maxDrivePower = 0.7;
    @Override
    public void runOpMode() throws InterruptedException {
        /*
        driveTrain = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mover = new FoundationMover(hardwareMap,"Servo1","Servo2");
        grabber = new StoneCollector(hardwareMap,"Grabber1","Grabber2","TouchSensor");

        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

         */
        vision = new Vision(hardwareMap);
        vision.activate();
        waitForStart();
        //driveToSkystone();


        alignWithTrackable(this.trackable);
        move(maxDrivePower,FOUNDATION);

        vision.stop();
    }

    private void driveToSkystone() throws InterruptedException {
        move(maxDrivePower,APPROACH_TARGET_VALUES); //move up
        int lastDirection = 0;
        while (Math.abs(vision.angleToSkyStone()) > 5){
            double degrees = vision.angleToSkyStone();
            if (degrees != 390){
                //go towards direction slightly
                int direction = (int)(degrees/Math.abs(degrees)); //left or right absed on sign
                move(direction*maxDrivePower,SLIDE_TARGET_VALUES);
            }
            else{
                move(lastDirection*maxDrivePower,SLIDE_TARGET_VALUES); //move left and right
                lastDirection *= -1;
            }
        }

        move(maxDrivePower,ROTATE);
        //grabber.activate(true);
        move(maxDrivePower,ATTACK_VALUES);
        move(-maxDrivePower,ATTACK_VALUES);


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
    private void move(double power,int[] positions){
        driveTrain.goToPositions(positions,power);
    }
}
