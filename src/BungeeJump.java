import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;


public class BungeeJump extends AbstractSimulation{

	DisplayFrame frame = new DisplayFrame("x", "y", "BungeeJump");
	
	double cordLength;
	double bridgeHeight;
	
	double gravity;
	
	double personMass;
	double cordMass;
	
	int segmentNumber;
	int cordNumber;
	
	protected void doStep() {



	}


	public void reset() {

		control.setValue("gravity", -9.81);
		control.setValue("Person Mass", 50);
		control.setValue("Cord Mass", 10);
		control.setValue("Cord Length", 40);
		control.setValue("Bridge Height", 100);
		control.setValue("Number of Segments", 20);
		control.setValue("Number of Cords", 1);
		
	}

	public void initialize() {
		
		gravity = control.getDouble("gravity");
		personMass = control.getDouble("Person Mass");
		cordMass = control.getDouble("Cord Mass");
		cordLength = control.getDouble("Cord Length");
		bridgeHeight = control.getDouble("Bridge Height");
		segmentNumber = (int) control.getDouble("Number of Segments");
		cordNumber = (int) control.getDouble("Number of Cords");
		
		
		bungeeCord[] cordArray = new bungeeCord[cordNumber];
		
		for (int i = 0; i < cordNumber; i++) {
			
			bungeeCord bungee = new bungeeCord(cordLength, cordMass, personMass, segmentNumber);
			
			
			cordArray[i] = bungee; 

		}
		
		
		
		
		
		frame.setVisible(true);
		
	
		
		
		
	}
	
	public static void main(String[] args){	
		SimulationControl.createApp(new BungeeJump());
	}
	
	public void updatePosition (Segment segment) {
		
		segment.acceleration = gravity + springForce(segment);
		
		
	}
	
	public double springForce (Segment segment) {
		
		return 3;
	}
	
	
}