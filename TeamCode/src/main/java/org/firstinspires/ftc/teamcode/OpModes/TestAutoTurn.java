package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

import org.firstinspires.ftc.teamcode.AutonomousDrive;

@Autonomous (name="test 90 turn")
public class TestAutoTurn extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);
        waitForStart();
        ad.restOdo(this);
        for(int i = 0; i <= 4; i++) {
            ad.goToHeading(this, 45 * i);
            sleep(1000);
            ad.goToHeading(this, -45 * i);
            sleep(1000);
        }

        while(opModeIsActive()) {
//            telemetry.addData("Distance Gone X", ad.getX());
//            telemetry.addData("Distance Gone Y", ad.getY());
//
//            telemetry.update();
        }





    }
}
