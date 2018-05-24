public class Particle {

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
	
	//How far down the cord the particle
	int springPosition;
	int orderPosition;
	
	//How far the particle is stretched from its original position
	double deltaX;
	
	//The force from the spring above the particle
	double springForceUp;
	
	//The force from the spring below the particle
	double springForceDown;
	
	//indiv K
	double individualSpringConstant;
}
