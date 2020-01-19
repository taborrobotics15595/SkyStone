package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class DriveTainCheck extends AutonomousMode {

    DriveTrain driveTrain;

    @Override
    public void runOpMode(){
        driveTrain = new DriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();

        driveTrain.goToPositions(encodersFromDistance(forward,1),1);
        driveTrain.goToPositions(encodersFromDistance(rotation,rotationDistance(90)),1);
        driveTrain.goToPositions(encodersFromDistance(sideways,1),1);

    }
}
