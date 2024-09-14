package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Control {
    boolean LBumper1;
    boolean RBumper1;

    double LStickY;
    double LStickX;
    double RStickY;
    double RStickX;

    double LTrigger1; //
    double RTrigger1; //

    boolean a1;
    boolean b1;
    boolean x1;
    boolean y1;

    boolean a2;
    boolean b2;
    boolean x2;
    boolean y2;

    double LTrigger2;
    double RTrigger2;
    boolean LBumper2;
    boolean RBumper2;

    double RStickY2;
    double RStickX2;
    double LStickY2;
    double LStickX2;

    boolean dpadUp1;
    boolean dpadDown1;
    boolean dpadRight1;
    boolean dpadLeft1;

    boolean dpadUp2;
    boolean dpadDown2;
    boolean dpadRight2;
    boolean dpadLeft2;

    Gamepad gm1;
    Gamepad gm2;

    boolean prevOptions;
    boolean options;


    public Control(OpMode opMode) {
        gm1 = opMode.gamepad1;
        gm2 = opMode.gamepad2;

    }
    public void update(){
         LBumper1 = gm1.left_bumper;
         RBumper1 = gm1.right_bumper;
         LStickY = gm1.left_stick_y;
         LStickX = -gm1.left_stick_x;
         RStickY = gm1.right_stick_y;
         RStickX = gm1.right_stick_x;
         LTrigger1 = gm1.left_trigger;
         RTrigger1 = gm1.right_trigger;

         a1 = gm1.a;
         b1 = gm1.b;
         x1 = gm1.x;
         y1 = gm1.y;

         a2 = gm2.a;
         b2 = gm2.b;
         x2 = gm2.x;
         y2 = gm2.y;
         LTrigger2 = gm2.left_trigger;
         RTrigger2 = gm2.right_trigger;
         LBumper2 = gm2.left_bumper;
         RBumper2 = gm2.right_bumper;

        RStickY2 = -gm2.right_stick_y;
        RStickX2 = gm2.right_stick_x;
        LStickY2 = -gm2.left_stick_y;
        LStickX2 = gm2.left_stick_x;

        dpadUp1 = gm1.dpad_up;
        dpadDown1 = gm1.dpad_down;
        dpadRight1 = gm1.dpad_right;
        dpadLeft1 = gm1.dpad_left;

        dpadUp2 = gm2.dpad_up;
        dpadDown2 = gm2.dpad_down;
        dpadRight2 = gm2.dpad_right;
        dpadLeft2 = gm2.dpad_left;

        prevOptions = options;
        options = gm1.options;





    }
}
