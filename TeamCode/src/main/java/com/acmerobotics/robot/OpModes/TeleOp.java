package com.acmerobotics.robot.OpModes;

import com.acmerobotics.robot.Robot.Drive;
import com.acmerobotics.robot.Robot.Launcher;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{
        Launcher launcher = new Launcher(hardwareMap);
        Drive drive = new Drive(hardwareMap, true);

        // drive init stuff

        waitForStart();

        // prevents a robot stop and restart when stop is pressed right after initialization finishes
        if (isStopRequested()) {
            return;
        }

        while(!isStopRequested()){
            launcher.update();

            if (gamepad1.right_trigger > 0.1){
                launcher.openGate();

                launcher.fire();
            }

            else{
                launcher.closeGate();

                launcher.firing = false;
            }

            if (gamepad1.x){
                launcher.closeGate();

                launcher.stopMotors();
            }

            // drive teleOp stuff
        }

    }

}
