package org.firstinspires.ftc.teamcode.Teleop;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by david.lin on 2/16/2017.
 */

@TeleOp(name = "Teleop With Perpective Drive WITH PROTECTION", group = "Competition")
@Disabled
public class WithProtectionTeleopWithHardwareFromRob extends LinearOpMode{
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
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        Balin.navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get(Balin.cdim),
                Balin.NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                Balin.NAVX_DEVICE_UPDATE_RATE_HZ);
        //Prevents Balin from running before callibration is complete
        while (Balin.navx_device.isCalibrating()) {
            telemetry.addData("Ready?", "No");
            telemetry.update();
        }
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        Balin.navx_device.zeroYaw();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if(opModeIsActive()) Balin.rollerRelease.setPosition(Balin.ROLLER_RELEASE_OUT);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            if (gamepad1.right_stick_button && gamepad1.left_stick_button) {
                if(opModeIsActive()) Balin.navx_device.zeroYaw();
            }

            //Sets controls for linear slides on forklift
            if (Math.abs(gamepad2.right_stick_y) > .15) {
                if(opModeIsActive()) Balin.liftLeft.setPower(Range.clip(gamepad2.right_stick_y, -1, 1));
                if(opModeIsActive()) Balin.liftRight.setPower(Range.clip(gamepad2.right_stick_y, -1, 1));
            } else {
                if(opModeIsActive()) Balin.liftLeft.setPower(0);
                if(opModeIsActive()) Balin.liftRight.setPower(0);
            }

            //Sets controls for shooter
            if (gamepad1.left_trigger > .15) {
                if(opModeIsActive()) Balin.shoot(1.0);
            } else if (gamepad1.left_bumper) {
                if(opModeIsActive()) Balin.shoot(0);
            }
            else{
                if(opModeIsActive()) Balin.shoot(0);
            }

            //Sets controls for collector
            if (gamepad1.right_trigger > 0.15) {
                if(opModeIsActive()) Balin.collector.setPower(-0.99);
            } else if (gamepad1.right_bumper) {
                if(opModeIsActive()) Balin.collector.setPower(0.99);
            } else {
                if(opModeIsActive()) Balin.collector.setPower(0);
            }

            //Sets the gamepad values to x, y, and z
            z = gamepad1.right_stick_x; //sideways
            y = gamepad1.left_stick_y; //forward and backward
            x = gamepad1.left_stick_x; //rotation

            //Converts x and y to a different value based on the gyro value
            trueX = ((Math.cos(Math.toRadians(360 - Balin.convertYaw(Balin.navx_device.getYaw())))) * x) - ((Math.sin(Math.toRadians(360 - Balin.convertYaw(Balin.navx_device.getYaw())))) * y); //sets trueX to rotated value
            trueY = ((Math.sin(Math.toRadians(360 - Balin.convertYaw(Balin.navx_device.getYaw())))) * x) + ((Math.cos(Math.toRadians(360 - Balin.convertYaw(Balin.navx_device.getYaw())))) * y);

            //Sets trueX and trueY to its respective value
            x = trueX;
            y = trueY;

            //Sets the motor powers of the wheels to the correct power based on all three of the above gyro values and
            //scales them accordingly
            flPower = Range.scale((-x + y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);
            frPower = Range.scale((-x - y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);
            blPower = Range.scale((x + y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);
            brPower = Range.scale((x - y - z), -1, 1, -Balin.MAX_MOTOR_SPEED, Balin.MAX_MOTOR_SPEED);

            //Sets each motor power to the correct power
            if(opModeIsActive()) Balin.fl.setPower(flPower);
            if(opModeIsActive()) Balin.fr.setPower(frPower);
            if(opModeIsActive()) Balin.bl.setPower(blPower);
            if(opModeIsActive()) Balin.br.setPower(brPower);

            //Backup movement if the above method fails
            if (x == 0 && y == 0 && z == 0) {
                if (gamepad1.dpad_right) {
                    if(opModeIsActive()) Balin.bl.setPower(Balin.MAX_MOTOR_SPEED);
                    if(opModeIsActive()) Balin.fl.setPower(Balin.MAX_MOTOR_SPEED);
                } else if (gamepad1.dpad_up) {
                    if(opModeIsActive()) Balin.bl.setPower(-Balin.MAX_MOTOR_SPEED);
                    if(opModeIsActive()) Balin.fl.setPower(-Balin.MAX_MOTOR_SPEED);
                } else if (gamepad1.dpad_down) {
                    if(opModeIsActive()) Balin.br.setPower(Balin.MAX_MOTOR_SPEED);
                    if(opModeIsActive()) Balin.fr.setPower(Balin.MAX_MOTOR_SPEED);
                } else if (gamepad1.dpad_left) {
                    if(opModeIsActive()) Balin.br.setPower(-Balin.MAX_MOTOR_SPEED);
                    if(opModeIsActive()) Balin.fr.setPower(-Balin.MAX_MOTOR_SPEED);
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
                if(opModeIsActive()) Balin.clampLeft.setPosition(Balin.LEFT_CLAMP_CLAMP);
                if(opModeIsActive()) Balin.clampRight.setPosition(Balin.RIGHT_CLAMP_CLAMP);
            }
            else if (gamepad2.y) {
                if(opModeIsActive()) Balin.clampLeft.setPosition(Balin.LEFT_CLAMP_UP);
                if(opModeIsActive()) Balin.clampRight.setPosition(Balin.RIGHT_CLAMP_UP);
            }

            //Returns important data to the driver.
            if(opModeIsActive()) telemetry.addData("GamePad 1 Right Stick X Actual", gamepad1.right_stick_x);
            if(opModeIsActive()) telemetry.addData("GamePad 1 Left Stick Y Actual", gamepad1.left_stick_y);
            if(opModeIsActive()) telemetry.addData("GamePad 1 Left Stick X Actual", gamepad1.left_stick_x);
            if(opModeIsActive()) telemetry.addData("GamePad 1 X", gamepad1.x);
            if(opModeIsActive()) telemetry.addData("FR Power", Balin.fr.getPower());
            if(opModeIsActive()) telemetry.addData("FL Power", Balin.fl.getPower());
            if(opModeIsActive()) telemetry.addData("BR Power", Balin.br.getPower());
            if(opModeIsActive()) telemetry.addData("BL Power", Balin.bl.getPower());
            if(opModeIsActive()) telemetry.addData("Yaw", Balin.convertYaw(Balin.navx_device.getYaw()));
            if(opModeIsActive()) telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            if(opModeIsActive()) Balin.waitForTick(40);
        }
    }
}
