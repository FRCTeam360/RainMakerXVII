package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	VictorSP motorL = RobotMap.motorL;
	public void driveL(double speed) {
		motorL.set (speed);
	}
	VictorSP motorR = RobotMap.motorR;
	public void driveR(double speed) {
		motorR.set (speed);
	}
	public void stop(){
		motorR.stopMotor();
		motorL.stopMotor();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

