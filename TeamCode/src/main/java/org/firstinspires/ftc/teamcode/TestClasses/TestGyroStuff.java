package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 1/25/2017.
 */

public class TestGyroStuff extends LinearOpMode {
    Hardware3415 Balin = new Hardware3415();
    public void runOpMode() {
        Balin.init(hardwareMap, true);
        Balin.navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get(Balin.cdim),
                Balin.NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                Balin.NAVX_DEVICE_UPDATE_RATE_HZ);
        //Prevents Balin from running before callibration is complete
        while (Balin.navx_device.isCalibrating()) {
            telemetry.addData("Ready?", "No");
            telemetry.update();
        }
        Balin.navx_device.zeroYaw();
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        turnToOriginalAngle();
    }

    public void turnToOriginalAngle() {
        double AngleToTurnTo = Balin.navx_device.getYaw() * -1;
        Balin.gyroAngle(AngleToTurnTo, .2, this);
    }
}
