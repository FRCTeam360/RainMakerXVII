package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

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
    	error = position - setPoint;
    	integral = integral + (error * dt);
    	derivative = (error - prevError) / dt;
    	output = (RobotMap.kPNavX * error) + (RobotMap.kINavX * integral) + (RobotMap.kDNavX * derivative);
    	if(output > 1){
    		output = 1;
    	} else if(output < -1){
    		output = -1;
    	}
    	return output;
    }
}