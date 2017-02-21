package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/20/2017.
 */
@Autonomous(name = "White Line Test", group = "Test")
public class WhiteLine extends LinearOpMode{
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        waitForStart();
        while(opModeIsActive()) {
            followLine();
            telemetry.addData("Light Detected: ", balin.ods.getLightDetected());
            telemetry.update();
        }
    }
    public void followLine() {
        double adjustment = (.2 - balin.ods.getLightDetected()) * .15;
        if (adjustment <= 0) {
            balin.fl.setPower(.1 - adjustment);
            balin.fr.setPower(.1);
            balin.bl.setPower(.1 - adjustment);
            balin.br.setPower(.1);
            telemetry.addData("away from line: ", balin.fl.getPower());
            telemetry.update();
        } else {
            balin.fl.setPower(.1);
            balin.fr.setPower(.1 + adjustment);
            balin.bl.setPower(.1);
            balin.br.setPower(.1 + adjustment);
            telemetry.addData("On Line: ", balin.fl.getPower());
            telemetry.update();
        }
        telemetry.addData("adjustment", adjustment);
        telemetry.update();
    }
}
