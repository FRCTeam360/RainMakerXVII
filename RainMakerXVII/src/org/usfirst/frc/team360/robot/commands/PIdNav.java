package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.Robot;
import org.usfirst.frc.team360.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIdNav extends Command {
	
	double motorSpeed = 0.4;
	double direction = 0;
	double currentAngle = 0;
	double distance = 0;
    double gainMultiplier = 0.1;
    double kPStraight = 0.5;
    double kIStraight = 0.03;
    double kDStraight = 0.5;
    double error = 0;
    double pAdjustment = 0;
    double iAdjustment = 0;
    double dAdjustment = 0;
    double lastError = 0;
    double PIDAdjustment = 0;
    double speed = 0;
    double way = 1;
    int n = 0;
    Timer time;
    int i = 0;
    boolean pid = false;
    public PIdNav(double direction) { //direction is called as 270
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    //	this.motorSpeed = motorSpeed;
    	this.direction = direction;
    //	this.distance = distance;
    	requires(Robot.drivetrain); 
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	dAdjustment = 0;
    	iAdjustment = 0;
    	SmartDashboard.putString("done", "going");
    	n = 0;
    	i = 0;
    	pAdjustment = 0;
    	error = 0;
    	lastError = 0;
    	PIDAdjustment = 0;
//    	if(direction > 180){
//    		way = -1;
//    	} else {
//    		way = 1;
//    	}
    	time = new Timer(); 
    	time.reset();
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("angle: ", Robot.navx.getAngle());
      	SmartDashboard.putNumber("angle target: ", direction);
    	currentAngle = Robot.navx.getAngle();
    	error = direction - currentAngle;
    	pAdjustment = error * kPStraight * gainMultiplier;
    	iAdjustment = iAdjustment + (error * kIStraight * gainMultiplier);
    	dAdjustment = (error - lastError) * kDStraight * gainMultiplier;
    	lastError = error;
    	SmartDashboard.putNumber("error: ", error);
    	SmartDashboard.putNumber("prop:  ", pAdjustment);
      	SmartDashboard.putNumber("inte: ", iAdjustment);
    	PIDAdjustment = pAdjustment + iAdjustment + dAdjustment;
    	SmartDashboard.putNumber("deriv: ", dAdjustment);
    	SmartDashboard.putNumber("prop: ", motorSpeed);
      	SmartDashboard.putNumber("inte: ", iAdjustment);
      	SmartDashboard.putNumber("right: ", motorSpeed);
      	SmartDashboard.putNumber("left: ", motorSpeed + PIDAdjustment);
      	//drive motors may be reverse 10/10 should fix
      	if(Robot.navx.getAngle() < 10 + direction && Robot.navx.getAngle() > direction - 10 && pid == false){
      		
      		iAdjustment = 0;
      		pid = true;
      		
      	}
      	if(pid == true){
      		if(PIDAdjustment > .3){
      			speed = .3;
      		} else if(PIDAdjustment < -.3){
      			speed = -.3;
      		} else {
      			speed = PIDAdjustment;
      		}
      	} else {
      		if(error > 0){
      			speed = motorSpeed;
      		} else {
      			speed = -motorSpeed;
      		}
      	}
    		Robot.drivetrain.driveR(-(speed));
      		Robot.drivetrain.driveL((speed));	

  		if(Robot.navx.getAngle() < 2 + direction && Robot.navx.getAngle() > direction - 2){
  			n++;
  			i++;
  		} else {
  			n = 0;
  		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.navx.getAngle() < 2 + direction && Robot.navx.getAngle() > direction - 2 && n > 5);// || time.get() > 3;
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    	SmartDashboard.putString("done", "done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
