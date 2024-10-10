package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Intake {
    DcMotor hslide;
    Servo wheelServo;
    Servo transferServo;
    ColorSensor cs;
    DistanceSensor ds;
    TouchSensor hslideBottom;

    static double inSlidePos = 0;

    static double transferServoBase = 0; // siting position of transfer sevo
    static double transferServoTransfer = 1; // position that dumps the sample
    public Intake(OpMode opMode){
        hslide = opMode.hardwareMap.get(DcMotor.class, "hslide");
        wheelServo = opMode.hardwareMap.get(Servo.class, "servoWheel");
        transferServo = opMode.hardwareMap.get(Servo.class, "servoDump");

        cs = opMode.hardwareMap.get(ColorSensor.class, "csi");
        ds = (DistanceSensor)cs;

        hslideBottom = opMode.hardwareMap.get(TouchSensor.class, "hslideLimitSwitch");

        hslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }


    public boolean slideAtBottom(){
        return hslideBottom.isPressed();
    }

    public void resetVSlide(){
        if(slideAtBottom()) {
            hslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }



    public void runWheels(boolean in){
        wheelServo.setPosition(in ? 0 : 1);

    }
    public void stopWheels(){
        wheelServo.setPosition(.5);
    }

    public void transferSample(){
        transferServo.setPosition(transferServoTransfer);
    }

    public void resetTransferServo(){
        transferServo.setPosition(transferServoBase);
    }


    public SampleColor getColor(){
        if(cs.red() > 180) return SampleColor.RED;
        if(cs.blue() > 180) return SampleColor.BLUE;
        return SampleColor.YELLOW;
    }

    public boolean detectSample(){
        return (ds.getDistance(DistanceUnit.MM) < 15);
    }





}
