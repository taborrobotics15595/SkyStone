package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MakeNathanProud extends LinearOpMode {

    @Override
    public void runOpMode(){
        DcMotor motor = hardwareMap.get(DcMotor.class,"Motor");

        waitForStart();
        while(opModeIsActive()){
            double power = Range.clip(gamepad1.left_stick_y,-0.6,0.6);
            motor.setPower(power);
        }

    }
}
