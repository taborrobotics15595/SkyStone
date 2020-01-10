package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class StoneCollector {
    private ArrayList<DcMotor> motors;
    private DigitalChannel sensor;
    Servo s1,s2;

    private final double[] POWERS = {0.5,0.6,0.7};
    private int index = 0;
    private double maxPower = POWERS[index];
    private double currentPower = 0;

    public StoneCollector(HardwareMap hardwareMap,String motor1,String motor2,String sensorName){
        this.motors = new ArrayList<>();
        this.motors.add(hardwareMap.get(DcMotor.class,motor1));
        this.motors.add(hardwareMap.get(DcMotor.class,motor2));
        this.sensor = hardwareMap.get(DigitalChannel.class,sensorName);
        this.sensor.setMode(DigitalChannel.Mode.INPUT);
        s1 = hardwareMap.get(Servo.class,"ServoL");
        s2 = hardwareMap.get(Servo.class,"ServoR");
    }

    public void moveServo(){
        s1.setPosition(1);
        s2.setPosition(0);
    }
    private void setMotorPower(double power){
        motors.get(0).setPower(-power);
        motors.get(1).setPower(power);
    }

    public void activate(boolean forward){
        currentPower = (forward)?maxPower:-maxPower;
        setMotorPower(currentPower);
    }

    public double getPower(){
        return maxPower;
    }
    public void changePower(){
        index = (index + 1)%POWERS.length;
        maxPower = POWERS[index];
    }

    public boolean checkObtained(){
        if (!sensor.getState()) {
            stop();
            return true;
        }
        return false;
    }

    public void stop(){
        currentPower = 0;
        setMotorPower(0);
    }

}
