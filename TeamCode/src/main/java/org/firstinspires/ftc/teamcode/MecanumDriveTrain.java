package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDriveTrain extends DriveTrain {
    public MecanumDriveTrain(HardwareMap hardwareMap, String ... names){
        super(hardwareMap,names);
        double[] forward = {-1,1,-1,1};
        double[] sideways = {-1,-1,1,1};
        double[] turn = {-1,-1,-1,-1};
        super.forward = forward;
        super.sideways = sideways;
        super.turn = turn;
    }
}
