package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class Intake {

    //Objects
    public DcMotor hslide;
    public Servo wheelServo;
    public Servo transferServo;
    public ColorSensor cs;
    public DistanceSensor ds;
    public TouchSensor hslideBottom;

    //Vars
    double inSlidePos = 0;
    double hSlideMax = 1800;

    public double tsDown = .22; //
    public double tsMiddle = .575;
    public double tsUp = .74; // position that dumps the sample
    public double tsTarget = tsMiddle;


    public Intake(OpMode opMode){
        hslide = opMode.hardwareMap.get(DcMotor.class, "hslide");

        wheelServo = opMode.hardwareMap.get(Servo.class, "servoWheelBlue");
        transferServo = opMode.hardwareMap.get(Servo.class, "servoTransferWhite");

//        cs = opMode.hardwareMap.get(ColorSensor.class, "csi");
//        ds = (DistanceSensor)cs;

        hslideBottom = opMode.hardwareMap.get(TouchSensor.class, "hslidelimit");
        hslide.setDirection(DcMotorSimple.Direction.REVERSE);

        hslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    public int getCurrentHPos(){return hslide.getCurrentPosition();}
    public boolean slideAtBottom(){
        return hslideBottom.isPressed();
    }

    public void resetHSlide(){
        if(slideAtBottom()) {
            hslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void runSlide(double power){

        hslide.setPower(power);

    }
    public void hslideToPow(double power){
        if(hslide.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            hslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        hslide.setPower(power);
    }

    public void hslideToPos(int pos, double power){
        hslide.setTargetPosition(pos);
        hslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hslide.setPower(power);
    }


    public void stopSlide(){
        hslide.setPower(0);
    }


    public void runWheels(boolean in){
        wheelServo.setPosition(in ? 0 : 1);

    }
    public void stopWheels(){
        wheelServo.setPosition(.5);
    }

    public void setTransferServo() {
        transferServo.setPosition(tsTarget);
    }




//    public SampleColor getColor(){
//        if(cs.red() > 100) return SampleColor.RED;
//        if(cs.blue() > 100) return SampleColor.BLUE;
//        return SampleColor.YELLOW;
//    }
//
//    public boolean detectSample(){
//        return (ds.getDistance(DistanceUnit.MM) < 15);
//    }





}
