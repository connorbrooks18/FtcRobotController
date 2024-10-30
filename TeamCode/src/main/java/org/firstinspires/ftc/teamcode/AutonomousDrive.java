package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutonomousDrive {


    public double errorTolerance = 0.01;

    private GoBildaPinpointDriver odo;



    public AutonomousDrive(OpMode opMode) {

        odo = opMode.hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(139.5, 101.6);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        odo.resetPosAndIMU();
        ((LinearOpMode)opMode).sleep(250);


    }

    public void forward(LinearOpMode opMode, double distanceTotal) {
        double startpos = getX();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > this.errorTolerance && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getX() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(power, power, power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);
    }
    public void strafe(LinearOpMode opMode, double distanceTotal){
        double startpos = getY();
        double distanceToGo = distanceTotal;
        while (Math.abs(distanceToGo) > this.errorTolerance && opMode.opModeIsActive()) {

            distanceToGo = distanceTotal - (getY() - startpos);
            double power = powerCurving(distanceToGo);
            Robot.drive(-power, power, -power, power);
            this.outputDriveInfo(opMode, distanceToGo, distanceTotal, power);

        }

        Robot.drive(0, 0, 0, 0);
        opMode.sleep(250);

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
        double slope = 18;
        double min = .15;
        if(distanceToGo > 0) {
            return (distanceToGo / slope < min) ? min : distanceToGo / slope;
        }else{
            return (distanceToGo / slope > -min) ? -min : distanceToGo / slope;
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

    public void outputDriveInfo(LinearOpMode opMode, double distanceToGo, double distanceTotal, double power){
        opMode.telemetry.addData("distanceToGo", distanceToGo);
        opMode.telemetry.addData("distanceTotal", distanceTotal);
        opMode.telemetry.addData("power", power);
        opMode.telemetry.addData("get x", this.getX());
        opMode.telemetry.addData("get y", this.getY());
        opMode.telemetry.update();
    }


}
