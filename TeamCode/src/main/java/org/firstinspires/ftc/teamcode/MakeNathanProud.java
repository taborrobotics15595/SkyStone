package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MakeNathanProud extends LinearOpMode {
    double max = 1;

    @Override
    public void runOpMode(){
        DcMotor motor = hardwareMap.get(DcMotor.class,"Motor");
        DcMotor motor1 = hardwareMap.get(DcMotor.class,"Motor2");

        waitForStart();
        while(opModeIsActive()){
            double power = Range.clip(gamepad1.left_stick_y,-max,max);
            double power2 = Range.clip(gamepad1.right_stick_y,-max,max);
            motor.setPower(power);
            motor1.setPower(power2);
        }

    }
}
