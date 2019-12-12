package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;


public class HolonomicDriveTrain extends DriveTrain{


    public HolonomicDriveTrain(HardwareMap hardwareMap,String ... names){
        super(hardwareMap,names);
        double[] sideways = {1,-1,1,-1};
        double[] forward = {-1,-1,1,1};
        double[] turn = {1,1,1,1};
        super.forward = forward;
        super.sideways = sideways;
        super.turn = turn;
    }




}
