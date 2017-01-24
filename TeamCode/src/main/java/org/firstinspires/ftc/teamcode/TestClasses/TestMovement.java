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
public class TestMovement extends LinearOpMode {

    Hardware3415 balin = new Hardware3415();
    int red;
    int blue;
    int green;

    boolean whiteLine = false;

    public void runOpMode() {

        balin.init(hardwareMap, true);
        waitForStart();

        moveUntilLine("up", 0.3);
        //Shlok method to adjust
        moveUntilLine("left", 0.3);
        moveUntilLine("right", 0.3);

       /* moveAnywhere("right", 5, 0.3);
        moveAnywhere("left", 5, 0.3);
        moveAnywhere("forward", 5, 0.3);
        moveAnywhere("backward", 5, 0.3); */

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
        if (rgb[0] >= 210 && rgb[2] >= 210 && rgb[1] >= 210) {
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


    public boolean findCenterBase(boolean blueAlliance) {
        boolean centerBase = false;
        if (blueAlliance) {
            int[] rgb = getRGB();
            if (rgb[2] >= 210) {
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
            if (rgb[0] >= 210) {
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
            telemetry.addLine("unable to determine beacon color");
            telemetry.update();
            return beaconBlue;
        }

    }

    public int[] getRGB() {
        red = balin.colorSensor.red(); // store the values the color sensor returns
        blue = balin.colorSensor.blue();
        green = balin.colorSensor.green();
        telemetry.addData("Red", red);
        telemetry.addData("Blue", blue);
        telemetry.addData("Green", green);
        telemetry.update();
        int[] rgb = {red, green, blue};
        return rgb;
    }
}
