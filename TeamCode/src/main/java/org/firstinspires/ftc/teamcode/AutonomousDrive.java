package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousDrive {

    int[] startEncoderValue = {};
    int[] currentEncoderValue = {};

    DcMotor FEncoder;
    DcMotor MEncoder;


    public AutonomousDrive(DcMotor FEncoder, DcMotor MEncoder) {
        this.FEncoder = FEncoder;
        this.MEncoder = MEncoder;
    }

    public int[] updateEncoderPositions() {
        currentEncoderValue = new int[]{FEncoder.getCurrentPosition(), MEncoder.getCurrentPosition()};
        return currentEncoderValue;

    }

    public static double distanceForward(int[] prevEncoderValue, int[] curEncoderValue) {
        return (curEncoderValue[0] - prevEncoderValue[0]);

    }

    public static double distanceSide(int[] prevEncoderValue, int[] curEncoderValue) {

        return (curEncoderValue[1] - prevEncoderValue[1]);

    }


    public static double ticksToInches(double ticks) {return ((ticks /2000) * (48 * Math.PI))/25.4;}

    public static double inchesToTicks(double inches) {return ((inches/25.4)/(48*Math.PI))/2000;}



    public void forward(LinearOpMode opMode, double distanceTotal) {
        this.updateEncoderPositions();
        startEncoderValue = currentEncoderValue.clone();
        distanceTotal = inchesToTicks(distanceTotal);
        while (ticksToInches(distanceForward(startEncoderValue, currentEncoderValue)) < distanceTotal && opMode.opModeIsActive()) {
            double distanceToGo = distanceTotal - distanceForward(startEncoderValue, currentEncoderValue);
            double power = powerCurving(distanceToGo);
            Robot.drive(power, power, power, power);
            this.updateEncoderPositions();
        }
    }
    public void strafe(LinearOpMode opMode, double distanceTotal){
        this.updateEncoderPositions();
        startEncoderValue = currentEncoderValue.clone();
        while (ticksToInches(distanceSide(startEncoderValue, currentEncoderValue)) < distanceTotal && opMode.opModeIsActive()) {

            double distanceToGo = distanceTotal - ticksToInches(distanceSide(startEncoderValue, currentEncoderValue));
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.updateEncoderPositions();
        }

    }
    public void turnDegrees(LinearOpMode opMode, double degrees, IMUControl imu){
        double currentHeading = imu.getHeading();
        double startHeading = currentHeading;
        while(opMode.opModeIsActive() && Math.abs(startHeading  - currentHeading) > 3){
            currentHeading = imu.getHeading();
            double power = .5;
            Robot.drive(power, power, -power, -power);


        }
    }

    public static double powerCurving(double distanceToGo){
        return Math.pow((distanceToGo/16), 3.0);
    }


}
