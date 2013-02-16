package com.BCHS;

import edu.wpi.first.wpilibj.*;


public class Chasis 
{
    
    public static class RobotMode
    {
        public final int value;
        
        public static final RobotMode driveMode = new RobotMode(1);
        public static final RobotMode climbMode = new RobotMode(2);  
        
        public RobotMode(int value)
        {
            this.value = value;
        }
    }
    
	Bundle leftSide, rightSide;
	Encoder leftEncoder, rightEncoder;
	PIDController leftSidePID, rightSidePID, leftClimbPID, rightClimbPID;
	Solenoid driveSolenoid, climbSolenoid, wheelyBarUp, wheelyBarDown;
	Compressor compressor;

	public Chasis(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel)
	{
		leftEncoder = new Encoder(leftAChannel, leftBChannel);
		rightEncoder = new Encoder(rightAChannel, rightBChannel);
		this.leftSide = new Bundle(6, 9);
		this.rightSide = new Bundle(8, 7);

		leftEncoder.setDistancePerPulse(Config.LE_DPP);
		rightEncoder.setDistancePerPulse(Config.RE_DPP);
		
		leftEncoder.start();
		rightEncoder.start();
		
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		
		leftSidePID = new PIDController(Config.PID[0],Config.PID[1],Config.PID[2],leftEncoder, this.leftSide);
		rightSidePID = new PIDController(Config.PID[0],Config.PID[1],Config.PID[2],rightEncoder, this.rightSide);
		leftClimbPID = new PIDController(Config.CLIMB_PID[0],Config.CLIMB_PID[1],Config.CLIMB_PID[2],leftEncoder, this.leftSide);
		rightClimbPID = new PIDController(Config.CLIMB_PID[0],Config.CLIMB_PID[1],Config.CLIMB_PID[2],rightEncoder, this.rightSide);
		compressor = new Compressor(Config.PNEUMATICS[0], Config.PNEUMATICS[1]);
		
		driveSolenoid = new Solenoid(Config.SOLENOID_CHANNEL[0]);
		climbSolenoid = new Solenoid(Config.SOLENOID_CHANNEL[1]);
        wheelyBarUp = new Solenoid(Config.SOLENOID_CHANNEL[2]);
        wheelyBarDown = new Solenoid(Config.SOLENOID_CHANNEL[3]);
	}													
	
	public void set(double speed)
	{
		leftSide.set(-speed);
		rightSide.set(speed);
	}
	
	public void enable()
	{
		if (this.getMode() == RobotMode.driveMode) {
			leftSidePID.enable();
			rightSidePID.enable();
		} else {
			leftClimbPID.enable();
			rightClimbPID.enable();
		}
	}
	
	public void stop()
	{
		leftSide.stop();
		rightSide.stop();
		leftSidePID.disable();
		rightSidePID.disable();
		leftClimbPID.disable();
		rightClimbPID.disable();
	}
	
	public void reset()
	{
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void setSetpoint(double setpoint)
	{
		if (this.getMode() == RobotMode.driveMode) {
			leftSidePID.setSetpoint(setpoint);
			rightSidePID.setSetpoint(-setpoint);
		} else {
			leftClimbPID.setSetpoint(setpoint);
			rightClimbPID.setSetpoint(-setpoint);
		}
	}
    
    public void changeMode(RobotMode mode) //true is climb, false is drive
    {
        if (mode == RobotMode.climbMode) {
			leftEncoder.setDistancePerPulse(Config.CLIMB_DPP);
			rightEncoder.setDistancePerPulse(Config.CLIMB_DPP);
            climbSolenoid.set(true);
            driveSolenoid.set(false);
        } else {
			leftEncoder.setDistancePerPulse(Config.LE_DPP);
			rightEncoder.setDistancePerPulse(Config.RE_DPP);
            climbSolenoid.set(false);
            driveSolenoid.set(true);
        }
    }
    public RobotMode getMode()
    {
        if (climbSolenoid.get()) {
            return RobotMode.climbMode;
        } else {
            return RobotMode.driveMode;
        }
    }
    
    public void setWheelyOn()
    {
        wheelyBarDown.set(false);
        wheelyBarUp.set(true); 
    }
    public void setWheelyOff()
    {
        wheelyBarUp.set(false);
        wheelyBarDown.set(true);
    }
	public boolean getIsWheelyBarDown()
	{
		if (wheelyBarDown.get()) {
			return true;
		} else {
			return false;
		}
	}
}
