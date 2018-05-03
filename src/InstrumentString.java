import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;


public class InstrumentString extends AbstractSimulation{

	//1m string, 100 mass, 10g total
	//Cord variables
	double cordLength;
	double cordMass;
	double springConstant;
	double numMasses;
	double timeStep;



	protected void doStep() {
		// TODO Auto-generated method stub

	}
	public void reset() {

		control.setValue("Time Step", .01);


	}

	public void initialize() {

	}

	public static void main(String[] args) {

	}

}

