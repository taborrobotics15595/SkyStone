package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Autonomous
public class WillBot extends LinearOpMode {
    DcMotor drive1,drive2,drive3,drive4;
    DcMotor intake,shoot;
    Servo flip;

    double maxDrivePower = 0.5;
    double shootPower = 0.5;
    double inPower = 0.5;

    boolean intaking = false;

    double[] servoPositions = {0,0.5};
    int index = 0;
    @Override
    public void runOpMode(){
        drive1 = hardwareMap.get(DcMotor.class,"Motor1");
        drive2 = hardwareMap.get(DcMotor.class,"Motor2");
        drive3 = hardwareMap.get(DcMotor.class,"Motor3");
        drive4 = hardwareMap.get(DcMotor.class,"Motor4");

        intake = hardwareMap.get(DcMotor.class,"Intake");
        shoot = hardwareMap.get(DcMotor.class,"Shoot");

        flip = hardwareMap.get(Servo.class,"Flip");
        flip.setPosition(servoPositions[index]);

        waitForStart();
        while(opModeIsActive()){
            double power = Range.clip(gamepad1.left_stick_y + gamepad1.left_stick_x,-maxDrivePower,maxDrivePower);
            drive1.setPower(power);
            drive2.setPower(-power);
            drive3.setPower(power);
            drive4.setPower(-power);

            if (gamepad1.right_trigger > 0){
                shoot.setPower(shootPower);
            }
            else{
                shoot.setPower(0);
            }

            if (gamepad1.a){
                if (intaking){
                    intake.setPower(inPower);
                }
                else{
                    intake.setPower(0);
                }
                intaking = !intaking;
                sleep(200);
            }

            if (gamepad1.b){
                flip.setPosition(servoPositions[index]);
                index = (index + 1)%2;
            }
        }
    }
}
