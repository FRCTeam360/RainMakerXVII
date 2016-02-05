package org.usfirst.frc.team360.robot.commands;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import java.io.*;

import org.usfirst.frc.team360.robot.Robot;
import org.usfirst.frc.team360.robot.RobotMap;

public class usbSave extends Command {
	VictorSP motorR = RobotMap.motorR1;  
	VictorSP motorL = RobotMap.motorR2;  
	VictorSP motorRL = RobotMap.motorL1;
	VictorSP motorLR = RobotMap.motorR2;  
	protected void initialize() {
	    	
		try{
			FileOutputStream saveFile=new FileOutputStream("u/usbSaveFile.txt");
			
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(motorR.get());
			save.writeObject(motorL.get());
			save.writeObject(motorRL.get());
			save.writeObject(motorLR.get());
			           
			save.close();
			}
		
			catch(Exception exc){
			exc.printStackTrace();
		}
	}
	  protected boolean isFinished() {
	        return true;
	    }
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}