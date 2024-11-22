package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CameraPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import static org.firstinspires.ftc.teamcode.Robot.*;

@TeleOp(name = "Telop")
public class Teleop extends LinearOpMode {

    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);
//
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

        initAll(this);

        waitForStart();
        while(opModeIsActive()){
            c.update();

            rcDriving();
            rcIntake();

            rcOuttake(); // Use later

            telemetry.addData("Hslide pos", intake.hslide.getCurrentPosition());
            telemetry.addData("Hslide pow", intake.hslide.getPower());
//            telemetry.addData("VSlide pos", outtake.vslide.getCurrentPosition());
//            telemetry.addData("VSlide power", outtake.vslide.getPower());
//            telemetry.addData("VSlide target", outtake.targetPos);
//            telemetry.addData("VSlide mode", outtake.vslide.getMode());
//            telemetry.addData("Vslide bottom", outtake.slideAtBottom());
//            telemetry.addData("Vslide bottom value", outtake.vslideBottom.getValue());
            telemetry.addData("found bottom", foundBottom);
            telemetry.addData("Time is ",  this.time);

//            telemetry.addData("Color Seen: ", intake.getColor());
            telemetry.update();

        }
    }
}