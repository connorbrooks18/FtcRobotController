package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousDrive {

    static int[] startEncoderValue = {};
    static int[] currentEncoderValue = {};

    DcMotor LEncoder;
    DcMotor MEncoder;
    DcMotor REncoder;


    public AutonomousDrive(DcMotor LEncoder, DcMotor MEncoder, DcMotor REncoder) {
        this.LEncoder = LEncoder;
        this.MEncoder = MEncoder;
        this.REncoder = REncoder;
    }

    public int[] getEncoderPositions() {

        return new int[]{LEncoder.getCurrentPosition(), MEncoder.getCurrentPosition(), REncoder.getCurrentPosition()};

    }

    public static double distanceForward(int[] prevEncoderValue, int[] curEncoderValue) {

        return (-((prevEncoderValue[0] + prevEncoderValue[2]) / 2.0)  + ((curEncoderValue[0] + curEncoderValue[2]) / 2.0));

    }

    public static double distanceSide(int[] prevEncoderValue, int[] curEncoderValue) {

        return (curEncoderValue[1] - prevEncoderValue[1]);

    }


    public static double ticksToInches(double ticks) {
        return ticks / 11873.736;
    }

    public static double inchesToTicks(double inches) {
        return (11873.736) * inches;
    }



    public void gotoDistanceV(LinearOpMode opMode, double distanceTotal) {
        startEncoderValue = this.getEncoderPositions();
        distanceTotal = inchesToTicks(distanceTotal);
        while (ticksToInches(distanceForward(startEncoderValue, this.getEncoderPositions())) < distanceTotal && opMode.opModeIsActive()) {

            double distanceToGo = distanceTotal - distanceForward(startEncoderValue, this.getEncoderPositions());
            double power = distanceToGo * 1.0;
            Robot.drive(power, power, power, power);
        }
    }
    public void gotoDistanceH(LinearOpMode opMode, double distanceTotal){
        startEncoderValue = this.getEncoderPositions();
        distanceTotal = inchesToTicks(distanceTotal);
        while (ticksToInches(distanceSide(startEncoderValue, this.getEncoderPositions())) < distanceTotal && opMode.opModeIsActive()) {

            double distanceToGo = distanceTotal - distanceSide(startEncoderValue, this.getEncoderPositions());
            double power = distanceToGo * 1.0;
            Robot.drive(-power, power, -power, power);
        }




    }
}
