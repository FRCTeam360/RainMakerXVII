package org.usfirst.frc.team360.robot.subsystems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RaspberryPiServer extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	ServerSocket server;
	Socket clientSocket = null;
	PrintWriter out;
	BufferedReader in;
	String responseLine;
	public void startConnection() {
		int portNumber = 25500;

		try {
		    server = new ServerSocket(portNumber);
		    clientSocket = server.accept();
		    out = new PrintWriter(clientSocket.getOutputStream(), true);
		    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		    responseLine = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hi");
	}
	public void run(){
//		 if ((responseLine = is.readLine()) != null) {
//             System.out.println("Server: " + responseLine);
	}
	public void stop(){
		try {
			server.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

