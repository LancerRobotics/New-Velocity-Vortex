package org.firstinspires.ftc.teamcode.TestClasses;

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
            if (balin.ods.getLightDetected() >= .2) {
                white_line = true;
            }
            balin.bl.setPower(1);
            balin.fr.setPower(1);
            balin.br.setPower(-.19);
            balin.fl.setPower(-.19);
            double reflectance = balin.ods.getLightDetected();
            telemetry.addData("reflectance", reflectance);
            telemetry.update();
        }
        balin.setDrivePower(0);
        sleep(1000);




        while (white_line && balin.ods.getLightDetected() <= .2 && opModeIsActive()) {
            balin.fr.setPower(-.5);
            balin.br.setPower(.5);
            balin.fl.setPower(.5);
            balin.bl.setPower(-.5);
        }
        balin.setDrivePower(0);
        sleep(1000);
        while(balin.ods.getLightDetected()<= .35 && opModeIsActive()){
            balin.fr.setPower(.4);
            balin.br.setPower(.4);
            balin.fl.setPower(-.4);
            balin.bl.setPower(-.4);
        }
        balin.setDrivePower(0);
        sleep(1000);

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
}
