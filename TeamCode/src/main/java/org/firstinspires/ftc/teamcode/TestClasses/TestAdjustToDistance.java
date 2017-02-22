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
    ModernRoboticsI2cRangeSensor sonar;
    public void runOpMode() {
        balin.init(hardwareMap, true);
        sonar = (ModernRoboticsI2cRangeSensor) hardwareMap.get("sonar");
        waitForStart();
        adjustToDistance(12.0, .3);
    }

    public void adjustToDistance(double distance, double power) {
        double currDistance = readSonar();
        while (!(distance - 2 < currDistance) && !(currDistance < distance + 2) && opModeIsActive() && !isStopRequested()) {
            if(currDistance < distance) {
                balin.setDrivePower(-power);
                while(currDistance < distance && opModeIsActive() && !isStopRequested()) {
                    currDistance = readSonar();
                }
            }
            else if (currDistance > distance) {
                balin.setDrivePower(power);
                while(currDistance > distance && opModeIsActive() && !isStopRequested()) {
                    currDistance = readSonar();
                }
            }
            currDistance = readSonar();
        }
        balin.restAndSleep(this);
    }

    public double readSonar() {
        double sonarData = sonar.cmUltrasonic();
        return sonarData;
    }
}
