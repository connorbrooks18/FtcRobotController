package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


public class Robot {


    public static final double slow = .5;
    public static final double fast = 1;

    public static DcMotor rf;
    public static DcMotor rb;
    public static DcMotor lb;
    public static DcMotor lf;


    public static ColorSensor cs1;

    public static Servo camServo;


    public static double gear = 1;
    private static double gearprev = 1;


    public static Control c;


    public static Intake intake;
    public static Outtake outtake;

    public static AutonomousDrive ad;
    public static IMUControl imu;
    public static AprilTagPipeline aptag;


    public static void initDrive(OpMode opmode) {
        rf = opmode.hardwareMap.get(DcMotor.class, "rf");
        rb = opmode.hardwareMap.get(DcMotor.class, "rb");
        lb = opmode.hardwareMap.get(DcMotor.class, "lb");
        lf = opmode.hardwareMap.get(DcMotor.class, "lf");




        //0 ports is the back motors config and both motors are going to both control and expansion hub
        //Encoders go to those ports


        DcMotor[] motors = {rf, rb, lb, lf};
        for (int i = 0; i < 4; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public static void initAccessories(OpMode opMode){
        //camServo = opMode.hardwareMap.get(Servo.class, "camservo");
        intake = new Intake(opMode);
        //outtake = new Outtake(opMode);

    }



    public static void initAll(OpMode opMode){

        initDrive(opMode);
        initAccessories(opMode);
        imu = new IMUControl(opMode);

        c = new Control(opMode);

        ad = new AutonomousDrive(opMode);

        aptag = new AprilTagPipeline(camServo,rf,rb,lf,lb);

    }





    public static void drive(double rfPower, double rbPower, double lbPower, double lfPower) {

        rf.setPower(rfPower * gear);
        rb.setPower(rbPower * gear);
        lb.setPower(lbPower * gear);
        lf.setPower(lfPower * gear);


    }



    public static double updateGear(){

        if(c.options && !c.prevOptions){
            gearprev = (gearprev == slow) ? fast : slow;
        }
        gear = (intake.getCurrentHPos() > 250) ? slow : gearprev;

        return gear;
    }


    public static double[] rcDriving(){

        updateGear();

        double v1 = 0; // lf
        double v2 = 0; // rf
        double v3 = 0; // lb
        double v4 = 0; // rb

        double sp = .25;


        if (Math.abs(c.LStickX) > 0.05 || Math.abs(c.LStickY) > 0.05 || Math.abs(c.RStickX) > 0.05) {


            double r = Math.hypot(c.LStickX, c.LStickY) * gear;
            double robotAngle = Math.atan2(c.LStickY, c.LStickX) - Math.PI / 4;

            v1 = r * Math.sin(robotAngle) + c.RStickX; //lf // was cos
            v2 = r * Math.cos(robotAngle) - c.RStickX; //rf // was sin
            v3 = r * Math.cos(robotAngle) + c.RStickX; //lb // was sin
            v4 = r * Math.sin(robotAngle) - c.RStickX; //rb // was cos


        }

        else if (c.LTrigger1 > .25) {
            v1 = -sp;
            v2 = sp;
            v3 = sp;
            v4 = -sp;

        } else if (c.RTrigger1 > .25) {
            v1 = sp;
            v2 = -sp;
            v3 = -sp;
            v4 = sp;
        } else if (c.dpadUp1) {
            v1 = sp;
            v2 = sp;
            v3 = sp;
            v4 = sp;
        } else if (c.dpadRight1) {
            v1 = sp;
            v2 = -sp;
            v3 = -sp;
            v4 = sp;
        } else if (c.dpadDown1) {
            v1 = -sp;
            v2 = -sp;
            v3 = -sp;
            v4 = -sp;
        } else if (c.dpadLeft1) {
            v1 = -sp;
            v2 = sp;
            v3 = sp;
            v4 = -sp;
        } else {
            v1 = 0;
            v2 = 0;
            v3 = 0;
            v4 = 0;
        }


        drive(v2, v4, v3, v1);
        return new double[] {v2, v4, v3, v1};



    }

    public static void rcIntake(){
        if(c.RBumper2){
            intake.runWheels(true);
        } else if(c.LBumper2){
            intake.runWheels(false);
        } else {
            intake.stopWheels();
        }

        if(c.a2){
            intake.tsCurrent = intake.tsDown;
        }else if(c.b2){
            intake.tsCurrent = intake.tsMiddle;
        }else if(c.y2){
            intake.tsCurrent = intake.tsUp;
        }
        intake.setTransferServo();




        if(Math.abs(c.LStickY2) > .05){
            intake.runSlide(c.LStickY2);
        } else {
            intake.stopSlide();
        }

    }

    //Outtake Control
    public static void rcOuttake(){
        //Slide Controls
        if(c.dpadUp2){
            outtake.setBucketPos(1);
        } else if (c.dpadLeft2) {
            outtake.setBucketPos(0);
        } else if (c.dpadDown2){
            //outtake.targetPos = outtake.bottomSlidePos; //timing will need testing
            outtake.setBucketPos(0.5);
        }
        //Bucket Positions (for dumping)
        if (c.LTrigger2 > .25){
            outtake.targetBucketPos = outtake.bucketRegPos;
        } else if (c.RTrigger2 > .25){
            outtake.targetBucketPos = outtake.bucketOutPos;
        }

        //outtake.vslideToPos(outtake.targetPos, outtake.slidePower);
        //outtake.setBucketPos(outtake.targetBucketPos);
    }




}












