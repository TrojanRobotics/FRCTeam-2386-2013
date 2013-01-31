#ifndef BCHS_BOT_H_
#define BCHS_BOT_H_

#include "WPILib.h"
#include "MadCatz.h"


class BCHSBot : public IterativeRobot
{
public:
	BCHSBot();
	virtual ~BCHSBot();
	virtual float limitOutput(float x);
	virtual float signSquare(float x);
	virtual void TeleopPeriodic();
	
	
private:
	Joystick *driveStick;
	Jaguar *leftJag1, *leftJag2, *rightJag1, *rightJag2;
	float rY, lY;
	DriverStationLCD *ds;
	MadCatz *cat;
	
};

START_ROBOT_CLASS(BCHSBot);

#endif
