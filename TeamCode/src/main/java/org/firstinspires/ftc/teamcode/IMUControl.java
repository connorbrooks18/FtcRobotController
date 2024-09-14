package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMUControl {

    static BNO055IMU imu;
    static Orientation angles;



    public IMUControl(OpMode opMode) {

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

}
