package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutonomousDrive {

    public int[] startEncoderValue = {};
    public int[] currentEncoderValue = {};

    private DcMotor FEncoder;
    private DcMotor MEncoder;

    private GoBildaPinpointDriver odo;


    public AutonomousDrive(OpMode opMode, DcMotor FEncoder, DcMotor MEncoder) {
        this.FEncoder = FEncoder;
        this.MEncoder = MEncoder;

        this.FEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.MEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.FEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.MEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        odo = opMode.hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(139.5, 101.6);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU();


    }

    public int[] updateEncoderPositions() {
        currentEncoderValue = new int[]{-FEncoder.getCurrentPosition(), -MEncoder.getCurrentPosition()};
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



//    public void forward(LinearOpMode opMode, double distanceTotal) {
//        this.updateEncoderPositions();
//        startEncoderValue = currentEncoderValue.clone();
//        //distanceTotal = inchesToTicks(distanceTotal);
//        double distanceToGo = distanceTotal - ticksToInches(distanceForward(startEncoderValue, currentEncoderValue));
//        while (Math.abs(distanceToGo) > .025 && opMode.opModeIsActive()) {
//
//            distanceToGo = distanceTotal - ticksToInches(distanceForward(startEncoderValue, currentEncoderValue));
//            double power = powerCurving(distanceToGo);
//            Robot.drive(power, power, power, power);
//            this.updateEncoderPositions();
//            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);
//
//
//        }
//        Robot.drive(0, 0, 0, 0);
//    }
//    public void strafe(LinearOpMode opMode, double distanceTotal){
//        this.updateEncoderPositions();
//        startEncoderValue = currentEncoderValue.clone();
//        double distanceToGo = distanceTotal - ticksToInches(distanceSide(startEncoderValue, currentEncoderValue));
//        while (Math.abs(distanceToGo) > 0.05 && opMode.opModeIsActive()) {
//
//            distanceToGo = distanceTotal - ticksToInches(distanceSide(startEncoderValue, currentEncoderValue));
//            double power = powerCurving(distanceToGo);
//            Robot.drive(-power, power, -power, power);
//            this.updateEncoderPositions();
//            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);
//
//        }
//
//        Robot.drive(0, 0, 0, 0);
//
//    }

    public void forward(LinearOpMode opMode, double distanceTotal) {
        double startpos = getY();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > 0.05 && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
    }
    public void strafe(LinearOpMode opMode, double distanceTotal){
        double startpos = getY();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > 0.05 && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);

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
        double slope = 18.0;
        double min = .20;
        if(distanceToGo > 0) {
            return (distanceToGo / slope < min) ? min : distanceToGo / slope;
        }else{
            return (distanceToGo / slope > -min) ? -min : distanceToGo / slope;
        }
    }

    public double getX(){
        return odo.getPosition().getX(DistanceUnit.INCH);
    }
    public double getY(){
        return odo.getPosition().getY(DistanceUnit.INCH);
    }

    public void outputDriveInfo(LinearOpMode opMode, double distanceToGo, double distanceTotal, double power){
        opMode.telemetry.addData("distanceToGo", distanceToGo);
        opMode.telemetry.addData("distanceTotal", distanceTotal);
        opMode.telemetry.addData("power", power);
//        opMode.telemetry.addData("F Encoder", this.currentEncoderValue[0]);
//        opMode.telemetry.addData("M Encoder", this.currentEncoderValue[1]);
        opMode.telemetry.update();
    }


}
