
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
@Autonomous(name="Blue Auton NOT PROTECTED", group="Competition")
@Disabled
public class BlueAutonLeagueChampionshipsWithoutinsurance extends LinearOpMode {
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
        while (white_line && balin.ods.getRawLightDetected() <= .6 && opModeIsActive() && !isStopRequested()) {
            balin.fr.setPower(-.25);
            balin.br.setPower(.25);
            balin.fl.setPower(.25);
            balin.bl.setPower(-.25);
        }

        balin.setDrivePower(0);
        sleep(500);
        turnToOriginalAngle();
        balin.restAndSleep(this);
        sleep(500);
        while(opModeIsActive() && !isStopRequested() && (balin.colorSensor.red() == 0 || balin.colorSensor.blue() == 0)) {
            balin.setDrivePower(.05);
        }
        balin.restAndSleep(this);
        if(balin.colorSensor.blue() > balin.colorSensor.red()) {
            balin.beaconBlue = true;
        }
        else {
            balin.beaconBlue = false;
        }
        balin.restAndSleep(this);
        if(balin.beaconBlue) {
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
        }
        else {
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
        }
        sleep(500);
        balin.setDrivePower(.1);
        sleep(1500);
        balin.restAndSleep(this);
        balin.setDrivePower(-.1);
        sleep(1000);
        balin.restAndSleep(this);
        balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
        balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
        white_line = false;
        balin.changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        balin.bl.setPower(.4);
        balin.fr.setPower(.4);
        balin.br.setPower(-.4);
        balin.fl.setPower(-.4);
        sleep(1500);
        balin.gyroAngle(-balin.navx_device.getYaw() - 5, .2, this);
        while ((!(white_line)) && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .6) {
                white_line = true;
            }

            balin.bl.setPower(.4);
            balin.fr.setPower(.4);
            balin.br.setPower(-.4);
            balin.fl.setPower(-.4);
            double reflectance = balin.ods.getLightDetected();
            telemetry.addData("reflectance", reflectance);
            telemetry.update();
        }
        balin.setDrivePower(0);
        sleep(500);

        while (white_line && balin.ods.getRawLightDetected() <= .6 && opModeIsActive() && !isStopRequested()) {
            balin.fr.setPower(-.25);
            balin.br.setPower(.25);
            balin.fl.setPower(.25);
            balin.bl.setPower(-.25);
        }

        balin.setDrivePower(0);
        sleep(500);
        while(opModeIsActive() && !isStopRequested() && (balin.colorSensor.red() == 0 || balin.colorSensor.blue() == 0)) {
            balin.setDrivePower(.05);
        }
        balin.restAndSleep(this);
        if(balin.colorSensor.blue() > balin.colorSensor.red()) {
            balin.beaconBlue = true;
        }
        else {
            balin.beaconBlue = false;
        }
        balin.restAndSleep(this);
        if(balin.beaconBlue) {
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
        }
        else {
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
        }
        sleep(500);
        balin.setDrivePower(.1);
        sleep(1500);
        balin.restAndSleep(this);
        balin.setDrivePower(-.1);
        sleep(750);
        balin.restAndSleep(this);
    }
    public void followWhiteLine(){
        double adjustment = (.2 - balin.ods.getLightDetected())*.15;
        if(adjustment <=0){
            balin.fl.setPower(.1-adjustment);
            balin.fr.setPower(.1);
            balin.bl.setPower(.1-adjustment);
            balin.br.setPower(.1);
        }
        else{
            balin.fl.setPower(.1);
            balin.fr.setPower(.1+adjustment);
            balin.bl.setPower(.1);
            balin.br.setPower(.1+adjustment);
        }
    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        balin.gyroAngle(AngleToTurnTo, .2, this);
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
