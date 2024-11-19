package robo3;
import robocode.*;
 import java.awt.Color;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Robo3 - a robot by (your name here)
 */
public class Robo3 extends Robot
{
double enemyBearing;
    double gunOffset;
    boolean isMovingForward = true;
	/**
	 * run: Robo02's default behavior
	 */
	public void run() {
	
setBulletColor(Color.GREEN);
 setGunColor(Color.RED);
 setBodyColor(Color.BLACK);
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
	while (true) {
            if (isMovingForward) {
                ahead(100); // Move ahead 100 pixels
            } else {
                back(100); // Move back 100 pixels
            }
            turnRight(90); // Turn right by 90 degrees
            isMovingForward = !isMovingForward;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
public void onScannedRobot(ScannedRobotEvent e) {
	 // Calculate the bearing and distance of the enemy robot
        double bearing = getHeading() + e.getBearing();
        double distance = e.getDistance();

        // Calculate the offset to the gun's bearing
        gunOffset = robocode.util.Utils.normalRelativeAngleDegrees(bearing - getGunHeading());

        // If the enemy robot is within a certain distance, aim and fire the gun
        if (distance <= 200) {
            // If the enemy robot is in front of the gun, aim at the enemy robot and fire
            if (Math.abs(gunOffset) <= 3) {
                fire(3);
            }
            // If the enemy robot is not in front of the gun, turn the gun to the enemy robot's bearing and fire
            else {
                turnGunRight(gunOffset);
                fire(3);
            }
        }

        // If the enemy robot is far away, move closer
        if (distance > 200) {
            // Turn the robot to the enemy robot's bearing
            enemyBearing = e.getBearing();
            turnRight(normalizeBearing(enemyBearing));
            ahead(distance - 150);
        }
    }

    // Normalize the bearing to between -180 and 180 degrees
    double normalizeBearing(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    
}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
public void onHitByBullet(HitByBulletEvent e) {
	double energy = getEnergy();
	
 // update the energy level when hit by bullet
   // Update the energy level when hit by bullet
    energy -= e.getPower();

    double bearing = e.getBearing(); //Get the direction which is arrived the bullet.
    if(energy < 100){ // if the energy is low, the robot goes away from the enemy
        turnRight(-bearing); //This isn't accurate but release your robot.
        ahead(100); //The robot goes away from the enemy.
    }
    else {
        turnRight(360); // scan
  
}


   
}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
public void onHitWall(HitWallEvent e) {
	 double currentHeading = getHeading();

    // Calculate the difference between the current heading and the angle to the wall
    double wallBearing = e.getBearing();
    double turnAngle = currentHeading - wallBearing;

    // If the robot is facing towards the wall, turn away from it
    if (Math.abs(turnAngle) < 90) {
        turnRight(turnAngle + 90);
    }
    // Otherwise, turn towards the wall
    else {
        turnLeft(turnAngle - 90);
    }

    // Move forward to avoid hitting the wall again
    ahead(100);
	// Change color of robot when it hits a wall
    setColors(Color.RED, Color.YELLOW, Color.BLUE);

    // Print a message to let the user know that the robot has hit a wall
    System.out.println("Oops, I hit a wall!");
}
public void onWin(WinEvent event) {
		    // Perform actions when your robot wins a round

		    // Print a message to the console
		    System.out.println("I won the round!");

		    // Celebrate by performing some dance moves
		    turnLeft(90);
		    ahead(100);
		    turnRight(180);
		    back(100);
		    turnLeft(180);
		    ahead(100);
		}
public void onHitRobot(HitRobotEvent event) {
        //If our robot sees another robot in front, it moves back, otherwise it will move forward
		if (event.getBearing() > -90 && event.getBearing() <= 90) {
            back(100);
        } else {
              ahead(100);
       
}
		  
    
	// Add a spin move after colliding with the enemy robot
    turnLeft(360);
}

}


