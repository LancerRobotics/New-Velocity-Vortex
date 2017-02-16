package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;
/**
 * Created by david.lin on 2/16/2017.
 */

@TeleOp(name = "Teleop without Perspective Drive WITHOUT PROTECTION", group = "Competition")

public class WithoutProtectionTeleopWithHardwareFromRobWithoutPerspectiveDrive extends LinearOpMode{
    /* Declare OpMode members. */
    Hardware3415 Balin           = new Hardware3415();
    public static double x, y, z, trueX, trueY;
    public static double frPower, flPower, brPower, blPower;

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        Balin.init(hardwareMap, false);

        // Send telemetry message to signify Balin waiting;
        telemetry.addData("Ready?", "Yes");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        Balin.rollerRelease.setPosition(Balin.ROLLER_RELEASE_OUT);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Sets controls for linear slides on forklift
            if (Math.abs(gamepad2.right_stick_y) > .15) {
                Balin.liftLeft.setPower(Range.clip(gamepad2.right_stick_y, -1, 1));
                Balin.liftRight.setPower(Range.clip(gamepad2.right_stick_y, -1, 1));
            } else {
                Balin.liftLeft.setPower(0);
                Balin.liftRight.setPower(0);
            }

            //Sets controls for shooter
            if (gamepad1.left_trigger > .15) {
                Balin.shoot(1);
            } else if (gamepad1.left_bumper) {
                Balin.shoot(0);
            }

            //Sets controls for collector
            if (gamepad1.right_trigger > 0.15) {
                Balin.collector.setPower(0.99);
            } else if (gamepad1.right_bumper) {
                Balin.collector.setPower(-0.99);
            } else {
                Balin.collector.setPower(0);
            }

            //Sets the gamepad values to x, y, and z
            z = gamepad1.right_stick_x; //sideways
            y = gamepad1.left_stick_y; //forward and backward
            x = gamepad1.left_stick_x; //rotation

            //Sets the motor powers of the wheels to the correct power based on all three of the above gyro values and
            //scales them accordingly
            flPower = Range.scale((-x + y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);
            frPower = Range.scale((-x - y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);
            blPower = Range.scale((x + y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);
            brPower = Range.scale((x - y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);

            //Sets each motor power to the correct power
            Balin.fl.setPower(flPower);
            Balin.fr.setPower(frPower);
            Balin.bl.setPower(blPower);
            Balin.br.setPower(brPower);

            //Backup movement if the above method fails
            if (x == 0 && y == 0 && z == 0) {
                if (gamepad1.dpad_right) {
                    Balin.bl.setPower(Balin.MAX_MOTOR_SPEED);
                    Balin.fl.setPower(Balin.MAX_MOTOR_SPEED);
                } else if (gamepad1.dpad_up) {
                    Balin.bl.setPower(-Balin.MAX_MOTOR_SPEED);
                    Balin.fl.setPower(-Balin.MAX_MOTOR_SPEED);
                } else if (gamepad1.dpad_down) {
                    Balin.br.setPower(Balin.MAX_MOTOR_SPEED);
                    Balin.fr.setPower(Balin.MAX_MOTOR_SPEED);
                } else if (gamepad1.dpad_left) {
                    Balin.br.setPower(-Balin.MAX_MOTOR_SPEED);
                    Balin.fr.setPower(-Balin.MAX_MOTOR_SPEED);
                }
            }

            //Control servo toggling for beacon pushers and clamps
            Balin.beaconPushLeftToggleReturnArray = Balin.servoToggle(gamepad2.left_trigger > .15, Balin.beaconPushLeft, Balin.beaconPushLeftPositions, Balin.beaconPushLeftPos, Balin.beaconPushLeftButtonPressed);
            Balin.beaconPushLeftPos = Balin.beaconPushLeftToggleReturnArray[0];
            Balin.beaconPushLeftButtonPressed = Balin.beaconPushLeftToggleReturnArray[1] == 1;

            Balin.beaconPushRightToggleReturnArray = Balin.servoToggle(gamepad2.right_trigger > .15, Balin.beaconPushRight, Balin.beaconPushRightPositions, Balin.beaconPushRightPos, Balin.beaconPushRightButtonPressed);
            Balin.beaconPushRightPos = Balin.beaconPushRightToggleReturnArray[0];
            Balin.beaconPushRightButtonPressed = Balin.beaconPushRightToggleReturnArray[1] == 1;

            if(gamepad2.a) {
                Balin.clampLeft.setPosition(Balin.LEFT_CLAMP_CLAMP);
                Balin.clampRight.setPosition(Balin.RIGHT_CLAMP_CLAMP);
            }
            else if (gamepad2.y) {
                Balin.clampLeft.setPosition(Balin.LEFT_CLAMP_UP);
                Balin.clampRight.setPosition(Balin.RIGHT_CLAMP_UP);
            }

            //Returns important data to the driver.
            telemetry.addData("GamePad 1 Right Stick X Actual", gamepad1.right_stick_x);
            telemetry.addData("GamePad 1 Left Stick Y Actual", gamepad1.left_stick_y);
            telemetry.addData("GamePad 1 Left Stick X Actual", gamepad1.left_stick_x);
            telemetry.addData("GamePad 1 X", gamepad1.x);
            telemetry.addData("FR Power", Balin.fr.getPower());
            telemetry.addData("FL Power", Balin.fl.getPower());
            telemetry.addData("BR Power", Balin.br.getPower());
            telemetry.addData("BL Power", Balin.bl.getPower());
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            Balin.waitForTick(40);
        }
    }
}

