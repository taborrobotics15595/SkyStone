package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    MecanumDriveTrain robot;

    private double power = 0.7;
    private int mode = 1;


    double powerY,powerX,turn;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new MecanumDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        //robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        while (opModeIsActive()) {
            powerY = -mode*Range.clip(gamepad1.left_stick_y, -power, power);
            powerX = mode*Range.clip(gamepad1.left_stick_x, -power, power);
            turn = Range.clip(gamepad1.right_stick_x, -power, power);

            robot.setPower(power,powerY,powerX,turn);


            if (gamepad1.y){
                mode *= -1;
            }





        }
    }
}
