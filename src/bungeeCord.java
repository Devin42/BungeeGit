import org.opensourcephysics.display.Circle;

public class bungeeCord {

	double cordLength;
	double cordMass;
	double personMass;
	int segmentNumber; //number of segments
	double k; //spring constant
	
	Segment[] segmentArray = new Segment[segmentNumber];
	Circle[] circleArray = new Circle[segmentNumber];
	
	public bungeeCord(double cordLength, double cordMass, double personMass, int segmentNumber) {
		this.cordLength = cordLength;
		this.cordMass = cordMass;
		this.personMass = personMass;
		this.segmentNumber = segmentNumber;
	}
	
	
	
}
