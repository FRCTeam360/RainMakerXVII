package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoLowBarShoot extends CommandGroup {
    
    public  AutoLowBarShoot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new DriveEncs(200, .5));
    	addSequential(new IntakeArmDown());
    	addSequential(new DriveEncs(1600, .5));
    	addSequential(new resetEnc());
    	addSequential(new PIdNav(65));
    	addSequential(new WaitCommand(.25));
    	addSequential(new DriveEncs(1800, .5));
    	addSequential(new PIdNav(1));
    	addSequential(new Shoot());
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}

