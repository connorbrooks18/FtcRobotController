package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

import org.firstinspires.ftc.teamcode.AutonomousDrive;

@Autonomous (name="test 48in.")
public class ExampleAuto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);

        waitForStart();


        ad.strafe(this, 24);

        while(opModeIsActive()) {
            telemetry.addData("distance Gone", AutonomousDrive.ticksToInches(ad.currentEncoderValue[1] - ad.startEncoderValue[1]));
            telemetry.addData("F Encoder", ad.currentEncoderValue[0]);
            telemetry.addData("M Encoder", ad.currentEncoderValue[1]);
            telemetry.update();
        }




    }
}
