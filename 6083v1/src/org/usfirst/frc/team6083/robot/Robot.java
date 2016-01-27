package org.usfirst.frc.team6083.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Joystick;


/**
 * This is a demo program showing the use of the NIVision class to do vision processing. 
 * The image is acquired from the USB Webcam, then a circle is overlayed on it. 
 * The NIVision class supplies dozens of methods for different types of processing. 
 * The resulting image can then be sent to the FRC PC Dashboard with setImage()
 */
public class Robot extends SampleRobot {
    int session;
    Image frame;
    VictorSP talon_left = new VictorSP(0);
    VictorSP talon_right = new VictorSP(1);
    Joystick joy = new Joystick(0);
    
    
    

    public void robotInit() {

        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
    }

    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
        NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

        while (isOperatorControl() && isEnabled()) {

            NIVision.IMAQdxGrab(session, frame, 1);
            NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
            
            CameraServer.getInstance().setImage(frame);

            /** robot code here! **/
            Timer.delay(0.005);// wait for a motor update time
            
            if(joy.getRawButton(5)){
         	   talon_left.set(joy.getRawAxis(1)/-2.5);
         	}
         	else{
         		talon_left.set(joy.getRawAxis(1)/-5);
         	}
         	
         	if(joy.getRawButton(6)){
         	    talon_right.set(joy.getRawAxis(5)/2.5);
         	}
         	else{ 
         		talon_right.set(joy.getRawAxis(5)/5);
         	}
         	
         	
            
            
            
        } 
        NIVision.IMAQdxStopAcquisition(session);
    }

    public void test() {
    }
}
