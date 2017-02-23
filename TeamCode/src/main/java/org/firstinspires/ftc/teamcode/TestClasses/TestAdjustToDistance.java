package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 2/22/2017.
 */

@Autonomous(name="Adjust To Distance Test", group="Test")
public class TestAdjustToDistance extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode() {
        balin.init(hardwareMap, true);
        waitForStart();
        adjustToDistance(12.0, .25);
    }

    public void adjustToDistance(double distance, double power) {
        if (balin.readSonar1() < distance - 2) {
            while (balin.readSonar1() < distance - 2) {
                balin.setDrivePower(-power);
            }
        } else if (balin.readSonar1() > distance + 2) {
            while (balin.readSonar1() > distance + 2) {
                balin.setDrivePower(power);
            }
        }
        balin.restAndSleep(this);
    }
}
