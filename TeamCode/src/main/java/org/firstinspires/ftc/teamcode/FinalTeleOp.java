package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class FinalTeleOp extends LinearOpMode {
    MecanumDriveTrain robot;
    FoundationMover mover;
    StoneCollector collector;
    ElapsedTime runtime;
    DcMotor motor;
    private double[] powers = {1,0.4};
    private int index = 0;
    private double power = powers[index];
    private int mode = 1;

    int[] current;

    double powerY,powerX,turn;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new MecanumDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor = hardwareMap.get(DcMotor.class,"Motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mover = new FoundationMover(hardwareMap,"Servo1","Servo2");

        collector = new StoneCollector(hardwareMap,"Grabber1","Grabber2","Touch_Sensor");
        runtime = new ElapsedTime();
        boolean going = false;
        waitForStart();

        collector.moveServo();
        while (opModeIsActive()) {
            powerY = -mode*Range.clip(gamepad1.left_stick_y, -power, power);
            powerX = mode*Range.clip(gamepad1.left_stick_x, -power, power);
            turn = Range.clip(gamepad1.right_stick_x, -power, power);

            robot.setPower(power,powerY,powerX,turn);

            if (gamepad2.left_bumper){
                motor.setPower(0.5);
            }
            else if(gamepad2.right_bumper){
                motor.setPower(-0.5);
            }
            else{
                motor.setPower(0);
            }

            if (gamepad2.b){
                mover.toggle();
                Thread.sleep(400);
            }


            if (gamepad2.a){
                collector.activate(true);
                Thread.sleep(400);
            }

            if (gamepad2.x){
                going = !going;
                if (going){
                    collector.stop();
                }
                else{
                    collector.activate(false);
                }
                Thread.sleep(400);
            }

            if (gamepad1.y){
                mode *= -1;
            }
            collector.checkObtained();

            current = robot.getCurrentPositions();
            String message = "Current positions: ";
            for(int i:current){
                message += i + " ";
            }
            telemetry.addData("Drive Train information:",message);
            telemetry.update();




        }
    }
}
