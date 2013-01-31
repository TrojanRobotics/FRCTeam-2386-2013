package com.BCHS;

import com.BCHS.misc.XboxController;
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
		system.out.println("");
	}
	
	public void testPeriodic()
	{
		
	}
}
