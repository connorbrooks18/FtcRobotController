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

        ad.goToPointConstantHeading(12, 24);
       // ad.goToHeading(180);
        ad.goToPointConstantHeading(6, 10);
        ad.goToPointConstantHeading(0, 0);
        sleep(3000);
    }
}
