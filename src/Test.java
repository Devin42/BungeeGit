import javax.media.j3d.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class Test {

	public static Mixer mixer;
	public static Clip clip;
	
	public static void main(String[] args) {
		
		Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
		
		mixer = AudioSystem.getMixer(mixInfo[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {clip = (Clip) mixer.getLine(dataInfo); }
		catch(LineUnavailableException lue) {lue.printStackTrace();}
		
		
		
		
	}
}
