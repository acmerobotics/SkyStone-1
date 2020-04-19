package com.acmerobotics.robot.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.robot.Drive;
import com.acmerobotics.robot.Launcher;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, true);
        Launcher launcher = new Launcher(hardwareMap);

        waitForStart();


        while(!isStopRequested()){

            Pose2d v = new Pose2d(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            drive.setPower(v);

            if(gamepad1.a) {

                launcher.openGate();
            }

            if (gamepad1.b) {

                launcher.closeGate();
            }

            if (gamepad1.left_trigger > 0) {

                launcher.setPower(1);

            }

        }

    }
}
