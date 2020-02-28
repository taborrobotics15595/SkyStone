package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class EncoderCheck extends LinearOpMode {
    DcMotor motor;
    double power = 0.8;
    int target = 0,current = 0;

    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"Extend");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while(opModeIsActive()){
            double p = Range.clip(gamepad1.left_stick_y,-power,power);
            motor.setPower(p);

            if (gamepad1.a){
                target = motor.getCurrentPosition();
            }

            if (gamepad1.x){
                motor.setTargetPosition(target);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(power);
                while(motor.isBusy()){

                }
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                motor.setPower(0);


            }
            current = motor.getCurrentPosition();
            telemetry.addData("Motor status: ","Current Position: " + current + "Target: " + target);
            telemetry.update();
        }

    }

}
