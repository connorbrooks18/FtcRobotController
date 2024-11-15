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

        // get to tall bucket
        ad.forward( 4);
        ad.goToHeading(180);
        ad.strafe(-10);
        ad.goToHeading(180);


        // OUT TAKE WHATEVER


        // */


        // GET near ascension
        ad.strafe(5);
        ad.goToHeading(180);
        ad.forward(48);
        ad.goToHeading(270);

        // TOUCH LOWER BAR


        //







    }
}
