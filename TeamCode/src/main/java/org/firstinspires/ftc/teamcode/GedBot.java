package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class GedBot extends LinearOpMode {
    DcMotor drive1,drive2,turn;

    double maxPower = 1,turnPower = 0.3;

    @Override
    public void runOpMode(){
        drive1 = hardwareMap.get(DcMotor.class,"Drive1");
        drive2 = hardwareMap.get(DcMotor.class,"Drive2");
        turn = hardwareMap.get(DcMotor.class,"Turn");

        waitForStart();
        while(opModeIsActive()){
            double leftP = Range.clip(gamepad1.left_stick_y  ,-maxPower,maxPower);
            double rightP = -Range.clip(gamepad1.right_stick_y,-maxPower,maxPower);
            double turnP = 0;
            if (gamepad1.right_bumper){
                turnP = turnPower;
            }
            else if (gamepad1.left_bumper){
                turnP = -turnPower;
            }
            drive1.setPower(leftP );
            drive2.setPower(rightP );
            turn.setPower(turnP);

        }
    }
}
