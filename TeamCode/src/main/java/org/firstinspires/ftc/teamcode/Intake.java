package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Intake {
    Servo servoWheel;
    Servo servoDump;
    ColorSensor cs;
    DistanceSensor ds;
    public Intake(OpMode opMode){
        servoWheel = opMode.hardwareMap.get(Servo.class, "servoWheel");
        servoDump = opMode.hardwareMap.get(Servo.class, "servoDump");
        cs = opMode.hardwareMap.get(ColorSensor.class, "csi");
        ds = (DistanceSensor)cs;
    }

    public void runWheels(boolean in){
        servoWheel.setPosition(in ? 0 : 1);

    }
    public void stopWheels(){
        servoWheel.setPosition(.5);
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
