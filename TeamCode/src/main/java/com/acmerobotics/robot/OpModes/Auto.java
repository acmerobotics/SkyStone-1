package com.acmerobotics.robot.OpModes;

import com.acmerobotics.robot.Robot.Drive;
import com.acmerobotics.robot.Robot.Launcher;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="auto")
public class Auto extends LinearOpMode {

    private int state = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        Drive drive = new Drive(hardwareMap, false);
        Launcher launcher = new Launcher(hardwareMap);
        ElapsedTime time = new ElapsedTime();

        // init
        drive.resetAngle();
        drive.resetEncoders();
        drive.resetEncoderOmni();
        drive.resetStrafingPos();

        waitForStart();

        while(!isStopRequested()){

            switch (state){
                case 0:
                    // move 2 feet
                   drive.goToPosition(-2, 1);

                    if (drive.atLinearPos()){
                        drive.stopMotors();
                        state++;

                    }
                    break;

                case 1:
                    // turn 30 degrees
                    drive.turnTo("right", 30);

                    if (drive.atLinearPos()){
                        drive.stopMotors();
                        state++;

                    }
                    break;

                case 2:
                    // load
                    launcher.openGate();
                    time.reset();

                    // delay to let ball move through gate
                    if (time.seconds() < 1){
                        // wait
                    }

                    else {

                        // close
                        launcher.closeGate();

                        // launch preloaded
                        launcher.setPower(1);

                        state++;
                    }
                    break;

                case 3:
                    //turn 60 degrees
                    drive.turnTo("left", -60);

                    if (drive.atLinearPos()){
                        drive.stopMotors();
                        state++;

                    }
                    break;

                case 4:
                    drive.goToPosition(-3, 1);

                    if (drive.atLinearPos()){
                        drive.stopMotors();
                        state++;

                    }
                    break;
            }

        }

    }

}
