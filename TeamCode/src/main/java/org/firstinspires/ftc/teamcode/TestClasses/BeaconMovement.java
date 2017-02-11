package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/10/2017.
 */
@Autonomous (name= "Beacon Movement", group = "test")
public class BeaconMovement extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){

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
        while(opModeIsActive()){
            //balin.setDrivePower(.2);
            telemetry.addData("red", balin.colorSensor.red());
            telemetry.addData("blue", balin.colorSensor.blue());
            telemetry.update();
        } /*
        if(detectColor()){
            boolean color = detectColor();
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
            telemetry.addData("blue", color);
            telemetry.update();
        }
        else{
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
        }
        balin.setDrivePower(.3);
        sleep(2000);
*/
    }
    public boolean detectColor(){ //for blue
        //int [] rgb = balin.getRGB();
        if(balin.colorSensor.red() > balin.colorSensor.blue()){
            return false;
        }
        return true;
    }
}