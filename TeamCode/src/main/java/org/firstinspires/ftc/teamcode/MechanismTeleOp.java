package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MechanismTeleOp extends LinearOpMode {
    StoneCollector collector;
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {
        collector = new StoneCollector(hardwareMap,"Grabber1","Grabber2","Touch_Sensor");
        motor = hardwareMap.get(DcMotor.class,"Motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        boolean going = false;
        waitForStart();
        while(opModeIsActive()){

            if (gamepad1.a){
                collector.activate(true);
                Thread.sleep(400);
            }

            if(gamepad1.left_bumper){
                motor.setPower(0.5);
            }
            else if (gamepad1.right_bumper){
                motor.setPower(-0.5);
            }
            else{
                motor.setPower(0);
            }



            if (gamepad1.x){
                going = !going;
                if (going){
                    collector.stop();
                }
                else{
                    collector.activate(false);
                }
                Thread.sleep(400);
            }
            collector.checkObtained();


        }
    }
}
