package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MechanismTeleOp extends LinearOpMode {
    StoneCollector collector;

    @Override
    public void runOpMode() throws InterruptedException {
        collector = new StoneCollector(hardwareMap,"Motor1","Motor2","Sensor");
        boolean going = false;
        waitForStart();
        while(opModeIsActive()){

            if (gamepad1.a){
                collector.activate(true);
                Thread.sleep(400);
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
            idle();
        }
    }
}
