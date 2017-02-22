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
    ModernRoboticsI2cRangeSensor sonar;
    public void runOpMode() {
        balin.init(hardwareMap, true);
        sonar = (ModernRoboticsI2cRangeSensor) hardwareMap.get("sonar");
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("Ultrasonic Data", sonar.cmUltrasonic());
            telemetry.addData("ODS Data", sonar.cmOptical());
            telemetry.update();
        }
    }
}
