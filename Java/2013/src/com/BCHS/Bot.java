package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;


public class Bot extends IterativeRobot
{
	
	XboxController Controller;
	
	public void robotInit()
	{
		Controller = new XboxController(1);
	}
	
	public void disabledPeriodic()
	{
		
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
		
		Chasis.leftSide.set(Lib.limitOutput(y - x));
		Chasis.rightSide.set(-Lib.limitOutput(y + x));
			
		System.out.println(x);
		System.out.println(y);
	}
	
	public void testPeriodic()
	{
		
	}
}
