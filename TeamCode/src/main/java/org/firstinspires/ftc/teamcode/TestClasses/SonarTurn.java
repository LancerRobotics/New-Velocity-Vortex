package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

import java.lang.*;

/**
 * Created by david.lin on 2/22/2017.
 */

@Autonomous(name = "SonarTurn", group = "Competition")
public class SonarTurn extends LinearOpMode {
    Hardware3415 balin = new Hardware3415();
    public void runOpMode() {
            double currentDistance1 = balin.readSonar(balin.sonar);
            double currentDistance2 = balin.readSonar(balin.sonar2);
            long roundDistance1 = Math.round(currentDistance1);
            long roundDistance2 = Math.round(currentDistance2);


        while (roundDistance1 != roundDistance2) {
            if (roundDistance1 < roundDistance2) {
                balin.turn(0.1);
                currentDistance1 = balin.readSonar(balin.sonar);
                currentDistance2 = balin.readSonar(balin.sonar2);
                roundDistance1 = Math.round(currentDistance1);
                roundDistance2 = Math.round(currentDistance2);
            } else if (roundDistance1 > roundDistance2) {
                balin.turn(-0.1);
                currentDistance1 = balin.readSonar(balin.sonar);
                currentDistance2 = balin.readSonar(balin.sonar2);
                roundDistance1 = Math.round(currentDistance1);
                roundDistance2 = Math.round(currentDistance2);
            }
        }
        balin.turn(0);
    }
}
