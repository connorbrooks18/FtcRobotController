package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

@Autonomous (name="Left Auto")
public class LeftOne extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);
        outtake.stopVSlide();
        outtake.setBucketPos(outtake.bucketRegPos);
        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();

        waitForStart();

        // get to tall bucket
        ad.forward( 4);
        ad.goToHeading(180);
        ad.strafe(-22);
        ad.goToHeading(135);


        // put slide up
        outtake.vslideToPos(outtake.highBucketSlidePos, outtake.slidePower);

        sleep(2500); // wait for slide to get up
        ad.forward(-1);

        outtake.setBucketPos(outtake.bucketOutPos);

        sleep(2000);

        outtake.setBucketPos(outtake.bucketRegPos);

        sleep(500);

        // go slide back down
        ad.forward(3);
        outtake.vslideToPos(outtake.bottomSlidePos, outtake.slidePower);
        sleep(3000);
        outtake.stopVSlide();
        outtake.resetVSlide();
        ad.forward(-3);

        //Go To Sub
        ad.goToHeading(180);
        ad.strafe(22);
        ad.goToHeading(180);
        ad.forward(48);
        ad.goToHeading(270);
        //Slide Up to Touch Bar
        outtake.vslideToPos((outtake.lowBucketSlidePos / 2), outtake.slidePower + 0.1);
        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();
        outtake.setBucketPos(outtake.bucketOutPos);
        sleep (1000);
        ad.forward(-2);


        sleep(3000);














    }
}
