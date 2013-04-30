package com.BCHS;

public interface Config
{
	/*
	 * GenericHID Contants
	 */
	
	static final int MAIN_JOYSTICK = 1;
	static final int SECONDARY_JOYSTICK = 2;
	
	static double [] CLIMB_PID = {1.0, 0.0, 0.0};
	
	/*
	 * PWM Constants
	 */
	static final int[] LDRIVE = {8, 7};
	static final int[] RDRIVE = {6, 9};
	

	static final int[] SOLENOID_CHANNEL = {3, 4, 1, 2, 5, 6};
	static final int[] RETRIEVAL_CHANNEL = {7, 8};
	
	static final int SHOOTER_RELAY_CHANNEL = 7;
	
	
	static final int[] PNEUMATICS = {13, 8};
	
	/*
	 * Digital Constants
	 */
	static final int[] LENCODER = { 5, 6 };
	static final int[] RENCODER = { 3, 4 };
	static final int[] SENCODER = { 1, 2 };
	
	
	static final int TLIMIT_SWITCH = 8;
	static final int BLIMIT_SWITCH = 9;
	static final int RLIMIT_SWITCH = 7;
	
	/**
	 * Jag/Vic Constants
	 */
	static final int LIGHTS = 2;
	

	/**
	 * Value Constants
	 */
	static final double[] PID = { 0.05, 0.0, 0.0 };
	static final double[] SHOOTER_PID = { 0.5, 0.0, 0.0 };
	
	static final int[] CAM_HSL = { 0, 23, 31, 142, 73, 255 };
	static final int[] CAM_RGB = { 191, 255, 129, 229, 117, 190 };
	
	static final double LE_DPP = (((Math.PI * 6.0) / 360) / 0.75);
	static final double RE_DPP = (((Math.PI * 6.0) / 360) / 0.75);
	static final double SE_DPP = (((Math.PI * 6.0) / 360) / 3);
	static final double CLIMB_DPP = (((Math.PI * 1.25) / 360) / 3);
}


