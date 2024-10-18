package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.AutonomousDrive;
import org.firstinspires.ftc.teamcode.IMUControl;
import org.openftc.easyopencv.OpenCvCamera;

@TeleOp(name = "TelopTest")
public class TeleopTest extends LinearOpMode {

    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);
//

//        CameraPipeline pipeline = new CameraPipeline(telemetry, 0,1);
//        webcam.setPipeline(pipeline);
//
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
//            }
//
//
//            @Override
//            public void onError(int errorCode) {
//                webcam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
//            }
//            /*
//             * This will be called if the camera could not be opened
//             */
//
//        });

        IMUControl imu = new IMUControl(this);
        DcMotor fe = this.hardwareMap.get(DcMotor.class, "rb");
        DcMotor me = this.hardwareMap.get(DcMotor.class, "lb");
        DcMotor sm = this.hardwareMap.get(DcMotor.class, "vslide");
        sm.setDirection(DcMotorSimple.Direction.REVERSE);

        fe.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        me.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fe.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        me.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fe.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();
        while(opModeIsActive()){



            sm.setPower(Math.abs(gamepad1.left_stick_y) > .05 ? -gamepad1.left_stick_y : 0);
//            telemetry.addData("Distance: ", ds.getDistance(DistanceUnit.MM));
//            telemetry.addData("R: ", cs.red());
//            telemetry.addData("G: ", cs.green());
//            telemetry.addData("B: ", cs.blue());
//            int[] encoderValues = ad.getEncoderPositions();
            telemetry.addData("FE : ", fe.getCurrentPosition());
            telemetry.addData("ME : ", me.getCurrentPosition());
            telemetry.addData("FE inches : ", AutonomousDrive.ticksToInches(fe.getCurrentPosition()));
            telemetry.addData("ME inches : ", AutonomousDrive.ticksToInches(me.getCurrentPosition()));

//
            telemetry.addData("Z: ", imu.getAdjustedHeading());
            telemetry.addData("Slide encoder val", sm.getCurrentPosition());


            telemetry.update();

        }
    }
}
