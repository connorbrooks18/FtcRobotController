package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;



import org.firstinspires.ftc.teamcode.Control;




public class Robot {


    static final double slow = .5;
    static final double fast = 1;

    static DcMotor rf;
    static DcMotor rb;
    static DcMotor lb;
    static DcMotor lf;


    static ColorSensor cs1;

    static Servo camServo;


    static double gear = 1;


    static Intake intake;

    static AutonomousDrive ad;
    static IMUControl imu;
    static AprilTagPipeline aptag;


    public static void initMotors(OpMode opmode) {
        rf = opmode.hardwareMap.get(DcMotor.class, "rf");
        rb = opmode.hardwareMap.get(DcMotor.class, "rb");
        lb = opmode.hardwareMap.get(DcMotor.class, "lb");
        lf = opmode.hardwareMap.get(DcMotor.class, "lf");

        //0 ports is the back motors config and both motors are goinf to both cobntrol and expasion hub
        //Encoders go to those ports


        DcMotor[] motors = {rf, rb, lb, lf};
        for (int i = 0; i < 4; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public static void intServos(OpMode opMode){
        camServo = opMode.hardwareMap.get(Servo.class, "camservo");
        intake = new Intake(opMode);
    }



    public static void initAUTO(OpMode opMode){

        initMotors(opMode);
        imu = new IMUControl(opMode);

        ad = new AutonomousDrive(lb, rb);

        aptag = new AprilTagPipeline(camServo,rf,rb,lf,lb);

    }





    public static void drive(double rfPower, double rbPower, double lbPower, double lfPower) {

        rf.setPower(rfPower * gear);
        rb.setPower(rbPower * gear);
        lb.setPower(lbPower * gear);
        lf.setPower(lfPower * gear);


    }



    public static double updateGear(Control c){


        if(c.options && !c.prevOptions){
            gear = (gear == slow) ? fast : slow;
        }
        return gear;
    }


    public static double[] rcDriving(Control c){

        updateGear(c);

        double v1 = 0;
        double v2 = 0;
        double v3 = 0;
        double v4 = 0;

        double sp = .25;


        if (Math.abs(c.LStickX) > 0.05 || Math.abs(c.LStickY) > 0.05 || Math.abs(c.RStickX) > 0.05) {


            double r = Math.hypot(c.LStickX, c.LStickY) * gear;
            double robotAngle = Math.atan2(-c.LStickY, c.LStickX) - Math.PI / 4;

            v1 = r * Math.cos(robotAngle) + c.RStickX; //lf // wsa cos
            v2 = r * Math.sin(robotAngle) - c.RStickX; //rf // was sin
            v3 = r * Math.sin(robotAngle) + c.RStickX; //lb // was sin
            v4 = r * Math.cos(robotAngle) - c.RStickX; //rb // was cos


        }

        else if (c.LTrigger1 > .25) {
            v1 = -sp;
            v2 = sp;
            v3 = sp;
            v4 = -sp;

        } else if (c.RTrigger1 > .25){
            v1 = sp;
            v2 = -sp;
            v3 = -sp;
            v4 = sp;
        } else if(c.dpadUp1){
            v1 = sp;
            v2 = sp;
            v3 = sp;
            v4 = sp;
        } else if(c.dpadRight1){
            v1 = sp;
            v2 = -sp;
            v3 = -sp;
            v4 = sp;
        } else if(c.dpadDown1){
            v1 = -sp;
            v2 = -sp;
            v3 = -sp;
            v4  = -sp;
        } else if(c.dpadLeft1){
            v1 = -sp;
            v2 = sp;
            v3 = sp;
            v4 = -sp;
        }
        else {
            v1 = 0;
            v2 = 0;
            v3 = 0;
            v4 = 0;
        }

        drive(v1, v2, v3, v4);
        return new double[] {v1, v2, v3, v4};



    }

    public SampleColor intakeSample(Control c){
        intake.runWheels(true);

        return SampleColor.YELLOW;

    }




}












