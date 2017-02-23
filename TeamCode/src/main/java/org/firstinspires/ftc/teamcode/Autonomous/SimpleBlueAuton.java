package org.firstinspires.ftc.teamcode.Autonomous;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by andrew.keenan on 2/11/2017.
 */
@Autonomous(name = "BlueSimple", group = "Auton")

public class SimpleBlueAuton extends LinearOpMode {
    private ElapsedTime     runtime = new ElapsedTime();
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, true);
        balin.navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get(balin.cdim),
                balin.NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                balin.NAVX_DEVICE_UPDATE_RATE_HZ);
        while (balin.navx_device.isCalibrating() && !isStopRequested()) {
            telemetry.addData("Ready?", "No");
            telemetry.update();
        }
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        balin.changeDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //balin.turn();
        if(opModeIsActive() && !isStopRequested()) balin.navx_device.zeroYaw();
        //Move forward
        if(opModeIsActive() && !isStopRequested()) balin.moveStraightnew(8, this);
        if(opModeIsActive() && !isStopRequested()) sleep(1400);
        if(opModeIsActive() && !isStopRequested()) balin.setDrivePower(0);
        if(opModeIsActive() && !isStopRequested()) sleep(100);
        //Get first particle into the shooter
        if(opModeIsActive() && !isStopRequested()) balin.door.setPosition(balin.DOOR_OPEN);
        if(opModeIsActive() && !isStopRequested()) balin.collector.setPower(1.0);
        if(opModeIsActive() && !isStopRequested()) sleep(2000);
        if(opModeIsActive() && !isStopRequested()) balin.collector.setPower(0);
        if(opModeIsActive() && !isStopRequested()) balin.door.setPosition(balin.DOOR_CLOSED);
        if(opModeIsActive() && !isStopRequested()) sleep(500);
        //Shoot first particle
        if(opModeIsActive() && !isStopRequested()) balin.shoot(1.0);
        if(opModeIsActive() && !isStopRequested()) sleep(600);
        if(opModeIsActive() && !isStopRequested()) balin.shoot(0);

        //Get second particle into the shooter
        if(opModeIsActive() && !isStopRequested()) balin.door.setPosition(balin.DOOR_OPEN);
        if(opModeIsActive() && !isStopRequested()) balin.collector.setPower(1.0);
        if(opModeIsActive() && !isStopRequested()) sleep(2000);
        if(opModeIsActive() && !isStopRequested()) balin.collector.setPower(0);
        if(opModeIsActive() && !isStopRequested()) balin.door.setPosition(balin.DOOR_CLOSED);
        if(opModeIsActive() && !isStopRequested()) sleep(500);
        //Shoot second particle
        if(opModeIsActive() && !isStopRequested()) balin.shoot(1.0);
        if(opModeIsActive() && !isStopRequested()) sleep(600);
        if(opModeIsActive() && !isStopRequested()) balin.shoot(0);
        if(opModeIsActive() && !isStopRequested()) sleep(200);
        if(opModeIsActive() && !isStopRequested()) balin.gyroAngle(31, .25, this);
        if(opModeIsActive() && !isStopRequested()) balin.restAndSleep(this);
        boolean white_line = false;
        if(opModeIsActive() && !isStopRequested()) balin.setDrivePower(.2);
        while(!white_line && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .5) {
                white_line = true;
             }

            telemetry.addData("Following White Line: ", balin.ods.getLightDetected());
            telemetry.addData("FL: ", balin.fl.getPower());
            telemetry.addData("FR: ", balin.fr.getPower());
            telemetry.addData("BR: ", balin.br.getPower());
            telemetry.addData("BL:", balin.bl.getPower());
            telemetry.update();
        }

        if(opModeIsActive() && !isStopRequested()) balin.setDrivePower(0);
        if(opModeIsActive() && !isStopRequested()) balin.restAndSleep(this);
        if(opModeIsActive() && !isStopRequested()) balin.gyroAngle(43, .25, this);
        if(opModeIsActive() && !isStopRequested()) sleep(200);
        white_line = false;
        while (white_line  && opModeIsActive() && !isStopRequested()) {
            if (balin.ods.getRawLightDetected() >= .5) {
                white_line = true;
            }
            balin.fr.setPower(-.25);
            balin.br.setPower(.25);
            balin.fl.setPower(.25);
            balin.bl.setPower(-.25);
        }
        //Driving Forward to knock the cap ball off

    }
    public void turnToOriginalAngle() {
        double AngleToTurnTo = balin.navx_device.getYaw() * -1;
        if(opModeIsActive()) balin.gyroAngle(AngleToTurnTo, .2, this);
    }
}
