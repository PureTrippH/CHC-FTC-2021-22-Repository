package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name = "Free Shipping: Encoders")
public class EncoderDrive extends LinearOpMode {
    private HardwareStarTrek robot = new HardwareStarTrek();
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    =  1316; //: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP

    static final double     WHEEL_DIAMETER_INCHES   = 3.74 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415926);
    static final double     DRIVE_SPEED             = .5;
    static final double     TURN_SPEED              = .4;




    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to sigleftnify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftDrive.getCurrentPosition(),
                robot.rightDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //encoderDrive(TURN_SPEED,   -12, -12, 30);
        encoderDrive(DRIVE_SPEED,  -13, 13, 30);  // S1
        encoderDrive(TURN_SPEED,   11.10, 11.10, 30);
        encoderDrive(DRIVE_SPEED,  22.3, -22.3, 30);
        encoderDrive(TURN_SPEED,   -11.10, -11.10, 30);
        robot.duckSpinner.setPower(-1);
        encoderDrive(0.2,  10, -10, 30);
        encoderDrive(0.01,  0.5, -0.5, 30);
        robot.duckSpinner.setPower(0);
        encoderDrive(DRIVE_SPEED,  -10, 10, 30);
        encoderDrive(TURN_SPEED,   -2.3, -2.3, 30);
        encoderDrive(DRIVE_SPEED,   -21, 21, 30);
        encoderDrive(TURN_SPEED,   -11.10, -11.10, 30);
        encoderDrive(DRIVE_SPEED,   25, -25, 30);
        robot.armMotor.setPower(0.5);
        sleep(2900);
        robot.armMotor.setPower(0);
        robot.spinner.setPower(1);
        sleep(2900);
        robot.spinner.setPower(0);
        encoderDrive(DRIVE_SPEED,   -28, 28, 30);
        encoderDrive(TURN_SPEED,   14.10, 14.10, 30);
        encoderDrive(DRIVE_SPEED,   10, -10, 30);


        //encoderDrive(TURN_SPEED,   -10.76, -10.76, 30);
        //encoderDrive(DRIVE_SPEED,  100, -100, 30);  // S1



        //ARM COMPONENTS// : Forward 48 Inches with 5 Sec timeout
        //     encoderDrive(DRIVE_SPEED, 100, 100, 20);  // S3: Reverse 24 Inches with 4 Sec timeout

       /* robot.leftClaw.setPosition(1.0);            // S4: Stop and close the claw.
        robot.rightClaw.setPosition(0.0);
        sleep(1000);     // pause for servos to move
        telemetry.addData("Path", "Complete");
        telemetry.update(); */
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
