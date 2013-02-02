package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Bot extends IterativeRobot
{
	Chasis chasis;
	Joystick madcatz, secondary;
	XboxController Controller;
	
	
	public void robotInit()
	{
		madcatz = new Joystick(Config.Madcatz_JOYSTICK);
		//secondary = new Joystick(Config.SECONDARY_JOYSTICK);
		
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
		double x = Controller.getX(GenericHID.Hand.kLeft);
		double y = Controller.getY(GenericHID.Hand.kLeft);
		
		x = Lib.round(x, 4);
		y =Lib.round(y, 4);
				
		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		
		chasis.leftSide.set(Lib.limitOutput(y - x));
		chasis.rightSide.set(-Lib.limitOutput(y + x));
			
		System.out.println(x);
		System.out.println(y);
	}
	
	public void testPeriodic()
	{
		
	}
}
