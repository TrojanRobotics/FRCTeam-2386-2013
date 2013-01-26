
#include "BCHSBot.h"

BCHSBot::BCHSBot()
{
	driveStick = new Joystick(1);
	leftJag1 = new Jaguar(3);
	leftJag2 = new Jaguar(4);
	rightJag1 = new Jaguar(1);
	rightJag2 = new Jaguar(2);
	ds = DriverStationLCD::GetInstance();
}
BCHSBot::~BCHSBot()
{
	printf("Hey I'm deleting this");
	delete driveStick;
	delete leftJag1; 
	delete leftJag2;
	delete rightJag1;
	delete rightJag2;
	delete ds;
}

float BCHSBot::limitOutput(float x)
{
	if (x > 1.0)
	{
		return 1.0;
	} 
	else if (x < -1.0)
	{
		return -1.0;
	}
	else
	{
		return x;
	}
}

float BCHSBot::signSquare(float x)
{
	if (x < 0.0)
	{
		return -1.0 * x * x;
	}
	else
	{
		return x * x;
	}
}

void BCHSBot::TeleopPeriodic()
{
	x = driveStick->GetX();
	y = driveStick->GetY();
	
	x = signSquare(x);
	y = signSquare(y);
	x = limitOutput(x);
	y = limitOutput(y);
	
	leftJag1->Set(y - x);
	leftJag2->Set(y - x);
	rightJag1->Set(-(y + x));
	rightJag2->Set(-(y + x));
	
	ds->Printf(DriverStationLCD::kUser_Line1, 1, "%f", limitOutput(y - x));
	ds->Printf(DriverStationLCD::kUser_Line2, 1, "%f", limitOutput(-1 * (y + x)));	

	ds->UpdateLCD();
}
