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
	double k1;
	
	double timeStep;
	
	int segmentNumber;
	int cordNumber;
	
	ArrayList <Particle> particleArray = new ArrayList <Particle>();
	ArrayList <Circle> circleArray = new ArrayList <Circle>();

	double[] springForces; 
			
	protected void doStep() {
		
		this.setDelayTime(1);
		
		System.out.println(particleArray.get(1).velocity);
		System.out.println(particleArray.get(1).velocityLast);
		
		for (int i = 1; i < particleArray.size(); i++) {
			
			//Sets ∆x to the distance between the particle's original position and its current positions
			particleArray.get(i).deltaX = (particleArray.get(i - 1).position- particleArray.get(i).position) - (cordLength/segmentNumber);
			
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
		control.setValue("Spring Constant", 30);
		control.setValue("Person Mass", 50);
		control.setValue("Cord Mass", 10);
		control.setValue("Cord Length", 40);
		control.setValue("Bridge Height", 100);
		control.setValue("Number of Segments", 20);
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
		k1 = springConstant*segmentNumber;
		
		//Adds the circles representing each particle to the frame
		for (int i = 0; i < segmentNumber; i++) {
			
			Circle circle = new Circle();
			Particle particle = new Particle();
			
			if (i== segmentNumber - 1) {
				circle.color = Color.BLUE;
			}
			
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
		
		springForces = new double[segmentNumber - 1];
		
	}
	
	public static void main(String[] args){	
		SimulationControl.createApp(new BungeeJump());
	}
	
	//Updates position of a particle based on the net force exterted on it
	public void updatePosition (Particle particle) {
		
		particle.velocityLast = particle.velocity;
		
		particle.acceleration = netForce(particle)/(cordMass/segmentNumber);
		particle.velocity += particle.acceleration * timeStep;
		particle.position += (particle.velocity + particle.velocityLast)/2 * timeStep;
		
		
		
	}
	
	//Calculates the net force on a particle
	public double netForce (Particle particle) {
		
		double gravityForce = (cordMass/segmentNumber) * gravity;
		
		particle.springForceUp = particle.deltaX * (k1/particle.orderPosition);
		
		springForces[particle.orderPosition - 1] = particle.springForceUp;
		
		double personForce = personMass * gravity;
		
		if (particle.orderPosition == segmentNumber - 1) {
			
			return gravityForce + personForce + particle.springForceUp;
		}
		
		else {

			particle.springForceDown = -particleArray.get(particle.orderPosition + 1).springForceUp;
			
			/*for (int i = particle.orderPosition; i < segmentNumber; i++) {
				
				particle.springForceDown += particleArray.get(i).acceleration * (cordMass/segmentNumber);
				
			}*/
			
			if (particle.orderPosition == segmentNumber - 2) {
				//System.out.println("Second to last Particle ∆x: " + particle.deltaX);
				//System.out.println("Second to last Particle SFU: " + particle.springForceUp);
			}
			
			return gravityForce + particle.springForceUp + particle.springForceDown;
		}
		
	}
	
}