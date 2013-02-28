
package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.templates.Camera.Direction;
import edu.wpi.first.wpilibj.DriverStationLCD;


public class RobotTemplate extends IterativeRobot 
{
	Joystick mainJoystick;
	Joystick secondaryJoystick;
	Bundle leftSide, rightSide;
    double x, y;
	Camera cam;
	Shooter shooter;
	ParticleAnalysisReport [] particles;
	int[] RGBThreshold = {202, 255, 86, 207, 0, 255};
	DriverStationLCD ds;
	
    public void robotInit() 
	{
		mainJoystick = new Joystick(1);
		secondaryJoystick = new Joystick(2);
		leftSide = new Bundle(3, 4);
		rightSide = new Bundle(1, 2);
		cam = new Camera();
		shooter = new Shooter(1, Config.SENCODER[0], Config.SENCODER[1]);
		ds = DriverStationLCD.getInstance();
    }

    
    public void autonomousPeriodic() {

    }

   
    public void teleopPeriodic() 
	{
        x = mainJoystick.getX();
		y = mainJoystick.getY();
		
		
		x = Lib.signSquare(x);
		y = Lib.signSquare(y);
		
		System.out.println("x: " + x);
		System.out.println("y:" + y);
		
		leftSide.set(Lib.limitOutput(y - x));
		rightSide.set(-Lib.limitOutput(y + x));
		
			if (secondaryJoystick.getRawButton(4)) {
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
			
			if (secondaryJoystick.getTrigger()) {
				shooter.set(-0.75);
			} else {
				shooter.set(0.0);
			}
    }
    
    /**
     * This function is called periodically during test mode
     */
	
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
	
	public void centerWithCamX(Direction direction, int mode) {
		if (direction == Direction.right) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "left  ");
			if (mode == 1) {
				this.rightSide.set(-0.3);
				this.leftSide.set(0);
			}

		} else if (direction == Direction.left) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "right  ");
			if (mode == 1) {
				this.leftSide.set(0.3);
				this.rightSide.set(0);
			}

		} else if (direction == Direction.center) {
			ds.println(DriverStationLCD.Line.kUser1, 1, "center");
			if (mode == 1) {
				this.leftSide.set(0);
				this.rightSide.set(0);
			}
		}
		ds.updateLCD();
	}

		
    public void testPeriodic() {
    
    }
    
}
