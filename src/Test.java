import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;

public class Test {


	double mass;

	//Particle's position, only need one because it's one dimensional motion
	double xPosition;
	double yPosition;
	double zPosition;

	double xPositionLast;
	double yPositionLast;
	double zPositionLast;

	//Particle's velocity
	double xVelocity;
	double yVelocity;
	double zVelocity;

	//Particle's acceleration
	double xAcceleration;
	double yAcceleration;
	double zAcceleration;

	//The force from the spring above the particle
	double FNup;

	//The force from the spring below the particle
	double FGdown;
}



