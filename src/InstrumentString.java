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
	double totalspringConstant;
	double numMasses;
	double individualSpringConstant;
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
		totalspringConstant = control.getDouble("Spring Constant");
		individualSpringConstant = totalspringConstant*numMasses;
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
			
			particleArray.add(particle);
			circleArray.add(circle);
			
			frame.addDrawable(circle);
		}
		
	}

	public void netForce (Particle particle) {
		
		//Force from the particle before
		Particle previousParticle = particleArray.get(particle.orderPosition - 1);
		Particle nextParticle = particleArray.get(particle.orderPosition+1);
		
		
		double[] angleInfPrev = angleBetweenTwoParticles(particle, previousParticle);
		double[] angleInfNext = angleBetweenTwoParticles(particle, nextParticle);

		double FTx = angleInfNext[1]*individualSpringConstant*distanceFrom(nextParticle, particle)*Math.cos(angleInfNext[0]) + angleInfPrev[1]*individualSpringConstant*distanceFrom(previousParticle, particle)*Math.cos(angleInfNext[0]);
		
		
		
		
	}
	
	public double[] angleBetweenTwoParticles(Particle P1, Particle P2) {

		double angleinfo[] = new double[3];
		double signC = 1;
		double signS = 1;

		double angle;
		
		double xDiff = P2.xPosition - P1.xPosition;
		double yDiff = P2.yPosition - P1.yPosition;
		
		if(xDiff == 0) {
			angle = Math.PI/2;
		}
		else if(yDiff == 0) {
			angle = 0;
		}
		else {
			angle = Math.abs(Math.atan(yDiff/xDiff));
		}
		
		if(P1.xPosition > P2.xPosition) {
			signC = -1;
		}
		if(P1.yPosition > P2.yPosition) {
			signS = -1;
		}
		
		angleinfo[0] = angle;
		angleinfo[1] = signC;
		angleinfo[2] = signS;
		
		return angleinfo;
	}
	public double distanceFrom(Particle P1, Particle P2) {
		double distanceFrom = Math.sqrt(Math.pow((P1.xPosition - P2.xPosition), 2) + Math.pow((P1.yPosition - P2.yPosition), 2));
		
		return distanceFrom;
	}
	
	
	public static void main(String[] args) {
		SimulationControl.createApp(new InstrumentString());
	}

}

