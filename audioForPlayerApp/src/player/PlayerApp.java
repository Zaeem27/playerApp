package player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerApp {
	
	private static PlayerApp instance = null;
	private ArrayList <String> story = new ArrayList <String> ();
	
	private PlayerApp(String fileName){
		File file = new File (fileName);
		StringBuilder sb = new StringBuilder();
		Scanner input;
		String line;
		try {
			input = new Scanner(file);
			while(input.hasNextLine()){
				sb.append(input.nextLine()+" ");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		line = sb.toString();
		
		
		 for (String part : line.split("<")) {
			 part.trim();
			 story.add(part);
		 }
		 
	/*	 System.out.println(line);
		 for (String part : story) {
			 System.out.println(part);
		 }*/
	}
	
	public static PlayerApp getPlayerApp(String fileName){
		if (instance == null){
			instance = new PlayerApp(fileName);
		}
		return instance;
	}
	
	public void playScenario(){
		int checkpoint = 0;
		for (int counter = 0; counter < story.size();counter++){
			String line = story.get(counter);
			
			if (line.equals("read")){
				checkpoint = counter;
			}
			else if (line.equals("repeat")){
				if(repeat()){
					counter = checkpoint;
				}
			}
			else if(line.matches("random[2-5]")){
				
			}
			else if(line.matches("sfx [A-Za-z]+.wav")){
				String [] audioClip = line.split(" ");
				System.out.println(audioClip[1]); //Play Audio
			}
			
			else{
				System.out.println(line); //Text to speech
			}
		}
	}

	private boolean repeat() {
		Scanner input = new Scanner(System.in);
		int i =input.nextInt();
		if(i==1){
			return true;
		}
		return false;
	}
	
	
	
}
