package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Robot;

public class TeleopTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Robot.initMotors(this);
        Robot.initIMU(this);

        waitForStart();
        while(opModeIsActive()){



            telemetry.addData("X: ", Robot.getANGLE(this, "X"));
            telemetry.addData("Y: ", Robot.getANGLE(this, "Y"));
            telemetry.addData("Z: ", Robot.getANGLE(this, "Z"));


            telemetry.update();

        }
    }
}
