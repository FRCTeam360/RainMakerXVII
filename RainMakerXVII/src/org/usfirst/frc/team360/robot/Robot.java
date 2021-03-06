package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

import org.usfirst.frc.team360.robot.commands.*;
import org.usfirst.frc.team360.robot.subsystems.*;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

public class Robot extends IterativeRobot {

	// ENCODERS ON COMP BOT ARE 21.16 ticks per In
	// ENCODER ON PRACTICE BOT ARE 21.12 but we will default to COMP BOT
	public static DriveTrain drivetrain;
	public static SuperShifter supershifter;
	public static Pneumatics pneumatics;
	public static Catapult catapult;
	public static IntakeArms intakearm;
	public static IntakeMotor intakemotor;
	public static NavX navx;
	public static RPIServer rpiserver;
	public static Lights lights;
	
	String auto4Str = "auto 4";
	String auto3Str = "auto 3";
	String auto2Str = "auto 2";
	
	public static enum AutoMode1 {NONE, BREACH, SHOOT, BREACHANDSHOOT, TEST};
	public static enum AutoMode2 {LOWBAR, MOAT, RAMPARTS, ROCKWALL, ROUGHTERRAIN};
	public static enum AutoMode3 {POSITION1, POSITION2, POSITION3, POSITION4, POSITION5};
	public static enum AutoMode4 {RIGHT, CENTER, LEFT};
	
	static AutoMode1 automode1;
	static AutoMode2 automode2;
	static AutoMode3 automode3;
	static AutoMode4 automode4;
	SendableChooser auto;
	SendableChooser auto1;
	SendableChooser auto2;
	SendableChooser auto3;
	SendableChooser auto4;

	Command printnavxangle;
	Command autoCommand;
	Command getencs;
	Command resetencs;
	Command rpidistance;
	Command rpiangle;
	Command pressurize;
	Command printrpidata;
	Command getcatapultposition;
	
	CameraServer cam;
	
	public Image frame;
	
	public static OI oi;
	
