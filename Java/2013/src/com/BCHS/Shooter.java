package com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class Shooter 
{
	Bundle motorBundle;
	Encoder encoder;
	PIDController ShooterPID;
	double kp, ki, kd;
	
	public Shooter(int channelOne, int channelTwo, int aChannel, int bChannel)
	{
		motorBundle = new Bundle(channelOne, channelTwo);
		ShooterPID = new PIDController(kp, ki, kd, encoder, motorBundle);
		encoder = new Encoder(aChannel, bChannel);
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		encoder.setDistancePerPulse(Config.SE_DPP);
		encoder.start();
		
		
		kp = 0.0;
		ki = 0.0;
		kd = 0.0;
		
	}
	
	public void setPRM(double RPM)
	{
		ShooterPID.enable();
		ShooterPID.setSetpoint(RPM);
	}
	public void set(double speed)
	{
		motorBundle.set(-speed);
	}
	
	public void stop()
	{
		motorBundle.stop();
		ShooterPID.disable();
	}
}
