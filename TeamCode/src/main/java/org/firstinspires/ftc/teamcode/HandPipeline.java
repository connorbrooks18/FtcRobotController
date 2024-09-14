package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "Hand Detection and Counting")
public class HandPipeline extends com.qualcomm.robotcore.eventloop.opmode.OpMode {

    private OpenCvCamera camera;
    private Mat hsvMat = new Mat();
    private Mat mask = new Mat();
    private int handCount = 0;

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Set up and start the OpenCV camera pipeline
        camera.setPipeline(new OpenCvPipeline() {
            @Override
            public Mat processFrame(Mat input) {
                // Convert the frame to HSV color space
                Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

                // Define skin color range in HSV for white skin (adjust values for lighting conditions)
                Scalar lowerSkin = new Scalar(0, 10, 60);
                Scalar upperSkin = new Scalar(20, 150, 255);

                // Create a mask for skin color detection
                Core.inRange(hsvMat, lowerSkin, upperSkin, mask);

                // Perform morphological operations to clean the mask
                Imgproc.erode(mask, mask, new Mat(), new Point(-1, -1), 2);
                Imgproc.dilate(mask, mask, new Mat(), new Point(-1, -1), 2);

                // Find contours in the mask
                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                // Count the number of hands based on contour size and shape
                handCount = 0; // Reset hand count

                for (MatOfPoint contour : contours) {
                    double area = Imgproc.contourArea(contour);

                    // Assuming hands fall within a certain area range
                    if (area > 1000) { // Adjust this threshold as necessary
                        handCount++;

                        // Draw the detected hand contour
                        Imgproc.drawContours(input, contours, contours.indexOf(contour), new Scalar(0, 255, 0), 2);
                    }
                }

                return input; // Return the processed frame
            }
        });

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
            }
        });
    }

    @Override
    public void loop() {
        // Display the number of detected hands
        telemetry.addData("Hands Detected", handCount);
        telemetry.update();
    }
}
