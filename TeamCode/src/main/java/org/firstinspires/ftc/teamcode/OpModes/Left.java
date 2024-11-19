package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

@Autonomous (name="Left Auto")
public class Left extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);
        waitForStart();

        // get to tall bucket
        ad.forward( 4);
        ad.goToHeading(180);
        ad.strafe(-24);
        ad.goToHeading(135);


        // put slide up
        outtake.targetPos = outtake.highBucketSlidePos;
        outtake.vslideToPos(outtake.targetPos, outtake.slidePower);

        sleep(1000); // wait for slide to get up

        // closer to bucket
        ad.forward(-5.5);


        outtake.setBucketPos(outtake.bucketOutPos);

        sleep(5000);












    }
}
