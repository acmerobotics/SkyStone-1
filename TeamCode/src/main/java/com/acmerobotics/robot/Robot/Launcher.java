package com.acmerobotics.robot.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Interface for a mechanism to launch balls.
 *
 * A launcher consists of two flywheels driven in opposite directions by
 * two motors (one motor per flywheel). There is also a gate controlled by a servo
 * that releases a ball into the flywheels to be launched.
 */
public class Launcher {

    public double maxVelocity;
    public double sleepVelocity; // run motors at a low velocity so that when you want to shoot it doesn't
                                    // take as long to get to full speed. I could also just have the wheels always
                                    // running at full but that would take up battery power and wouldn't be as fun to build.

    public DcMotorEx LMotor, RMotor;

    public double targetVelocity = 0;

    private Servo gateServo;

    public boolean firing = false;

    private boolean isStopped = false;


    /**
     * Constructor for a Launcher.
     *
     * Uses the hardware map to initialize its internal components.
     * @param hardwareMap
     */
    public Launcher(HardwareMap hardwareMap) {
        LMotor = hardwareMap.get(DcMotorEx.class, "LMotor");
        RMotor = hardwareMap.get(DcMotorEx.class, "RMotor");
        gateServo = hardwareMap.get(Servo.class, "gateServo");

        // These direction would be correct if the motor shafts are facing up
        LMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        LMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        RMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    /**
     * Sets the run mode for the launcher motors.
     * @param runMode the run mode for the launcher motors
     */
    public void setMode(DcMotor.RunMode runMode) {
        LMotor.setMode(runMode);
        RMotor.setMode(runMode);

    }

    /**
     * Sets the power for the launcher motors.
     * @param power the power for the flywheel motors
     */
    public void setPower(double power) {
        LMotor.setPower(power);
        RMotor.setPower(power);
    }

    /**
     * Sets the target velocity for the launcher motors.
     * @param velocity  the target velocity for the flywheel motors
     */
    public void setTargetVelocity(double velocity) {

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // sets velocity in ticks per second
        LMotor.setVelocity(velocity);
        RMotor.setVelocity(velocity);

        targetVelocity = velocity;

    }

    /**
     * Returns whether the launcher is ramping up to the target velocity.
     * @return true, if the flywheel motors are ramping up to the target velocity
     */
    public boolean isRamping() {

        double currentVelocity = LMotor.getVelocity();

        if (currentVelocity != targetVelocity){
            // motors haven't reached the target

            return true;
        }

        else {

            return false;
        }
    }

    /**
     * Returns whether the flywheels are turning.
     * @return  true, if the launcher is running
     */
    public boolean isRunning() {

        double currentPower = LMotor.getPower();

        if (currentPower != 0){
            // motors are running

            return true;
        }

        else {

            return false;
        }
    }


    public boolean doneRamping(){

        if (isRunning() && !isRamping()){
            return true;
        }

        else{
            return false;
        }
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
        gateServo.setPosition(1);

    }

    /**
     * Closes the gate so balls are not sent into the flywheels.
     */
    public void closeGate() {
        gateServo.setPosition(0.5);

    }

    public void fire(){
        isStopped = false;
        firing = true;

        firePID();

        if (doneRamping()){
            openGate();
        }

        else {
            setTargetVelocity(maxVelocity);
        }
    }

    public void sleepMotors(){

        sleepPID();

        closeGate();
        setTargetVelocity(sleepVelocity);

    }

    public void update(){
        // when the motors aren't being used then the velocity wil be brought down to a reasonable
        // place. If the motors are already moving then when the launcher has to fire it will take less
        // time to ramp up.

        if (!firing && !isStopped){
            sleepMotors();
        }
    }


    public void stopMotors(){
        isStopped = true;

        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setPower(0);
    }


    public void setPID(double p, double i, double d){

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(p, i, d, 0);

        LMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
    }

    public void firePID(){

        // this PID controller needs to be as fast as possible

        setPID(10, 5, 3);
    }


    public void sleepPID(){

        // this PID controller can be slow it does not really matter, actually slower is better.
        // if the finger of the user accidenly slips a fast PID controller will drive the velocity of the
        // flywheel down quickly which will result in a longer amount of time to bring them back up.
        // If the PID controller is slow then a accident would be ok bc the PID controller would slowly drive the
        // velocity down, meaning the velocity can also quickly be brought up without wasting time ramping.
        // I think there are 3 reasons this works 1) static friction won't be as much of a problem bc the wheels are
        // already in motion 2) getting to a velocity from 0 to 10 takes more time then 5 to 10 3) I think (80% sure) the inertia of the
        // wheels will also be less of a problem bc they are already in motion meaning ... (more physics stuff I'll finish the explaination later)

        setPID(5, 2, 1);
    }
}

