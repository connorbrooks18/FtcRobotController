package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import static org.firstinspires.ftc.teamcode.Robot.*;

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







        initAll(this);
        intake.tsTarget = intake.tsMiddle;
        intake.setTransferServo();

        waitForStart();
        while(opModeIsActive()){
            c.update();

            outtake.resetVSlide();


            if(Math.abs(c.LStickY2) > .05){
                outtake.vslideToPow(c.LStickY2);
            } else {
                outtake.vslideToPow(0);
            }
            outtake.setBucketPos(c.RTrigger2);



            telemetry.addData("vslide encoder", outtake.getVSlidePos());
            telemetry.addData("bucket pos", outtake.getBucketPos());
            telemetry.addData("vslide limit", outtake.slideAtBottom());
            telemetry.update();

        }
    }
}
