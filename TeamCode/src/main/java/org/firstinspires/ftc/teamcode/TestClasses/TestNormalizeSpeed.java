package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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
        /*
        balin.fr.setPower(.5);
        balin.br.setPower(-.5);
        balin.fl.setPower(-.5);
        balin.bl.setPower(.5);
        sleep(150);
        balin.fr.setPower(.4);
        balin.br.setPower(-.4);
        balin.fl.setPower(-.4);
        balin.bl.setPower(.4);
        sleep(150);
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
        */
        balin.bl.setPower(.9);
        balin.fr.setPower(.9);
        balin.br.setPower(-.1);
        balin.fl.setPower(-.1);
        sleep(150);
        boolean white_line = false;
        while ((!(white_line)) && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .6) {
                white_line = true;
            }
            normalizeSpeedDiaganol();
        }
        balin.restAndSleep(this);
    }

    public void normalizeSpeedDiaganol() {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        float flEncoderBefore = Math.abs(balin.fl.getCurrentPosition());
        double flTimeBefore = timer.time();
        float frEncoderBefore = Math.abs(balin.fr.getCurrentPosition());
        double frTimeBefore = timer.time();
        float blEncoderBefore = Math.abs(balin.bl.getCurrentPosition());
        double blTimeBefore = timer.time();
        float brEncoderBefore = Math.abs(balin.br.getCurrentPosition());
        double brTimeBefore = timer.time();
        sleep(200);
        float flEncoderAfter = Math.abs(balin.fl.getCurrentPosition());
        double flTimeAfter = timer.time();
        float frEncoderAfter = Math.abs(balin.fr.getCurrentPosition());
        double frTimeAfter = timer.time();
        float blEncoderAfter = Math.abs(balin.bl.getCurrentPosition());
        double blTimeAfter = timer.time();
        float brEncoderAfter = Math.abs(balin.br.getCurrentPosition());
        double brTimeAfter = timer.time();
        float flChangeInTick = Math.abs(flEncoderAfter - flEncoderBefore);
        float frChangeInTick = Math.abs(frEncoderAfter - frEncoderBefore);
        float blChangeInTick = Math.abs(blEncoderAfter - blEncoderBefore);
        float brChangeInTick = Math.abs(brEncoderAfter - brEncoderBefore);
        double flChangeInRad = (2.0 * Math.PI * flChangeInTick) / 560.0;
        double frChangeInRad = (2.0 * Math.PI * frChangeInTick) / 560.0;
        double blChangeInRad = (2.0 * Math.PI * blChangeInTick) / 560.0;
        double brChangeInRad = (2.0 * Math.PI * brChangeInTick) / 560.0;
        double flAngVel = flChangeInRad / (flTimeAfter - flTimeBefore);
        double frAngVel = frChangeInRad / (frTimeAfter - frTimeBefore);
        double blAngVel = blChangeInRad / (blTimeAfter - blTimeBefore);
        double brAngVel = brChangeInRad / (brTimeAfter - brTimeBefore);
        double flTranVel = flAngVel * (balin.WHEEL_DIAMETER/2.0);
        double frTranVel = frAngVel * (balin.WHEEL_DIAMETER/2.0);
        double blTranVel = blAngVel * (balin.WHEEL_DIAMETER/2.0);
        double brTranVel = brAngVel * (balin.WHEEL_DIAMETER/2.0);
        telemetry.addData("FL TRAN VEL", flTranVel);
        telemetry.addData("FR TRAN VEL", frTranVel);
        telemetry.addData("BL TRAN VEL", blTranVel);
        telemetry.addData("BR TRAN VEL", brTranVel);
        telemetry.update();
        double flAdjustedPower = balin.fl.getPower(), frAdjustedPower = balin.fr.getPower(), blAdjustedPower = balin.bl.getPower(), brAdjustedPower = balin.br.getPower();
        if(frTranVel != blTranVel) {
            double biggestFRBL = biggestTwoDouble(frTranVel, blTranVel);
            if(biggestFRBL == frTranVel) {
                blAdjustedPower = (balin.bl.getPower() * biggestFRBL)/blTranVel;
            }
            else {
                frAdjustedPower = (balin.fr.getPower() * biggestFRBL)/frTranVel;
            }
        }
        if(flTranVel != brTranVel) {
            double biggestFLBR = biggestTwoDouble(flTranVel, brTranVel);
            if(biggestFLBR == flTranVel) {
                brAdjustedPower = (balin.br.getPower() * biggestFLBR)/brTranVel;
            }
            else {
                flAdjustedPower = (balin.fl.getPower() * biggestFLBR)/flTranVel;
            }
        }
        balin.fl.setPower(Range.clip(flAdjustedPower,-1,1));
        balin.fr.setPower(Range.clip(frAdjustedPower,-1,1));
        balin.bl.setPower(Range.clip(blAdjustedPower,-1,1));
        balin.br.setPower(Range.clip(brAdjustedPower,-1,1));
        telemetry.addData("FL Power", flAdjustedPower);
        telemetry.addData("FR Power", frAdjustedPower);
        telemetry.addData("BL Power", blAdjustedPower);
        telemetry.addData("BR Power", brAdjustedPower);
        telemetry.update();
    }

    public double biggestTwoDouble(double a, double b) {
        if(a > b) {
            return a;
        }
        else {
            return b;
        }
    }

    public void normalizeSpeedFourMotorsForward() {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        float flEncoderBefore = Math.abs(balin.fl.getCurrentPosition());
        double flTimeBefore = timer.time();
        float frEncoderBefore = Math.abs(balin.fr.getCurrentPosition());
        double frTimeBefore = timer.time();
        float blEncoderBefore = Math.abs(balin.bl.getCurrentPosition());
        double blTimeBefore = timer.time();
        float brEncoderBefore = Math.abs(balin.br.getCurrentPosition());
        double brTimeBefore = timer.time();
        sleep(200);
        float flEncoderAfter = Math.abs(balin.fl.getCurrentPosition());
        double flTimeAfter = timer.time();
        float frEncoderAfter = Math.abs(balin.fr.getCurrentPosition());
        double frTimeAfter = timer.time();
        float blEncoderAfter = Math.abs(balin.bl.getCurrentPosition());
        double blTimeAfter = timer.time();
        float brEncoderAfter = Math.abs(balin.br.getCurrentPosition());
        double brTimeAfter = timer.time();
        float flChangeInTick = Math.abs(flEncoderAfter - flEncoderBefore);
        float frChangeInTick = Math.abs(frEncoderAfter - frEncoderBefore);
        float blChangeInTick = Math.abs(blEncoderAfter - blEncoderBefore);
        float brChangeInTick = Math.abs(brEncoderAfter - brEncoderBefore);
        double flChangeInRad = (2.0 * Math.PI * flChangeInTick) / 560.0;
        double frChangeInRad = (2.0 * Math.PI * frChangeInTick) / 560.0;
        double blChangeInRad = (2.0 * Math.PI * blChangeInTick) / 560.0;
        double brChangeInRad = (2.0 * Math.PI * brChangeInTick) / 560.0;
        double flAngVel = flChangeInRad / (flTimeAfter - flTimeBefore);
        double frAngVel = frChangeInRad / (frTimeAfter - frTimeBefore);
        double blAngVel = blChangeInRad / (blTimeAfter - blTimeBefore);
        double brAngVel = brChangeInRad / (brTimeAfter - brTimeBefore);
        double flTranVel = flAngVel * (balin.WHEEL_DIAMETER/2.0);
        double frTranVel = frAngVel * (balin.WHEEL_DIAMETER/2.0);
        double blTranVel = blAngVel * (balin.WHEEL_DIAMETER/2.0);
        double brTranVel = brAngVel * (balin.WHEEL_DIAMETER/2.0);
        telemetry.addData("FL TRAN VEL", flTranVel);
        telemetry.addData("FR TRAN VEL", frTranVel);
        telemetry.addData("BL TRAN VEL", blTranVel);
        telemetry.addData("BR TRAN VEL", brTranVel);
        telemetry.update();
        if(!isStopRequested() && opModeIsActive() && (flTranVel != frTranVel || flTranVel != blTranVel || flTranVel != brTranVel)) {
            double biggestTranVel = biggestDouble(flTranVel, frTranVel, blTranVel, brTranVel), flAdjustedPower, frAdjustedPower, blAdjustedPower, brAdjustedPower;
            if(flTranVel != biggestTranVel) {
                flAdjustedPower = (balin.fl.getPower() * biggestTranVel)/flTranVel;
            }
            else {
                flAdjustedPower = balin.fl.getPower();
            }
            if(frTranVel != biggestTranVel) {
                frAdjustedPower = (balin.fr.getPower() * biggestTranVel)/frTranVel;
            }
            else {
                frAdjustedPower = balin.fr.getPower();
            }
            if(blTranVel != biggestTranVel) {
                blAdjustedPower = (balin.bl.getPower() * biggestTranVel)/blTranVel;
            }
            else {
                blAdjustedPower = balin.bl.getPower();
            }
            if(brTranVel != biggestTranVel) {
                brAdjustedPower = (balin.br.getPower() * biggestTranVel)/brTranVel;
            }
            else {
                brAdjustedPower = balin.br.getPower();
            }
            balin.fl.setPower(Range.clip(flAdjustedPower,-1,1));
            balin.fr.setPower(Range.clip(frAdjustedPower,-1,1));
            balin.bl.setPower(Range.clip(blAdjustedPower,-1,1));
            balin.br.setPower(Range.clip(brAdjustedPower,-1,1));
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
        timer.reset();
        float flEncoderBefore = Math.abs(balin.fl.getCurrentPosition());
        double flTimeBefore = timer.time();
        float frEncoderBefore = Math.abs(balin.fr.getCurrentPosition());
        double frTimeBefore = timer.time();
        float blEncoderBefore = Math.abs(balin.bl.getCurrentPosition());
        double blTimeBefore = timer.time();
        float brEncoderBefore = Math.abs(balin.br.getCurrentPosition());
        double brTimeBefore = timer.time();
        sleep(200);
        float flEncoderAfter = Math.abs(balin.fl.getCurrentPosition());
        double flTimeAfter = timer.time();
        float frEncoderAfter = Math.abs(balin.fr.getCurrentPosition());
        double frTimeAfter = timer.time();
        float blEncoderAfter = Math.abs(balin.bl.getCurrentPosition());
        double blTimeAfter = timer.time();
        float brEncoderAfter = Math.abs(balin.br.getCurrentPosition());
        double brTimeAfter = timer.time();
        float flChangeInTick = Math.abs(flEncoderAfter - flEncoderBefore);
        float frChangeInTick = Math.abs(frEncoderAfter - frEncoderBefore);
        float blChangeInTick = Math.abs(blEncoderAfter - blEncoderBefore);
        float brChangeInTick = Math.abs(brEncoderAfter - brEncoderBefore);
        double flChangeInRad = (2.0 * Math.PI * flChangeInTick) / 560.0;
        double frChangeInRad = (2.0 * Math.PI * frChangeInTick) / 560.0;
        double blChangeInRad = (2.0 * Math.PI * blChangeInTick) / 560.0;
        double brChangeInRad = (2.0 * Math.PI * brChangeInTick) / 560.0;
        double flAngVel = flChangeInRad / (flTimeAfter - flTimeBefore);
        double frAngVel = frChangeInRad / (frTimeAfter - frTimeBefore);
        double blAngVel = blChangeInRad / (blTimeAfter - blTimeBefore);
        double brAngVel = brChangeInRad / (brTimeAfter - brTimeBefore);
        double flTranVel = flAngVel * (balin.WHEEL_DIAMETER/2.0);
        double frTranVel = frAngVel * (balin.WHEEL_DIAMETER/2.0);
        double blTranVel = blAngVel * (balin.WHEEL_DIAMETER/2.0);
        double brTranVel = brAngVel * (balin.WHEEL_DIAMETER/2.0);
        telemetry.addData("FL TRAN VEL", flTranVel);
        telemetry.addData("FR TRAN VEL", frTranVel);
        telemetry.addData("BL TRAN VEL", blTranVel);
        telemetry.addData("BR TRAN VEL", brTranVel);
        telemetry.update();
        if(!isStopRequested() && opModeIsActive() && (flTranVel != frTranVel || flTranVel != blTranVel || flTranVel != brTranVel)) {
            double biggestTranVel = biggestDouble(flTranVel, frTranVel, blTranVel, brTranVel), flAdjustedPower, frAdjustedPower, blAdjustedPower, brAdjustedPower;
            if(flTranVel != biggestTranVel) {
                flAdjustedPower = (balin.fl.getPower() * biggestTranVel)/flTranVel;
            }
            else {
                flAdjustedPower = balin.fl.getPower();
            }
            if(frTranVel != biggestTranVel) {
                frAdjustedPower = (balin.fr.getPower() * biggestTranVel)/frTranVel;
            }
            else {
                frAdjustedPower = balin.fr.getPower();
            }
            if(blTranVel != biggestTranVel) {
                blAdjustedPower = (balin.bl.getPower() * biggestTranVel)/blTranVel;
            }
            else {
                blAdjustedPower = balin.bl.getPower();
            }
            if(brTranVel != biggestTranVel) {
                brAdjustedPower = (balin.br.getPower() * biggestTranVel)/brTranVel;
            }
            else {
                brAdjustedPower = balin.br.getPower();
            }
            balin.fl.setPower(Range.clip(flAdjustedPower,-1,1));
            balin.fr.setPower(Range.clip(frAdjustedPower,-1,1));
            balin.bl.setPower(Range.clip(blAdjustedPower,-1,1));
            balin.br.setPower(Range.clip(brAdjustedPower,-1,1));
            telemetry.addData("FL Power", flAdjustedPower);
            telemetry.addData("FR Power", frAdjustedPower);
            telemetry.addData("BL Power", blAdjustedPower);
            telemetry.addData("BR Power", brAdjustedPower);
            telemetry.update();
        }
    }
}
