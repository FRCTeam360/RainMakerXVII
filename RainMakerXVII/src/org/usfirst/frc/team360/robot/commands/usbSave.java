/*package org.usfirst.frc.team360.robot.commands;

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
	FileOutputStream Textfile;
	ObjectOutputStream save;
	protected void initialize() {
		
		try{
			 Textfile=new FileOutputStream("u/usbSaveFile.txt");
			
			// Might need to change saveFile to u/usbSaveFile
			 save = new ObjectOutputStream(Textfile);

			
			
			System.out.println("Test!");
			           
			}
		
			catch(Exception etc){
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
}*/

package org.usfirst.frc.team360.robot.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import edu.wpi.first.wpilibj.VictorSP;
import java.io.*;

import org.usfirst.frc.team360.robot.Robot;
import org.usfirst.frc.team360.robot.RobotMap;

@SuppressWarnings("unused")
public class usbSave {
	@SuppressWarnings("null")
	public static void main(String[] args) {
		
		VictorSP motorR = RobotMap.motorR1;  
		VictorSP motorL = RobotMap.motorR2;  
		VictorSP motorRL = RobotMap.motorL1;
		VictorSP motorLR = RobotMap.motorL2;  
		ObjectOutputStream save = null;

		File file = new File("u/newfile.txt");
		String content = "Starting Log";

		try (FileOutputStream fop = new FileOutputStream(file)) {

			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			save.writeObject(motorR.get());
			save.writeObject(motorL.get());
			save.writeObject(motorRL.get());
			save.writeObject(motorLR.get());
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
