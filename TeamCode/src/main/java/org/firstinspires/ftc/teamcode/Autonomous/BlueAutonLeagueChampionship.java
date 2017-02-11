package org.firstinspires.ftc.teamcode.Autonomous;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/5/2017.
 */
@Autonomous(name="Blue Auton", group="Competition")
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

        while ((!(white_line)) && opModeIsActive()) {
            if (balin.ods.getRawLightDetected() >= .6) {
                white_line = true;
            }

            balin.bl.setPower(.9);
            balin.fr.setPower(.9);
            balin.br.setPower(-.05);
            balin.fl.setPower(-.05);
            double reflectance = balin.ods.getLightDetected();
            telemetry.addData("reflectance", reflectance);
            telemetry.update();
        }
        balin.setDrivePower(0);
        sleep(500);




        while (white_line && balin.ods.getRawLightDetected() <= .6 && opModeIsActive()) {
            balin.fr.setPower(-.35);
            balin.br.setPower(.35);
            balin.fl.setPower(.35);
            balin.bl.setPower(-.35);
        }

        balin.setDrivePower(0);
        sleep(500);
        turnToOriginalAngle();
        sleep(500);
        //BEACON CODE GOES HERE:

        while(!(balin.colorSensor.red()> balin.colorSensor.blue())&&!(balin.colorSensor.blue()>balin.colorSensor.red())){
            balin.setDrivePower(.2);
        }
        if(detectColor()){
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
        }
        else{
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
        }
        balin.setDrivePower(.3);
        sleep(2000);

        white_line = false;

        while ((!(white_line)) && opModeIsActive()) {
            if (balin.ods.getRawLightDetected() >= .6) {
                white_line = true;
            }

            balin.bl.setPower(.5);
            balin.fr.setPower(.5);
            balin.br.setPower(-.5);
            balin.fl.setPower(-.5);
            double reflectance = balin.ods.getLightDetected();
            telemetry.addData("reflectance", reflectance);
            telemetry.update();
        }
        balin.setDrivePower(0);
        sleep(500);

        while (white_line && balin.ods.getRawLightDetected() <= .6 && opModeIsActive()) {
            balin.fr.setPower(-.35);
            balin.br.setPower(.35);
            balin.fl.setPower(.35);
            balin.bl.setPower(-.35);
        }

        balin.setDrivePower(0);
        sleep(500);
        turnToOriginalAngle();
        sleep(500);

    }
    public void followWhiteLine(){
        double adjustment = .2 - balin.ods.getLightDetected();
        if(adjustment <=0){
            balin.fl.setPower(.3-adjustment);
            balin.fr.setPower(.3);
            balin.bl.setPower(.3-adjustment);
            balin.br.setPower(.3);
        }
        else{
            balin.fl.setPower(.3);
            balin.fr.setPower(.3+adjustment);
            balin.bl.setPower(.3);
            balin.br.setPower(.3+adjustment);
        }
    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        balin.gyroAngle(AngleToTurnTo, .2, this);
        balin.navx_device.zeroYaw();
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
