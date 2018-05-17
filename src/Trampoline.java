import java.util.ArrayList;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display3d.core.Element;
import org.opensourcephysics.display3d.simple3d.ElementCone;
import org.opensourcephysics.display3d.simple3d.ElementEllipsoid;
import org.opensourcephysics.frames.Display3DFrame;

public class Trampoline extends AbstractSimulation{
	
	Display3DFrame frame = new Display3DFrame("Trampoline");
	
	ArrayList <ArrayList<Particle>> springArray = new ArrayList <ArrayList<Particle>>();
	ArrayList <ArrayList<Element>> springSphereArray = new ArrayList <ArrayList<Element>>();
	
	//Declare variables
	double timeStep;
	int springNumber;
	int particlesPerSpring;
	double trampolineRadius;
	double totalSpringConstant;
	double individualSpringConstant;
	double restLength;
	
	protected void doStep() {
		
	}
	
	public void reset() {
		
		control.setValue("Time Step", 1E-6);
		control.setValue("Number of Springs", 8);
		control.setValue("Particles Per Spring", 10);
		control.setValue("Trampoline Radius", 1);
		control.setValue("Spring Constant", 100);
		control.setValue("Rest Length", 0);
		
	}
	
	public void initialize() {
		
		frame.setSize(600, 600);
		
		timeStep = control.getDouble("Time Step");
		springNumber = (int) control.getDouble("Number of Springs");
		particlesPerSpring = (int) control.getInt("Particles Per Spring");
		trampolineRadius = control.getDouble("Trampoline Radius");
		totalSpringConstant = control.getDouble("Spring Constant");
		individualSpringConstant = totalSpringConstant*springNumber;
		restLength = control.getDouble("Rest Length");
		
		for (int i = 0; i < springNumber; i++) {
			
			ArrayList<Particle> spring = new ArrayList<Particle>();
			ArrayList<Element> springSpheres = new ArrayList<Element>();
			
			for (int j = 0; j < particlesPerSpring; j++) {
				
				Particle particle = new Particle();
				Element sphere = new ElementEllipsoid();
				
				double angle = (2 * Math.PI/springNumber) * i;
				double length = trampolineRadius/particlesPerSpring * (j+1);
				
				particle.xPosition = length * Math.cos(angle);
				particle.yPosition = length * Math.sin(angle);
				particle.zPosition = 0;
				
				sphere.setXYZ(particle.xPosition, particle.yPosition, particle.zPosition);
				sphere.setSizeXYZ(.1, .1, .1);
				
				particle.springPosition = i;
				particle.orderPosition = j;
				
				spring.add(particle);
				springSpheres.add(sphere);
				
				frame.addElement(sphere);
				
			}
			
			springArray.add(spring);
			springSphereArray.add(springSpheres);
			
		}
		
	}

	//Calculates the force in the x direction on a particle
	public double xForce (Particle particle) {
		
		Particle previousParticle = springArray.get(particle.springPosition).get(particle.orderPosition - 1);
		Particle nextParticle = springArray.get(particle.springPosition).get(particle.orderPosition + 1);
		
		//Forces in the x direction from both particles (kx * cos)
		double previousForce = individualSpringConstant * (distanceFromLast(previousParticle, particle) - restLength) * 
		(previousParticle.xPositionLast - particle.xPosition)/distanceFromLast(previousParticle,particle);
		
		double nextForce = individualSpringConstant * (distanceFrom(nextParticle, particle) - restLength) * 
				(nextParticle.xPosition - particle.xPosition)/distanceFrom(nextParticle,particle);
		
		return nextForce + previousForce;
	}
	
	//Calculates the force in the y direction on a particle
	public double yForce (Particle particle) {
		
		Particle previousParticle = springArray.get(particle.springPosition).get(particle.orderPosition - 1);
		Particle nextParticle = springArray.get(particle.springPosition).get(particle.orderPosition + 1);
		
		//Forces in the x direction from both particles (kx * cos)
		double previousForce = individualSpringConstant * (distanceFromLast(previousParticle, particle) - restLength) * 
		(previousParticle.yPositionLast - particle.yPosition)/distanceFromLast(previousParticle,particle);
		
		double nextForce = individualSpringConstant * (distanceFrom(nextParticle, particle) - restLength) * 
				(nextParticle.yPosition - particle.yPosition)/distanceFrom(nextParticle,particle);
		
		return nextForce + previousForce;
	}
	
	//Calculates the force in the z direction on a particle
	public double zForce (Particle particle) {
		
		Particle previousParticle = springArray.get(particle.springPosition).get(particle.orderPosition - 1);
		Particle nextParticle = springArray.get(particle.springPosition).get(particle.orderPosition + 1);
		
		//Forces in the x direction from both particles (kx * cos)
		double previousForce = individualSpringConstant * (distanceFromLast(previousParticle, particle) - restLength) * 
		(previousParticle.zPositionLast - particle.zPosition)/distanceFromLast(previousParticle,particle);
		
		double nextForce = individualSpringConstant * (distanceFrom(nextParticle, particle) - restLength) * 
				(nextParticle.zPosition - particle.zPosition)/distanceFrom(nextParticle,particle);
		
		return nextForce + previousForce;
	}

	//Updates the position of the particle
	public void updatePosition (Particle particle) {

		particle.xPositionLast = particle.xPosition;
		particle.yPositionLast = particle.yPosition;

		particle.xAcceleration = xForce(particle)/particle.mass;
		particle.yAcceleration = yForce(particle)/particle.mass;

		particle.xVelocity += particle.xAcceleration * timeStep;
		particle.yVelocity += particle.yAcceleration * timeStep;

		particle.xPosition += particle.xVelocity * timeStep;
		particle.yPosition += particle.yVelocity * timeStep;

		}
	
	
	public double distanceFrom (Particle P1, Particle P2) {
		return Math.sqrt(Math.pow((P1.xPosition - P2.xPosition), 2) + Math.pow((P1.yPosition - P2.yPosition), 2) + Math.pow((P1.zPosition - P2.zPosition), 2));
	}
	
	public double distanceFromLast (Particle P1, Particle P2) {
		return Math.sqrt(Math.pow((P1.xPositionLast - P2.xPosition), 2) + Math.pow((P1.yPositionLast - P2.yPosition), 2) + Math.pow((P1.zPositionLast - P2.zPosition), 2));
	}
	
	public static void main(String[] args) {
		SimulationControl.createApp(new Trampoline());
	}
}
