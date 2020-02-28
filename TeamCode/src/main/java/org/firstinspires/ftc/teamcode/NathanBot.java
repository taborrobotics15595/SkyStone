package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class NathanBot extends LinearOpMode {
    ElapsedTime runtime;
    DcMotor drive1,drive2,out1,out2;
    CRServo servo;

    double drivePower = 0.5;


    boolean turning = false;
    double time = 0;
    @Override
    public void runOpMode(){
        runtime = new ElapsedTime();
        drive1 = hardwareMap.get(DcMotor.class,"Motor1");
        drive2 = hardwareMap.get(DcMotor.class,"Motor2");
        out1 = hardwareMap.get(DcMotor.class,"Out1");
        out2 = hardwareMap.get(DcMotor.class,"Out2");

        servo = hardwareMap.get(CRServo.class,"Servo");

        waitForStart();
        while(opModeIsActive()){
            double fwd = Range.clip(gamepad1.left_stick_y,-drivePower,drivePower);
            double turn = Range.clip(gamepad1.right_stick_x,-drivePower,drivePower);

            drive1.setPower(turn - fwd);
            drive2.setPower(fwd + turn);

            double shoot = gamepad1.right_trigger;
            out1.setPower(-shoot);
            out2.setPower(shoot);


            if (gamepad1.a){
                turning = true;
                servo.setPower(1);
                time = runtime.seconds();
            }

            if (turning){
                if (runtime.seconds() >= time + 1){
                    servo.setPower(0);
                    turning = false;
                }
            }



        }
    }
}
