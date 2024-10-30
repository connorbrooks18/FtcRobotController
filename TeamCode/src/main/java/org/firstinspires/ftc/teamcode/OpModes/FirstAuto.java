package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.*;

import org.firstinspires.ftc.teamcode.AutonomousDrive;

@Autonomous (name="left side")
public class FirstAuto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initAll(this);

        waitForStart();

        // Point 0 to point 1
        ad.forward(this, 4);

        // Point 1 to point 2
        ad.strafe(this, -24);
        ad.forward(this, -1);

        // Point 2 to point 3
        ad.strafe(this, 1);
        ad.forward(this, 55);

        // Point 3 to point 4
        ad.strafe(this, 32);
        ad.forward(this, 2);


    }
}
