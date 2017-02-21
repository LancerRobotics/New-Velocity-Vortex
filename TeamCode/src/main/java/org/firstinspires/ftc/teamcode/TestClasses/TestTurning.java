package org.firstinspires.ftc.teamcode.TestClasses;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.DriversAndHardware.Hardware3415;

/**
 * Created by shlok.khandelwal on 2/20/2017.
 */
@TeleOp(name = "TestTurn", group = "Tests")
public class TestTurning extends LinearOpMode{
    Hardware3415 balin = new Hardware3415();
    public void runOpMode(){
        balin.init(hardwareMap, false);
        balin.navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get(balin.cdim),
                balin.NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                balin.NAVX_DEVICE_UPDATE_RATE_HZ);
        while (balin.navx_device.isCalibrating()) {
            telemetry.addData("Ready?", "No");
            telemetry.update();
        }
        telemetry.addData("Ready?", "Yes");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.a)
                balin.gyroAngle(30, .3, this);
            if(gamepad1.b)
                balin.gyroAngle(45, .3, this);
            if(gamepad1.x)
                balin.gyroAngle(-45, .3, this);
            if(gamepad1.y){
                balin.gyroAngle(-30, .3, this);
            }
            telemetry.addData("Yaw: ", balin.navx_device.getYaw());
            telemetry.update();
        }
    }
}
