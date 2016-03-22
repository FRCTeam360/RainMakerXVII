package org.usfirst.frc.team360.robot;

import org.usfirst.frc.team360.robot.commands.CatapultDown;
import org.usfirst.frc.team360.robot.commands.CatapultUp;
import org.usfirst.frc.team360.robot.commands.IntakeArmDown;
import org.usfirst.frc.team360.robot.commands.IntakeArmUp;
import org.usfirst.frc.team360.robot.commands.IntakeMotors;
import org.usfirst.frc.team360.robot.commands.IntakeMotorsOut;
import org.usfirst.frc.team360.robot.commands.ResetEncs;
import org.usfirst.frc.team360.robot.commands.ShiftDown;
import org.usfirst.frc.team360.robot.commands.ShiftUp;
import org.usfirst.frc.team360.robot.commands.Shoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joyR = new Joystick(0);
    public static Joystick joyL = new Joystick(1);
    public static Joystick joyOI = new Joystick(2); 
   // public static Joystick joyDriver = new Joystick(4); 
    //joyK is for the separate "joystick" keyboard device....
    public static Button buttonUp = new JoystickButton(joyR, 1);
    public static Button buttonDown = new JoystickButton(joyL, 1);
  /*  public static Button buttonDriveUp = new JoystickButton(joyDriver, 8);
    public static Button buttonDriveDown = new JoystickButton(joyDriver, 7);*/
    public static Button buttonReset = new JoystickButton(joyL, 2);
    public static Button buttonIntakeArmUp = new JoystickButton(joyOI, 4);
    public static Button buttonIntakeArmDown = new JoystickButton(joyOI, 2);
    public static Button buttonShoot = new JoystickButton(joyOI, 8);
    public static Button buttonIntakeMotor = new JoystickButton(joyOI, 1);
    public static Button buttonIntakeMotorOut = new JoystickButton(joyOI, 3);
    public OI(){
    	buttonUp.whenPressed(new ShiftUp());
    	buttonDown.whenPressed(new ShiftDown());
    	/*buttonDriveUp.whenPressed(new ShiftUp());
    	buttonDriveDown.whenPressed(new ShiftDown());*/
    	buttonIntakeArmUp.whenPressed(new IntakeArmUp());
    	buttonIntakeArmDown.whenPressed(new IntakeArmDown());
    	buttonIntakeMotor.whileHeld(new IntakeMotors());
    	buttonIntakeMotorOut.whileHeld(new IntakeMotorsOut());
    	buttonShoot.whenPressed(new Shoot());
    	buttonReset.whenPressed(new ResetEncs());
    }
}


