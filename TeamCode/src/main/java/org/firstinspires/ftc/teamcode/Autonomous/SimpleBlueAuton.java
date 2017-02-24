package org.firstinspires.ftc.teamcode.Autonomous;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 2/11/2017.
 */
@Autonomous(name = "BlueSimple", group = "Auton")

public class SimpleBlueAuton extends LinearOpMode {
    private ElapsedTime     runtime = new ElapsedTime();
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        balin.navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get(balin.cdim),
                balin.NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                balin.NAVX_DEVICE_UPDATE_RATE_HZ);
        while (balin.navx_device.isCalibrating() && !isStopRequested()) {
            telemetry.addData("Ready?", "No");
            telemetry.update();
        }
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        balin.changeDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //balin.turn();
        balin.navx_device.zeroYaw();
        balin.moveStraightnew(8, this);
        balin.setDrivePower(0);
        balin.collector.setPower(0);
        sleep(500);

        //Shoot first particle
        balin.shoot(1.0);
        sleep(600);
        balin.shoot(0);

        //Get second particle into the shooter
        balin.door.setPosition(balin.DOOR_OPEN);
        balin.collector.setPower(1.0);
        sleep(2000);
        balin.collector.setPower(0);
        balin.door.setPosition(balin.DOOR_CLOSED);
        sleep(500);
        //Shoot second particle
        balin.shoot(1.0);
        sleep(600);
        balin.shoot(0);
        sleep(200);
        balin.gyroAngle(39, .25, this);
        balin.restAndSleep(this);
        boolean white_line = false;
        balin.collector.setPower(-1.0);
        balin.setDrivePower(.2);
        while(!white_line && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .5) {
                white_line = true;
             }
            telemetry.addData("Following White Line: ", balin.ods.getLightDetected());
            telemetry.addData("FL: ", balin.fl.getPower());
            telemetry.addData("FR: ", balin.fr.getPower());
            telemetry.addData("BR: ", balin.br.getPower());
            telemetry.addData("BL:", balin.bl.getPower());
            telemetry.update();
        }
        balin.collector.setPower(0);
        balin.setDrivePower(0);
        balin.rest();
        sleep(150);
        balin.navx_device.zeroYaw();
        sleep(100);
        balin.gyroAngle(49, .25, this);
        balin.rest();
        sleep(200);
        telemetry.addLine("Moving To Strafe");
        telemetry.update();
        balin.Straighten();
        balin.Straighten();
        balin.adjustToDistance(15, .17, this);
        white_line = false;
        while (!white_line  && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .5) {
                white_line = true;
            }
            balin.fr.setPower(-.3);
            balin.br.setPower(.3);
            balin.fl.setPower(.3);
            balin.bl.setPower(-.3);
        }
        balin.rest();
        telemetry.addLine("Strafe Complete");
        balin.restAndSleep(this);
        telemetry.addLine("Starting Adjust to 12 cm");
        telemetry.update();
        balin.adjustToDistance(12, .17, this);
        telemetry.addLine("Detecting Color");
        telemetry.update();
        String color = balin.detectColor();
        balin.setDrivePower(0);
        if(color.equals("Red")){
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
            telemetry.addData("Color: ", color);
            telemetry.update();
            sleep(500);
        } else if(color.equals("Blue")) {
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
            telemetry.addData("Color: ", color);
            telemetry.update();
            sleep(500);
        } else{
            telemetry.addData("Color: ", color);
            telemetry.update();
            sleep(500);
        }
        telemetry.addLine("Hitting Beacon");
        telemetry.update();
        if(color.equals("Red") || color.equals("Blue")) {
            balin.moveStraightnew(3.75, this);
        }
        balin.rest();
        sleep(250);
        telemetry.addLine("Preppring For Next Strafe");
        balin.adjustToDistance(10, .17, this);
        balin.rest();
        sleep(150);
        white_line = false;
        balin.fr.setPower(.5);
        balin.br.setPower(-.5);
        balin.fl.setPower(-.5);
        balin.bl.setPower(.5);
        sleep(786);
        while (!white_line  && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .5) {
                white_line = true;
            }
        }
        balin.setDrivePower(0);
        balin.Straighten();
        balin.Straighten();
        balin.adjustToDistance(15, .17, this);
        sleep(150);
        white_line = false;
        while (!white_line  && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .5) {
                white_line = true;
            }
            balin.fr.setPower(-.3);
            balin.br.setPower(.3);
            balin.fl.setPower(.3);
            balin.bl.setPower(-.3);
        }
        balin.setDrivePower(0);
    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        if(opModeIsActive()) balin.gyroAngle(AngleToTurnTo, .2, this);
    }
}
