package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.Autonomous1.CAR_HEIGHT;
import static org.firstinspires.ftc.teamcode.Autonomous1.CAR_WIDTH;
import static org.firstinspires.ftc.teamcode.Autonomous1.TICKSPERREV;
import static org.firstinspires.ftc.teamcode.Autonomous1.WHEEL_DIAMETER;

@Autonomous
public class Foundation extends LinearOpMode {
    DriveTrain driveTrain;
    FoundationMover mover;
    Vision vision;

    double power = 1;
    @Override
    public void runOpMode(){
        driveTrain = new DriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mover = new FoundationMover(hardwareMap,"Servo1","Servo2");

        vision = new Vision(hardwareMap,"Dist");
        vision.activate();
        telemetry.addData("Status:","Ready");
        telemetry.update();

        waitForStart();

        while(vision.getInfo() == null){

        }
        double[] data = vision.getInfo();
        telemetry.addData("Position: ","X: " + data[0] + "Angle: " + data[1]);
        telemetry.update();
        mover.toggle();
        sleep(200);

        driveTrain.goToPositions(encodersFromDistance(new int[]{-1,1,-1,1},18*0.0254),power);
        driveTrain.goToPositions(encodersFromDistance(new int[]{-1,-1,-1,-1},rotationDistance(45)),power);
        driveTrain.goToPositions(encodersFromDistance(new int[]{-1,1,-1,1},5*0.0254),power);
        driveTrain.goToPositions(encodersFromDistance(new int[]{-1,-1,-1,-1},rotationDistance(140)),power);




    }

    private int[] encodersFromDistance(int[] direction,double distance){
        int[] positions = new int[direction.length];
        int encoder = (int) (distance*TICKSPERREV/(Math.PI*WHEEL_DIAMETER));
        for(int i = 0;i<positions.length;i++){
            positions[i] = direction[i]*encoder;
        }
        return positions;
    }

    private double rotationDistance(double theta){
        return  Math.toRadians(theta)*Math.sqrt(Math.pow(CAR_WIDTH/2,2) + Math.pow(CAR_HEIGHT/2,2));

    }

}
