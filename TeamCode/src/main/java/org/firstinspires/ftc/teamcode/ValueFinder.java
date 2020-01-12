package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ValueFinder extends LinearOpMode {
    Vision finder;
    MecanumDriveTrain driveTrain;

    int[] current = new int[4],target = new int[4];
    double maxPower = 0.8,angle;


    @Override
    public void runOpMode(){
        finder = new Vision(hardwareMap,"Dist");
        finder.activate();

        driveTrain = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while(opModeIsActive()){
            double xPower = gamepad1.left_stick_x;
            double yPower = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            driveTrain.setPower(maxPower,yPower,xPower,turn);


            current = driveTrain.getCurrentPositions();
            String message = "Current positions: ";
            for(int i:current){
                message += i + " ";
            }
            message += "Target positions: ";
            for(int i:target){
                message += i + " ";
            }

            if (gamepad1.x){
                driveTrain.goToPositions(target,maxPower);
            }
            if (gamepad1.a){
                target = current;
            }



            /*
            angle = finder.angleToSkyStone();

            if (angle != 390){
                message += "Angle: " + angle;
            }

            else{
                message += "can't see";
            }
            */
            double[] data = finder.getInfo();
            if (data != null){
                message += "Distance: " + data[0] + "Angle: " + data[1];
            }


            telemetry.addData("Status:",message);
            telemetry.update();
        }
    }



}
