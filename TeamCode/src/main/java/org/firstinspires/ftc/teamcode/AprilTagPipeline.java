//package org.firstinspires.ftc.teamcode;
//
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import android.util.Size;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//import org.openftc.apriltag.AprilTagPose;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class AprilTagPipeline {
//
//    Servo camservo;
//    DistanceSensor dist1;
//    DistanceSensor dist2;
//
//
//    final static double ERROR_TOLERANCE = 0.1;
//
//
//
//    // Adjust these numbers to suit your robot.
//    final double DESIRED_DISTANCE = 12.0; //  this is how close the camera should get to the target (inches)
//
//    //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
//    //  applied to the drive motors to correct the error.
//    //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
//    final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
//    final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
//    final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
//
//    final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
//    final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value (adjust for your robot)
//    final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)
//
//    private DcMotor leftFrontDrive   = null;  //  Used to control the left front drive wheel
//    private DcMotor rightFrontDrive  = null;  //  Used to control the right front drive wheel
//    private DcMotor leftBackDrive    = null;  //  Used to control the left back drive wheel
//    private DcMotor rightBackDrive   = null;  //  Used to control the right back drive wheel
//
//    private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
//    private static final int DESIRED_TAG_ID = -1;     // Choose the tag you want to approach or set to -1 for ANY tag.
//    private VisionPortal visionPortal;               // Used to manage the video source.
//    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
//    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag
//
//
//    boolean targetFound     = false;    // Set to true when an AprilTag target is detected
//    double  drive           = 0;        // Desired forward power/speed (-1 to +1)
//    double  strafe          = 0;        // Desired strafe power/speed (-1 to +1)
//    double  turn            = 0;        // Desired turning power/speed (-1 to +1)
//
//
//    public AprilTagPipeline(OpMode opMode){
//        camservo = camservo;
//
//        //Start April Tag processor
//        this.initAprilTag();
//        this.dist1 = opMode.hardwareMap.get(DistanceSensor.class, "dist1");
//        this.dist2 = opMode.hardwareMap.get(DistanceSensor.class, "dist2");
//    }
//
//
//
//    /**
//     * Move robot according to desired axes motions
//     * <p>
//     * Positive X is forward
//     * <p>
//     * Positive Y is strafe left
//     * <p>
//     * Positive Yaw is counter-clockwise
//     */
//    public void moveRobot(double x, double y, double yaw) {
//        // Calculate wheel powers.
//        double v1 = 0; // lf
//        double v2 = 0; // rf
//        double v3 = 0; // lb
//        double v4 = 0; // rb
//
//        double sp = .25;
//
//
//        if (Math.abs(x) > 0.05 || Math.abs(y) > 0.05 || Math.abs(yaw) > 0.05) {
//
//
//            double r = Math.hypot(x, y);
//            double robotAngle = Math.atan2(y, x) - Math.PI / 4;
//
//            v1 = r * Math.sin(robotAngle) + yaw; //lf // was cos
//            v2 = r * Math.cos(robotAngle) - yaw; //rf // was sin
//            v3 = r * Math.cos(robotAngle) + yaw; //lb // was sin
//            v4 = r * Math.sin(robotAngle) - yaw; //rb // was cos
//
//
//        }
//
//        /**
//         * Initialize the AprilTag processor.
//         */
//    }
//
//    private void initAprilTag (){
//        // Create the AprilTag processor by using a builder.
//        aprilTag = new AprilTagProcessor.Builder().build();
//
//        // Adjust Image Decimation to trade-off detection-range for detection-rate.
//        // e.g. Some typical detection data using a Logitech C920 WebCam
//        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
//        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
//        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
//        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
//        // Note: Decimation can be changed on-the-fly to adapt during a match.
//        aprilTag.setDecimation(2);
//
//        // Create the vision portal by using a builder.
//
//            if (USE_WEBCAM) {
//                visionPortal = new VisionPortal.Builder()
//                        .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                        .addProcessor(aprilTag)
//                        .build();
//            } else {
//                visionPortal = new VisionPortal.Builder()
//                        .setCamera(BuiltinCameraDirection.BACK)
//                        .addProcessor(aprilTag)
//                        .build();
//
//        }
//    }
//
//    public ArrayList<AprilTagDetection> getCurrentDectections(){
//        return aprilTag.getDetections();
//    }
//
//    public double[] getRobotPose(){
//        double[] pose = new double[2];
//        ArrayList<AprilTagDetection> tags = getCurrentDectections();
//        if(tags.size() > 0){
//            pose[0] = dist * Math.cos(Math.toRadians(angle));
//            pose[1] = dist * Math.sin(Math.toRadians(angle));
//        }
//        return pose;
//    }
//
//
//
//}
