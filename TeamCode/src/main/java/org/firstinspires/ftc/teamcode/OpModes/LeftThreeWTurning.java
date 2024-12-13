package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.Robot.ad;
import static org.firstinspires.ftc.teamcode.Robot.initAll;
import static org.firstinspires.ftc.teamcode.Robot.intake;
import static org.firstinspires.ftc.teamcode.Robot.outtake;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name="Left Three W Turning", preselectTeleOp="Telop")
public class LeftThreeWTurning extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);

        //Prep Robot for Auto
        outtake.stopVSlide();
        outtake.setBucketPos(outtake.bucketRegPos);
        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();

        waitForStart();

        goAndScore(true, true);

        goAndIntakeAndTransfer(2);

        goAndScore(true, false);
        goAndIntakeAndTransfer(3);


        goAndScore(false, false);
        goAndIntakeAndTransfer(1);


        goAndScore(false, false);






    }

    public void goAndScore(boolean high, boolean goToPos){
        //Going to Bucket with Sample
        if(high) {
            outtake.vslideToPos(outtake.highBucketSlidePos, outtake.slidePower);
        } else {
            outtake.vslideToPos(outtake.lowBucketSlidePos, outtake.slidePower);
        }

        if(goToPos) {
            ad.goToPointConstantHeading(15, 14);
        }
        ad.goToHeading2(315);

        if(high && !goToPos){
            sleep(750);
        }
        outtake.setBucketPos(outtake.bucketOutPos);
        sleep(750);


        outtake.setBucketPos(outtake.bucketRegPos);
        sleep(500);
        outtake.vslideToPos(outtake.bottomSlidePos, outtake.slidePower);

    }


    public void goAndIntakeAndTransfer(int sample){ // sample is 1, 2, 3 (left to right)
        //Line Up With Floor Sample

//        double cur_x = ad.getY();
//        double cur_y = ad.getX();
//        double to_x = 0;
//        double to_y = 0;
//        switch (sample) {
//            case 1:
//                to_x = 3; // 3, 46
//                to_y = 46;
//                break;
//            case 2:
//                to_x = 13;
//                to_y = 46;
//                // 13, 46
//
//                break;
//            case 3:
//                to_x = 23;
//                to_y = 46;
//                // 23, 46
//                break;
//        }
//        double angle = Math.toDegrees(Math.atan2((to_y - cur_y), (to_x - cur_x)));
        double angle = 0;
        switch (sample) {
            case 1:
                angle = 22.5;
                break;
            case 2:
                angle = 5;
                break;
            case 3:
                angle = 345;
                break;
        }
        intake.runWheels(true);
        // start hslide slowly
        intake.hslideToPos(intake.slideOut, .4);
        ad.goToHeading(angle);


        //Put Intake Down
        intake.tsTarget = intake.tsDown;
        intake.setTransferServo();

        // speed up hslide once done turning
        intake.hslideToPos(intake.slideOut, .75);


        //Turn Active Intake On
        if(sample != 2) {
            sleep(1600);
        } else {
            sleep(1250);
        }


        //Move Transfer Servo to Middle
        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();
        intake.stopWheels();

        intake.hslideToPos(intake.slideForceIn, 1);
        if(sample != 2){
            sleep(1000);
        } else {
            sleep(750);
        }

        //Transfer Sample
        intake.runWheels(true);
        for(int i = 0; i < 2; i++) {
            intake.tsTarget = intake.tsUp;
            intake.setTransferServo();
            sleep(300);
            intake.tsTarget = intake.tsMiddle;
            intake.setTransferServo();
            sleep(150);
        }
        intake.stopWheels();
        intake.hslideToPow(0);



    }
}

//shimmy thing or hitting wall stopping it from tilting bucket
//did not align with bucket after trying to pick up second sample
//did not align with right-side sample too far right