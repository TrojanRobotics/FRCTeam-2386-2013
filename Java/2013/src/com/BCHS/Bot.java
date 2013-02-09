package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Bot extends IterativeRobot
{

	Joystick mainJoystick, secondaryJoystick;
	XboxController controller;

	Shooter shooter;
	Chasis chasis;
	Retrieval retrieval;
	Solenoid solenoid;
	Compressor compressorx;
	
	boolean joystick = true; //true = joystick, false = xbox controller
	double x, y; // x and y values for joysticks/controller
	
	public void robotInit()
	{
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		
		if (joystick) {
			mainJoystick = new Joystick(Config.MAIN_JOYSTICK);
		} else {
			controller = new XboxController(Config.MADCATZ_JOYSTICK);
		}
		
        mainJoystick = new Joystick(Config.MADCATZ_JOYSTICK);
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		compressorx = new Compressor(Config.PNEUMATICS[0], Config.PNEUMATICS[1], Config.PNEUMATICS[2], Config.PNEUMATICS[3]);
		//Controller = new XboxController(1);
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE, Config.SOLENOID_CHANNEL);
        retrieval = new Retrieval(2);
		solenoid = new Solenoid();
	}
	
	public void disabledPeriodic()
	{
		chasis.stop();

	}
	
	public void teleopinit()
	{
		chasis.stop();
	}
	
	public void autonomousPeriodic()
	{
		
	}
	
	public void teleopPeriodic()
	{
		
		if (joystick) {
			x = mainJoystick.getX();
			y = mainJoystick.getY();
		} else {
			x = controller.getX(GenericHID.Hand.kLeft);
			y = controller.getY(GenericHID.Hand.kLeft);
		}
				
		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		
		chasis.leftSide.set(Lib.limitOutput(y - x));
		chasis.rightSide.set(-Lib.limitOutput(y + x));
		
		if (secondaryJoystick.getTrigger())
			shooter.set(1.0);
		else if (secondaryJoystick.getRawButton(4))
			shooter.set(0.50);
		else 
			shooter.set(0.0);
        
		if (secondaryJoystick.getRawButton(2))
            retrieval.pushOut();
        else if (secondaryJoystick.getRawButton(3))
            retrieval.pullIn();
        else
            retrieval.Still();
		
		if (mainJoystick.getRawButton(6))
			solenoid.set(true);
		else
			solenoid.set(false);
                
        if (compressorx.getPressureSwitchValue(true)) 

			compressorx.start();
        else
			compressorx.stop();                         
	}

	public void testPeriodic()
	{
		
	}
}
