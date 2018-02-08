import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;


public class BungeeJump extends AbstractSimulation{

	DisplayFrame ballFrame = new DisplayFrame("x", "y", "Ball Motion");
	Trail cord = new Trail();
	double cordLegnth = 40;
	double bridgeHeight = 100;
	double personMass = 50;
	double ropeMass = 10;
	double NOS = 50; //NOS: number of segments
	double k = 10; //spring constant
	double k1 = NOS*k; //spring constant of one
	
	protected void doStep() {

		cord.clear();


		k++;
		System.out.println(k);
		
		double[] xcoord = {1+k,2+k,3+k};
		double[] ycoord = {1+k,2+k,3+k};

		for(int i = 0; i < xcoord.length; i ++) {
			cord.addPoint(xcoord[i], ycoord[i]);
		}

	}


	public void reset() {

		k = 0;
		
	}

	public void initialize() {
		ballFrame.addDrawable(cord);
		ballFrame.setVisible(true);
		ballFrame.setBounds(100, 100, 500, 500);
	}

	public static void main(String[] args){	
		SimulationControl.createApp(new BungeeJump());
	}

}