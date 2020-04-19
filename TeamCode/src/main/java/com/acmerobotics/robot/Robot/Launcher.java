package com.acmerobotics.robot.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Interface for a mechanism to launch balls.
 *
 * A launcher consists of two flywheels driven in opposite directions by
 * two motors (one motor per flywheel). There is also a gate controlled by a servo
 * that releases a ball into the flywheels to be launched.
 */
public class Launcher {

    /**
     * Constructor for a Launcher.
     *
     * Uses the hardware map to initialize its internal components.
     * @param hardwareMap
     */
    public Launcher(HardwareMap hardwareMap) {

    }

    /**
     * Sets the run mode for the launcher motors.
     * @param runMode the run mode for the launcher motors
     */
    public void setMode(DcMotor.RunMode runMode) {

    }

    /**
     * Sets the power for the launcher motors.
     * @param power the power for the flywheel motors
     */
    public void setPower(double power) {

    }

    /**
     * Sets the target velocity for the launcher motors.
     * @param velocity  the target velocity for the flywheel motors
     */
    public void setTargetVelocity(double velocity) {

    }

    /**
     * Returns whether the launcher is ramping up to the target velocity.
     * @return true, if the flywheel motors are ramping up to the target velocity
     */
    public boolean isRamping() {
        return false;
    }

    /**
     * Returns whether the flywheels are turning.
     * @return  true, if the launcher is running
     */
    public boolean isRunning() {
        return false;
    }

    /**
     * Returns whether the launcher gate is open so balls are free to go into the flywheels.
     * @return  true, if the gate is open.
     */
    public boolean isGateOpen() {
        return false;
    }

    /**
     * Opens the gate to release a ball into the flywheels.
     */
    public void openGate() {

    }

    /**
     * Closes the gate so balls are not sent into the flywheels.
     */
    public void closeGate() {

    }
}