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

    int[] SLIDE_TARGET_VALUES;
    int[] APPROACH_TARGET_VALUES;
    int[] ATTACK_VALUES;
    int[] ROTATE;

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

        waitForStart();
        driveToSkystone();
        while(opModeIsActive()){
            telemetry.addData("Visible:",vision.findTrackable());
            telemetry.update();
        }

    }

    private void driveToSkystone() throws InterruptedException {
        move(maxDrivePower,"forward");
        int lastDirection = 0;
        while (Math.abs(vision.angleToSkyStone()) > 5){
            double degrees = vision.angleToSkyStone();
            if (degrees != 390){
                //go towards direction slightly
                int direction = (int)(degrees/Math.abs(degrees));
                move(direction*maxDrivePower,String.valueOf(direction));
                Thread.sleep(1000);
            }
            else{
                move(maxDrivePower,"Find something");
                Thread.sleep(1000);
            }
        }

        move(maxDrivePower,"turn");
        Thread.sleep(1000);
        //grabber.activate(true);
        move(maxDrivePower,"attacj");
        Thread.sleep(1000);
        move(-maxDrivePower,"back");
        Thread.sleep(1000);


    }

    private void move(double power,String direction){
        telemetry.addData("Directions",direction);
        telemetry.update();
    }
}
