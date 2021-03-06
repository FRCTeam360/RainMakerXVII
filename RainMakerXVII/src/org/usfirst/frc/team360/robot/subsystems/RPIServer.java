package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RPIServer extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	double distance = 0;
	double retDistance = 0;
	double angle = 0;
	double retAngle = 0;
	public double getDistance(){
		distance = 0;
		if(RobotMap.distance7.get() == true){
			distance += 64;
		}
		if(RobotMap.distance6.get() == true){
			distance += 32;
		}
		if(RobotMap.distance5.get() == true){
			distance += 16;
		}
		if(RobotMap.distance4.get() == true){
			distance += 8;
		}
		if(RobotMap.distance3.get() == true){
			distance += 4;
		}
		if(RobotMap.distance2.get() == true){
			distance += 2;
		}
		if(RobotMap.distance1.get() == true){
			distance += 1;
		}
		if(distance != 0){
			retDistance = distance;
		}
		return retDistance;
	}

	public double getAngle(){
		angle = 0;
		if(RobotMap.angle7.get() == true){
			angle += 64;
		}
		if(RobotMap.angle6.get() == true){
			angle += 32;
		}
		if(RobotMap.angle5.get() == true){
			angle += 16;
		}
		if(RobotMap.angle4.get() == true){
			angle += 8;
		}
		if(RobotMap.angle3.get() == true){
			angle += 4;
		}
		if(RobotMap.angle2.get() == true){
			angle += 2;
		}
		if(RobotMap.angle1.get() == true){
			angle += 1;
		}
		angle /= 2; //angle is from 0-120 and we want 0-60 so we /2
		
			retAngle = angle;
		
		return retAngle;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}