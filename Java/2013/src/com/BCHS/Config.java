package com.BCHS;

public interface Config
{
	/*
	 * GenericHID Contants
	 */
	
	static final int MADCATZ_JOYSTICK = 1;
	static final int SECONDARY_JOYSTICK = 2;
	static final int MAIN_JOYSTICK = 1;
	
	static double [] CLIMB_PID = {1.0, 0.0, 0.0};
	
	/*
	 * PWM Constants
	 */
	static final int[] LDRIVE = {10, 9};
	static final int[] RDRIVE = {8, 7};
	

	static final int[] SOLENOID_CHANNEL = {3, 4};
	static final int RETRIEVAL_CHANNEL = 2;
	static final int[] CLIMBER_CHANNEL = {5, 6, 6};
	
	
	static final int[] PNEUMATICS = {13, 8};
	
	/*
	 * Digital Constants
	 */
	static final int[] LENCODER = { 3, 4 };
	static final int[] RENCODER = { 1, 2 };
	static final int[] SENCODER = { 5, 6 };
	
	
	static final int TLIMIT_SWITCH = 8;
	static final int BLIMIT_SWITCH = 9;
	static final int RLIMIT_SWITCH = 7;
	
	/**
	 * Relay Constants
	 */
	static final int LIGHTS = 1;
	

	/**
	 * Value Constants
	 */
	static final double[] PID = { 0.1, 0.0, 0.0 };
	static final int[] CAM_HSL = { 0, 23, 31, 142, 73, 255 };
	static final int[] CAM_RGB = { 191, 255, 129, 229, 117, 190 };
	
	static final double LE_DPP = 0.0190;
	static final double RE_DPP = 0.0240;
	static final double SE_DPP = 0.18;

	
	static final double CLIMB_DPP = 0.1337;
}


