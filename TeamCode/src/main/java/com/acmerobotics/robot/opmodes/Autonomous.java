package com.acmerobotics.robot.opmodes;

import com.acmerobotics.robot.Drive;
import com.acmerobotics.robot.Launcher;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Autonomous extends LinearOpMode {

    private int state;

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, false);
        Launcher launcher = new Launcher(hardwareMap);

        state = 0;

        drive.resetEncoders();

        waitForStart();

        while(!isStopRequested()){

            switch (state) {

                case 0:

                    drive.goToPosition(24, 0.5);
                    state++;
                    break;

                case 1:

                    if(drive.atLinearPos()){
                        drive.stopMotors();
                        drive.resetAngle();
                        state++;

                    }

                    break;

                case 2:


                    drive.setDegrees(-30);


                    if(drive.getAngle() == 0) {
                        drive.clockwise();
                    }

                    if(drive.getDegrees() > 0) {

                        if(drive.getAngle() < drive.getDegrees()){
                            drive.counterClockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    } else {

                        if(drive.getAngle() > drive.getDegrees()){
                            drive.clockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    }

                    break;

                case 3:

                    launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    launcher.setPower(1);
                    state++;

                    break;


                case 4:

                    launcher.firstShot();
                    state++;

                   break;


                case 5:

                    if(launcher.isShotTaken()){

                        drive.resetAngle();
                        state++;

                    }

                    break;


                case 6:

                    drive.setDegrees(-60);


                    if(drive.getAngle() == 0) {
                        drive.clockwise();
                    }

                    if(drive.getDegrees() > 0) {

                        if(drive.getAngle() < drive.getDegrees()){
                            drive.counterClockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    } else {

                        if(drive.getAngle() > drive.getDegrees()){
                            drive.clockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    }

                    break;

                case 7:

                    drive.resetEncoders();
                    state++;

                    break;


                case 8:

                    drive.goToPosition(36, 0.5);
                    state++;

                    break;

                case 9:

                    if(drive.atLinearPos()){
                        drive.stopMotors();

                    }

                    break;


                }


            }

        }
    }

