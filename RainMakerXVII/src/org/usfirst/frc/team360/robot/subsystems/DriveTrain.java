package org.usfirst.frc.team360.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team360.robot.*;
//import org.usfirst.frc.team360.robot.commands.JoystickTankDrive;
/**
 *
 */
public class DriveTrain extends Subsystem {
	VictorSP motorR1 = RobotMap.motorR1;
	VictorSP motorR2 = RobotMap.motorR2;
	VictorSP motorL1 = RobotMap.motorL1;
	VictorSP motorL2 = RobotMap.motorL2;
	Encoder encR = RobotMap.encR;
	Encoder encL = RobotMap.encL;

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrain(){
		encL.reset();
		encR.reset();
	}
	public void resetREnc(){
	
		encR.reset();
	}
	public void resetLEnc(){
		encL.reset();
	}
	public void resetEncs(){
		encL.reset();
		encR.reset();
	}
	public int getREnc(){
		return encR.get()*-1;
	}
	public int getLEnc(){
		return encL.get()*-1;
	}
	public void drive(double motorR, double motorL) {
		  motorR1.set(motorR);
		  motorR2.set(motorR);
		  motorL1.set(-motorL * .95);
		  motorL2.set(-motorL * .95);
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
		  motorL1.stopMotor();
		  motorL2.stopMotor();
		  motorR1.stopMotor();
		  motorR2.stopMotor(); 
	  }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
}

