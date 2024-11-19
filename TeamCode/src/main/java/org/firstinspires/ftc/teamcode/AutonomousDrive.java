package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutonomousDrive {


    public double errorTolerance = 0.01;

    private GoBildaPinpointDriver odo;

    private LinearOpMode opMode;

    /*
        180 is forward
        90 is right
        0/360 is backward
        270 is left


     */


    public AutonomousDrive(LinearOpMode opMode) {

        this.opMode = opMode;
        odo = opMode.hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(139.5, 101.6);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        this.resetOdo((LinearOpMode)opMode);
        ((LinearOpMode)opMode).sleep(250);


    }

    public void forward(double distanceTotal) {
        double startpos = getX();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > (this.errorTolerance + 0.02) && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(distanceToGo, distanceTotal, power);

        }
        while (Math.abs(distanceToGo) > this.errorTolerance && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo)/1.5;
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);
    }
    public void strafe(double distanceTotal){
        double startpos = getY();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > (this.errorTolerance + 0.05) && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo( distanceToGo, distanceTotal, power);

        }

        while (Math.abs(distanceToGo) > this.errorTolerance + 0.05 && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo) / 2;
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo(distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);

    }

    public double limitTurn(double x){
        return Math.min(360.0,Math.max(0.0, x));
    }

    public void goToHeading(double degrees){
        degrees = limitTurn(degrees);
        double currentHeading = getHeading();
        double angelTogo = degrees - currentHeading;
        while(opMode.opModeIsActive() && Math.abs(angelTogo) > .5){
            currentHeading =  getHeading();
            angelTogo = degrees - currentHeading;
            double power =  powerCurvingTurn(angelTogo);
            Robot.drive(power, power, -power, -power);

            opMode.telemetry.addData("Current Heading", currentHeading);
            opMode.telemetry.addData("Angle To go", angelTogo);
            opMode.telemetry.update();

        }
        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);
    }

    public static double powerCurving(double distanceToGo){
        double slope = 18;
        double max = .80;
        double min = .15;
        if(distanceToGo > 0) {
            return Math.max(Math.min(distanceToGo / slope, max), min);
        } else {
            return Math.min(Math.max(distanceToGo / slope, -max), -min);
        }
    }

    public static double powerCurvingTurn(double angleToGo){
        double slope = 90;
        double min = .2;
        if(angleToGo > 0) {
            return (angleToGo / slope < min) ? min : angleToGo / slope;
        }else{
            return (angleToGo / slope > -min) ? -min : angleToGo / slope;
        }
    }

    public double getX(){
        odo.update();
        return odo.getPosition().getX(DistanceUnit.INCH);

    }
    public double getY(){
        odo.update();
        return odo.getPosition().getY(DistanceUnit.INCH);
    }

    public double getHeading(){
        odo.update();
        return odo.getPosition().getHeading(AngleUnit.DEGREES) + 180;
    }

    public void resetOdo(LinearOpMode opMode){
        odo.recalibrateIMU();
        opMode.sleep(500);
        odo.resetPosAndIMU();
    }

    public void outputDriveInfo(double distanceToGo, double distanceTotal, double power){
        opMode.telemetry.addData("distanceToGo", distanceToGo);
        opMode.telemetry.addData("distanceTotal", distanceTotal);
        opMode.telemetry.addData("power", power);
        opMode.telemetry.addData("get x", this.getX());
        opMode.telemetry.addData("get y", this.getY());
        opMode.telemetry.addData("heading ", odo.getPosition().getHeading(AngleUnit.DEGREES));
        opMode.telemetry.update();
    }


}
