package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
            telemetry.addData("Say", "Hello Driver");    //
        }

        //Unused loops
        @Override
        public void init_loop() {
        }

        @Override
        public void start() {
        }

        //ACTUAL DRIVING
        @Override
        public void loop() {
             double leftPower;
             double rightPower;
             double turn;
             double forward;
             double max;
             double timeS;


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
        //SERVO MOTOR TEST

                    if (gamepad1.a){
                        robot.servoTest.setPosition(.9);
                   }

                    if(gamepad1.b) {
                        robot.servoTest.setPosition(0.1);
                    }






            // Duck Motor


              //      if ( gamepad2.a == true ) {
              //          robot.duckSpinner.setPower(-1);
              //      } else {
              //          robot.duckSpinner.setPower(0);
              //      }
         //ARM COMPONENT OSCILLATOR 1
            if ( gamepad2.dpad_up == true && gamepad2.a == true) {
                robot.oscillator1.setPosition(1);
            } else if(gamepad2.dpad_down == true && gamepad2.a == true) {
                robot.oscillator1.setPosition(0);
            } else {
                robot.oscillator1.setPosition(0.55);
            }

          //ARM COMPONENT OSCILLATOR 2
            if (gamepad2.dpad_up == true && gamepad2.b == true) {
                robot.oscillator2.setPosition(1);
            } else if(gamepad2.dpad_down == true && gamepad2.b == true){
                robot.oscillator2.setPosition(0);
            } else {
                robot.oscillator2.setPosition(0.5);
            }



         //encoder turn











               robot.leftDrive.setPower(leftPower);
               robot.rightDrive.setPower(rightPower);



          }
        @Override
        public void stop() {

        }
}

