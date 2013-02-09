package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.*;


public class Bot extends IterativeRobot
{

	Joystick mainJoystick, secondaryJoystick;
	XboxController controller;

	Shooter shooter;
	Chasis chasis;
	Retrieval retrieval;
	
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
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE, Config.SOLENOID_CHANNEL[0], Config.SOLENOID_CHANNEL[1]);
        retrieval = new Retrieval(Config.RETRIEVAL_CHANNEL);
		
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
		
		chasis.driveSolenoid.set(true);
		
		if (secondaryJoystick.getTrigger())
			shooter.set(1.0);
		else if (secondaryJoystick.getRawButton(4))
			shooter.set(0.50);
		else 
			shooter.set(0.0);
        
		if (secondaryJoystick.getRawButton(2))
            retrieval.pushOut();
        else 
            retrieval.pullIn();
        
		
		if (joystick) {
			
			if (mainJoystick.getRawButton(6))
				chasis.compressor.setRelayValue(Relay.Value.kOn);
			else
				chasis.compressor.setRelayValue(Relay.Value.kOff);
			
			if (mainJoystick.getRawButton(10))
				chasis.driveSolenoid.set(true);
			else
				chasis.driveSolenoid.set(false);
			
			if (mainJoystick.getRawButton(11))
				chasis.climbSolenoid.set(true);
			else
				chasis.climbSolenoid.set(false);
			
		} else {
			
			if (controller.getRawButton(XboxController.XboxButtons.kAButton))
				chasis.compressor.setRelayValue(Relay.Value.kOn);
			else
				chasis.compressor.setRelayValue(Relay.Value.kOff);
			
			if (controller.getRawButton(XboxController.XboxButtons.kRBButton))
				chasis.driveSolenoid.set(true);
			else
				chasis.driveSolenoid.set(false);
			
			if (controller.getRawButton(XboxController.XboxButtons.kLBButton))
				chasis.climbSolenoid.set(true);
			else
				chasis.climbSolenoid.set(false);
		}
                
        if (chasis.compressor.getPressureSwitchValue()) 
			chasis.compressor.start();
        else
			chasis.compressor.stop();                         
	}

	public void testPeriodic()
	{
		
	}
}
