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
	VictorSP motorLR = RobotMap.motorL2;  
	FileOutputStream saveFile;
	ObjectOutputStream save;
	protected void initialize() {
		
		try{
			 saveFile=new FileOutputStream("u/usbSaveFile.sav");
			
			// Might need to change saveFile to u/usbSaveFile
			 save = new ObjectOutputStream(saveFile);

			
			
			System.out.println("Test!");
			           
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
		try {
			save.writeObject(motorR.get());
			save.writeObject(motorL.get());
			save.writeObject(motorRL.get());
			save.writeObject(motorLR.get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		try {
			save.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}