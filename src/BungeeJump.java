import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;


public class BungeeJump extends AbstractSimulation{

	//Declare frame
	DisplayFrame frame = new DisplayFrame("x", "y", "BungeeJump");
	
	//Cord variables
	double cordLength;
	double cordMass;
	double springConstant;
	
	//Other adjustable variables
	double bridgeHeight;
	double gravity;
	double personMass;
	double timeStep;
	
	//How many segments the cord is split up into
	int segmentNumber;
	
	//ArrayList that holds all the particles, as well as an ArrayList for the corresponding circles that show up on the frame
	ArrayList <Particle> particleArray = new ArrayList <Particle>();
	ArrayList <Circle> circleArray = new ArrayList <Circle>();

	protected void doStep() {
		
		//Makes doStep run faster
		this.setDelayTime(1);
		
		//Starts at 1 so that the top particle remains "connected" to the bridge
		for (int i = 1; i < particleArray.size(); i++) {
			
			//Sets ∆x to the difference between the particle's current distance from the one above it and the particle's original distance from the one above it
			particleArray.get(i).deltaX = (particleArray.get(i - 1).position- particleArray.get(i).position) - (cordLength/((segmentNumber - 1)));
			
			//Sets circle position to particle position
			circleArray.get(i).setY(particleArray.get(i).position);
			
			//Updates particle position based on net force
			updatePosition(particleArray.get(i));
			
		}
		
	}

	public void reset() {
		
		//All values that can be changed by the user
		control.setValue("gravity", -9.8);
		control.setValue("Time Step", .01);
		control.setValue("Spring Constant", 17.6);
		control.setValue("Person Mass", 50);
		control.setValue("Cord Mass", 10);
		control.setValue("Cord Length", 40);
		control.setValue("Bridge Height", 100);
		control.setValue("Number of Segments", 50);
		
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
		
		//Adds the circles representing each particle to the frame
		for (int i = 0; i < segmentNumber; i++) {
			
			Circle circle = new Circle();
			Particle particle = new Particle();
			
			//Colors last circle green, representing the person
			if (i== segmentNumber - 1) {
				circle.color = Color.GREEN;
			}
			
			particle.orderPosition = i;
			
			circle.pixRadius = 5;
		
			//Sets particle and circle position so that they're evenly spaced along the length of the cord
			circle.setY(bridgeHeight - ((cordLength/segmentNumber)*i));
			particle.position = bridgeHeight - ((cordLength/segmentNumber)*i);
			
			//Adds circles and particle to the array
			particleArray.add(particle);
			circleArray.add(circle);
			
			//Adds circle to the frame
			frame.addDrawable(circle);
		}
		
		//Adds bridge to the frame
		DrawableShape bridge = DrawableShape.createRectangle(0, bridgeHeight, 400, 3);
		bridge.color = Color.GRAY;
		bridge.edgeColor = Color.BLACK;
		frame.addDrawable(bridge);
		
		//Adds water to the frame
		DrawableShape water = DrawableShape.createRectangle(0, -100, 400, 200);
		water.color = Color.BLUE;
		water.edgeColor = Color.BLACK;
		frame.addDrawable(water);
	}
	
	public static void main(String[] args){	
		SimulationControl.createApp(new BungeeJump());
	}
	
	//Updates position of a particle based on the net force exterted on it
	public void updatePosition (Particle particle) {
		
		//Uses Fnet = ma to find the particle's acceleration
		particle.acceleration = netForce(particle)/(cordMass/segmentNumber);
		
		//Finds the particle's velocity based on acceleration
		particle.velocity += particle.acceleration * timeStep;
		
		//Finds the particle's position based on velocity
		particle.position += particle.velocity * timeStep;
		
	}
	
	//Calculates the net force on a particle
	public double netForce (Particle particle) {
		
		//The force of gravity exerted on the particle (Fg = mg)
		double gravityForce = (cordMass/segmentNumber) * gravity;
		
		//The spring force from the spring above the particle is just Fsp = k∆x
		particle.springForceUp = particle.deltaX * (springConstant * segmentNumber);
		
		//Force of gravity of the person exerted on the bottom particle
		double personForce = personMass * gravity;
		
		//Bottom particle (the person) has the gravity of the person and the spring force up
		if (particle.orderPosition == segmentNumber - 1) {
			return personForce + particle.springForceUp;
		}
		
		//All other particles have the force of gravity, the force of the spring above, and the force of the spring below
		else {
			
			//The force of the spring pulling down is equal and opposite to the force pulling up on the particle blow
			particle.springForceDown = -particleArray.get(particle.orderPosition + 1).springForceUp;
			return gravityForce + particle.springForceUp + particle.springForceDown;
		}
		
	}
	
}