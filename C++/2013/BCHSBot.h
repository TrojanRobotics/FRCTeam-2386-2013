#ifndef BCHS_BOT_H_
#define BCHS_BOT_H_

#include "WPILib.h"


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
	float x, y, lX, lY, sX, sY, sLX, sLY;
	DriverStationLCD *ds;
	
};

START_ROBOT_CLASS(BCHSBot);

#endif
