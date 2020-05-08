package com.acmerobotics.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Interface for a mechanism to launch balls.
 *
 * A launcher consists of two flywheels driven in opposite directions by
 * two motors (one motor per flywheel). There is also a gate controlled by a servo
 * that releases a ball into the flywheels to be launched.
 */
public class Launcher {

    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    private Servo gate;

    private double targetVelocity = 0;

    private double gateOpen = 0.3;
    private double gateClosed = 0.7; //arbitrary numbers doesn't really matter what they are

    private boolean isGateOpen = false;

    /**
     * Constructor for a Launcher.
     *
     * Uses the hardware map to initialize its internal components.
     * @param hardwareMap
     */
    public Launcher(HardwareMap hardwareMap) {

        leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");

        gate = hardwareMap.get(Servo.class, "gate");

        leftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


    }

    /**
     * Sets the run mode for the launcher motors.
     * @param runMode the run mode for the launcher motors
     */
    public void setMode(DcMotor.RunMode runMode) {

        leftMotor.setMode(runMode);
        rightMotor.setMode(runMode);

    }

    /**
     * Sets the power for the launcher motors.
     * @param power the power for the flywheel motors
     */
    public void setPower(double power) {

        leftMotor.setPower(power);
        rightMotor.setPower(power);


    }

    /**
     * Sets the target velocity for the launcher motors.
     * @param velocity  the target velocity for the flywheel motors
     */
    public void setTargetVelocity(double velocity) {

        leftMotor.setVelocity(velocity);
        rightMotor.setVelocity(velocity);

        targetVelocity = velocity;

    }

    public void stopMotors() {

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    /**
     * Returns whether the launcher is ramping up to the target velocity.
     * @return true, if the flywheel motors are ramping up to the target velocity
     */
    public boolean isRamping() {

        double currentVelocity = (leftMotor.getVelocity() + rightMotor.getVelocity()) / 2;


        if(targetVelocity > 0 && currentVelocity != targetVelocity){

            return true;

        } else {

            return false;

        }

    }

    /**
     * Returns whether the flywheels are turning.
     * @return  true, if the launcher is running
     */
    public boolean isRunning() {

        double currentPower = (leftMotor.getPower() + rightMotor.getPower()) / 2;

        if(currentPower > 0){

            return true;

        } else {

            return false;
        }

    }

    /**
     * Returns whether the launcher gate is open so balls are free to go into the flywheels.
     * @return  true, if the gate is open.
     */
    public boolean isGateOpen() {

        if(isGateOpen){

            return false;

        } else {

            return true;
        }

    }

    /**
     * Opens the gate to release a ball into the flywheels.
     */
    public void openGate() {

        gate.setPosition(gateOpen);
        isGateOpen = true;

    }

    /**
     * Closes the gate so balls are not sent into the flywheels.
     */
    public void closeGate() {

        gate.setPosition(gateClosed);
        isGateOpen = false;

    }
}