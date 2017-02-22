package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
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
    Hardware3415 balin = new Hardware3415();
    public void runOpMode() {
        balin.init(hardwareMap, true);
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        waitForStart();
        balin.changeDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        balin.changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        balin.fr.setPower(.25);
        balin.br.setPower(-.25);
        balin.fl.setPower(-.25);
        balin.bl.setPower(.25);
        sleep(200);
        normalizeSpeedStrafe();
        sleep(2000);
        balin.restAndSleep(this);
        balin.changeDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        balin.changeDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        balin.setDrivePower(.3);
        sleep(200);
        normalizeSpeedFourMotorsForward();
        sleep(2000);
        balin.restAndSleep(this);

    }

    public void normalizeSpeedFourMotorsForward() {
        ElapsedTime timer = new ElapsedTime();
        double timeBeforeChange = timer.time();
        float flEncoderBefore = balin.fl.getCurrentPosition();
        float frEncoderBefore = balin.fr.getCurrentPosition();
        float blEncoderBefore = balin.bl.getCurrentPosition();
        float brEncoderBefore = balin.br.getCurrentPosition();
        while(balin.br.getCurrentPosition() == frEncoderBefore) {
            sleep(1);
        }
        float flEncoderAfter = balin.fl.getCurrentPosition();
        float frEncoderAfter = balin.fr.getCurrentPosition();
        float blEncoderAfter = balin.bl.getCurrentPosition();
        float brEncoderAfter = balin.br.getCurrentPosition();
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
        double flTranVel = flAngVel * (balin.WHEEL_DIAMETER/2);
        double frTranVel = frAngVel * (balin.WHEEL_DIAMETER/2);
        double blTranVel = blAngVel * (balin.WHEEL_DIAMETER/2);
        double brTranVel = brAngVel * (balin.WHEEL_DIAMETER/2);
        telemetry.addData("FL TRAN VEL", flTranVel);
        telemetry.addData("FR TRAN VEL", frTranVel);
        telemetry.addData("BL TRAN VEL", blTranVel);
        telemetry.addData("BR TRAN VEL", brTranVel);
        telemetry.update();
        if(!isStopRequested() && opModeIsActive() && (flTranVel != frTranVel || flTranVel != blTranVel || flTranVel != brTranVel)) {
            double biggestTranVel = biggestDouble(flTranVel, frTranVel, blTranVel, brTranVel);
            double flAdjustedPower = (balin.fl.getPower() * biggestTranVel)/flTranVel;
            double frAdjustedPower = (balin.fr.getPower() * biggestTranVel)/frTranVel;
            double blAdjustedPower = (balin.bl.getPower() * biggestTranVel)/blTranVel;
            double brAdjustedPower = (balin.br.getPower() * biggestTranVel)/brTranVel;
            balin.fl.setPower(flAdjustedPower);
            balin.fr.setPower(frAdjustedPower);
            balin.bl.setPower(blAdjustedPower);
            balin.br.setPower(brAdjustedPower);
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

    public void normalizeSpeedStrafe() {
        ElapsedTime timer = new ElapsedTime();
        double timeBeforeChange = timer.time();
        float flEncoderBefore = Math.abs(balin.fl.getCurrentPosition());
        float frEncoderBefore = Math.abs(balin.fr.getCurrentPosition());
        float blEncoderBefore = Math.abs(balin.bl.getCurrentPosition());
        float brEncoderBefore = Math.abs(balin.br.getCurrentPosition());
        while(balin.br.getCurrentPosition() == frEncoderBefore) {
            sleep(1);
        }
        float flEncoderAfter = Math.abs(balin.fl.getCurrentPosition());
        float frEncoderAfter = Math.abs(balin.fr.getCurrentPosition());
        float blEncoderAfter = Math.abs(balin.bl.getCurrentPosition());
        float brEncoderAfter = Math.abs(balin.br.getCurrentPosition());
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
        double flTranVel = flAngVel * (balin.WHEEL_DIAMETER/2);
        double frTranVel = frAngVel * (balin.WHEEL_DIAMETER/2);
        double blTranVel = blAngVel * (balin.WHEEL_DIAMETER/2);
        double brTranVel = brAngVel * (balin.WHEEL_DIAMETER/2);
        telemetry.addData("FL TRAN VEL", flTranVel);
        telemetry.addData("FR TRAN VEL", frTranVel);
        telemetry.addData("BL TRAN VEL", blTranVel);
        telemetry.addData("BR TRAN VEL", brTranVel);
        telemetry.update();
        if(!isStopRequested() && opModeIsActive() && (flTranVel != frTranVel || flTranVel != blTranVel || flTranVel != brTranVel)) {
            double biggestTranVel = biggestDouble(flTranVel, frTranVel, blTranVel, brTranVel);
            double flAdjustedPower = (balin.fl.getPower() * biggestTranVel)/flTranVel;
            double frAdjustedPower = (balin.fr.getPower() * biggestTranVel)/frTranVel;
            double blAdjustedPower = (balin.bl.getPower() * biggestTranVel)/blTranVel;
            double brAdjustedPower = (balin.br.getPower() * biggestTranVel)/brTranVel;
            balin.fl.setPower(flAdjustedPower);
            balin.fr.setPower(frAdjustedPower);
            balin.bl.setPower(blAdjustedPower);
            balin.br.setPower(brAdjustedPower);
            telemetry.addData("FL Power", flAdjustedPower);
            telemetry.addData("FR Power", frAdjustedPower);
            telemetry.addData("BL Power", blAdjustedPower);
            telemetry.addData("BR Power", brAdjustedPower);
            telemetry.update();
        }
    }
}
