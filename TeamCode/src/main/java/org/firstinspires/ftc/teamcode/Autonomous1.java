package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class Autonomous1 extends LinearOpMode {
    Vision finder;
    MecanumDriveTrain driveTrain;
    StoneCollector grabber;
    ElapsedTime runtime;

    int[] forward = {-1,1,-1,1};
    int[] sideways = {1,1,-1,-1};
    static final int TICKSPERREV = 1120;
    static final double WHEEL_DIAMETER = 0.075;

    double driveTo = (23.5 - 16.9)*0.0254;
    double drivePower = 0.7;

    String trackable = "Front Perimeter 1";



    @Override
    public void runOpMode(){
        finder = new Vision(hardwareMap);
        driveTrain = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grabber = new StoneCollector(hardwareMap,"Grabber1","Grabber2","Touch_Sensor");

        runtime = new ElapsedTime();

        finder.activate();

        telemetry.addData("Status","Ready");
        telemetry.update();

        waitForStart();
        grabber.moveServo();
        int[] enc = encodersFromDistance(sideways,driveTo);
        driveTrain.goToPositions(enc,drivePower);
        double angle = finder.angleToSkyStone();
        while (angle == 390){
            angle = finder.angleToSkyStone();
            telemetry.addData("Status","Looking" + runtime.seconds());
            telemetry.update();
        }
        double distanceX = (25*0.0254)*Math.tan(Math.toRadians(angle)) + 2*0.0254*(angle/Math.abs(angle));
        telemetry.addData("Distance", distanceX);
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(forward,distanceX),drivePower);

        telemetry.addData("Status", "In Line, rotating");
        telemetry.update();

        driveTrain.goToPositions(new int[]{2200,2200,2200,2200},drivePower);
        grabber.activate(true);
        telemetry.addData("Status","grabbing");
        telemetry.update();

        driveTrain.goToPositions(encodersFromDistance(forward,30*0.0254),drivePower);

        while(!grabber.checkObtained()){

        }
        telemetry.addData("Status","getting back");
        telemetry.update();
        driveTrain.goToPositions(encodersFromDistance(forward,-15*0.0254),drivePower);

        while(finder.findTrackableInfo(trackable) == null){

        }
        telemetry.addData("Status","Going for foundation");
        telemetry.update();
        double d =(finder.findTrackableInfo(trackable)[0] - 46)*0.0254;
        driveTrain.goToPositions(encodersFromDistance(sideways,d),drivePower);
    }


    private int[] encodersFromDistance(int[] direction,double distance){
        int[] positions = new int[direction.length];
        int encoder = (int) (distance*TICKSPERREV/(Math.PI*WHEEL_DIAMETER));
        for(int i = 0;i<positions.length;i++){
            positions[i] = direction[i]*encoder;
        }
        return positions;
    }
}
