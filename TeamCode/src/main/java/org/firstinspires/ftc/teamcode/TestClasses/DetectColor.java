package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/20/2017.
 */
@Autonomous(name = "DetectBeacon", group = "Tests")
public class DetectColor extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        waitForStart();
        moveUntilColor();
        String color = detectColor();
        while(color.equals("N/A")&& opModeIsActive()){
            color = detectColor();
            balin.setDrivePower(.2);
        }
        balin.setDrivePower(0);
        if(color.equals("Red")){
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_PUSH);
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_INITIAL_STATE);
            telemetry.addData("Color: ", color);
            telemetry.update();
        } else if(color.equals("Blue")) {
            balin.beaconPushLeft.setPosition(balin.LEFT_BEACON_INITIAL_STATE);
            balin.beaconPushRight.setPosition(balin.RIGHT_BEACON_PUSH);
            telemetry.addData("Color: ", color);
            telemetry.update();
        } else{
            telemetry.addData("Color: ", color);
            telemetry.update();
        }
        telemetry.addLine("Ready to Hit Beacon");
        telemetry.update();

    }
    public String detectColor(){
        if(balin.colorSensor.red()>9){
            return "Red";
        }
        else if(balin.colorSensor.blue()>9){
            return "Blue";
        }
        else{
            return "N/A";
        }
    }
    public void moveUntilColor(){
        if(detectColor().equals("N/A")){
            balin.setDrivePower(.3);
        }
        else{
            balin.setDrivePower(0);
        }
    }
    public String checkColor(){
        String color = detectColor();
        return color;
    }
}
