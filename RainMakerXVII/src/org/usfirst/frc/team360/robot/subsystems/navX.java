package org.usfirst.frc.team360.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team360.robot.RobotMap;
import com.kauailabs.navx.frc.AHRS; 
import edu.wpi.first.wpilibj.VictorSP; 
import org.usfirst.frc.team360.robot.*;
import org.usfirst.frc.team360.robot.commands.JoystickTankDrive;
//overall goal: Use navx to get our angle to use the PID subsystem 



public class NavX extends Subsystem{
	public static AHRS ahrs = RobotMap.ahrs;  
	DriveTrain myRobot; 

	public double getAngle(){

		return ahrs.getAngle();


	}
	public void reset(){
		  ahrs.reset();	
		 ahrs.resetDisplacement();
	}
	public float getDisplacementX(){
		return ahrs.getVelocityX();
	}
	public float getDisplacementY(){
		return ahrs.getVelocityY();
	}
	public float getDisplacementZ(){
		return ahrs.getVelocityZ();
	}
	/*public void operatorControl() {
	      PID.setSafetyEnabled(false);
	      while (isOperatorControl() && isEnabled()) {
	          boolean rotateToAngle = false;
	          if ( stick.getRawButton(1)) {
	              ahrs.reset();
	} */ 
	protected void initDefaultCommand() {



	}
}