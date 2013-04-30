package com.BCHS;

import com.BCHS.Camera.Direction;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.Timer;

public class Bot extends IterativeRobot {

	Joystick mainJoystick, secondaryJoystick;
	Shooter shooter;
	Chasis chasis;
	Retrieval retrieval;
	Relay relay;
	Victor victor;
	Jaguar jaguar;
	double x, y, secondaryY; // x and y values for joysticks
	double launchSpeed;
	double Kp, Ki, Kd, sKp, sKi, sKd;
	boolean setOnce, changeMode, wheelyBar, isDone, shoot;
	double throttleValue;
	DriverStation ds;
	DriverStationLCD dsLCD;
	//Camera cam;
	ParticleAnalysisReport[] particles;
	int[] RGBThreshold = {202, 255, 86, 207, 0, 255};

	public void robotInit() {
		wheelyBar = true;
		changeMode = true;
		setOnce = false;
		isDone = false;
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		mainJoystick = new Joystick(Config.MAIN_JOYSTICK);
		chasis = new Chasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1]);
		retrieval = new Retrieval(Config.RETRIEVAL_CHANNEL[0], Config.RETRIEVAL_CHANNEL[1]);
		shooter = new Shooter(1, Config.SENCODER[0], Config.SENCODER[1]);
		ds = DriverStation.getInstance();
		dsLCD = DriverStationLCD.getInstance();
		//relay = new Relay(Config.LIGHTS);
		//relay.setDirection(Relay.Direction.kForward);
		//victor = new Victor(Config.LIGHTS);
		jaguar = new Jaguar(Config.LIGHTS);
		
		


		//cam = new Camera();

		Kp = Config.PID[0];
		Ki = Config.PID[1];
		Kd = Config.PID[2];

		sKp = Config.SHOOTER_PID[0];
		sKi = Config.SHOOTER_PID[1];
		sKd = Config.SHOOTER_PID[2];

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
		isDone = false;
		Lib.clearLCD(dsLCD);
	}

	public void teleopinit() {
	}

	public void autonomousPeriodic() {
		System.out.println("fun fun fun");
		if (!isDone) {
			if (ds.getDigitalIn(1) && !setOnce) {
				launchSpeed = -0.80;
			} else if (ds.getDigitalIn(2) && !setOnce) {
				launchSpeed = -0.75;
			} else if (ds.getDigitalIn(3) && !setOnce) {
				launchSpeed = -0.70;
			} else if (ds.getDigitalIn(4) && !setOnce) {
				launchSpeed = -0.65;
			} else if (ds.getDigitalIn(5) && !setOnce) {
				launchSpeed = -0.20;
			} else if (ds.getDigitalIn(6) && !setOnce) {
				launchSpeed = -0.75;
				
				if (!isDone) {
				chasis.setTablePosition(Chasis.TilterMode.tiltup);
				chasis.leftEncoder.setReverseDirection(true);

				chasis.pullIn();
				shooter.set(launchSpeed);

				if (!chasis.compressor.getPressureSwitchValue()) {
					chasis.compressor.start();
				} else {
					chasis.compressor.stop();
				}
				Timer.delay(3.0);

				for (int shots = 1; shots <= 4; shots++) {
					chasis.pushOut();
					Timer.delay(0.50);
					chasis.pullIn();
					Timer.delay(0.75);
					
					if (shots == 4) {  
						
						chasis.set(-1.0);
						Timer.delay(0.75);
						chasis.set(0.0);
						chasis.setWheelyOff();
						Timer.delay(0.5);
						chasis.rightSide.set(-0.50);
						chasis.leftSide.set(-0.50);
						Timer.delay(0.5);
						chasis.rightSide.set(0.0);
						chasis.leftSide.set(0.0);
						
						
					}
				}
				
				
				isDone = true;
				
					
				
			}
			} else if (ds.getDigitalIn(7) && !setOnce) {
				launchSpeed = -0.75;
				
				if (!isDone) {
				chasis.setTablePosition(Chasis.TilterMode.tiltup);
				chasis.leftEncoder.setReverseDirection(true);

				chasis.pullIn();
				shooter.set(launchSpeed);

				if (!chasis.compressor.getPressureSwitchValue()) {
					chasis.compressor.start();
				} else {
					chasis.compressor.stop();
				}
				Timer.delay(3.0);

				for (int shots = 1; shots <= 4; shots++) {
					chasis.pushOut();
					Timer.delay(0.50);
					chasis.pullIn();
					Timer.delay(0.75);
					
					if (shots == 4) {  
						chasis.rightSide.set(-0.25);
						chasis.leftSide.set(-0.25);
						Timer.delay(1.0);
						chasis.rightSide.set(0.0);
						chasis.leftSide.set(0.0);
						Timer.delay(0.5);
						chasis.set(-0.60);
						Timer.delay(0.75);
						chasis.set(0.0);
						Timer.delay(0.5);
						chasis.rightSide.set(-0.50);
						chasis.leftSide.set(-0.50);
						Timer.delay(0.5);
						chasis.rightSide.set(0.0);
						chasis.leftSide.set(0.0);
						Timer.delay(0.5);
						chasis.set(-1.0);
						Timer.delay(0.5);
						chasis.set(0.0);
						
					}
				}
				
				
				isDone = true;
				
					
				
			}
				
			} else if (ds.getDigitalIn(8) && !setOnce) {
				launchSpeed = -0.75;
				
				if (!isDone) {
				chasis.setTablePosition(Chasis.TilterMode.tiltup);
				chasis.leftEncoder.setReverseDirection(true);

				chasis.pullIn();
				shooter.set(launchSpeed);

				if (!chasis.compressor.getPressureSwitchValue()) {
					chasis.compressor.start();
				} else {
					chasis.compressor.stop();
				}
				Timer.delay(3.0);

				for (int shots = 1; shots <= 4; shots++) {
					chasis.pushOut();
					Timer.delay(0.50);
					chasis.pullIn();
					Timer.delay(0.75);
					
					if (shots == 4) {  
						chasis.rightSide.set(0.25);
						chasis.leftSide.set(0.25);
						Timer.delay(1.0);
						chasis.rightSide.set(0.0);
						chasis.leftSide.set(0.0);
						Timer.delay(0.5);
						chasis.set(0.60);
						Timer.delay(0.75);
						chasis.set(0.0);
						Timer.delay(0.5);
						chasis.rightSide.set(0.50);
						chasis.leftSide.set(0.50);
						Timer.delay(0.5);
						chasis.rightSide.set(0.0);
						chasis.leftSide.set(0.0);
						Timer.delay(0.5);
						chasis.set(1.0);
						Timer.delay(0.5);
						chasis.set(0.0);
						
					}
				}
				
				
				isDone = true;
				
					
				
			}
			}
			/*if (!isDone) {
				chasis.setTablePosition(Chasis.TilterMode.tiltup);
				chasis.leftEncoder.setReverseDirection(true);

				chasis.pullIn();
				shooter.set(launchSpeed);
				if (!chasis.compressor.getPressureSwitchValue()) {
					chasis.compressor.start();
				} else {
					chasis.compressor.stop();
				}
				Timer.delay(3.0);

				for (int shots = 1; shots <= 4; shots++) {
					chasis.pushOut();
					Timer.delay(0.50);
					chasis.pullIn();
					Timer.delay(0.75);
					
					/*if (shots == 4) {  //To take out this chunk put / * infront of if and put * / (no space) below the first sqwiggly
						chasis.set(2.0);
						chasis.stop();
						chasis.set(2.0);
						
					}
					
				}
				
				
				isDone = true;
				
					
				
			}*/
			setOnce = true;
			shooter.stop();
		}
	}
	

	public void teleopPeriodic() {

		x = mainJoystick.getX();
		y = mainJoystick.getY();
		//secondaryY = secondaryJoystick.getY();

		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		secondaryY = Lib.signSquare(secondaryY);

		throttleValue = mainJoystick.getRawAxis(3);
		throttleValue = Lib.fixThrottle(throttleValue);
		throttleValue = Lib.round(throttleValue, 2);

		if (secondaryJoystick.getRawButton(11)) {
			chasis.setTablePosition(Chasis.TilterMode.tiltdown);
		}

		if (secondaryJoystick.getRawButton(10)) {
			chasis.setTablePosition(Chasis.TilterMode.tiltup);
		}
		
		if (secondaryJoystick.getRawButton(9)) {
			jaguar.set(1.0);
			//relay.set(Relay.Value.kOn);
		} else {
			jaguar.set(0.0);
		}	
		
		if (secondaryJoystick.getRawButton(8)) {
			jaguar.set(-1.0);
		} else {
			jaguar.set(0.0);
		}
		//MAIN JOYSTICK CONTROLS

		chasis.rightSide.set(-Lib.limitOutput(y - x));
		chasis.leftSide.set(Lib.limitOutput(y + x));


		if (mainJoystick.getRawButton(8)) {
			chasis.setWheelyOn();
		}
		/*
		 if (mainJoystick.getRawButton(8)) {
		 if (chasis.getMode() == Chasis.RobotMode.climbMode) {
		 chasis.setWheelyOn();
		 } else {
		 chasis.changeMode(Chasis.RobotMode.climbMode);
		 chasis.setWheelyOn();
		 }
		 }
		 * */

		if (mainJoystick.getRawButton(9)) {
			chasis.setWheelyOff();
		}

		//chasis.leftSide.set(Lib.limitOutput(y - x));
		//chasis.rightSide.set(-Lib.limitOutput(y + x));

		/*
		 if (chasis.getMode() == Chasis.RobotMode.climbMode) {
		 if (mainJoystick.getRawButton(10)) {
		 chasis.rightSide.set(throttleValue);
		 } else if (mainJoystick.getRawButton(11)) {
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
		 } else {
		 chasis.leftSide.set(Lib.limitOutput(y - x));
		 chasis.rightSide.set(-Lib.limitOutput(y + x));
		 }
		 * */


		//SECONDARY JOYSTICK CONTROLS
		/*
		 if (secondaryJoystick.getRawButton(7)) {
		 chasis.setWheelyOn();
		 }

		 if (secondaryJoystick.getRawButton(6)) {
		 chasis.setWheelyOff();
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
		 if (secondaryJoystick.getRawButton(11)) {
		 shooter.setTableForwards();
		 } else if (secondaryJoystick.getRawButton(10)) {
		 shooter.setTableReverse();
		 } else {
		 shooter.setTableNeutral();
		 }
		 }
		 */

		if (secondaryJoystick.getTrigger()) {
			shooter.set(-0.5);
			
		} else if (secondaryJoystick.getRawButton(3)) {
			shooter.set(-1.0);
		} else if (secondaryJoystick.getRawButton(4)) {
			shooter.set(-0.2);
		} else {
			shooter.set(0.0);
		}

		if (secondaryJoystick.getRawButton(2)) {
			//retrieval.pushOut();
			shoot = true;
		} else {
			//retrieval.pullIn();
			shoot = false;
		}

		if (shoot) {
			chasis.pushOut();
		} else {
			chasis.pullIn();
		}

		if (!chasis.compressor.getPressureSwitchValue()) {
			chasis.compressor.start();
		} else {
			chasis.compressor.stop();
		}
		/*
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
			
		 if (secondaryJoystick.getRawButton(4)) {
		 chasis.openClamp();
		 }
		 if (secondaryJoystick.getRawButton(5)) {
		 chasis.closeClamp();
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
		 */
		dsLCD.println(DriverStationLCD.Line.kUser1, 1, String.valueOf(chasis.leftEncoder.getDistance()));
		dsLCD.println(DriverStationLCD.Line.kUser2, 1, String.valueOf(chasis.rightEncoder.getDistance()));
		dsLCD.updateLCD();
	}

	public void testPeriodic() {
	}

	public boolean centerWithCamX(Direction direction, int mode) {
		boolean isCentred = false;
		if (direction == Direction.right) {
			dsLCD.println(DriverStationLCD.Line.kUser1, 1, "left  ");
			if (mode == 1) {
				chasis.rightSide.set(-0.3);
				chasis.leftSide.set(0);
			}

		} else if (direction == Direction.left) {
			dsLCD.println(DriverStationLCD.Line.kUser1, 1, "right  ");
			if (mode == 1) {
				chasis.leftSide.set(0.3);
				chasis.rightSide.set(0);
			}

		} else if (direction == Direction.center) {
			dsLCD.println(DriverStationLCD.Line.kUser1, 1, "center");
			if (mode == 1) {
				chasis.leftSide.set(0);
				chasis.rightSide.set(0);
			}
			isCentred = true;
		}
		dsLCD.updateLCD();
		return isCentred;
	}

	private boolean centerWithCamY(Direction nextDirectionY, int mode) {
		boolean isCentered = false;
		if (nextDirectionY == Direction.up) {
			dsLCD.println(DriverStationLCD.Line.kUser1, 1, "up  ");

			if (mode == 1) {
				shooter.setTableReverse();
			}

		} else if (nextDirectionY == Direction.down) {

			dsLCD.println(DriverStationLCD.Line.kUser1, 1, "down  ");

			if (mode == 1) {
				shooter.setTableForwards();
			}

		} else if (nextDirectionY == Direction.center) {

			dsLCD.println(DriverStationLCD.Line.kUser1, 1, "center");
			if (mode == 1) {
				shooter.setTableNeutral();
			}
			isCentered = true;
		}
		dsLCD.updateLCD();
		return isCentered;
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
