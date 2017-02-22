package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 2/22/2017.
 */

@Autonomous(name="Test Sonar", group="Test")
public class TestRangeSensor extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode() {
        balin.init(hardwareMap, true);
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("Ultrasonic Data", balin.readSonar1());
            telemetry.addData("Ultrasonic Data 2", balin.readSonar2());
            telemetry.update();
        }
    }
}
