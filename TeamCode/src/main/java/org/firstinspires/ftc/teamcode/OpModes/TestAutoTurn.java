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


        ad.goToHeading(this, 90);
        ad.goToHeading(this, 0);
        ad.goToHeading(this, 270);
        ad.goToHeading(this, 180);

        while(opModeIsActive()) {
//            telemetry.addData("Distance Gone X", ad.getX());
//            telemetry.addData("Distance Gone Y", ad.getY());
//
//            telemetry.update();
        }





    }
}
