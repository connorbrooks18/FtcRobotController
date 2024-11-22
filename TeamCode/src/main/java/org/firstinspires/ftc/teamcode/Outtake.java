package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Outtake {

    //Objects
    public DcMotor vslide;
    public TouchSensor vslideBottom;
    Servo bucket;
    Servo claw;


    //VARIABLES

    //Vars for Slide Positions
    public int bottomSlidePos = 0;
    public int highBucketSlidePos = 4150; //temp value
    public int lowBucketSlidePos = 2300; //temp value
    public int touchBarSlidePos = (lowBucketSlidePos / 2) - 100;
    public final int V_SLIDE_MAX = 4375;
    public int targetPos = bottomSlidePos;


    public double slidePower = 1; //temp value

    //Vars for Bucket Dumping Positions
    public double bucketAutoTouch = 0.0;
    public double bucketOutPos = 0.225; //temp value (prev 0.6)
    public double bucketRegPos = 0.675; //temp value (prev 0.3)

    public double targetBucketPos = bucketRegPos;


    //Constructor
    public Outtake(OpMode opMode){
        vslide = opMode.hardwareMap.get(DcMotor.class, "vslide");
        bucket = opMode.hardwareMap.get(Servo.class, "bucket");
//        claw = opMode.hardwareMap.get(Servo.class, "claw");
        vslideBottom = opMode.hardwareMap.get(TouchSensor.class, "vslidelimit");

        vslide.setDirection(DcMotorSimple.Direction.REVERSE);
        vslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

    public double getBucketPos(){
        return bucket.getPosition();
    }


    public boolean slideAtBottom(){
        return vslideBottom.isPressed();
    }

    public void vslideToPow(double power){
        if(vslide.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            vslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        vslide.setPower(power);

    }


    //Reset Vertical Slide
    public void resetVSlide(){
        if(slideAtBottom()) {
            vslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            vslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


    public void stopVSlide(){
        vslide.setPower(0);
    }



}

