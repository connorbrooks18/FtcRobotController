package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.Robot.ad;
import static org.firstinspires.ftc.teamcode.Robot.initAll;
import static org.firstinspires.ftc.teamcode.Robot.intake;
import static org.firstinspires.ftc.teamcode.Robot.outtake;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name="Left Two Auto", preselectTeleOp="Telop")
public class LeftTwo extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);
        outtake.stopVSlide();
        outtake.setBucketPos(outtake.bucketRegPos);
        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();

        waitForStart();

        //Drive to Tall Bucket
        ad.forward( 4);
        ad.goToHeading(180);
        ad.strafe(-24);
        ad.goToHeading(135);


        for(int i = 0; i < 1; i++) {
            // put slide up
            ad.forward(2);
            outtake.vslideToPos(outtake.highBucketSlidePos, outtake.slidePower);

            sleep(2250); // wait for slide to get up

            // get closer to basket
            ad.forward(-2);


            outtake.setBucketPos(outtake.bucketOutPos);

            sleep(1000);

            outtake.setBucketPos(outtake.bucketRegPos);

            sleep(500);

            // go slide back down
            ad.forward(4);
            outtake.vslideToPos(outtake.bottomSlidePos, outtake.slidePower);
            ad.goToHeading(180);
            sleep(1000);
            outtake.stopVSlide();
            outtake.resetVSlide();

        }

        ad.strafe(-2.5);

        intake.hslideToPos(1700, .75);
        sleep(450);
        intake.tsTarget = intake.tsDown;
        intake.setTransferServo();

        intake.runWheels(true);
        intake.hslideToPos(1800, .75);

        sleep(2000);

        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();

        intake.hslideToPos(-200, .75);
        sleep(1000);

        ad.strafe(1.75);


        ad.goToHeading(135);
        intake.tsTarget = intake.tsUp;
        intake.setTransferServo();
        sleep(1500);
        intake.runWheels(false);


        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();
        sleep(100);


        intake.tsTarget = intake.tsUp;
        intake.setTransferServo();
        intake.runWheels(true);
        sleep(2000);
        intake.runWheels(false);

        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();
        sleep(100);


        intake.hslideToPow(0);
        outtake.vslideToPos(outtake.highBucketSlidePos, outtake.slidePower);
        sleep(2500);

        ad.forward(-3);

        outtake.setBucketPos(outtake.bucketOutPos);
        sleep(1000);
        outtake.setBucketPos(outtake.bucketRegPos);
        //Back up
        ad.forward(3.0);
        outtake.vslideToPos(outtake.bottomSlidePos, outtake.slidePower);
        sleep(3000);



















    }
}
