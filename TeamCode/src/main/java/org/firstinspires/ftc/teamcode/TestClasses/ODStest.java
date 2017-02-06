package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/4/2017.
 */
@Autonomous(name="ODSTest", group="Tests")
public class ODStest extends LinearOpMode{
    Hardware3415 balin = new Hardware3415();
    int angle = 0;
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
        balin.gyroAngle(-33, .3, this);
        balin.setDrivePower(0);
        sleep(1000);
        boolean white_line = false;
            while(!(white_line)){
                if(balin.ods.getLightDetected() >= .2){
                    white_line = true;
                }
                balin.setDrivePower(.30);
                double reflectance = balin.ods.getLightDetected();
                telemetry.addData("reflectance", reflectance);
                telemetry.update();
            }
        balin.setDrivePower(0);
        sleep(1000);
            while(white_line && balin.ods.getLightDetected() <= .2){
                balin.setDrivePower(-.17);
            }
            balin.setDrivePower(0);
            sleep(1000);
            balin.gyroAngle(35, .3, this);
            sleep(1000);
        //while the beacon color is detected/differentiated
        //this will remove the sleep and then we go back and hit it and then it will be lit and then we check color tooo make sure IT IS RIGHT
            balin.setDrivePower(.3);
            sleep(500);
            balin.setDrivePower(.0);
            sleep(500);
        //Getting Beacon Code:

        //Ends
        /*
        sleep(1000);
        balin.fr.setPower(.5);
        balin.fl.setPower(-.5);
        balin.bl.setPower(.5);
        balin.br.setPower(-.5);
        sleep(1000);
        boolean white_line2 = false;
            while(!(white_line2)){
                if(balin.ods.getLightDetected() >= .2){
                    white_line2 = true;
                }
                balin.fr.setPower(.6);
                balin.fl.setPower(-.6);
                balin.bl.setPower(.6);
                balin.br.setPower(-.6);
                double reflectance = balin.ods.getLightDetected();
                telemetry.addData("reflectance", reflectance);
                telemetry.update();
            }
        balin.setDrivePower(0);
        sleep(1000);
        while(white_line2 && balin.ods.getLightDetected() <= .2){
            balin.fr.setPower(-.5);
            balin.fl.setPower(.5);
            balin.bl.setPower(-.5);
            balin.br.setPower(.5);
        }
        balin.setDrivePower(0);


        sleep(1000);
        turnToOriginalAngle();
    */

    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        balin.gyroAngle(AngleToTurnTo, .3, this);
        balin.navx_device.zeroYaw();
    }
    //.35 reflectance

}
