package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 3/3/2017.
 */

public class TurningTests extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        waitForStart();

    }
    // This is our most used, and most useful method.
    // We use it to turn to within our  threshold angle of 0.
    // The default power is .05 as that power performed with the best accuracy in our testing.
    public void AlignToWithin(double threshold){
        AlignToWithin(threshold, .05);
    }

    // The align to within algorithm uses our team's unique method of non-recovery turning.
    // Because the robot exits the control loop once the reading is past the target,
    // we can apply this logic and use it to control our turning to a precise degree of accuracy.
    public void AlignToWithin(double threshold, double power){
        TurnRightAbsolute(- threshold, power, this);
        TurnLeftAbsolute(threshold, power, this);
        TurnRightAbsolute(- threshold, power, this);
    }

    // The AlignToWithinOf method aligns to within threshold degrees of expected.
    // To do this, we subtract or add threshold to the expected reading,
    // following a similar structure to that of AlignToWithin
    public void AlignToWithinOf(double expected, double threshold, double power){
        TurnRightAbsolute(expected - threshold, power, this);
        TurnLeftAbsolute(expected + threshold, power, this);
        TurnRightAbsolute(expected - threshold, power, this);
    }
    public void TurnLeftPLoop(double degrees, double maxPower, LinearOpMode opMode){
        //Max Power should be normally set to .5, but for very precise turns a value of .05 is reccomended.


        if(!opMode.opModeIsActive())
            Finish(this);
        double power;
        while(balin.navx_device.getYaw() >= degrees && opMode.opModeIsActive()){
            power = Range.clip((balin.navx_device.getYaw() - degrees)/degrees, .05, maxPower);
            balin.br.setPower(power);
            balin.fr.setPower(power);
            balin.bl.setPower(-power);
            balin.fl.setPower(-power);
        }
        balin.setDrivePower(.3);
    }

    // Turns left to a precise angle, regardless of current lineup.
    public void TurnLeftAbsolute(double degrees, double power, LinearOpMode opMode){

        if(!opMode.opModeIsActive())
            Finish(this);
        while(balin.navx_device.getYaw() >= degrees && opMode.opModeIsActive()){
            balin.br.setPower(power);
            balin.fr.setPower(power);
            balin.bl.setPower(-power);
            balin.fl.setPower(-power);
        }
        balin.setDrivePower(0);

    }

    // Turns left to an angle based on the current reading.
    // This is most useful when we are using our file reading code,
    // or when we are later into the route and are worried the navX
    // may be drifted.
    public void TurnLeftRelative(double degrees, double power, LinearOpMode opMode){
        if(!balin.navx_device.isConnected()){
           // TurnLeftEnc(degrees, .10);
            return;
        }
        if(!opMode.opModeIsActive())
            Finish(this);
        double yaw = balin.navx_device.getYaw();
        degrees = -Math.abs(degrees);
        while(Math.abs(yaw - balin.navx_device.getYaw()) < degrees && opMode.opModeIsActive()){
            balin.br.setPower(power);
            balin.fr.setPower(power);
            balin.bl.setPower(-power);
            balin.fl.setPower(-power);
        }
        balin.setDrivePower(0);

    }

    // Functions just like TurnLeftAbsolute,
    // but allows us to input a positive number,
    // thus improving code legibility.
    public void TurnLeft(double degrees, double power){
        TurnLeftAbsolute(- Math.abs(degrees), power, this);
    }
    public void TurnRightPLoop(double degrees, double maxPower, LinearOpMode opMode){
        //Max Power should be normally set to .5, but for very precise turns a value of .05 is reccomended.

        if(!opMode.opModeIsActive())
            Finish(this);
        double power;
        while(balin.navx_device.getYaw() <= degrees && opMode.opModeIsActive()){
            power = Range.clip((degrees - balin.navx_device.getYaw())/degrees, .05, maxPower);
            balin.br.setPower(power);
            balin.fr.setPower(power);
            balin.bl.setPower(-power);
            balin.fl.setPower(-power);
        }
        balin.setDrivePower(0);
    }
    public void TurnRightAbsolute(double degrees, double power, LinearOpMode opMode){
        if(!opMode.opModeIsActive())
            Finish(this);
        while(balin.navx_device.getYaw() <= degrees && opMode.opModeIsActive()){
            balin.br.setPower(-power);
            balin.fr.setPower(-power);
            balin.bl.setPower(power);
            balin.fl.setPower(power);
        }
        balin.setDrivePower(0);
    }
    public void TurnRight(double degrees, double power){
        TurnRightAbsolute(degrees, power, this);
    }
    public void TurnRightRelative(double degrees, double power, LinearOpMode opMode){

        if(!opMode.opModeIsActive())
            Finish(this);
        double yaw = balin.navx_device.getYaw();
        while(Math.abs(yaw - balin.navx_device.getYaw()) < degrees && opMode.opModeIsActive()){
            balin.br.setPower(-power);
            balin.fr.setPower(-power);
            balin.bl.setPower(power);
            balin.fl.setPower(power);
        }
        balin.setDrivePower(0);
    }
    public void Finish(LinearOpMode opMode){
        balin.navx_device.close();
        balin.colorSensor.close();
        balin.ods.close();
        opMode.stop();
    }




}
