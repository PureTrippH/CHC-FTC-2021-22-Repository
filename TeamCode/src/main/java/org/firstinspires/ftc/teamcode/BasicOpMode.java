package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name = "Free Shipping: Driving")
public class BasicOpMode extends OpMode {

    //Declare Members
    private HardwareStarTrek robot = new HardwareStarTrek();

    //Initialization code
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Good Luck Driver!");    //
    }
    //ACTUAL DRIVING
    @Override
    public void loop() {
         double leftPower;
         double rightPower;
         double turn;
         double forward;
         double max;

    // BASIC CONTROLS AND STEERING
         forward = gamepad1.right_stick_x;
         turn = - gamepad1.left_stick_y;
         leftPower = forward + turn;
         rightPower = forward - turn;
         max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1.0){
            leftPower/= max;
            rightPower/= max;
        }
        robot.leftDrive.setPower(leftPower);
        robot.rightDrive.setPower(rightPower);
        if ( gamepad2.a == true ) {
            robot.duckSpinner.setPower(-1);
        } else {
            robot.duckSpinner.setPower(0);
        }
        //Spinner
        if ( gamepad2.b == true ) {
            robot.spinner.setPower(1);
        } else if ( gamepad2.x == true ) {
            robot.spinner.setPower(-1);
        } else if(gamepad2.y == true) {
            robot.spinner.setPower(0.0);
        }
        telemetry.addData("Servo Pos: ", robot.spinner.getPower());
      //ARM COMPONENT OSCILLATOR 2
        if (gamepad2.dpad_up == true) {
            robot.armMotor.setPower(0.5);
        } else if(gamepad2.dpad_down == true){
            robot.armMotor.setPower(-1.0);
        } else {
            robot.armMotor.setPower(0);
        }

        if (gamepad2.left_bumper == true ) {
            robot.oscillator1.setPower(1);
        } else if (gamepad2.left_trigger >=0.5) {
            robot.oscillator1.setPower(-1);
        } else {
            robot.oscillator1.setPower(0);
        }
        if (gamepad2.right_bumper == true) {
            robot.oscillator2.setPower(-1);
        } else if (gamepad2.right_trigger >=0.5) {
            robot.oscillator2.setPower(1);
        } else {
            robot.oscillator2.setPower(0);
        }
        robot.leftDrive.setPower(leftPower);
        robot.rightDrive.setPower(rightPower);
    }
    @Override
    public void stop() {

    }
}