package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 2/11/2017.
 */
@Autonomous(name= "WhiteLine", group = "tests")
public class FollowWhiteLine extends LinearOpMode {
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
        while(opModeIsActive() &&  !isStopRequested() && (balin.colorSensor.red() == 0 || balin.colorSensor.blue() == 0)){
            balin.setDrivePower(.05);
        }

    }
    public void followWhiteLine() {

        double adjustment = (.2 - balin.ods.getLightDetected()) * .15;
        if (adjustment <= 0) {
            balin.fl.setPower(.1 - adjustment);
            balin.fr.setPower(.1);
            balin.bl.setPower(.1 - adjustment);
            balin.br.setPower(.1);
        } else {
            balin.fl.setPower(.1);
            balin.fr.setPower(.1 + adjustment);
            balin.bl.setPower(.1);
            balin.br.setPower(.1 + adjustment);
        }
        telemetry.addData("adjustment", adjustment);
        telemetry.update();
    }
}
