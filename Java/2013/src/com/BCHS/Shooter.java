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
		
		kp = 0.0;
		ki = 0.0;
		kd = 0.0;
		
		encoder = new Encoder(aChannel, bChannel);
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		encoder.setDistancePerPulse(Config.SE_DPP);
		encoder.start();
	}
	
	public void set(double speed)
	{
		motorBundle.set(-speed);
	}
	
	public void stop()
	{
		motorBundle.stop();
	}
}
