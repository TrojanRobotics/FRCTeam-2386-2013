package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.IterativeRobot;


public class Bot extends IterativeRobot
{
	Chasis chasis;
	XboxController Controller;
	
	public void robotInit()
	{
		Controller = new XboxController(1);
		
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE);
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
		double x = Controller.getX();
		double y = Controller.getY();
		
		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		
		/*Chasis.leftSide.set*/ x = Lib.limitOutput(y - x);
		/*Chasis.rightSide.set*/ y = -Lib.limitOutput(y + x);
			
		System.out.println(x);
		System.out.println(y);
	}
	
	public void testPeriodic()
	{
		
	}
}
