package com.BCHS;

import com.BCHS.Camera.Direction;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.Timer;

public class Bot extends IterativeRobot {

	Joystick mainJoystick, secondaryJoystick;
	Shooter shooter;
	Chasis chasis;
	Retrieval retrieval;
	double x, y, secondaryY; // x and y values for joysticks
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
		retrieval = new Retrieval(Config.RETRIEVAL_CHANNEL);
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

			//chasis.leftSidePID.setPID(Kp, Ki, Kd);
			//chasis.rightSidePID.setPID(Kp, Ki, Kd);
			//Everything under this line is my attempt at autonomous.  I don't know how to work the camera, so ya. By: Laxman

			//autonomous start at top left, aims and shoots
		/*
			chasis.enable();
			chasis.leftSidePID.setSetpoint(2.0);
			chasis.rightSidePID.setSetpoint(2.0);
			shooter.setTableReverse();
			Timer.delay(1.5);
			shooter.setTableNeutral();
			shooter.set(-0.75);
			Timer.delay(0.5);
			retrieval.pushOut();
			Timer.delay(1.0);
			retrieval.pullIn();
			*/
			
			setOnce = true;
		}
		
		//TODO:
		//GO UP FIRST
		//THEN WHEN CENTERED
		//FIRE THE CANNON
		
		ParticleAnalysisReport[] orderedParticles;
				particles = cam.getLargestParticle(RGBThreshold);
				
				if (particles != null && particles.length > 0) {
					System.out.println("Amount of particles:" + particles.length);
					System.out.println("The largest particle's center x mass:" + particles[0].center_mass_x);
					System.out.println("The largest particle's center y mass:" + particles[0].center_mass_y);

					Direction nextDirectionX = cam.leftOrRight(particles[0]);
					Direction nextDirectionY = cam.upOrDown(particles[0]);

					this.centerWithCamX(nextDirectionX, 2);
					this.centerWithCamY(nextDirectionY, 1);

				} else {
					shooter.setTableReverse();
					System.out.println("Can't find the square, must go up!");
				}

		printData();
	}

	public void teleopPeriodic() {

		x = mainJoystick.getX();
		y = mainJoystick.getY();
		secondaryY = secondaryJoystick.getY();

		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		secondaryY = Lib.signSquare(secondaryY);

		throttleValue = secondaryJoystick.getRawAxis(3);
		throttleValue = Lib.fixThrottle(throttleValue);
		throttleValue = Lib.round(throttleValue, 2);

		if (mainJoystick.getRawButton(9)) {
			chasis.changeMode(Chasis.RobotMode.climbMode);
		}

		if (mainJoystick.getRawButton(8)) {
			chasis.changeMode(Chasis.RobotMode.driveMode);
		}

		if (secondaryJoystick.getRawButton(9)) {
			chasis.setWheelyOn();
		}

		if (secondaryJoystick.getRawButton(8)) {
			chasis.setWheelyOff();
		}

		if (!chasis.compressor.getPressureSwitchValue()) {
			chasis.compressor.start();
		} else {
			chasis.compressor.stop();
		}


		if (chasis.getMode() == Chasis.RobotMode.climbMode) {

			if (secondaryJoystick.getRawButton(10)) {
				chasis.rightSide.set(throttleValue);
			} else if (secondaryJoystick.getRawButton(11)) {
				chasis.rightSide.set(-throttleValue);
			} else {
				chasis.rightSide.set(0.0);
			}

			if (secondaryJoystick.getRawButton(6)) {
				chasis.leftSide.set(throttleValue);
			} else if (secondaryJoystick.getRawButton(7)) {
				chasis.leftSide.set(-throttleValue);
			} else {
				chasis.leftSide.set(0.0);
			}

			if (mainJoystick.getRawButton(3)) {
				retrieval.pushOut();
			} else {
				retrieval.pullIn();
			}

			if (mainJoystick.getTrigger()) {
				shooter.set(-0.75);
			} else {
				shooter.set(0.0);
			}

			if (y > 0.5) {
				shooter.setTableForwards();
			} else if (y < -0.5) {
				shooter.setTableReverse();
			} else {
				shooter.setTableNeutral();
			}

		} else {
			chasis.leftSide.set(Lib.limitOutput(y - x));
			chasis.rightSide.set(-Lib.limitOutput(y + x));

			if (secondaryJoystick.getTrigger()) {
				shooter.set(-0.75);
			} else {
				shooter.set(0.0);
			}

			if (secondaryJoystick.getRawButton(2)) {
				retrieval.pushOut();
			} else {
				retrieval.pullIn();
			}

			if (secondaryJoystick.getRawButton(4))  {
				ParticleAnalysisReport[] orderedParticles;
				particles = cam.getLargestParticle(RGBThreshold);
				
				if (particles != null && particles.length > 0) {
					System.out.println("Amount of particles:" + particles.length);
					System.out.println("The largest particle's center x mass:" + particles[0].center_mass_x);
					System.out.println("The largest particle's center y mass:" + particles[0].center_mass_y);

					Direction nextDirectionX = cam.leftOrRight(particles[0]);
					Direction nextDirectionY = cam.upOrDown(particles[0]);

					this.centerWithCamX(nextDirectionX, 2);
					this.centerWithCamY(nextDirectionY, 1);

				} else {
					System.out.println("There are no particles on the screen of the desired type.");
				}
			} else {
				if (secondaryY > 0.5) {
					System.out.println("secondaryY greater " + String.valueOf(secondaryY));
					shooter.setTableForwards();
				} else if (secondaryY < -0.5) {
					System.out.println("secondaryY lesser " + String.valueOf(secondaryY));
					shooter.setTableReverse();
				} else {
					System.out.println("secondaryY equal " + String.valueOf(secondaryY));
					shooter.setTableNeutral();
				}
			}
		}

		ds.println(DriverStationLCD.Line.kUser1, 1, String.valueOf(chasis.leftEncoder.getDistance()));
		ds.println(DriverStationLCD.Line.kUser2, 1, String.valueOf(chasis.rightEncoder.getDistance()));
		ds.updateLCD();
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
			ds.println(DriverStationLCD.Line.kUser1, 4, "up  ");
			if (mode == 1) {
				shooter.setTableReverse();
			}

		} else if (nextDirectionY == Direction.down) {
			ds.println(DriverStationLCD.Line.kUser1, 4, "down  ");
			if (mode == 1) {
				shooter.setTableForwards();
			}

		} else if (nextDirectionY == Direction.center) {
			ds.println(DriverStationLCD.Line.kUser1, 4, "center");
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