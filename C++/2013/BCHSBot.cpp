
#include "BCHSBot.h"

BCHSBot::BCHSBot()
{
	//driveStick = new Joystick(1);
	leftJag1 = new Jaguar(3);
	leftJag2 = new Jaguar(4);
	rightJag1 = new Jaguar(1);
	rightJag2 = new Jaguar(2);
	ds = DriverStationLCD::GetInstance();
	cat = new MadCatz(1);
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
	delete cat;
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

	lY = cat->GetY(GenericHID::kLeftHand);
	rY = cat ->GetY(GenericHID::kRightHand);
	
	
	lY = signSquare(lY);
	rY = signSquare(rY);
	
	
	leftJag1->Set(limitOutput(lY));
	leftJag2->Set(limitOutput(lY));
	rightJag1->Set(-limitOutput(rY));
	rightJag2->Set(-limitOutput(rY));
	
	ds->Printf(DriverStationLCD::kUser_Line1, 1, "%f", limitOutput(lY));
	ds->Printf(DriverStationLCD::kUser_Line2, 1, "%f", -limitOutput(rY));	

	ds->UpdateLCD();
}
