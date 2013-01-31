#ifndef MADCATZ_H_
#define MADCATS_H_

#include "GenericHID.h"
#include "ErrorBase.h"

class DriverStation;

class MadCatz : public GenericHID, public ErrorBase
{
public:
	//axis for controller
	static const UINT32 kDefaultLXAxis = 1;
	static const UINT32 kDefaultLYAxis = 2;
	static const UINT32 kDefaultRXAxis = 4;
	static const UINT32 kDefaultRYAxis = 5;
	static const UINT32 kDefaultLTAxis = 9;
	static const UINT32 kDefaultRTAxis = 10;
	static const UINT32 kDefaultPadX = 6;
	static const UINT32 kDefaultPadY = 7;
	typedef enum
	{
		kLXAxis = kDefaultLXAxis, kLYAxis = kDefaultLYAxis, kRXAxis = kDefaultRXAxis, kRYAxis = kDefaultRYAxis, kLTAxis = kDefaultLTAxis, kRTAxis = kDefaultRTAxis, kPadX = kDefaultPadX, kPadY = kDefaultPadY
	} AxisType;
	
	//buttons for controller
	static const UINT32 kDefaultAButton = 1;
	static const UINT32 kDefaultBButton = 2;
	static const UINT32 kDefaultXButton = 3;
	static const UINT32 kDefaultYButton = 4;
	static const UINT32 kDefaultLBump = 5;
	static const UINT32 kDefaultRBump = 6;
	static const UINT32 kDefaultStart = 7;
	static const UINT32 kDefaultBack = 8;
	static const UINT32 kDefaultLStick = 9;
	static const UINT32 kDefaultRStick = 10;
	typedef enum
	{
		kAButton = kDefaultAButton, kBButton = kDefaultBButton, kXButton = kDefaultXButton, kYButton = kDefaultYButton, kLBump = kDefaultLBump, kRBump = kDefaultRBump, kStart = kDefaultStart, kBack = kDefaultBack, kLStick = kDefaultLStick, kRStick = kDefaultRStick, kNumAxisTypes, kNumButtonTypes
	} ButtonType;
	
	explicit MadCatz(UINT32 port);
	MadCatz(UINT32 port, UINT32 numAxisTypes, UINT32 numButtonTypes);
	virtual ~MadCatz();
	
	//methods for axis
	UINT32 GetAxisChannel(AxisType axis);
	void SetAxisChannel(AxisType axis, UINT32 channel);
	virtual float GetX(JoystickHand hand = kRightHand);
	virtual float GetY(JoystickHand hand = kRightHand);
	virtual float GetZ();
	virtual float GetTwist();
	virtual float GetThrottle();
	virtual float GetAxis(AxisType axis);
	float GetRawAxis(UINT32 axis);
	
	//methods for buttons
	virtual bool GetTrigger(JoystickHand hand = kRightHand);
	virtual bool GetTop(JoystickHand hand = kRightHand);
	virtual bool GetBumper(JoystickHand hand = kRightHand);
	virtual bool GetButton(ButtonType button);
	bool GetRawButton(UINT32 button);
	static MadCatz* GetStickForPort(UINT32 port);
	
private:
	//DISSALLOW_COPY_AND_ASSIGN(MadCatz);
	void InitMadCatz(UINT32 numAxisTypes, UINT32 numButtonTypes);
	
	DriverStation *m_ds;
	UINT32 m_port;
	UINT32 *m_axes;
	UINT32 *m_buttons;
};

#endif
