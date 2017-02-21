package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 1/23/2017.
 */
@Autonomous(name="Encoder Drive", group="Tests")
@Disabled
public class MoveStraight extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        balin.fr.setPower(.6);
        balin.fl.setPower(-.6);
        balin.bl.setPower(.6);
        balin.br.setPower(-.6);
        sleep(1000);
        balin.setDrivePower(0);
        sleep(0);
       /* balin.moveStraight(12, false, this);
        balin.restAndSleep(this);
        sleep(2000);
        balin.newMoveStraight(12, true, this);
        sleep(2000);
        balin.newMoveStraight(12, false, this);
        sleep(2000);
        balin.moveStraight1(12, true, this);
        sleep(2000);
        balin.moveStraight1(12, false, this);
        sleep(2000);
    */
    }

}
