package audioForPlayerApp;


import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sun.speech.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public final class audio {

	private audio() {
		
		
	}
	
	
	public static void read(String toRead) {
		//Scanner s = new Scanner(System.in);
		//String toRead = s.nextLine();
		
		
		final String VOICE_NAME ="kevin16";
		VoiceManager vm = VoiceManager.getInstance();
		Voice voice = vm.getVoice(VOICE_NAME);
		voice.allocate();
		voice.speak(toRead);
	}
	
	
	public static void play(String toPlay) {
		try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(toPlay));
            Clip test = AudioSystem.getClip();  

            test.open(ais);
            test.start();

            while (!test.isRunning())
                Thread.sleep(10);
            while (test.isRunning())
                Thread.sleep(10);

            test.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
	}
	
	
	
}
