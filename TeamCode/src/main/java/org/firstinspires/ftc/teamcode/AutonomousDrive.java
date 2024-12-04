package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class AutonomousDrive {


    public double errorTolerance = 0.015;

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
        odo.setPosition(new Pose2D(DistanceUnit.INCH,8.5,36,AngleUnit.DEGREES, 180));
        ((LinearOpMode)opMode).sleep(250);


    }

    public void forward(double distanceTotal) {
        double startTime = this.opMode.time;
        double startpos = getX();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > (this.errorTolerance + 0.05) && opMode.opModeIsActive()) {
            if(isStuck(distanceToGo)) return;

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(distanceToGo, distanceTotal, power);
            if(this.opMode.time - startTime > 4) break;

        }

        while (Math.abs(distanceToGo) > this.errorTolerance && opMode.opModeIsActive()) {
            if(isStuck(distanceToGo)) return;

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo)/1.5;
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(distanceToGo, distanceTotal, power);
            if(this.opMode.time - startTime > 5) break;

        }

        Robot.drive(0, 0, 0, 0);

        opMode.sleep(250);
    }
    public void strafe(double distanceTotal){
        double startpos = getY();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > (this.errorTolerance + 0.075) && opMode.opModeIsActive()) {
            if(isStuck(distanceToGo))return;
            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo( distanceToGo, distanceTotal, power);

        }

        while (Math.abs(distanceToGo) > this.errorTolerance + .025 && opMode.opModeIsActive()) {
            if(isStuck(distanceToGo)) return;
            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo) / 1.5;
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
        double min = .175;
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

    public static double powerCurvingOmni(double distanceToGo){
        double slope = 18;
        double max = .90;
        double min = .20;
        if(distanceToGo > 0) {
            return Math.max(Math.min(distanceToGo / slope, max), min);
        } else {
            return Math.min(Math.max(distanceToGo / slope, -max), -min);
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

    //Returns False if robot is not moving substantially
    public boolean isMoving(){
        return Math.abs(odo.getVelocity().getX(DistanceUnit.INCH)) > 0.005
                || Math.abs(odo.getVelocity().getY(DistanceUnit.INCH)) > 0.005
                ||  Math.abs(odo.getVelocity().getHeading(AngleUnit.DEGREES)) > 0.005;
    }
    public boolean isStuck(double distanceToGo){
        return  !isMoving() && Math.abs(distanceToGo) < 1.75;
    }

    public void goToPoint(double targetX, double targetY, double degrees){
        // try switching from atan2 to atan
        //Get X and Y Distance and Total Distance
        double targetXDistance = targetX - odo.getPosition().getX(DistanceUnit.INCH);
        double targetYDistance = targetY - odo.getPosition().getY(DistanceUnit.INCH);
        double totalDistance = Math.hypot(targetYDistance, targetXDistance);
        double angle = Math.atan2(targetYDistance, targetXDistance);

        //Get Heading
        degrees = limitTurn(degrees);
        double currentHeading = getHeading();
        double angelTogo = degrees - currentHeading;
        double powerTurning =  powerCurvingTurn(angelTogo);
        double power = powerCurving(totalDistance);

        double v1 = totalDistance * Math.sin(angle) + powerTurning; //lf // was cos
        double v2 = totalDistance * Math.cos(angle) - powerTurning; //rf // was sin
        double v3 = totalDistance * Math.cos(angle) + powerTurning; //lb // was sin
        double v4 = totalDistance * Math.sin(angle) - powerTurning; //rb // was

        while(totalDistance > this.errorTolerance){

            targetXDistance = targetX - odo.getPosition().getX(DistanceUnit.INCH);
            targetYDistance = targetY - odo.getPosition().getY(DistanceUnit.INCH);
            totalDistance = Math.hypot(targetYDistance, targetXDistance);
            angle = Math.atan2(targetYDistance, targetXDistance);


            degrees = limitTurn(degrees);
            currentHeading = getHeading();
            angelTogo = degrees - currentHeading;
            powerTurning =  powerCurving(angelTogo)/2;
            power = powerCurving(totalDistance);


            v1 = power * Math.sin(angle) + powerTurning; //lf // was cos
            v2 = power * Math.cos(angle) - powerTurning; //rf // was sin
            v3 = power * Math.cos(angle) + powerTurning; //lb // was sin
            v4 = power * Math.sin(angle) - powerTurning; //rb // was

            Robot.drive(v2,v4,v3,v1);

        }


        this.goToHeading(degrees);

        Robot.drive(0,0,0,0);

        opMode.sleep(250);


    }

    public void goToPointConstantHeading(double targetX, double targetY){
        // try switching from atan2 to atan
        //Get X and Y Distance and Total Distance
        odo.update();
        double targetYDistance = targetY - getY();
        double targetXDistance = targetX - getX();
        double totalDistance = Math.hypot(targetYDistance, targetXDistance);
        double angle = Math.atan2(targetYDistance, targetXDistance);

        //Get Heading
        double power;

        double v1; //lf // was cos
        double v2; //rf // was sin
        double v3; //lb // was sin
        double v4; //rb // was

        while(Math.abs(targetYDistance) > this.errorTolerance && Math.abs(targetYDistance) > this.errorTolerance){
            odo.update();

            if(isStuck(totalDistance))return;

            targetYDistance = (targetY - odo.getPosition().getY(DistanceUnit.INCH));
            targetXDistance = (targetX - odo.getPosition().getX(DistanceUnit.INCH));
            totalDistance = Math.hypot(targetYDistance, targetXDistance);
            angle = Math.atan2(targetYDistance, targetXDistance) + Math.PI/4;


            power = powerCurvingOmni(totalDistance);


            v1 = power * Math.sin(angle); //lf // was cos
            v2 = power * Math.cos(angle); //rf // was sin
            v3 = power * Math.cos(angle); //lb // was sin
            v4 = power * Math.sin(angle); //rb // was

            Robot.drive(v2,v4,v3,v1);

            opMode.telemetry.addData("get x", getX());
            opMode.telemetry.addData("get y", getY());

            opMode.telemetry.addData("LF", v1);
            opMode.telemetry.addData("RF", v2);
            opMode.telemetry.addData("LB", v3);
            opMode.telemetry.addData("RB", v4);
            opMode.telemetry.update();
        }




        Robot.drive(0,0,0,0);

        opMode.sleep(250);



    }






    public void outputDriveInfo(double distanceToGo, double distanceTotal, double power){
        opMode.telemetry.addData("distanceToGo", distanceToGo);
        opMode.telemetry.addData("distanceTotal", distanceTotal);
        opMode.telemetry.addData("power", power);
//        opMode.telemetry.addData("get x", this.getX());
//        opMode.telemetry.addData("get y", this.getY());
        opMode.telemetry.addData("heading ", odo.getPosition().getHeading(AngleUnit.DEGREES));
        opMode.telemetry.addData("is moving", isMoving());
        opMode.telemetry.update();
    }


}
