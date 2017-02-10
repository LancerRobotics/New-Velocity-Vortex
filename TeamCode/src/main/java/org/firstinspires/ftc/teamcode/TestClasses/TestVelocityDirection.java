package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.INCH;

/**
 * Created by andrew.keenan on 2/10/2017.
 */

public class TestVelocityDirection extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    Velocity velocity = new Velocity();
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
        while(opModeIsActive()) {
             double heading = balin.navx_device.getCompassHeading();
            telemetry.addData("heading" , heading);
            telemetry.addData("X Velocity", velocity.xVeloc);
            telemetry.addData("Y Velocity", velocity.yVeloc);
            double numerator = velocity.xVeloc * velocity.yVeloc;
            double denominator = Math.sqrt(velocity.xVeloc) * Math.sqrt(velocity.yVeloc);
            double cosine = numerator/denominator;
            double theta = Math.acos(cosine);
            telemetry.addData("Theta", theta);
            telemetry.update();
        }

    }
}
