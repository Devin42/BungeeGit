public class Particle {

	//Particle's position, only need one because it's one dimensional motion
	double position;
	
	//Particle's velocity
	double velocity;
	
	//Particle's acceleration
	double acceleration;
	
	//How far down the cord the particle
	int orderPosition;
	
	//How far the particle is stretched from its original position
	double deltaX;
	
	//The force from the spring above the particle
	double springForceUp;
	
	//The force from the spring below the particle
	double springForceDown;
}
