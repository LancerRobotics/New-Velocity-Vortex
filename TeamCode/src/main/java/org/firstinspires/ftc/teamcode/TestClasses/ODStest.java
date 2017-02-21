package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/4/2017.
 */
@Autonomous(name="ODSTest", group="Tests")
//@Disabled
public class ODStest extends LinearOpMode{
    Hardware3415 balin = new Hardware3415();
    int angle = 0;

    public void runOpMode(){
        balin.init(hardwareMap, true);
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
while(opModeIsActive()) {
    double reflectance = balin.ods.getLightDetected();
    double rawreflectance = balin.ods.getRawLightDetected();
    telemetry.addData("reflectance", reflectance);
    telemetry.addData("reflectance Raw", rawreflectance);
    telemetry.update();
}
    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        balin.gyroAngle(AngleToTurnTo, .3, this);
        balin.navx_device.zeroYaw();
    }
    //.35 reflectance

}
