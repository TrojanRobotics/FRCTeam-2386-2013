package com.BCHS;

import com.BCHS.Camera.Direction;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

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
	Camera cam;
	ParticleAnalysisReport[] particles;
	int[] RGBThreshold = {202, 255, 86, 207, 0, 255};
	

	public void robotInit() {
		wheelyBar = true;
		changeMode = true;
		setOnce = false;
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		mainJoystick = new Joystick(Config.MAIN_JOYSTICK);
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1]);
		//retrieval = new Retrieval(Config.RETRIEVAL_CHANNEL);
		shooter = new Shooter(1, Config.SENCODER[0], Config.SENCODER[1]);
		ds = DriverStationLCD.getInstance();

		cam = new Camera();
		
		Kp = Config.PID[0];
		Ki = Config.PID[1];
		Kd = Config.PID[2];
		System.out.println("left DPP: " + Config.LE_DPP);
		System.out.println("right DPP: " + Config.RE_DPP);
		printData();

	}

	public void disabledPeriodic() {
		chasis.stop();
		chasis.reset();

		//Kp = SmartDashboard.getNumber("P", Kp);
		//Ki = SmartDashboard.getNumber("I", Ki);
		//Kd = SmartDashboard.getNumber("D", Kd);

		setOnce = false;
		Lib.clearLCD(ds);
	}

	public void teleopinit() {
	}

	public void autonomousPeriodic() {
		if (!setOnce) {
			chasis.changeMode(Chasis.RobotMode.driveMode);
			chasis.leftEncoder.setReverseDirection(true);
			//chasis.leftSidePID.enable();
			//c/hasis.leftSidePID.setSetpoint(10.0);
			System.out.println("zomg");
			
			chasis.enable();
			//chasis.leftSidePID.setPID(Kp, Ki, Kd);
			//chasis.rightSidePID.setPID(Kp, Ki, Kd);
			chasis.setSetpoint(-24.0);
			setOnce = true;
			
			//Everything under this line is my attempt at autonomous.  I don't know how to work the camera, so ya.
			
		}

		if (secondaryJoystick.getRawButton(7)) 
		{
			ParticleAnalysisReport[] orderedParticles;
			particles = cam.getLargestParticle(RGBThreshold);
			if (particles != null && particles.length > 0) {
					System.out.println("Amount of particles:" + particles.length);
					System.out.println("The largest particle's center x mass:" + particles[0].center_mass_x);
					System.out.println("The largest particle's center y mass:" + particles[0].center_mass_y);


					Direction nextDirectionX = cam.leftOrRight(particles[0]);
					Direction nextDirectionY = cam.upOrDown(particles[0]);
			
					this.centerWithCamX(nextDirectionX, 2);
					this.centerWithCamY(nextDirectionY, 2);

			} else {
				System.out.println("There are no particles on the screen of the desired type.");
			}
		} 
		else {
			shooter.setTableNeutral();
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
		
		throttleValue = mainJoystick.getRawAxis(3);
		throttleValue = Lib.fixThrottle(throttleValue);
		throttleValue = Lib.round(throttleValue, 2);
		
		if (secondaryJoystick.getTrigger()) {
			shooter.set(-0.75);
		} else {
			shooter.set(0.0);
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
				chasis.rightSide.set(throttleValue);
			} else if (mainJoystick.getRawButton(11)) {
				chasis.rightSide.set(-throttleValue);
			} else {
				chasis.rightSide.set(0.0);
			}
			if (mainJoystick.getRawButton(6)) {
				chasis.leftSide.set(throttleValue);
			} else if (mainJoystick.getRawButton(7)) {
				chasis.leftSide.set(-throttleValue);
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


		//System.out.println("right side: " + chasis.rightEncoder.getDistance());

		if (!chasis.compressor.getPressureSwitchValue()) {
			chasis.compressor.start();
		} else {
			chasis.compressor.stop();
		}
	}

	public void testPeriodic() {
	}

	public void centerWithCamX(Direction direction, int mode) {
		if (direction == Direction.right) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "left  ");
			if (mode == 1) {
				chasis.rightSide.set(-0.3);
				chasis.leftSide.set(0);
			}

		} else if (direction == Direction.left) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "right  ");
			if (mode == 1) {
				chasis.leftSide.set(0.3);
				chasis.rightSide.set(0);
			}

		} else if (direction == Direction.center) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "center");
			if (mode == 1) {
				chasis.leftSide.set(0);
				chasis.rightSide.set(0); 
			}
		} 
		ds.updateLCD();
	}

	private void centerWithCamY(Direction nextDirectionY, int mode) {
		if (nextDirectionY == Direction.up) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "up  ");
			if (mode == 1) {
				shooter.setTableForwards();
			}

		} else if (nextDirectionY == Direction.down) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "down  ");
			if (mode == 1) {
				shooter.setTableReverse();
			}

		} else if (nextDirectionY == Direction.center) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "center");
			if (mode == 1) {
				shooter.setTableNeutral();
			}
		} 
		ds.updateLCD();
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
