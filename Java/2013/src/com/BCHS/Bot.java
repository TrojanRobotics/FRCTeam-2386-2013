package com.BCHS;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStationLCD;

public class Bot extends IterativeRobot {

	Joystick mainJoystick, secondaryJoystick;
	Shooter shooter;
	Chasis chasis;
	Retrieval retrieval;
	double x, y, y2; // x and y values for joysticks
	double Kp, Ki, Kd;
	boolean setOnce, changeMode, wheelyBar;
	double throttleValue;
	DriverStationLCD ds;

	public void robotInit() {
		wheelyBar = true;
		changeMode = true;
		setOnce = false;
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		mainJoystick = new Joystick(Config.MAIN_JOYSTICK);
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1]);
		//retrieval = new Retrieval(Config.RETRIEVAL_CHANNEL);
		shooter = new Shooter(2, Config.SENCODER[0], Config.SENCODER[1]);
		ds = DriverStationLCD.getInstance();
		
		Kp = Config.PID[0];
		Ki = Config.PID[1];
		Kd = Config.PID[2];
		
		printData();

	}

	public void disabledPeriodic() {
		chasis.stop();
		chasis.leftEncoder.reset();
		chasis.rightEncoder.reset();

		Kp = SmartDashboard.getNumber("P", Kp);
		Ki = SmartDashboard.getNumber("I", Ki);
		Kd = SmartDashboard.getNumber("D", Kd);
		
		setOnce = true;
	}

	public void teleopinit() {
	}

	public void autonomousPeriodic() {
		if (!setOnce) {
			chasis.leftSidePID.setPID(Kp, Ki, Kd);
			chasis.rightSidePID.setPID(Kp, Ki, Kd);
			chasis.leftSidePID.setSetpoint(12.0);
			chasis.rightSidePID.setSetpoint(-12.0);
			setOnce = true;
		}
		printData();
	}

	public void teleopPeriodic() {
		x = mainJoystick.getX();
		y = mainJoystick.getY();
		y2 = secondaryJoystick.getY();

		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		y2 = Lib.signSquare(y2);
		y2 = Lib.limitOutput(y2);
		/*
		 if (secondaryJoystick.getTrigger()) {
		 shooter.set(1.0);
		 }else {
		 shooter.set(0.0);
		 }*/

		if (secondaryJoystick.getRawButton(7)) {
			shooter.setTableForwards();
		} else if (secondaryJoystick.getRawButton(6)) {
			shooter.setTableReverse();
		} else {
			shooter.setTableNeutral();
		}
		/*
		 if (secondaryJoystick.getRawButton(2)) {
		 retrieval.pushOut();
		 } else { 
		 retrieval.pullIn();
		 }
		 */
		if (mainJoystick.getRawButton(9)) {
			chasis.changeMode(Chasis.RobotMode.climbMode);
		}
		
		if (mainJoystick.getRawButton(8)) {
			chasis.changeMode(Chasis.RobotMode.driveMode);
		}

		if (chasis.getMode() == Chasis.RobotMode.climbMode) {
			if (mainJoystick.getRawButton(10)) {
				chasis.rightSide.set(0.2);
			} else if (mainJoystick.getRawButton(11)) {
				chasis.rightSide.set(-0.2);
			} else {
				chasis.rightSide.set(0.0);
			}
			if (mainJoystick.getRawButton(6)) {
				chasis.leftSide.set(0.2);
			} else if (mainJoystick.getRawButton(7)) {
				chasis.leftSide.set(-0.2);
			} else {
				chasis.leftSide.set(0.0);
			}
		} else {
			chasis.leftSide.set(Lib.limitOutput(y - x));
			chasis.rightSide.set(-Lib.limitOutput(y + x));
		}


		if (secondaryJoystick.getRawButton(8)) {
			chasis.setWheelyOn();
		} 
		
		if (secondaryJoystick.getRawButton(9)) {
			chasis.setWheelyOff();
		}
		/*
			
		 /*
		 if (mainJoystick.getRawButton(9)) {
		 chasis.driveSolenoid.set(true);
		 chasis.leftSide.set(Lib.limitOutput(y - x));
		 chasis.rightSide.set(-Lib.limitOutput(y + x));
		 } else {
		 chasis.driveSolenoid.set(false);
		 }
			
		 /*if (mainJoystick.getRawButton(11)) {
		 chasis.climbSolenoid.set(true);
		 chasis.leftSide.set(Lib.limitOutput(y));
		 chasis.rightSide.set(-Lib.limitOutput(y2));
		 } else {
		 chasis.climbSolenoid.set(false);
		 }
		 */

		throttleValue = mainJoystick.getThrottle();
		//System.out.println(throttleValue);
		Lib.fixThrottle(throttleValue);
		//System.out.println(throttleValue);		


		/*
		 * this if block in unfinished 
		 * works the left and right hockey sticks when buttons are pressed.
		 */
		/*
		 if (mainJoystick.getRawButton(8)){
		 chasis.changeMode(Chasis.RobotMode.climbMode);
				
		 } else if (mainJoystick.getRawButton(9)) {
		 chasis.changeMode(Chasis.RobotMode.driveMode);
		 chasis.leftSide.set(Lib.limitOutput(y - x));
		 chasis.rightSide.set(-Lib.limitOutput(y + x));	
		 }

            
		 if (mainJoystick.getRawButton(4)) {
		 shooter.setTableForward();
		 } else if (mainJoystick.getRawButton(5)) {
		 shooter.setTableReverse();
		 } else {
		 shooter.setTableNeutral();
		 }
            
		 if (secondaryJoystick.getRawButton(8)) {
		 climber.setWheelyBar(true);
		 } else {
		 climber.setWheelyBar(false);
		 }
		 */
		ds.println(DriverStationLCD.Line.kUser1, 1, String.valueOf(chasis.leftEncoder.getDistance()));
		ds.println(DriverStationLCD.Line.kUser2, 1, String.valueOf(chasis.rightEncoder.getDistance()));
		ds.updateLCD();
		
		System.out.println("left side: " + chasis.leftEncoder.getDistance());
		//System.out.println("right side: " + chasis.rightEncoder.getDistance());

		if (!chasis.compressor.getPressureSwitchValue()) {
			chasis.compressor.start();
		} else {
			chasis.compressor.stop();
		}
	}

	public void testPeriodic() {
	}

	public void printData() {
		SmartDashboard.putNumber("leftside", (chasis.leftEncoder.getRate()) * -1);
		SmartDashboard.putNumber("rightside", chasis.rightEncoder.getRate());

		SmartDashboard.putNumber("leftsideD", (chasis.leftEncoder.getDistance()));
		SmartDashboard.putNumber("rightsideD", chasis.rightEncoder.getDistance());

		SmartDashboard.putNumber("P", Kp);
		SmartDashboard.putNumber("I", Ki);
		SmartDashboard.putNumber("D", Kd);
	}
}
