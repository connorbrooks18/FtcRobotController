package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Outtake {

    //Objects
    DcMotor vslide;
        TouchSensor vslideBottom;
    Servo bucket;
    Servo claw;


    //VARIABLES

    //Vars for Slide Positions
    public int bottomSlidePos = 0;
    public int highBucketSlidePos = 1; //temp value
    public int lowBucketSlidePos = 2; //temp value
    public int targetPos = bottomSlidePos;


    public double slidePower = .75; //temp value

    //Vars for Bucket Dumping Positions
    public double bucketOutPos = 0; //temp value
    public double bucketRegPos = 0; //temp value

    public double targetBucketPos = bucketRegPos;

    //Constructor
    public Outtake(OpMode opMode){
        vslide = opMode.hardwareMap.get(DcMotor.class, "vslide");
        bucket = opMode.hardwareMap.get(Servo.class, "bucket");
        claw = opMode.hardwareMap.get(Servo.class, "claw");
        vslideBottom = opMode.hardwareMap.get(TouchSensor.class, "vslideLimitSwitch");

        vslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    //Set Run Slide Power
    public void runSlidePow(double power){
        vslide.setPower(power);
    }

    //Vertical Slide to Position
    public void vslideToPos(int pos, double power){
        vslide.setTargetPosition(pos);
        vslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vslide.setPower(power);
    }

    //Set Bucket Position
    public void setBucketPos(double pos){
        bucket.setPosition(pos);
    }

    //Get Vertical Slide Position
    public int getVSlidePos(){
        return vslide.getCurrentPosition();
    }


    public boolean slideAtBottom(){
        return vslideBottom.isPressed();
    }

    //Reset Vertical Slide
    public void resetVSlide(){
        if(slideAtBottom()) {
            vslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            vslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }



}
