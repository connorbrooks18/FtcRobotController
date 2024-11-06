package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

@Autonomous (name="ex. auto")
public class ExampleAuto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);
        waitForStart();

        ad.resetOdo(this);


//        ad.strafe(this, 24);
//        sleep(2500);
        ad.forward(this,24);
        sleep(1000);
        ad.goToHeading(this,180);
        sleep(500);
        ad.strafe(this, 12);
        sleep(1000);
        ad.goToHeading(this,180);
        sleep(500);
        ad.forward(this, -24);
        sleep(1000);
        ad.goToHeading(this,180);
        sleep(500);
        ad.strafe(this,-6);

        while(opModeIsActive()) {
//            telemetry.addData("Distance Gone X", ad.getX());
//            telemetry.addData("Distance Gone Y", ad.getY());
//
//            telemetry.update();
        }





    }
}
