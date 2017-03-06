package player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import audioForPlayerApp.audio;
import simulator.Simulator;

public class PlayerApp {
	
	
	private static PlayerApp instance = null;
	private ArrayList <String> story = new ArrayList <String> ();
	private Simulator sim;
	private int numButton;
	private int numCell;
	private int buttonActivate;
	private int buttonPressed;
	
	
	private PlayerApp(String fileName){
		File file = new File (fileName);
		StringBuilder sb = new StringBuilder();
		Scanner input;
		String line;
		try{
			input = new Scanner(file);
			while(input.hasNextLine()){
				sb.append(input.nextLine()+" ");
			}
			line = sb.toString();
			for (String part : line.split("<")) {
				 part.trim();
				 story.add(part);
			 }
			if (story.size()>1&&story.get(1).matches("![0-9]+![0-9]+!")){
				String cellButtonNum []= story.get(1).split("!");
				this.numCell = Integer.parseInt(cellButtonNum[1]);
				this.numButton = Integer.parseInt(cellButtonNum[2]);
			}
			else{
				throw new IllegalArgumentException("First keyword did not specify number of cells and buttons properly.");
			}
		
			if (numButton <= 1||numButton > 30){
				throw new IllegalArgumentException("Button number is not greater than one or is greater than thirty.");
			}
			
			if (numCell <= 0||numCell > 30){
				throw new IllegalArgumentException("Cell number is not greater than zero or is greater than thirty.");
			}
			sim = new Simulator(numCell, numButton);
			/* System.out.println(line);
			 for (String part : story) {
				 System.out.println(part);
			 }*/
		}catch(IllegalArgumentException e){
			audio.read("Error. Please get an adult. " + e.getMessage());
			e.printStackTrace();
		}catch (FileNotFoundException e) {
		audio.read("Error. Please get an adult. File not found.");
		e.printStackTrace();
		return;
		}
	}
	
	//Singleton for PlayerApp.
	public static PlayerApp getPlayerApp(String fileName){
		if (instance == null){
			instance = new PlayerApp(fileName);
		}
		return instance;
	}
	
	//Plays the text file.
	public void playScenario(){
		if (sim==null){
			
		}
		else{
			int checkpoint = 0;
			for (int counter = 0; counter < story.size();counter++){
				try{
					String line = story.get(counter);
					
					if (line.matches("![A-Z]{1,2}[a-z]!")){//skips to next end
						for (; counter<story.size() && !story.get(counter).equals("!end!");counter++){
						}
					}
					else if(line.matches("![A-Z]{1,2}![a-z]+![a-z[ ]]+!")){
						String parts[] = line.split("!");
						String label = parts[1];
						if (parts[2].length()>numButton){
							throw new IllegalArgumentException("In "+label+" keyword, there are not enough buttons.");
						}
						if (parts[3].length()>numCell){
							throw new IllegalArgumentException("In "+label+" keyword, there are not enough cells.");
						}
						
						label = "!" + label + this.scenario(parts[2], parts[3]) + "!";
						for (counter = 0; counter<story.size() && !story.get(counter).equals(label);counter++){
						}
						sim.clearAllCells();
					}
					else if (line.equals("!end!")|| line.matches("![0-9]+![0-9]+!")){
					}
					
					else if (line.equals("!checkpoint!")){
						checkpoint = counter;
					}
					else if (line.equals("!repeat!")){
						if(repeat()){
							counter = checkpoint;
						}
					}
					else if(line.matches("random[0-9]+")){
						int difficulty = Integer.parseInt(line.substring(6, line.length()));
						if (difficulty > 26){
								throw new IllegalArgumentException("Random keyword has too high difficulty.");
						}
						
						if (difficulty <= 1 || difficulty > numButton){
							throw new IllegalArgumentException("Random keyword has too low difficulty or "
									+ "difficulty exceeds number of buttons availible.");
						}
						
						boolean pass = randomScenario(difficulty);
						if (!pass){
							counter--;
						}
						sim.clearAllCells();
					}
					else if(line.matches("sfx [A-Za-z0-9]+.wav")){
						String [] audioClip = line.split(" ");
						try {
							System.out.println(audioClip[1]); //Play Audio here instead
							audio.play(audioClip[1]);
						} catch(Exception ex){
				        	audio.read("Error. Please get an adult. Sound file not found.");
				            ex.printStackTrace();
				            return;
				        }
					}
					
					else{
						//System.out.println(line); Text to speech
						audio.read(line);
					}
				}catch(IllegalArgumentException e){
					audio.read("Error. Please get an adult. " + e.getMessage());
					e.printStackTrace();
					return;
				}
			}
		}
	}


	//Gets user input for "repeat" keyword and returns a truth value based on user input.
	private boolean repeat() {
		audio.read("Press Button 0 to continue, press Button 1 to repeat");
		int buttonPress = getUserInput(2);
		if(buttonPress==1){
			return true;
		}
		return false;
	}
	
	//Creates action listener for all the buttons for one input only, removing them
	//and input is received.
	private int getUserInput(int numButton){
		Semaphore semaphore = new Semaphore(0);
	
		for (buttonActivate=0;buttonActivate<numButton;buttonActivate++){
			sim.getButton(buttonActivate).addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonPressed = 0;
					while(e.getSource()!= sim.getButton(buttonPressed)){
						buttonPressed++;
					}	
					semaphore.release();
				}
	
	});
		}
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
		}
		for(int buttonActivate = 0; buttonActivate < numButton; buttonActivate++){
			for(ActionListener act : sim.getButton(buttonActivate).getActionListeners()) {
			sim.getButton(buttonActivate).removeActionListener(act);
			}
		}
		
		return buttonPressed;
	}
	
	//Generates a random scenario for "randomX" keyword.
	private boolean randomScenario(int difficulty) {
		int buttonPressed;
		StringBuilder alphabet = new StringBuilder ("abcdefghijklmnopqrstuvwxyz");
		ArrayList <Character> option = new ArrayList <Character>();
		Random random = new Random();
		int index = random.nextInt(alphabet.length());
		char correct = alphabet.charAt(index);
		option.add(correct);
		alphabet.deleteCharAt(index);
		for (int counter = 0; counter < difficulty-1; counter++){
			index = random.nextInt(alphabet.length());
			option.add(alphabet.charAt(index));
			alphabet.deleteCharAt(index);
		}	
		Collections.shuffle(option);
		/*for (char c: option){
		System.out.print(c);
		}*/
		sim.clearAllCells();
		sim.getCell(0).displayCharacter(correct);
		for (int counter = 0; counter<option.size();counter++){
			//System.out.println("Press Button " + counter + " if the letter display is " + option.get(counter)); //Text to speech
		String play = "Press Button " + counter + " if the letter displayed in the first cell is " + option.get(counter);
		audio.read(play);
		}
		buttonPressed = this.getUserInput(difficulty);
		if (option.get(buttonPressed) == correct){
			return true;
		}	
		return false;
	}
	
	//Plays a user generated scenario specified in file.
	private String scenario(String question, String output) {
		int buttonPressed;
		sim.clearAllCells();
		for (int i = 0; i<output.length();i++){
			sim.getCell(i).displayCharacter(output.charAt(i));
		}
		//sim.getCell(0).displayCharacter(question.charAt(question.length()-1));
		
		
		buttonPressed = this.getUserInput(question.length());
		return String.valueOf(question.charAt(buttonPressed));
	}
}
