package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.Robot;
import org.usfirst.frc.team360.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class NavXPIDSubsystem extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	double integral = 0;
	double error = 0;
	double prevError = 0;
	double derivative = 0;
	double dt = .002;
	double output = 0;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public double doPID(double position, double setPoint){
    	error = Robot.navx.getAngle() - setPoint;
        SmartDashboard.putNumber("error", error);
        SmartDashboard.putNumber("set", setPoint);
        SmartDashboard.putNumber("pos", Robot.navx.getAngle());
    	integral = integral + (error);
    	derivative = (error - prevError);
    	output = (RobotMap.kPNavX * error);// + (RobotMap.kINavX * integral) + (RobotMap.kDNavX * derivative);
    	prevError = error;
    	if(output > 1){
    		output = 1;
    	} else if(output < -1){
    		output = -1;
    	}
    	return output;
    }
}