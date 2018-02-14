import java.awt.Color;
import java.util.ArrayList;

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
	double springConstant;
	
	double timeStep;
	
	int segmentNumber;
	int cordNumber;
	
	int z = 0;
	ArrayList <Particle> particleArray = new ArrayList <Particle>();
	ArrayList <Circle> circleArray = new ArrayList <Circle>();
	
	protected void doStep() {

		for (int i = 1; i < particleArray.size(); i++) {
			
			//Sets ∆x to the distance between the particle's original position and its current positions
			particleArray.get(i).deltaX = particleArray.get(i).originalPosition - particleArray.get(i).position;
		
			//Sets circle position to particle position
			circleArray.get(i).setY(particleArray.get(i).position);
		
			//Updates particle position based on net force
			updatePosition(particleArray.get(i));
			
		}
		
	}

	public void reset() {

		//All values that can be changed by the user
		control.setValue("gravity", -9.81);
		control.setValue("Time Step", .01);
		control.setValue("Spring Constant", 2600);
		control.setValue("Person Mass", 50);
		control.setValue("Cord Mass", 10);
		control.setValue("Cord Length", 40);
		control.setValue("Bridge Height", 100);
		control.setValue("Number of Segments", 80);
		control.setValue("Number of Cords", 1);
		
	}

	public void initialize() {
		
		//Frame settings
		frame.setPreferredMinMax(-10, 10, -10, 150);
		frame.setVisible(true);
		
		//Prefer to use global variables instead of having to write control.getDouble every time
		gravity = control.getDouble("gravity");
		timeStep = control.getDouble("Time Step");
		springConstant = control.getDouble("Spring Constant");
		personMass = control.getDouble("Person Mass");
		cordMass = control.getDouble("Cord Mass");
		cordLength = control.getDouble("Cord Length");
		bridgeHeight = control.getDouble("Bridge Height");
		segmentNumber = (int) control.getDouble("Number of Segments");
		cordNumber = (int) control.getDouble("Number of Cords");
		
		//Adds the circles representing each particle to the frame
		for (int i = 0; i < segmentNumber; i++) {
			
			Circle circle = new Circle();
			Particle particle = new Particle();
			
			particle.orderPosition = i;
			
			circle.pixRadius = 5;
			
			//Sets particle and circle position so that they're evenly spaced along the length of the cord
			circle.setY(bridgeHeight - ((cordLength/segmentNumber)*i));
			particle.position = bridgeHeight - ((cordLength/segmentNumber)*i);
			particle.originalPosition = bridgeHeight-((cordLength/segmentNumber)*i);
			
			//Adds circles and particle to the array
			particleArray.add(particle);
			circleArray.add(circle);
			
			//Adds circle to the frame
			frame.addDrawable(circle);
			
		}
		
	}
	
	public static void main(String[] args){	
		SimulationControl.createApp(new BungeeJump());
	}
	
	//Updates position of a particle based on the net force exterted on it
	public void updatePosition (Particle particle) {
		
		particle.acceleration = netForce(particle)/(cordMass/segmentNumber);
		particle.velocity += particle.acceleration * timeStep;
		particle.position += particle.velocity * timeStep;
	}
	
	//Calculates the net force on a particle
	public double netForce (Particle particle) {
		
		//Force of gravity only from the particle's own mass: Particle mass * gravity
		double gravityForce = (cordMass/segmentNumber) * gravity;
		
		//Force of gravity from all particles below this particle: (Number of particles below) * (Particle Mass) * gravity
		double restofCordForce = (segmentNumber - particle.orderPosition) * (cordMass/segmentNumber) * gravity;
		
		//Force of gravity from the person: Person mass * gravity
		double personForce = personMass * gravity;
		
		//Force of the bungee chord on the particle upwards. ∆x is the particle's position compared to its starting position. To find k, we 
		//use the spring in a series equation, 1/k = 1/k1+1/k2+... Since all the spring constants are the same for each spring, we can write the
		//equation of the spring constant at the nth particle as 1/k=n/k0 (where k0 is the overall bungee spring constant). We multiply out and get
		//that k=k0/n
		double springUpForce = particle.deltaX * (springConstant/particle.orderPosition);
		
		if (particle.orderPosition == segmentNumber - 1) {
			return gravityForce + personForce + restofCordForce + springUpForce;
		}
		
		else {
			//Opposite of up spring force on next particle
			double springDownForce = -(particle.deltaX) * (springConstant/(particle.orderPosition + 1));
			return gravityForce + personForce + restofCordForce + springUpForce + springDownForce;
		}
		
		
		
		
		
		
	}
	
	
}