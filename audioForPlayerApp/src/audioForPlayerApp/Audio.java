package audioForPlayerApp;


import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import com.sun.speech.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public final class Audio {

	private Audio() {
		
		
	}
	
	// Reads the string toRead using free tts
	public static void read(String toRead) {
		final String VOICE_NAME ="kevin16";
		VoiceManager vm = VoiceManager.getInstance();
		Voice voice = vm.getVoice(VOICE_NAME);
		voice.setStyle("casual");
		voice.allocate();
		voice.speak(toRead);
		voice.deallocate();
		// for testing purposes only, not part of program
		System.out.println(toRead.trim());
	}
	
	//Plays the audio file (.wav) specified by string in argument.
	public static void play(String toPlay) throws Exception {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(toPlay));
            Clip test = AudioSystem.getClip();  

            test.open(ais);
            test.start();

            while (!test.isRunning())
                Thread.sleep(10);
            while (test.isRunning())
                Thread.sleep(10);

            test.close();
         // for testing purposes only, not part of program
            System.out.println(toPlay);
        }
	
	
	
}
