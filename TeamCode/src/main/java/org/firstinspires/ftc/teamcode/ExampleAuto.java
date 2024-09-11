package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot;


public class ExampleAuto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initMotors(this);
        waitForStart();


        Robot.forward(this, 10, 1.0);


    }
}
