package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

import org.firstinspires.ftc.teamcode.AutonomousDrive;

@Autonomous (name="test 24in.")
public class ExampleAuto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);

        waitForStart();


        ad.forward(this, 24);
        sleep(250);
        ad.forward(this, -24);

        while(opModeIsActive()) {
            telemetry.addData("distance Gone", AutonomousDrive.ticksToInches(ad.currentEncoderValue[1] - ad.startEncoderValue[1]));
            telemetry.addData("F Encoder", ad.currentEncoderValue[0]);
            telemetry.addData("M Encoder", ad.currentEncoderValue[1]);
            telemetry.update();
        }





    }
}
