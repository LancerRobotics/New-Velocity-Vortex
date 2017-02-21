package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by david.lin on 1/11/2017.
 */

@Autonomous(name = "TestMovement", group = "Tests")
@Disabled
public class TestMovement extends LinearOpMode {

    Hardware3415 balin = new Hardware3415();
    int red;
    int blue;
    int green;

    boolean blueColor = false;
    boolean redColor = false;
    boolean whiteLine = false;
    boolean anyBeaconColor = false;

    public void runOpMode() {

        balin.init(hardwareMap, true);
        waitForStart();

        goStraightUntilDetectBeacon(true, 0.1);
        goStraightUntilDetectBeacon(false, 0.1);
        /*
       while(opModeIsActive()) {
           getRGB();
       }
       */
/*
        moveUntilLine("up", 0.3);
        //Shlok method to adjust
        moveUntilLine("left", 0.3);
        moveUntilLine("right", 0.3);
 */
    }

    public void moveUntilLine(String direction, double power) {


        if (direction.equals("left")) {
            while (!whiteLine) {
                whiteLine = findWhiteLine();
                balin.bl.setPower(power);
                balin.br.setPower(-power);
                balin.fl.setPower(-power);
                balin.fr.setPower(power);
                telemetry.addLine("Going left");

            }
        } else if (direction.equals("right")) {
            while (!whiteLine) {
                whiteLine = findWhiteLine();
                balin.bl.setPower(-power);
                balin.br.setPower(power);
                balin.fl.setPower(power);
                balin.fr.setPower(-power);
                telemetry.addLine("Going right");

            }
        } else if (direction.equals("forward")) {
            while (!whiteLine) {
                whiteLine = findWhiteLine();
                balin.bl.setPower(power);
                balin.br.setPower(power);
                balin.fl.setPower(power);
                balin.fr.setPower(power);
                telemetry.addLine("Going forward");
            }
        } else if (direction.equals("back")) {
            while (!whiteLine) {
                whiteLine = findWhiteLine();
                balin.bl.setPower(-power);
                balin.br.setPower(-power);
                balin.fl.setPower(-power);
                balin.fr.setPower(-power);
                telemetry.addLine("Going backward");
            }
        } else if (direction.equals("leftDown")) {
            while (!findCenterBase(false)) {
                balin.bl.setPower(-power);
                balin.fr.setPower(-power);
            }
        } else if (direction.equals("rightDown")) {
            while (!findCenterBase(true)) {
                balin.br.setPower(-power);
                balin.fl.setPower(-power);
            }
        } else if (direction.equals("leftUp")) {
            while (!findCenterBase(false)) {
                balin.bl.setPower(power);
                balin.fr.setPower(power);
            }
        } else if (direction.equals("rightUp")) {
            while (!findCenterBase(true)) {
                balin.br.setPower(power);
                balin.fl.setPower(power);
            }
        } else {
            telemetry.addLine("nothing detected");
            telemetry.update();
        }

    }


    public boolean findWhiteLine() {

        int[]rgb = getRGB();

        boolean firstWhiteLine = false;
        if (rgb[0] >= 1 && rgb[2] >= 1 && rgb[1] >= 1) {
            firstWhiteLine = true;
            telemetry.addLine("White line detected");
            telemetry.update();
        } else {
            firstWhiteLine = false;
            telemetry.addLine("No white line detected");
            telemetry.update();
        }
        return firstWhiteLine;
    }


    public void pushLeftBeacon (boolean blueBeaconLeft, double power, long backTime, long forwardTime) {
        if(blueBeaconLeft) {
            //Go back the distance wanted!!!
            balin.fl.setPower(-power);
            balin.fr.setPower(-power);
            balin.bl.setPower(-power);
            balin.br.setPower(-power);
            sleep(backTime);
            balin.fl.setPower(0);
            balin.fr.setPower(0);
            balin.bl.setPower(0);
            balin.br.setPower(0);
            balin.beaconPushLeft.setPosition(Hardware3415.LEFT_BEACON_PUSH);
            //Go forwards the distance wanted!!!
            balin.fl.setPower(power);
            balin.fr.setPower(power);
            balin.bl.setPower(power);
            balin.br.setPower(power);
            sleep(forwardTime);
            balin.fr.setPower(0);
            balin.fr.setPower(0);
            balin.bl.setPower(0);
            balin.br.setPower(0);

        }
        else {
            //Go back the distance wanted!!!
            balin.fl.setPower(-power);
            balin.fr.setPower(-power);
            balin.bl.setPower(-power);
            balin.br.setPower(-power);
            sleep(backTime);
            balin.fl.setPower(0);
            balin.fr.setPower(0);
            balin.bl.setPower(0);
            balin.br.setPower(0);
            balin.beaconPushRight.setPosition(Hardware3415.RIGHT_BEACON_PUSH);
            //Go forwards the distance wanted!!!
            balin.fl.setPower(power);
            balin.fr.setPower(power);
            balin.bl.setPower(power);
            balin.br.setPower(power);
            sleep(forwardTime);
            balin.fr.setPower(0);
            balin.fr.setPower(0);
            balin.bl.setPower(0);
            balin.br.setPower(0);
        }
        balin.beaconPushRight.setPosition(Hardware3415.RIGHT_BEACON_INITIAL_STATE);
        balin.beaconPushLeft.setPosition(Hardware3415.LEFT_BEACON_INITIAL_STATE);

    }

