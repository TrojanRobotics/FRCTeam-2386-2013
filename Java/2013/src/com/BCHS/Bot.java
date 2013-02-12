package com.BCHS;

import com.BCHS.misc.XboxController;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Bot extends IterativeRobot
{

	Joystick mainJoystick, secondaryJoystick;
	XboxController controller;
	//Shooter shooter;
	Chasis chasis;
	//Retrieval retrieval;
	//Climber climber;
	
	boolean joystick = true; //true = joystick, false = xbox controller
	double x, y, y2; // x and y values for joysticks/controller
	double Kp, Ki, Kd;
	boolean setOnce;
	
	public void robotInit()
	{
		setOnce = false;
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		
		if (joystick) {
			mainJoystick = new Joystick(Config.MAIN_JOYSTICK);
		} else {
			controller = new XboxController(Config.MADCATZ_JOYSTICK);
		}
		
        mainJoystick = new Joystick(Config.MADCATZ_JOYSTICK);
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.LDRIVE, Config.RDRIVE);
        //retrieval = new Retrieval(Config.RETRIEVAL_CHANNEL);
		//climber = new Climber(Config.CLIMBER_CHANNEL);
		

		printData();
		
	}
	
	public void disabledPeriodic()
	{
		chasis.stop();
		chasis.leftEncoder.reset();
		chasis.rightEncoder.reset();
		
		Kp = SmartDashboard.getNumber("P", Kp);
		Ki = SmartDashboard.getNumber("I", Ki);
		Kd = SmartDashboard.getNumber("D", Kd);

	}
	
	public void teleopinit()
	{
		
	}
	
	public void autonomousPeriodic()
	{
		if (!setOnce) {
			chasis.leftSidePID.setPID(Kp, Ki, Kd);
			chasis.rightSidePID.setPID(Kp, Ki, Kd);
			chasis.leftSidePID.setSetpoint(24.0);
			chasis.rightSidePID.setSetpoint(-24.0);
			setOnce = true;
		}
		printData();
	}
	
	public void teleopPeriodic()
	{
		
		if (joystick) {
			x = mainJoystick.getX();
			y = mainJoystick.getY();
            y2 = secondaryJoystick.getY();
		} else {
			x = controller.getX(GenericHID.Hand.kLeft);
			y = controller.getY(GenericHID.Hand.kLeft);
		}
				
		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
        y2 = Lib.signSquare(y2);
		
		
		/*
		if (secondaryJoystick.getTrigger()) {
			shooter.set(1.0);
        }else if (secondaryJoystick.getRawButton(4)) {
			shooter.set(0.50);
        }else {
			shooter.set(0.0);
        }
        
		if (secondaryJoystick.getRawButton(2)) {
            retrieval.pushOut();
        } else { 
            retrieval.pullIn();
        }
        */
		
		if (joystick) {
			
			if (mainJoystick.getRawButton(6)) {
				chasis.compressor.setRelayValue(Relay.Value.kOn);
            } else {
				chasis.compressor.setRelayValue(Relay.Value.kOff);
            }
            
			if (mainJoystick.getRawButton(10)) {
				chasis.driveSolenoid.set(true);
                chasis.leftSide.set(Lib.limitOutput(y - x));
                chasis.rightSide.set(-Lib.limitOutput(y + x));
            } else {
				chasis.driveSolenoid.set(false);
            }
            
			if (mainJoystick.getRawButton(11)) {
				chasis.climbSolenoid.set(true);
                chasis.leftSide.set(Lib.limitOutput(y));
                chasis.rightSide.set(-Lib.limitOutput(y2));
            } else {
				chasis.climbSolenoid.set(false);
            }
            /*
			if (mainJoystick.getRawButton(9)) {
				shooter.setTableForward();
            } else if (mainJoystick.getRawButton(8)) {
				shooter.setTableReverse();
            } else {
				shooter.setTableNeutral();
            }
            
			if (mainJoystick.getRawButton(4)) {
				climber.setWheelyBar(true);
            } else {
				climber.setWheelyBar(false);
            }
            */
		} else {
			if (controller.getRawButton(XboxController.XboxButtons.kAButton)) {
				chasis.compressor.setRelayValue(Relay.Value.kOn);
            } else {
				chasis.compressor.setRelayValue(Relay.Value.kOff);
            }
			
			if (controller.getRawButton(XboxController.XboxButtons.kRBButton)) {
				chasis.driveSolenoid.set(true);
            } else {
				chasis.driveSolenoid.set(false);
            }
            
			if (controller.getRawButton(XboxController.XboxButtons.kLBButton)) {
				chasis.climbSolenoid.set(true);
            } else {
				chasis.climbSolenoid.set(false);
            }
			/*
			if (controller.getRawButton(XboxController.XboxButtons.kBButton)) {
				shooter.setTableForward();
            } else if (controller.getRawButton(XboxController.XboxButtons.kXButton)) {
				shooter.setTableReverse();
            } else {
				shooter.setTableNeutral();
            }
			
			if (controller.getRawButton(XboxController.XboxButtons.kYButton)) {
				climber.setWheelyBar(true);
            } else {
				climber.setWheelyBar(false);
            }
            */
		}
		
        if (!chasis.compressor.getPressureSwitchValue()) {
			chasis.compressor.start();
        } else {
			chasis.compressor.stop();    
        }
	}

	public void testPeriodic()
	{
		
	}
	
	public void printData()
	{
		SmartDashboard.putNumber("leftside",(chasis.leftEncoder.getRate()) * -1);
		SmartDashboard.putNumber("rightside",chasis.rightEncoder.getRate());
		
		SmartDashboard.putNumber("leftsideD",(chasis.leftEncoder.getDistance()));
		SmartDashboard.putNumber("rightsideD",chasis.rightEncoder.getDistance());
		
		SmartDashboard.putNumber("P", Kp);
		SmartDashboard.putNumber("I", Ki);
		SmartDashboard.putNumber("D", Kd);
	}
}
