package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import static org.firstinspires.ftc.teamcode.Robot.*;

public class TeleopTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        initMotors(this);
        initAUTO(this);
        Control c = new Control(this);

        waitForStart();
        while(opModeIsActive()){
            c.update();

            rcDriving(c);





            int[] encoderValues = ad.getEncoderPositions();
            telemetry.addData("LEncoder: ", encoderValues[0]);
            telemetry.addData("MEncoder: ", encoderValues[1]);
            telemetry.addData("REncoder: ", encoderValues[2]);


            telemetry.addData("X: ", imu.getANGLE(this, "X"));
            telemetry.addData("Y: ", imu.getANGLE(this, "Y"));
            telemetry.addData("Z: ", imu.getANGLE(this, "Z"));


            telemetry.update();

        }
    }
}
