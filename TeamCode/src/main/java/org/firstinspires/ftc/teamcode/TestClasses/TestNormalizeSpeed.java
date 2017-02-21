package org.firstinspires.ftc.teamcode.TestClasses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 2/21/2017.
 */

@Autonomous(name="Test Normalize Speed", group = "Test")
public class TestNormalizeSpeed extends LinearOpMode {
    Hardware3415 Balin = new Hardware3415();
    public void runOpMode() {
        Balin.init(hardwareMap, true);
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        waitForStart();
        Balin.changeDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Balin.changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Balin.setDrivePower(.3);
        sleep(200);
        normalizeSpeedFourMotorsForward(Balin.fl.getPower());
        sleep(2000);
        Balin.restAndSleep(this);
    }

    public void normalizeSpeedFourMotorsForward(double power) {
        ElapsedTime timer = new ElapsedTime();
        double timeBeforeChange = timer.time();
        sleep(150);
        float flEncoderBefore = Balin.fl.getCurrentPosition();
        float frEncoderBefore = Balin.fr.getCurrentPosition();
        float blEncoderBefore = Balin.bl.getCurrentPosition();
        float brEncoderBefore = Balin.br.getCurrentPosition();
        sleep(100);
        float flEncoderAfter = Balin.fl.getCurrentPosition();
        float frEncoderAfter = Balin.fr.getCurrentPosition();
        float blEncoderAfter = Balin.bl.getCurrentPosition();
        float brEncoderAfter = Balin.br.getCurrentPosition();
        double timeAfterChange = timer.time();
        float flChangeInTick = flEncoderAfter - flEncoderBefore;
        float frChangeInTick = frEncoderAfter - frEncoderBefore;
        float blChangeInTick = blEncoderAfter - blEncoderBefore;
        float brChangeInTick = brEncoderAfter - brEncoderBefore;
        double flChangeInRad = (2.0 * Math.PI * flChangeInTick) / 560.0;
        double frChangeInRad = (2.0 * Math.PI * frChangeInTick) / 560.0;
        double blChangeInRad = (2.0 * Math.PI * blChangeInTick) / 560.0;
        double brChangeInRad = (2.0 * Math.PI * brChangeInTick) / 560.0;
        double flAngVel = flChangeInRad / (timeAfterChange - timeBeforeChange);
        double frAngVel = frChangeInRad / (timeAfterChange - timeBeforeChange);
        double blAngVel = blChangeInRad / (timeAfterChange - timeBeforeChange);
        double brAngVel = brChangeInRad / (timeAfterChange - timeBeforeChange);
        double flTranVel = flAngVel * (Balin.WHEEL_DIAMETER/2);
        double frTranVel = frAngVel * (Balin.WHEEL_DIAMETER/2);
        double blTranVel = blAngVel * (Balin.WHEEL_DIAMETER/2);
        double brTranVel = brAngVel * (Balin.WHEEL_DIAMETER/2);
        telemetry.addData("FL TRAN VEL", flTranVel);
        telemetry.addData("FR TRAN VEL", frTranVel);
        telemetry.addData("BL TRAN VEL", blTranVel);
        telemetry.addData("BR TRAN VEL", brTranVel);
        telemetry.update();
        if(!isStopRequested() && opModeIsActive() && (flTranVel != frTranVel || flTranVel != blTranVel || flTranVel != brTranVel)) {
            double biggestTranVel = biggestDouble(flTranVel, frTranVel, blTranVel, brTranVel);
            double flAdjustedPower = (Balin.fl.getPower() * biggestTranVel)/flTranVel;
            double frAdjustedPower = (Balin.fr.getPower() * biggestTranVel)/frTranVel;
            double blAdjustedPower = (Balin.bl.getPower() * biggestTranVel)/blTranVel;
            double brAdjustedPower = (Balin.br.getPower() * biggestTranVel)/brTranVel;
            Balin.fl.setPower(flAdjustedPower);
            Balin.fr.setPower(frAdjustedPower);
            Balin.bl.setPower(blAdjustedPower);
            Balin.br.setPower(brAdjustedPower);
            telemetry.addData("FL Power", flAdjustedPower);
            telemetry.addData("FR Power", frAdjustedPower);
            telemetry.addData("BL Power", blAdjustedPower);
            telemetry.addData("BR Power", brAdjustedPower);
            telemetry.update();
        }
    }

    public double biggestDouble(double a, double b, double c, double d) {
        double biggest;
        if (Math.abs(a) < Math.abs(b)) {
            biggest = Math.abs(b);
        } else {
            biggest = Math.abs(a);
        }
        if (biggest < Math.abs(c)) {
            biggest = Math.abs(c);
        }
        if (biggest < Math.abs(d)) {
            biggest = Math.abs(d);
        }
        return biggest;
    }
}
