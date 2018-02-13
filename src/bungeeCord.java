import java.util.ArrayList;

import org.opensourcephysics.display.Circle;

public class bungeeCord {

	double cordLength;
	double cordMass;
	double personMass;
	int segmentNumber; //number of segments
	double k; //spring constant
	
	ArrayList <Particle> particleArray = new ArrayList <Particle>();
	ArrayList <Circle> circleArray = new ArrayList <Circle>();
	
	public bungeeCord(double cordLength, double cordMass, double personMass, int segmentNumber) {
		this.cordLength = cordLength;
		this.cordMass = cordMass;
		this.personMass = personMass;
		this.segmentNumber = segmentNumber;
	}
	
	
	
}
