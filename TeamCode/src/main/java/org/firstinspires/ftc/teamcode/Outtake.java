package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Outtake {

    DcMotor vslide;
    Servo bucket;
    Servo claw;

    TouchSensor vslideBottom;

    static double bottomSlidePos = 0;
    static double highBucketSlidePos = 1;
    static double lowBucketSlidePos = 2;


    public Outtake(OpMode opMode){
        vslide = opMode.hardwareMap.get(DcMotor.class, "vslide");
        bucket = opMode.hardwareMap.get(Servo.class, "bucket");
        claw = opMode.hardwareMap.get(Servo.class, "claw");
        vslideBottom = opMode.hardwareMap.get(TouchSensor.class, "vslideLimitSwitch");

        vslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public boolean slideAtBottom(){
        return vslideBottom.isPressed();
    }

    public void resetVSlide(){
        if(slideAtBottom()) {
            vslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            vslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }



}
