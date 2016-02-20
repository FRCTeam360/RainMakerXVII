package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class DriveTrain extends Subsystem {
	public static Talon motorR1 = RobotMap.motorR1;
	public static Talon motorR2 = RobotMap.motorR2;
	public static Talon motorL1 = RobotMap.motorL1;
	public static Talon motorL2 = RobotMap.motorL2;

		public String getMotor(){
			
			return (" Motor R1=" + motorR1.get() + " Motor R2=" + motorR2.get() + 
					" Motor L1=" + motorL1.get() + " Motor L2=" + motorL2.get());
		}
	  public void drive(double motorR, double motorL) {
		  motorR1.set(motorR);
		  motorR2.set(motorR);
		  motorL1.set(-motorL);
		  motorL2.set(-motorL);
	  }
	  public void driveBackwards(double motorR, double motorL) {
		  motorR1.set(-motorR);
		  motorR2.set(-motorR);
		  motorL1.set(motorL);
		  motorL2.set(motorL);
	  }
	  public void driveR(double RMotor){
		  motorR1.set(RMotor);
		  motorR2.set(RMotor);
	  }
	  public void driveL(double LMotor){
		  motorL1.set(-LMotor);
		  motorL2.set(-LMotor);
	  }
	  public void stopR(){
		  motorR1.stopMotor();
		  motorR2.stopMotor();  
	  }
	  public void stopL(){
		  motorL1.stopMotor();
		  motorL2.stopMotor();
	  }
	  public void stop(){
		  motorR1.stopMotor();
		  motorR2.stopMotor();
		  motorL1.stopMotor();
		  motorL2.stopMotor();
	  }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
}

