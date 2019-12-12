package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

public class DriveTrain {
    ArrayList<DcMotor> motors = new ArrayList<>();

    double[] forward = new double[4];
    double[] sideways = new double[4];
    double[] turn = new double[4];

    public DriveTrain(HardwareMap hardwareMap,String ... names){
        for(String name:names){
            DcMotor motor = hardwareMap.get(DcMotor.class,name);
            this.motors.add(motor);
        }
    }

    public void setPower(double max,double powerY,double powerX,double powerTurn){
        double[] yComp = multiplyLists(forward,powerY);
        double[] xComp = multiplyLists(sideways,powerX);
        double[] turnComp = multiplyLists(turn,powerTurn);
        double[] total = addLists(yComp,xComp,turnComp);
        applyPower(total,max);
    }


    public void applyPower(double[] power,double max){
        for(int i = 0;i<motors.size();i++){
            DcMotor motor = motors.get(i);
            double powerA  =Range.clip(power[i],-max,max);
            motor.setPower(powerA);

        }
    }

    public double[] multiplyLists(double[] list,double scalar){
        double[] newList = new double[list.length];
        for(int i = 0;i<list.length;i++){
            double value = list[i]*scalar;
            newList[i] = value;
        }
        return newList;
    }


    private double[] addLists(double[] ... lists){
        double[] sum =new double[lists[0].length];
        for(int i=0;i<lists[0].length;i++){
            double s = 0;
            for (double[] list:lists){
                s += list[i];
            }
            sum[i] = s;
        }
        return sum;
    }


    public void setMode(DcMotor.RunMode mode){
        for(DcMotor motor:motors){
            motor.setMode(mode);
        }
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        for(DcMotor motor:motors){
            motor.setZeroPowerBehavior(behavior);
        }
    }

    public int[] getCurrentPositions(){
        int[] positions = new int[motors.size()];
        for(int i = 0;i<motors.size();i++){
            int p = motors.get(i).getCurrentPosition();
            positions[i] = p;
        }
        return positions;
    }

    public void goToPositions(int[] target,double power){
        for(int i = 0;i<motors.size();i++){
            motors.get(i).setTargetPosition(target[i]);
            motors.get(i).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        int busy = 4;
        while (busy > 0){
            busy = 4;
            for(DcMotor motor:motors){
                if (motor.isBusy()){
                    motor.setPower(power);
                }
                else{
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    busy -= 1;
                }
            }
        }
    }




}
