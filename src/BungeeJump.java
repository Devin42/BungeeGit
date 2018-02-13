import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
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
		control.setValue("Number of Segments", 10);
		control.setValue("Number of Cords", 1);
		
	}

	public void initialize() {
		
		frame.setPreferredMinMax(-10, 10, -10, 150);
		
		gravity = control.getDouble("gravity");
		personMass = control.getDouble("Person Mass");
		cordMass = control.getDouble("Cord Mass");
		cordLength = control.getDouble("Cord Length");
		bridgeHeight = control.getDouble("Bridge Height");
		segmentNumber = (int) control.getDouble("Number of Segments");
		cordNumber = (int) control.getDouble("Number of Cords");
		
		bungeeCord[] bungeeArray = new bungeeCord[cordNumber];
		
		//Fills bungee array with cords based on user input values
		for (int i = 0; i < cordNumber; i++) {
			
			bungeeCord bungee = new bungeeCord(cordLength, cordMass, personMass, segmentNumber);
			
			//Adds the circles representing each particle to the frame
			for (int j = 0; j < segmentNumber; j++) {
				
				Circle circle = new Circle();
				circle.pixRadius = 5;
				
				
				circle.setY(bridgeHeight);
				bungee.circleArray.add(circle);
				frame.addDrawable(circle);
				frame.addDrawable(bungee.circleArray.get(j));
				
				
			}
			bungeeArray[i] = bungee; 

			
			
		}
		
		
		
		
		frame.setVisible(true);
		
	
		
		
		
	}
	
	public static void main(String[] args){	
		SimulationControl.createApp(new BungeeJump());
	}
	
	public void updatePosition (Particle segment) {
		
		segment.acceleration = gravity + springForce(segment);
		
		
	}
	
	public double springForce (Particle segment) {
		
		return 3;
	}
	
	
}