package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Bot extends IterativeRobot
{
	Joystick madcatz, secondary;
	XboxController Controller;
	Shooter shooter;
	Chasis chasis;
    Retrieval retrieval;
	Solenoid solenoid;
	
	public void robotInit()
	{
		madcatz = new Joystick(Config.Madcatz_JOYSTICK);
		secondary = new Joystick(Config.SECONDARY_JOYSTICK);
		
		Controller = new XboxController(1);
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE);
        retrieval = new Retrieval(2);
		solenoid = new Solenoid();
	}
	
	public void disabledPeriodic()
	{
		chasis.stop();
        retrieval.Limit();
	}
	
	public void teleopinit()
	{
		chasis.stop();
        retrieval.Limit();
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
		
		
		if (secondary.getTrigger())
			shooter.set(1.0);
		else if (secondary.getRawButton(4))
			shooter.set(0.50);
		else 
			shooter.set(0.0);
        
		if (secondary.getRawButton(2))
            retrieval.pushOut();
        else if (secondary.getRawButton(3))
            retrieval.pullIn();
        else
            retrieval.Still();
		
		if (madcatz.getRawButton(6))
			solenoid.set(true);
		else
			solenoid.set(false);
		
         
    }
	
	public void testPeriodic()
	{
		
	}

	 
}
