package org.firstinspires.ftc.teamcode.Autonomous;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/5/2017.
 */
@Autonomous(name="Blue Auton", group="Competition")
@Disabled
public class BlueAutonLeagueChampionship extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode() {
        balin.init(hardwareMap, true);
        balin.navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get(balin.cdim),
                balin.NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                balin.NAVX_DEVICE_UPDATE_RATE_HZ);
        while (balin.navx_device.isCalibrating()) {
            telemetry.addData("Ready?", "No");
            telemetry.update();
        }
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //balin.turn();
        balin.navx_device.zeroYaw();

        boolean white_line = false;

        while ((!(white_line)) && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .6) {
                white_line = true;
            }

            if(opModeIsActive())     balin.bl.setPower(.9);
            if(opModeIsActive())     balin.fr.setPower(.9);
            if(opModeIsActive())     balin.br.setPower(-.05);
            if(opModeIsActive())     balin.fl.setPower(-.05);
            double reflectance = balin.ods.getLightDetected();
            if(opModeIsActive())     telemetry.addData("reflectance", reflectance);
            if(opModeIsActive())     telemetry.update();
        }
        if(opModeIsActive())    balin.setDrivePower(0);
        if(opModeIsActive())    sleep(500);
        while (white_line && balin.ods.getRawLightDetected() <= .6 && opModeIsActive() && !isStopRequested()) {
            if(opModeIsActive()) balin.fr.setPower(-.25);
            if(opModeIsActive()) balin.br.setPower(.25);
            if(opModeIsActive()) balin.fl.setPower(.25);
            if(opModeIsActive()) balin.bl.setPower(-.25);
        }

        if(opModeIsActive()) balin.setDrivePower(0);
        if(opModeIsActive()) sleep(500);
        if(opModeIsActive()) turnToOriginalAngle();
        if(opModeIsActive()) balin.restAndSleep(this);
        if(opModeIsActive()) sleep(500);
        while(opModeIsActive() && !isStopRequested() && (balin.colorSensor.red() == 0 || balin.colorSensor.blue() == 0)) {
            if(opModeIsActive()) balin.setDrivePower(.05);
        }
        if(opModeIsActive()) balin.restAndSleep(this);
        if(balin.colorSensor.blue() > balin.colorSensor.red()) {
            balin.beaconBlue = true;
        }
        else {
            balin.beaconBlue = false;
        }
        if(opModeIsActive()) balin.restAndSleep(this);
        if(balin.beaconBlue) {
            if(opModeIsActive()) balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
            if(opModeIsActive()) balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
        }
        else {
            if(opModeIsActive()) balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
            if(opModeIsActive()) balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
        }
        if(opModeIsActive()) sleep(500);
        if(opModeIsActive()) balin.setDrivePower(.1);
        if(opModeIsActive()) sleep(1500);
        if(opModeIsActive()) balin.restAndSleep(this);
        if(opModeIsActive()) balin.setDrivePower(-.1);
        if(opModeIsActive()) sleep(1000);
        if(opModeIsActive()) balin.restAndSleep(this);
        if(opModeIsActive()) balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
        if(opModeIsActive()) balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
        white_line = false;
        if(opModeIsActive()) balin.changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(opModeIsActive()) balin.bl.setPower(.4);
        if(opModeIsActive()) balin.fr.setPower(.4);
        if(opModeIsActive()) balin.br.setPower(-.4);
        if(opModeIsActive()) balin.fl.setPower(-.4);
        if(opModeIsActive()) sleep(1500);
        if(opModeIsActive()) balin.gyroAngle(-balin.navx_device.getYaw() - 5, .2, this);
        while ((!(white_line)) && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .6) {
                white_line = true;
            }

            if(opModeIsActive()) balin.bl.setPower(.4);
            if(opModeIsActive()) balin.fr.setPower(.4);
            if(opModeIsActive()) balin.br.setPower(-.4);
            if(opModeIsActive()) balin.fl.setPower(-.4);
            double reflectance = balin.ods.getLightDetected();
            if(opModeIsActive()) telemetry.addData("reflectance", reflectance);
            if(opModeIsActive()) telemetry.update();
        }
        if(opModeIsActive()) balin.setDrivePower(0);
        if(opModeIsActive()) sleep(500);

        while (white_line && balin.ods.getRawLightDetected() <= .6 && opModeIsActive() && !isStopRequested()) {
            if(opModeIsActive()) balin.fr.setPower(-.25);
            if(opModeIsActive()) balin.br.setPower(.25);
            if(opModeIsActive()) balin.fl.setPower(.25);
            if(opModeIsActive()) balin.bl.setPower(-.25);
        }

        if(opModeIsActive()) balin.setDrivePower(0);
        if(opModeIsActive()) sleep(500);
        while(opModeIsActive() && !isStopRequested() && (balin.colorSensor.red() == 0 || balin.colorSensor.blue() == 0)) {
            if(opModeIsActive()) balin.setDrivePower(.05);
        }
        if(opModeIsActive()) balin.restAndSleep(this);
        if(balin.colorSensor.blue() > balin.colorSensor.red()) {
            balin.beaconBlue = true;
        }
        else {
            balin.beaconBlue = false;
        }
        if(opModeIsActive()) balin.restAndSleep(this);
        if(balin.beaconBlue) {
            if(opModeIsActive()) balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
            if(opModeIsActive()) balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
        }
        else {
            if(opModeIsActive()) balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
            if(opModeIsActive()) balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
        }
            if(opModeIsActive()) sleep(500);
        if(opModeIsActive()) balin.setDrivePower(.1);
        if(opModeIsActive()) sleep(1500);
        if(opModeIsActive()) balin.restAndSleep(this);
        if(opModeIsActive()) balin.setDrivePower(-.1);
        if(opModeIsActive()) sleep(750);
        if(opModeIsActive()) balin.restAndSleep(this);
    }
    public void followWhiteLine(){
        double adjustment = (.2 - balin.ods.getLightDetected())*.15;
        if(adjustment <=0){
            if(opModeIsActive()) balin.fl.setPower(.1-adjustment);
            if(opModeIsActive()) balin.fr.setPower(.1);
            if(opModeIsActive()) balin.bl.setPower(.1-adjustment);
            if(opModeIsActive()) balin.br.setPower(.1);
        }
        else{
            if(opModeIsActive()) balin.fl.setPower(.1);
            if(opModeIsActive()) balin.fr.setPower(.1+adjustment);
            if(opModeIsActive()) balin.bl.setPower(.1);
            if(opModeIsActive()) balin.br.setPower(.1+adjustment);
        }
    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        if(opModeIsActive()) balin.gyroAngle(AngleToTurnTo, .2, this);
    }
   /* public void detectBeaconhit{
        if(balin.detectAColor()){
            return
        }
    }
    */
    public boolean detectColor(){ //for blue
    //int [] rgb = balin.getRGB();
        if(balin.colorSensor.red() > balin.colorSensor.blue()){
            return false;
        }
        return true;
    }


}