    public void goStraightUntilDetectBeacon (boolean blueAlliance, double power){
        //Color sensor by default is on the left side of the robot
        if(blueAlliance) {
            while (!anyBeaconColor) {
                balin.bl.setPower(power);
                balin.br.setPower(power);
                balin.fl.setPower(power);
                balin.fr.setPower(power);
                int[] rgb = getRGB();
                if(rgb[2] > rgb[0] ) {
                    blueColor = true;
                    redColor = false;
                    anyBeaconColor = true;
                    telemetry.addLine("Left side of beacon is blue detected, now going to hit left side of beacon!");

                    pushLeftBeacon(true, 0.1, 2000, 3000);

                }
                else if (rgb[2] < rgb[0]) {
                    blueColor = false;
                    redColor = true;
                    anyBeaconColor = true;
                    telemetry.addLine("Left side of beacon is red detected, now going to hit right side of beacon!");

                    pushLeftBeacon(false, 0.1, 2000, 3000);

                }
                else {
                    telemetry.addLine("Can't determine color because blue value and red value are equal");
                }
            }
        }
        else {
            while (!anyBeaconColor) {
                balin.bl.setPower(power);
                balin.br.setPower(power);
                balin.fl.setPower(power);
                balin.fr.setPower(power);
                int[] rgb = getRGB();
                if(rgb[2] >= 2 && rgb[0] == 0) {
                    blueColor = true;
                    redColor = false;
                    anyBeaconColor = true;
                    telemetry.addLine("Left side of beacon is blue detected, now going to hit right side of beacon!");

                    pushLeftBeacon(true, 0.1, 2000, 3000);

                }
                else if (rgb[2] == 0 && rgb[0] >= 7) {
                    blueColor = false;
                    redColor = true;
                    anyBeaconColor = true;
                    telemetry.addLine("Left side of beacon is red detected, now going to hit left side of beacon!");

                    pushLeftBeacon(false, 0.1, 2000, 3000);

                }
            }
        }
        anyBeaconColor = false;
        blueColor = false;
        redColor = false;
    }


    public boolean findCenterBase(boolean blueAlliance) {

        //THIS DOES NOT WORK AT THE MOMENT
        boolean centerBase = false;
        if (blueAlliance) {
            int[] rgb = getRGB();
            if (rgb[2] >= 8) {
                //stop robot
                centerBase = true;
                telemetry.addLine("Blue center tape detected");
                telemetry.update();
            } else {
                centerBase = false;
                telemetry.addLine("Blue center tape not detected");
                telemetry.update();
            }
        } else if (!blueAlliance) {
            int[]rgb = getRGB();
            detectColor();
            if (rgb[0] >= 3) {
                //stop robot
                centerBase = true;
                telemetry.addLine("Red center tape detected");
                telemetry.update();
            } else {
                centerBase = false;
                telemetry.addLine("Red center tape not detected");
                telemetry.update();
            }
        }
        return (centerBase);
    }

    public int detectColor() {
        int beaconBlue;

        //Detect color

        int[] rgb = getRGB();
        beaconBlue = 0;



        //Set the color sensor values into an array to work with

        //Check for if there is more blue than red or red than blue to determine beacon color.
        if (rgb[0] > rgb[2]) {
            beaconBlue = 0;
            telemetry.addLine("detected red");
            telemetry.update();
            return beaconBlue;
        } else if (rgb[0] < rgb[2]) {
            beaconBlue = 1;
            telemetry.addLine("detected blue");
            telemetry.update();
            return beaconBlue;
        } else {
            beaconBlue = 2;
            telemetry.addLine("unable to determine beacon color");
            telemetry.update();
            return beaconBlue;
        }

    }

    public int[] getRGB() {
        /*
        red = balin.colorSensor.red(); // store the values the color sensor returns
        blue = balin.colorSensor.blue();
        green = balin.colorSensor.green();
        telemetry.addData("Red", red);
        telemetry.addData("Blue", blue);
        telemetry.addData("Green", green);
        telemetry.update();
        */
        int[] rgb = {red, green, blue};
        return rgb;
    }

}
