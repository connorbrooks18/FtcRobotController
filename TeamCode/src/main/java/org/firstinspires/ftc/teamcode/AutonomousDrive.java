package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutonomousDrive {


    public double errorTolerance = 0.01;

    private GoBildaPinpointDriver odo;



    public AutonomousDrive(OpMode opMode) {

        odo = opMode.hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(139.5, 101.6);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        odo.recalibrateIMU();
        odo.resetPosAndIMU();
        ((LinearOpMode)opMode).sleep(250);


    }

    public void forward(LinearOpMode opMode, double distanceTotal) {
        double startpos = getX();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > (this.errorTolerance + 0.02) && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }
        while (Math.abs(distanceToGo) > this.errorTolerance && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo)/1.5;
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);
    }
    public void strafe(LinearOpMode opMode, double distanceTotal){
        double startpos = getY();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > (this.errorTolerance + 0.05) && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        while (Math.abs(distanceToGo) > this.errorTolerance + 0.05 && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo) / 2;
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);

    }

    public double limitTurn(double x){
        return Math.min(360.0,Math.max(0.0, x));
    }

    public void goToHeading(LinearOpMode opMode, double degrees){
        degrees = limitTurn(degrees);
        double currentHeading = getHeading();
        double targetheading = limitTurn(degrees);
        double angelTogo = targetheading - currentHeading;
        while(opMode.opModeIsActive() && Math.abs(angelTogo) > 3){
            currentHeading =  getHeading();
            angelTogo = targetheading - currentHeading;
            double power = (degrees >= 0) ? powerCurvingTurn(angelTogo): powerCurvingTurn(-angelTogo);
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
        double min = .15;
        if(distanceToGo > 0) {
            return (distanceToGo / slope < min) ? min : distanceToGo / slope;
        }else{
            return (distanceToGo / slope > -min) ? -min : distanceToGo / slope;
        }
    }

    public static double powerCurvingTurn(double angleToGo){
        double slope = 45;
        double min = 0.15;
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

    public void restOdo(LinearOpMode opMode){
        odo.recalibrateIMU();
        opMode.sleep(300);
        odo.resetPosAndIMU();
    }

    public void outputDriveInfo(LinearOpMode opMode, double distanceToGo, double distanceTotal, double power){
        opMode.telemetry.addData("distanceToGo", distanceToGo);
        opMode.telemetry.addData("distanceTotal", distanceTotal);
        opMode.telemetry.addData("power", power);
        opMode.telemetry.addData("get x", this.getX());
        opMode.telemetry.addData("get y", this.getY());
        opMode.telemetry.addData("heading ", odo.getPosition().getHeading(AngleUnit.DEGREES));
        opMode.telemetry.update();
    }


}
