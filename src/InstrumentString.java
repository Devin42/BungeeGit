import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;


public class InstrumentString extends AbstractSimulation{

	//Declare frame
	DisplayFrame frame = new DisplayFrame("x", "y", "Instrument String");
	
	//1m string, 100 mass, 10g total
	//Cord variables
	double cordLength;
	double cordMass;
	double springConstant;
	double numMasses;
	double timeStep;
	
	//ArrayList that holds all the particles, as well as an ArrayList for the corresponding circles that show up on the frame
	ArrayList <Particle> particleArray = new ArrayList <Particle>();
	ArrayList <Circle> circleArray = new ArrayList <Circle>();

	protected void doStep() {
		// TODO Auto-generated method stub

	}
	public void reset() {
		control.setValue("Time Step", .01);
		control.setValue("Spring Constant", 17.6);
		control.setValue("Cord Mass", .01);
		control.setValue("Cord Length", 1);
		control.setValue("Number of Masses", 50);
	}

	public void initialize() {

		//Frame settings
		frame.setPreferredMinMax(-.1, 1.5, -.15, .15);
		frame.setSize(600, 600);
		frame.setVisible(true);
		
		//Prefer to use global variables instead of having to write control.getDouble every time
		timeStep = control.getDouble("Time Step");
		springConstant = control.getDouble("Spring Constant");
		cordMass = control.getDouble("Cord Mass");
		cordLength = control.getDouble("Cord Length");
		numMasses= control.getDouble("Number of Masses");

		//Adds the circles representing each particle to the frame
		for (int i = 0; i < numMasses; i++) {
			
			Circle circle = new Circle();
			Particle particle = new Particle();
			
			//Colors first circle differently
			if (i == 0) {
				circle.color = Color.BLACK;
			}
			
			particle.orderPosition = i;
			
			circle.pixRadius = 5;
			
			particle.xPosition = i * (cordLength/numMasses);
			circle.setX(i * (cordLength/numMasses));
			
			frame.addDrawable(circle);
		}
		
	}

	public void netForce (Particle particle) {
		
		//Force from the particle before
		Particle previousParticle = particleArray.get(particle.orderPosition - 1);
		
		
		
	}
	
	
	public static void main(String[] args) {
		SimulationControl.createApp(new InstrumentString());
	}

}

