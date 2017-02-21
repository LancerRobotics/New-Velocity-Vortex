package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/9/2017.
 */
@Autonomous (name= "ColorValues", group = "Tests")
//@Disabled
public class ColorTest extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Red: ", balin.colorSensor.red());
            telemetry.addData("Greem", balin.colorSensor.green());
            telemetry.addData("Blue: ", balin.colorSensor.blue());
            telemetry.addData("Alpha", balin.colorSensor.alpha());
            telemetry.update();
        }
    }
}
