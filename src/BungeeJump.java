import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.DisplayFrame;


public class BungeeJump extends AbstractSimulation{

	DisplayFrame ballFrame = new DisplayFrame("x", "y", "Ball Motion");
	Trail cord = new Trail();
	int k = 1;

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