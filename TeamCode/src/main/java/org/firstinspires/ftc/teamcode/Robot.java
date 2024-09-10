package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Robot {

    static DcMotor rf;
    static DcMotor rb;
    static DcMotor lb;
    static DcMotor lf;

    static DcMotor LEncoder = lf;
    static DcMotor REncoder = rf;
    static DcMotor MEncoder = rb;

    int[] startEncoderValue = {};
    int[] currentEncoderValue = {};


    public static void initMotors(OpMode opmode){
        rf = opmode.hardwareMap.get(DcMotor.class, "rf");
        rb = opmode.hardwareMap.get(DcMotor.class, "rb");
        lb = opmode.hardwareMap.get(DcMotor.class, "lb");
        lf = opmode.hardwareMap.get(DcMotor.class, "lf");

        DcMotor[] motors = {rf, rb, lb, lf};
        for(int i = 0; i < 4; i++){
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public static void drive(double rfPower, double rbPower, double lbPower, double lfPower){

        rf.setPower(rfPower);
        rb.setPower(rbPower);
        lb.setPower(lbPower);
        lf.setPower(lfPower);


    }

    public static int[] getEncoderPositions(){

        return new int[] {LEncoder.getCurrentPosition(), MEncoder.getCurrentPosition(), REncoder.getCurrentPosition()};

    }

    public static double distanceForward(int[] prevEncoderValue, int[] curEncoderValue){

        return (((prevEncoderValue[0] + prevEncoderValue[2])/2.0) - ((curEncoderValue[0] + curEncoderValue[2])/2.0));

    }

    public static double ticksToInches(double ticks){
        return ticks/11873.736;
    }
    public static double inchesToTicks(double inches){
        return (11873.736) * inches;
    }

    public static void forward(double inches, double power){





    }









}
