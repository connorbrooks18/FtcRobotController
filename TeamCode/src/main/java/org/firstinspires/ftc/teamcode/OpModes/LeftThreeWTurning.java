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


        goAndScore(true, false);
        goAndIntakeAndTransfer(1);


        goAndScore(true, false);






    }
    //Method Drives Robot to Bucket and Dumps Sample
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
        ad.goToHeading(315);

        if(high && !goToPos){
            sleep(750);
        }
        outtake.setBucketPos(outtake.bucketOutPos);
        sleep(1000);


        outtake.setBucketPos(outtake.bucketRegPos);
        sleep(500);
        outtake.vslideToPos(outtake.bottomSlidePos, outtake.slidePower);

    }

    //Robot Collects Inputted Sample (Left = 1, Middle = 2, Right = 3)
    public void goAndIntakeAndTransfer(int sample){
        //
        double angle = 0;
        switch (sample) {
            case 1:
                angle = 25;
                break;
            case 2:
                angle = 5;
                break;
            case 3:
                angle = 345;
                break;
        }
        intake.runWheels(true);
        //Start HSlide Slowly
        intake.hslideToPos(intake.slideOut, 1);
        //(for finley to understand) allows us to start moving the transfer servo mid movement to save time
        intake.tsTarget = .25;//intake.tsDown;
        intake.setTransferServo();

        ad.goToHeading(angle);
        //, 180, () -> {intake.tsTarget = intake.tsDown; intake.setTransferServo();});


        //Put Intake Down
        intake.tsTarget = intake.tsDown;
        intake.setTransferServo();

        //Speed up HSlide after turn
        intake.hslideToPos(intake.slideOut, .75);


        //Turn Active Intake On
        if(sample != 2) {
            sleep(750);
        } else {
            sleep(500);
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
            sleep(500);
            intake.tsTarget = intake.tsMiddle;
            intake.setTransferServo();
            sleep(150);
        }
        intake.stopWheels();
        intake.hslideToPow(0);



    }
}
