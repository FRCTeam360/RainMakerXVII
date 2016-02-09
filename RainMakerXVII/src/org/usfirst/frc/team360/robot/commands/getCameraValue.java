package org.usfirst.frc.team360.robot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.usfirst.frc.team360.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class getCameraValue extends Command {
	String i = "";
    public getCameraValue() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.raspberrypiserver);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.raspberrypiserver.startConnection();
    	
  
    }
    // Called repeatedly when this Command is scheduled to run
   // @SuppressWarnings("deprecation")
	protected void execute() {
		SmartDashboard.putString("out", i + "f");
    	try {
    		SmartDashboard.putString("ou", i + "f");
    		//SmartDashboard.putInt("out", Robot.raspberrypiserver.recieved());
    		//SmartDashboard.putString("output", "er");
        	Robot.raspberrypiserver.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.raspberrypiserver.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
