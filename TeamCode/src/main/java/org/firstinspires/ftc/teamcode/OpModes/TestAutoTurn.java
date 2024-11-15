package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

@Autonomous (name="test 90 turn")
public class TestAutoTurn extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);
        waitForStart();


        ad.goToHeading(90);
        ad.goToHeading( 0);
        ad.goToHeading( 270);
        ad.goToHeading( 180);

        while(opModeIsActive()) {
//            telemetry.addData("Distance Gone X", ad.getX());
//            telemetry.addData("Distance Gone Y", ad.getY());
//
//            telemetry.update();
        }





    }
}
