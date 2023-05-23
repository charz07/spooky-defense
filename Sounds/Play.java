import java.applet.*;
import java.io.File;
import java.net.*;
  import java.io.File;
    import java.io.IOException;
    import java.net.MalformedURLException;
    import javax.sound.sampled.AudioInputStream;
    import javax.sound.sampled.AudioSystem;
    import javax.sound.sampled.Clip;

public class Play
{
	public static void main(String[]args)
	{
		playIt("click.wav");
	}
	public static void playIt(String filename)
	{
		try
		{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace(System.out);
		}
	}
}
