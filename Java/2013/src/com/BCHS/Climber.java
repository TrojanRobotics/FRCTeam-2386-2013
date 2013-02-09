/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author Laxman
 */
public class Climber 
{
	Encoder climbEncoder;
	Jaguar climbJaguar;
	PIDController climbPID;
	Solenoid solenoid;
	double Kp, Ki, Kd;
	
	public Climber(int aChannel, int bChannel, int jagChannel)
	{
		Kp = 1.0;
		Ki = 0.0;
		Kd = 0.0;
		
		climbEncoder = new Encoder(aChannel, bChannel);
		climbEncoder.setDistancePerPulse(Config.CLIMB_DPP);
		climbEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		solenoid = new Solenoid(11);
		climbEncoder.start();
		
		climbJaguar = new Jaguar(jagChannel);
		
		climbPID = new PIDController(Kp, Ki, Kd, climbEncoder, climbJaguar);	
	}

	Climber(int[] CLIMBER_CHANNEL) {
	}
	
	public void setSpeed(double speed)
	{
		climbJaguar.set(speed);
	}
	
	public void stop()
	{
		climbPID.disable();
		climbJaguar.disable();
	}
	
	public void setWheelyBar(boolean wheelyBar)
	{
		solenoid.set(wheelyBar);
	}
	
	
}
