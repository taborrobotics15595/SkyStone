package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class DriveTrainHolonomic extends LinearOpMode {
    HolonomicDriveTrain robot;
    ElapsedTime runtime;
    RevBlinkinLedDriver driver;

    private double maxPower = 0.3;

    int[] target1 = {500,-500,500,-500};
    int[] target2 = {500,500,-500,-500};
    int[] target3 = {280,280,280,280};

    double powerY,powerX,turn;


    @Override
    public void runOpMode() {
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        runtime = new ElapsedTime();

        driver = hardwareMap.get(RevBlinkinLedDriver.class,"Driver");

        waitForStart();
        while (opModeIsActive()) {
            powerY = -Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);
            turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);

            if ((powerX + powerY + turn) != 0){
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_LAVA_PALETTE);

            }
            else{
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_OCEAN_PALETTE);
            }

            robot.setPower(maxPower,powerY,powerX,turn);

            if (gamepad1.a){
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.SHOT_BLUE);
                robot.goToPositions(target1,0.3);
            }
            if (gamepad1.b){
                robot.goToPositions(target2,0.3);
            }
            if (gamepad1.x){
                robot.goToPositions(target3,0.3);
            }
            if (gamepad1.y){
                robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            int[] current = robot.getCurrentPositions();
            String message = formatString("Positions:",current);
            telemetry.addData("Time:", message);
            telemetry.update();
        }
    }

    private String formatString(String pre,int[] positions){
        String m = "";
        for(int p:positions){
            m += pre + p;
        }
        return m;
    }
}
