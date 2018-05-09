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
	double totalSpringConstant;
	double numMasses;
	double individualSpringConstant;
	double timeStep;
	double totalTime;
	
	//ArrayList that holds all the particles, as well as an ArrayList for the corresponding circles that show up on the frame
	ArrayList <Particle> particleArray = new ArrayList <Particle>();
	ArrayList <Circle> circleArray = new ArrayList <Circle>();

	protected void doStep() {
	
		//Makes program fun faster
		for (int z = 0; z < 100; z++) {
		
			particleArray.get(0).yPosition = .1*Math.sin(totalTime);
			circleArray.get(0).setY(.1*Math.sin(totalTime));
			
			for (int i = 1; i < numMasses - 1; i++) {
				updatePosition(particleArray.get(i));
				circleArray.get(i).setXY(particleArray.get(i).xPosition, particleArray.get(i).yPosition);
				
			}
			
			totalTime += timeStep;
			System.out.println();
		}
	}
	
	public void reset() {
		control.setValue("Time Step", .000001);
		control.setValue("Spring Constant", 2000);
		control.setValue("Cord Mass", .01);
		control.setValue("Cord Length", 1);
		control.setValue("Number of Masses", 50);
	}

	public void initialize() {
		
		//Makes doStep run faster
		this.setDelayTime(0);
		
		//Frame settings
		frame.setPreferredMinMax(-.1, 1.5, -.15, .15);
		frame.setSize(600, 600);
		frame.setVisible(true);
		
		//Prefer to use global variables instead of having to write control.getDouble every time
		timeStep = control.getDouble("Time Step");
		totalSpringConstant = control.getDouble("Spring Constant");
		cordMass = control.getDouble("Cord Mass");
		cordLength = control.getDouble("Cord Length");
		numMasses= control.getDouble("Number of Masses");
		individualSpringConstant = totalSpringConstant*numMasses;

		//Adds the circles representing each particle to the frame
		for (int i = 0; i < numMasses; i++) {
			
			Circle circle = new Circle();
			Particle particle = new Particle();
			
			//Colors first circle differently
			if (i == 0) {
				circle.color = Color.BLACK;
			}
			
			particle.orderPosition = i;
			particle.mass = cordMass / numMasses;
			
			circle.pixRadius = 5;
			
			particle.xPosition = i * (cordLength/numMasses);
			circle.setX(i * (cordLength/numMasses));
			
			particleArray.add(particle);
			circleArray.add(circle);
			
			frame.addDrawable(circle);
		}
		
	}

	/*public void netForce (Particle particle) {
		
		//Force from the particle before
		Particle previousParticle = particleArray.get(particle.orderPosition - 1);
		Particle nextParticle = particleArray.get(particle.orderPosition+1);
		
		
		double[] angleInfPrev = angleBetweenTwoParticles(particle, previousParticle);
		double[] angleInfNext = angleBetweenTwoParticles(particle, nextParticle);

		double FTx = angleInfNext[1]*individualSpringConstant*distanceFrom(nextParticle, particle)*Math.cos(angleInfNext[0]) + angleInfPrev[1]*individualSpringConstant*distanceFrom(previousParticle, particle)*Math.cos(angleInfNext[0]);
		
		
		
		
	}*/
	
	//Returns the force in the x direction on a particle
	public double xForce (Particle particle) {
		
		//Particles before and after the current one on the string
		Particle previousParticle = particleArray.get(particle.orderPosition - 1);
		Particle nextParticle = particleArray.get(particle.orderPosition + 1);
		
		//Forces in the x direction from both particles (kx * cos)
		double previousForce = individualSpringConstant * distanceFrom(previousParticle, particle) * 
		(previousParticle.xPosition - particle.xPosition)/distanceFrom(previousParticle,particle);
		
		double nextForce = individualSpringConstant * distanceFrom(nextParticle, particle) * 
		(nextParticle.xPosition - particle.xPosition)/distanceFrom(nextParticle,particle);
		
		return nextForce + previousForce;
		
	}
	
	//Returns the force in the y direction on a particle
	public double yForce (Particle particle) {
		
		//Particles before and after the current one on the string
		Particle previousParticle = particleArray.get(particle.orderPosition - 1);
		Particle nextParticle = particleArray.get(particle.orderPosition + 1);
		
		//Forces in the x direction from both particles (kx * cos)
		double previousForce = individualSpringConstant * distanceFrom(previousParticle, particle) * 
		(previousParticle.yPosition - particle.yPosition)/distanceFrom(previousParticle,particle);
		
		double nextForce = individualSpringConstant * distanceFrom(nextParticle, particle) * 
		(nextParticle.yPosition - particle.yPosition)/distanceFrom(nextParticle,particle);
		
		return nextForce + previousForce;
		
	}
	
	//Updates the position of the particle
	public void updatePosition (Particle particle) {
		
		particle.xAcceleration = xForce(particle)/particle.mass;
		particle.yAcceleration = yForce(particle)/particle.mass;
		
		particle.xVelocity += particle.xAcceleration * timeStep;
		particle.yVelocity += particle.yAcceleration * timeStep;
		
		particle.xPosition += particle.xVelocity * timeStep;
		particle.yPosition += particle.yVelocity * timeStep;
		
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
	
	//Returns the distance between two particles
	public double distanceFrom(Particle P1, Particle P2) {
		return Math.sqrt(Math.pow((P1.xPosition - P2.xPosition), 2) + Math.pow((P1.yPosition - P2.yPosition), 2));
	}
	
	public static void main(String[] args) {
		SimulationControl.createApp(new InstrumentString());
	}

}

