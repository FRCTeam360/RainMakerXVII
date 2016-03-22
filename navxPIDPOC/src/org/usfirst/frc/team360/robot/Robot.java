package org.usfirst.frc.team360.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the navX MXP to implement
 * a "rotate to angle" feature.
 *
 * This example will automatically rotate the robot to one of four
 * angles (0, 90, 180 and 270 degrees).
 *
 * This rotation can occur when the robot is still, but can also occur
 * when the robot is driving.  When using field-oriented control, this
 * will cause the robot to drive in a straight line, in whathever direction
 * is selected.
 *
 * This example also includes a feature allowing the driver to "reset"
 * the "yaw" angle.  When the reset occurs, the new gyro angle will be
 * 0 degrees.  This can be useful in cases when the gyro drifts, which
 * doesn't typically happen during a FRC match, but can occur during
 * long practice sessions.
 *
 * Note that the PID Controller coefficients defined below will need to
 * be tuned for your drive system.
 */

public class Robot extends SampleRobot implements PIDOutput {
    AHRS ahrs;
    RobotDrive myRobot;
    Joystick stick;
    PIDController turnController;
    double rotateToAngleRate;
    
    /* The following PID Controller coefficients will need to be tuned */
    /* to match the dynamics of your drive system.  Note that the      */
    /* SmartDashboard in Test mode has support for helping you tune    */
    /* controllers by displaying a form where you can enter new P, I,  */
    /* and D constants and test the mechanism.                         */
    
    static final double kP = 0.03;
    static final double kI = 0.00001;
    static final double kD = 0.00;
    static final double kF = 0.00;
    
    static final double kToleranceDegrees = 2.0f;

    // Channels for the wheels
    public static VictorSP motorR1;
    public static VictorSP motorR2;
    public static VictorSP motorL1;
    public static VictorSP motorL2;
    final static int rearLeftChannel	= 3;
    final static int frontRightChannel	= 1;
    final static int rearRightChannel	= 0;
        
    public Robot() {
    	motorR1 = new VictorSP(0);
    	motorR2 = new VictorSP(1);
    	motorL1 = new VictorSP(2);
    	motorL2 = new VictorSP(3);
        myRobot = new RobotDrive(motorL1, motorL2,
        		motorR1, motorR2);
        myRobot.setExpiration(0.1);
        stick = new Joystick(0);
        try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            ahrs = new AHRS(SPI.Port.kMXP); 
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        turnController = new PIDController(kP, kI, kD, kF, ahrs, this);
        turnController.setInputRange(0,  360);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setPercentTolerance(6);
        turnController.setContinuous(true);
        
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        LiveWindow.addActuator("DriveSystem", "RotateController", turnController);

      	LiveWindow.addSensor("gyro", "gryo", ahrs);
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
        myRobot.setSafetyEnabled(false);
   //     myRobot.arcadeDrive(stick.getRawAxis(1), stick.getRawAxis(1));    // stop robot
        Timer.delay(2.0);		    //    for 2 seconds
        myRobot.drive(0.0, 0.0);	// stop robot
    }

    /**
     * Runs the motors with onnidirectional drive steering.
     * 
     * Implements Field-centric drive control.
     * 
     * Also implements "rotate to angle", where the angle
     * being rotated to is defined by one of four buttons.
     * 
     * Note that this "rotate to angle" approach can also 
     * be used while driving to implement "straight-line
     * driving".
     */
    public void operatorControl() {
    	LiveWindow.run();
  	  myRobot.setSafetyEnabled(true);
        while (isEnabled()) {
      	 
        	     // stop robot`
            boolean rotateToAngle = false;
            if ( stick.getRawButton(1)) {
                ahrs.reset();
            }
            LiveWindow.run();
            if ( stick.getRawButton(2)) {
                turnController.setSetpoint(0.0f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(3)) {
                turnController.setSetpoint(90.0f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(4)) {
                turnController.setSetpoint(179.9f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(5)) {
                turnController.setSetpoint(270.0f);
                rotateToAngle = true;
            } else if ( stick.getRawButton(6)) {
                turnController.setSetpoint(turnController.getSetpoint() + 10);
                rotateToAngle = true;
            } else {
            	turnController.disable();
            	rotateToAngle = false;
            }
            double currentRotationRate;
            if ( rotateToAngle ) {
                turnController.enable();
                currentRotationRate = rotateToAngleRate;
            } else {
                turnController.disable();
                currentRotationRate = stick.getTwist();
            }
            try {
               // Use the joystick X axis for lateral movement,          
                // Y axis for forward movement, and the current           
                // calculated rotation rate (or joystick Z axis),         
                // depending upon whether "rotate to angle" is active.    
				myRobot.arcadeDrive(stick.getRawAxis(1), currentRotationRate);
            } catch( RuntimeException ex ) {
                DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
            }
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    	LiveWindow.run();
    	  myRobot.setSafetyEnabled(true);
          while (isEnabled()) {
        	  LiveWindow.run();
          	     // stop robot`
              boolean rotateToAngle = false;
              if ( stick.getRawButton(1)) {
                  ahrs.reset();
              }
              if ( stick.getRawButton(2)) {
                  turnController.setSetpoint(0.0f);
                  rotateToAngle = true;
              } else if ( stick.getRawButton(3)) {
                  turnController.setSetpoint(90.0f);
                  rotateToAngle = true;
              } else if ( stick.getRawButton(4)) {
                  turnController.setSetpoint(179.9f);
                  rotateToAngle = true;
              } else if ( stick.getRawButton(5)) {
                  turnController.setSetpoint(270.0f);
                  rotateToAngle = true;
              } else if ( stick.getRawButton(6)) {
                  turnController.setSetpoint(turnController.getSetpoint() + 10);
                  rotateToAngle = true;
              } else {
              	turnController.disable();
              	rotateToAngle = false;
              }
              double currentRotationRate;
              if ( rotateToAngle ) {
                  turnController.enable();
                  currentRotationRate = rotateToAngleRate;
              } else {
                  turnController.disable();
                  currentRotationRate = stick.getTwist();
              }
              try {
                 // Use the joystick X axis for lateral movement,          
                  // Y axis for forward movement, and the current           
                  // calculated rotation rate (or joystick Z axis),         
                  // depending upon whether "rotate to angle" is active.    
  				myRobot.arcadeDrive(stick.getRawAxis(1), currentRotationRate);
              } catch( RuntimeException ex ) {
                  DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
              }
              Timer.delay(0.005);		// wait for a motor update time
              
          }
    }

    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
