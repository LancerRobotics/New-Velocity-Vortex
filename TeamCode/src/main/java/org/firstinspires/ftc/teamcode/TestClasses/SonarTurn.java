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
        balin.init(hardwareMap, true);
        waitForStart();
        Straighten();
    }
    public void Straighten(){
        while(Math.abs(Math.round(balin.readSonar1()) - Math.round(balin.readSonar2()))> 1 ){
            if(balin.readSonar1()< balin.readSonar2()){
                balin.fl.setPower(-.15);
                balin.bl.setPower(-.15);
            }
            if(balin.readSonar1() > balin.readSonar2()){
                balin.fr.setPower(- .15);
                balin.br.setPower(- .15);
            }
        }
        balin.setDrivePower(0);
    }
}
