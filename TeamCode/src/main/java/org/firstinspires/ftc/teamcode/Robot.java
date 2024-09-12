package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class Robot {

    static BNO055IMU imu;
    static Orientation angles;


    static DcMotor rf;
    static DcMotor rb;
    static DcMotor lb;
    static DcMotor lf;

    static DcMotor LEncoder = lf;
    static DcMotor REncoder = rf;
    static DcMotor MEncoder = rb;

    static int[] startEncoderValue = {};
    static int[] currentEncoderValue = {};


    public static void initMotors(OpMode opmode) {
        rf = opmode.hardwareMap.get(DcMotor.class, "rf");
        rb = opmode.hardwareMap.get(DcMotor.class, "rb");
        lb = opmode.hardwareMap.get(DcMotor.class, "lb");
        lf = opmode.hardwareMap.get(DcMotor.class, "lf");

        DcMotor[] motors = {rf, rb, lb, lf};
        for (int i = 0; i < 4; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public static void initIMU(OpMode opMode) {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        opMode.telemetry.addData("Status", "IMU Initialized");
        opMode.telemetry.update();


    }

    public static double getANGLE(OpMode opMode, String axis) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        switch (axis.toUpperCase()) {
            case "X":
                return angles.secondAngle;
            case "Y":
                return angles.thirdAngle;
            case "Z":
                return angles.firstAngle;
            default:
                opMode.telemetry.addData("Error", "Invalid axis specified");
                opMode.telemetry.update();
                return 0.0;
        }
    }


    public static void drive(double rfPower, double rbPower, double lbPower, double lfPower) {

        rf.setPower(rfPower);
        rb.setPower(rbPower);
        lb.setPower(lbPower);
        lf.setPower(lfPower);


    }

    public static int[] getEncoderPositions() {

        return new int[]{LEncoder.getCurrentPosition(), MEncoder.getCurrentPosition(), REncoder.getCurrentPosition()};

    }

    public static double distanceForward(int[] prevEncoderValue, int[] curEncoderValue) {

        return (((prevEncoderValue[0] + prevEncoderValue[2]) / 2.0) - ((curEncoderValue[0] + curEncoderValue[2]) / 2.0));

    }

    public static double ticksToInches(double ticks) {
        return ticks / 11873.736;
    }

    public static double inchesToTicks(double inches) {
        return (11873.736) * inches;
    }

    public static void forward(LinearOpMode opMode, double inches, double power) {
        while (ticksToInches(distanceForward(startEncoderValue, getEncoderPositions())) < inches && opMode.opModeIsActive()) {
            double inchesLeft = inches - distanceForward(startEncoderValue, getEncoderPositions());
            double percentLeft = inchesLeft / inches;
            power = Math.pow(percentLeft, .25);
            drive(power, power, power, power);
        }

    }

    public static void gotoDistance(LinearOpMode opMode, double distanceGone, double power, double porportion) {
        while (ticksToInches(distanceForward(startEncoderValue, getEncoderPositions())) < distanceGone && opMode.opModeIsActive()) {

            double distanceToGo = distanceGone - distanceForward(startEncoderValue, getEncoderPositions());
            power = distanceToGo * porportion;
            drive(power, power, power, power);
        }
    }
}