	public void robotInit() {

		try {
			
			RobotMap.dangerZone = false;
			
			lights = new Lights();
			supershifter = new SuperShifter();
			pneumatics = new Pneumatics();
			drivetrain = new DriveTrain();
			catapult = new Catapult();
			intakearm = new IntakeArms();
			intakemotor = new IntakeMotor();
			navx = new NavX();
			rpiserver = new RPIServer();
			
			getcatapultposition = new GetCatapultPosition();
			printrpidata = new PrintRPIData();
			pressurize = new Pressurize();
			rpiangle = new RPIAngle();
			rpidistance = new RPIDistance();
			resetencs = new ResetEncs();
			getencs = new GetEncs();
			printnavxangle = new PrintNavXAngle();
			
			oi = new OI();
			
			RobotMap.lights = new Relay(0);
			
			RobotMap.back = new USBCamera("cam0");
			RobotMap.front = new USBCamera("cam1");
			RobotMap.back.setFPS(15);
			RobotMap.front.setFPS(15);
			RobotMap.back.startCapture();
			RobotMap.cam = RobotMap.back;
			RobotMap.frontCam = false;
			
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void autoChooserThingy(){
		auto = new SendableChooser();
		auto.addDefault("No Auto", automode1.NONE);
		auto.addObject("breach defense B and D dead reckoned", new AutoDeadReconningBreachBAndDDefenses());
		auto.addObject("breach low bar dead reckoned", new AutoDeadReconningBreachLowBar());
		auto.addObject("breach low bar", new AutoBreachLowBar());
		auto.addObject("breach cheval", new AutoBreachCheval());
		auto.addObject("breach moat", new AutoBreachMoat());
		auto.addObject("breach ramparts", new AutoBreachRamparts());
		auto.addObject("breach rock wall", new AutoBreachRockWall());
		auto.addObject("breach rough terrain", new AutoBreachRoughTerrain());
		auto.addObject("spy box left face", new AutoSpyBotLeft());
		auto.addObject("Cheval position 2 shoot center face", new AutoChevalPosition2CenterFace());
		auto.addObject("Cheval position 4 shoot center face", new AutoChevalPosition4CenterFace());
		auto.addObject("Cheval position 5 shoot center face", new AutoChevalPosition5CenterFace());
		auto.addObject("Lowbar position 1 shoot center face", new AutoLowBarPosition1CenterFace());
		auto.addObject("Lowbar position 1 shoot left face", new AutoLowBarPosition1LeftFace());
		auto.addObject("Moat position 2 shoot center face", new AutoMoatPosition2CenterFace());
		auto.addObject("Moat position 2 shoot left face", new AutoMoatPosition2LeftFace());
		auto.addObject("Moat position 3 shoot center face", new AutoMoatPosition3CenterFace());
		auto.addObject("Moat position 4 shoot center face", new AutoMoatPosition4CenterFace());
		auto.addObject("Moat position 4 shoot right face", new AutoMoatPosition4RightFace());
		auto.addObject("Moat position 5 shoot center face", new AutoMoatPosition5CenterFace());
		auto.addObject("Moat position 5 shoot right face", new AutoMoatPosition5RightFace());
		auto.addObject("Ramparts position 2 shoot center face", new AutoRampartsPosition2CenterFace());
		auto.addObject("Ramparts position 2 shoot left face", new AutoRampartsPosition2LeftFace());
		auto.addObject("Ramparts position 3 shoot center face", new AutoRampartsPosition3CenterFace());
		auto.addObject("Ramparts position 4 shoot center face", new AutoRampartsPosition4CenterFace());
		auto.addObject("Ramparts position 4 shoot right face", new AutoRampartsPosition4RightFace());
		auto.addObject("Ramparts position 5 shoot center face", new AutoRampartsPosition5CenterFace());
		auto.addObject("Ramparts position 5 shoot right face", new AutoRampartsPosition5RightFace());
		auto.addObject("Rock Wall position 2 shoot center face", new AutoRockWallPosition2CenterFace());
		auto.addObject("Rock Wall position 2 shoot left face", new AutoRockWallPosition2LeftFace());
		auto.addObject("Rock Wall position 3 shoot center face", new AutoRockWallPosition3CenterFace());
		auto.addObject("Rock Wall position 4 shoot center face", new AutoRockWallPosition4CenterFace());
		auto.addObject("Rock Wall position 4 shoot right face", new AutoRockWallPosition4RightFace());
		auto.addObject("Rock Wall position 5 shoot center face", new AutoRockWallPosition5CenterFace());
		auto.addObject("Rock Wall position 5 shoot right face", new AutoRockWallPosition5RightFace());
		auto.addObject("Rough Terrain position 2 shoot center face", new AutoRoughTerrainPosition2CenterFace());
		auto.addObject("Rough Terrain position 2 shoot left face", new AutoRoughTerrainPosition2LeftFace());
		auto.addObject("Rough Terrain position 3 shoot center face", new AutoRoughTerrainPosition3CenterFace());
		auto.addObject("Rough Terrain position 4 shoot center face", new AutoRoughTerrainPosition4CenterFace());
		auto.addObject("Rough Terrain position 4 shoot right face", new AutoRoughTerrainPosition4RightFace());
		auto.addObject("Rough Terrain position 5 shoot center face", new AutoRoughTerrainPosition5CenterFace());
		auto.addObject("Rough Terrain position 5 shoot right face", new AutoRoughTerrainPosition5RightFace());
		auto.addObject("forward", new DriveStraightPID(.85, 180, 4000));
		auto.addObject("back", new DriveStraightPID(-.85, 180, 4000));

		auto.addObject("turn", 
		    	(new PIdNav2(20)));
		SmartDashboard.putData("auto mode", auto);
	}
	
	public void disabledInit() {
		try {
			/*auto1 = new SendableChooser();
			auto1.addDefault("No Auto", automode1.NONE);
			auto1.addObject("Breach Auto", automode1.BREACH);
			auto1.addObject("Shoot Auto", automode1.SHOOT);
			auto1.addObject("Breach and Shoot Auto", automode1.BREACHANDSHOOT);
			auto1.addObject("Test", automode1.TEST);
			SmartDashboard.putData("auto mode", auto1);*/
			rpiangle.start();
			rpidistance.start();
			printrpidata.start();
			navx.reset();
			autoChooserThingy();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void disabledPeriodic() {
		try {
			
			Scheduler.getInstance().run();
			/*AutoMode1 autoChoice = (AutoMode1) auto1.getSelected();
			auto2 = new SendableChooser();
			auto3 = new SendableChooser();
			auto4 = new SendableChooser();
				switch(autoChoice){
				default:
				break;
				case NONE:
					auto2.addDefault("Literally Nothing", null);
					auto3.addDefault("Literally Nothing", null);
					auto4.addDefault("Literally Nothing", null);
					SmartDashboard.putData("auto type 2", auto2);
					SmartDashboard.putData("auto3Str", auto3);
					SmartDashboard.putData("auto4Str", auto4);
				break;
				case BREACH:
					auto2.addDefault("Literally Nothing", null);
					auto3.addDefault("Literally Nothing", null);
					auto4.addDefault("breach low bar", new AutoBreachLowBar());
					auto4.addObject("breach moat", new AutoBreachMoat());
					auto4.addObject("breach ramparts", new AutoBreachRamparts());
					auto4.addObject("breach rock wall", new AutoBreachRockWall());
					auto4.addObject("breach rough terrain", new AutoBreachRoughTerrain());
					SmartDashboard.putData("auto type 2", auto2);
					SmartDashboard.putData("auto3Str", auto3);
					SmartDashboard.putData("auto4Str", auto4);
				break;
				case SHOOT:
					auto2.addDefault("Literally Nothing", null);
					auto3.addDefault("Literally Nothing", null);
					auto4.addDefault("spy box left face", new AutoSpyBotLeft());
					SmartDashboard.putData("auto type 2", auto2);
					SmartDashboard.putData("auto3Str", auto3);
					SmartDashboard.putData("auto4Str", auto4);
				break;
				case BREACHANDSHOOT:
					auto2.addDefault("breach low bar", automode2.LOWBAR);
					auto2.addObject("breach moat", automode2.MOAT);
					auto2.addObject("breach ramparts", automode2.RAMPARTS);
					auto2.addObject("breach rock wall", automode2.ROCKWALL);
					auto2.addObject("breach rough terrain", automode2.ROUGHTERRAIN);
					SmartDashboard.putData("auto type 2", auto2);
					AutoMode2 autoChoice2 = (AutoMode2) auto2.getSelected();
					switch(autoChoice2){
						case LOWBAR:
							autoLowBarBreachAndShoot();
						break;
						case MOAT:
							autoMoatBreachAndShoot();
						break;
						case RAMPARTS:
							autoRampartsBreachAndShoot();
						break;
						case ROCKWALL:
							autoRockWallBreachAndShoot();
						break;
						case ROUGHTERRAIN:
							autoRoughTerrainBreachAndShoot();
						break;
					}
					
					
				break;
				case TEST:
					auto2.addDefault("Literally Nothing", null);
					auto3.addDefault("Literally Nothing", null);
					auto4.addDefault("auto", new AutoLowBarPosition1CenterFace());
					auto4.addObject("straight", new DriveStraightPID(.95, 180, 2382));
					SmartDashboard.putData("auto type 2", auto2);
					SmartDashboard.putData("auto3Str", auto3);
					SmartDashboard.putData("auto4Str", auto4);
				break;
			}
			*/
			
		} catch (Exception e) {
			DriverStation.reportError(e.toString(), false);
		}
	}
	
	public void autoLowBarBreachAndShoot(){
		auto3.addDefault("position 1", automode3.POSITION1);
		SmartDashboard.putData("auto3Str", auto3);
		AutoMode3 autoChoice3 = (AutoMode3) auto3.getSelected();
		switch(autoChoice3){
			case POSITION1:
				auto4.addDefault("center face", new AutoLowBarPosition1CenterFace());
				auto4.addObject("left face", new AutoLowBarPosition1LeftFace());
			break;
		}
		SmartDashboard.putData("auto4Str", auto4);
	}
	
	public void autoMoatBreachAndShoot(){
		auto3.addDefault("position 2", automode3.POSITION2);
		auto3.addObject("position 3", automode3.POSITION3);
		auto3.addObject("position 4", automode3.POSITION4);
		auto3.addObject("position 5", automode3.POSITION5);
		SmartDashboard.putData("auto3Str", auto3);
		AutoMode3 autoChoice3 = (AutoMode3) auto3.getSelected();
		switch(autoChoice3){
			case POSITION2:
				auto4.addDefault("center face", new AutoMoatPosition2CenterFace());
				auto4.addObject("left face", new AutoMoatPosition2LeftFace());
			break;
			case POSITION3:
				auto4.addDefault("center face", new AutoMoatPosition3CenterFace());
				auto4.addObject("suprisingly, also center face", new AutoMoatPosition3CenterFace());
			break;
			case POSITION4:
				auto4.addDefault("center face", new AutoMoatPosition4CenterFace());
				auto4.addObject("right face", new AutoMoatPosition4RightFace());
				break;
			case POSITION5:
				auto4.addDefault("center face", new AutoMoatPosition5CenterFace());
				auto4.addObject("right face", new AutoMoatPosition5RightFace());
			break;
			}
		SmartDashboard.putData("auto4Str", auto4);
	}
	
	public void autoRampartsBreachAndShoot(){
		auto3.addDefault("position 2", automode3.POSITION2);
		auto3.addObject("position 3", automode3.POSITION3);
		auto3.addObject("position 4", automode3.POSITION4);
		auto3.addObject("position 5", automode3.POSITION5);
		SmartDashboard.putData("auto3Str", auto3);
		AutoMode3 autoChoice3 = (AutoMode3) auto3.getSelected();
		switch(autoChoice3){
		case POSITION2:
			auto4.addDefault("center face", new AutoRampartsPosition2CenterFace());
			auto4.addObject("left face", new AutoRampartsPosition2LeftFace());
		break;
		case POSITION3:
			auto4.addDefault("center face", new AutoRampartsPosition3CenterFace());
			auto4.addObject("suprisingly, also center face", new AutoRampartsPosition3CenterFace());
		break;
		case POSITION4:
			auto4.addDefault("center face", new AutoRampartsPosition4CenterFace());
			auto4.addObject("right face", new AutoRampartsPosition4RightFace());
			break;
		case POSITION5:
			auto4.addDefault("center face", new AutoRampartsPosition5CenterFace());
			auto4.addObject("right face", new AutoRampartsPosition5RightFace());
		break;
		}
		SmartDashboard.putData("auto4Str", auto4);
	}
	
	public void autoRockWallBreachAndShoot(){
		auto3.addDefault("position 2", automode3.POSITION2);
		auto3.addObject("position 3", automode3.POSITION3);
		auto3.addObject("position 4", automode3.POSITION4);
		auto3.addObject("position 5", automode3.POSITION5);
		SmartDashboard.putData("auto3Str", auto3);
		AutoMode3 autoChoice3 = (AutoMode3) auto3.getSelected();
		switch(autoChoice3){
		case POSITION2:
			auto4.addDefault("center face", new AutoRockWallPosition2CenterFace());
			auto4.addObject("left face", new AutoRockWallPosition2LeftFace());
		break;
		case POSITION3:
			auto4.addDefault("center face", new AutoRockWallPosition3CenterFace());
			auto4.addObject("suprisingly, also center face", new AutoRockWallPosition3CenterFace());
		break;
		case POSITION4:
			auto4.addDefault("center face", new AutoRockWallPosition4CenterFace());
			auto4.addObject("right face", new AutoRockWallPosition4RightFace());
			break;
		case POSITION5:
			auto4.addDefault("center face", new AutoRockWallPosition5CenterFace());
			auto4.addObject("right face", new AutoRockWallPosition5RightFace());
		break;
		}
		SmartDashboard.putData("auto4Str", auto4);
	}
	
	public void autoRoughTerrainBreachAndShoot(){
		auto3.addDefault("position 2", automode3.POSITION2);
		auto3.addObject("position 3", automode3.POSITION3);
		auto3.addObject("position 4", automode3.POSITION4);
		auto3.addObject("position 5", automode3.POSITION5);
		SmartDashboard.putData("auto3Str", auto3);
		AutoMode3 autoChoice3 = (AutoMode3) auto3.getSelected();
		switch(autoChoice3){
		case POSITION2:
			auto4.addDefault("center face", new AutoRoughTerrainPosition2CenterFace());
			auto4.addDefault("left face", new AutoRoughTerrainPosition2LeftFace());
		break;
		case POSITION3:
			auto4.addDefault("center face", new AutoRoughTerrainPosition3CenterFace());
			auto4.addObject("suprisingly, also center face", new AutoRoughTerrainPosition3CenterFace());
		break;
		case POSITION4:
			auto4.addDefault("center face", new AutoRoughTerrainPosition4CenterFace());
			auto4.addDefault("right face", new AutoRoughTerrainPosition4RightFace());
			break;
		case POSITION5:
			auto4.addDefault("center face", new AutoRoughTerrainPosition5CenterFace());
			auto4.addDefault("right face", new AutoRoughTerrainPosition5RightFace());
		break;
		}
		SmartDashboard.putData("auto4Str", auto4);
	}
	
	public void autonomousInit() {
		try{
			resetencs.start();
			navx.reset();
			rpiangle.start();
			getencs.start();
			if(auto.getSelected() != null){/* && autoCommand instanceof Command){*/// might work, 50-50
				autoCommand = (Command)auto.getSelected();
			}
			//autoCommand = new DriveStraightPID(.95, 180, 4000);
			
			//autoCommand = new AutoDeadReconningreachLowBar();
			//autoCommand = new AutoLowBarPosition1LeftFace();
			//autoCommand = new AutoRampartsPosition2CenterFace();
			//autoCommand = new AutoChevalPosition4CenterFace();
			if(autoCommand != null){
				autoCommand.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void autonomousPeriodic() {
		try{
			Scheduler.getInstance().run();
			RobotMap.cam.getImage(frame);
			CameraServer.getInstance().setImage(frame);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		try{
			if (autoCommand != null) {
				autoCommand.cancel();
			}
			RobotMap.dangerZone = false;
			resetencs.start();
			navx.reset();
			rpiangle.start();
			rpidistance.start();
			printrpidata.start();
			getencs.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void teleopPeriodic() {
		try {
			Scheduler.getInstance().run();
		} catch (Exception e) {
			System.out.println(e);
		}
		try{
			pressurize.start();
			printnavxangle.start();
			getencs.start();
			printrpidata.start();
			getcatapultposition.start();
		} catch (Exception e) {
			System.out.println(e);
		}
		try{
			RobotMap.cam.getImage(frame);
			CameraServer.getInstance().setQuality(100);
			CameraServer.getInstance().setImage(frame);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void testPeriodic() {
		try{
			LiveWindow.run();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}