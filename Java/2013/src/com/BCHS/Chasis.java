package com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;


public class Chasis 
{
	Bundle leftSide, rightSide;
	Encoder leftEncoder, rightEncoder;
	AnalogChannel ultrasonic;
	PIDController leftSidePID, rightSidePID;
	Solenoid solenoid;

	public Chasis(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel, int ultraSonic, int[] leftSide, int[] rightSide, int solenoidChannel)
	{
		leftEncoder = new Encoder(leftAChannel, leftBChannel);
		rightEncoder = new Encoder(rightAChannel, rightBChannel);
		ultrasonic = new AnalogChannel(ultraSonic);
		this.leftSide = new Bundle(leftSide[0], leftSide[1]);
		this.rightSide = new Bundle(rightSide[0], rightSide[1]);

		
		leftEncoder.setDistancePerPulse(Config.LE_DPP);
		rightEncoder.setDistancePerPulse(Config.RE_DPP);

		solenoid = new Solenoid(solenoidChannel);
		
		leftEncoder.setDistancePerPulse(Config.LEFT_SIDE_ENCODER_DPP);
		rightEncoder.setDistancePerPulse(Config.RIGHT_SIDE_ENCODER_DPP);
	}													
	
	public void set(double speed)
	{
		leftSide.set(-speed);
		rightSide.set(speed);
	}
	
	public void enable()
	{
		leftSidePID.enable();
		rightSidePID.enable();
	}
	
	public void stop()
	{
		leftSide.stop();
		rightSide.stop();
		leftSidePID.disable();
		rightSidePID.disable();
	}
	
	public void reset()
	{
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void setSetpoint(double setpoint)
	{
		leftSidePID.setSetpoint(setpoint);
		rightSidePID.setSetpoint(-setpoint);
	}
	
	public void solenoidOn()
	{
		solenoid.set(true);
	}
	
	public void solenoidOff()
	{
		solenoid.set(false);
	}
	
	public boolean getSolenoidStatus()
	{
		return solenoid.get();
	}
}
