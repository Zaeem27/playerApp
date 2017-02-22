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

import simulator.Simulator;

public class PlayerApp {
	
	private static final int CELL_NUM = 1;
	private static final int MAX_BUTTON = 5;
	private static PlayerApp instance = null;
	private ArrayList <String> story = new ArrayList <String> ();
	private Simulator sim;
	private String numButton;
	private int buttonActivate;
	private int buttonPressed;
	
	
	private PlayerApp(String fileName, int numButton){
		File file = new File (fileName);
		StringBuilder sb = new StringBuilder();
		Scanner input;
		String line;
		try {
			if (numButton == 1 || numButton> MAX_BUTTON){
				throw new IllegalArgumentException();
			}
			sim = new Simulator(CELL_NUM, numButton);
			input = new Scanner(file);
			while(input.hasNextLine()){
				sb.append(input.nextLine()+" ");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Button number is not greater than one or is greater than " + MAX_BUTTON + ".");
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		line = sb.toString();
		for (String part : line.split("<")) {
			 part.trim();
			 story.add(part);
		 }
		this.numButton = Integer.toString(numButton);
		 
	/*	 System.out.println(line);
		 for (String part : story) {
			 System.out.println(part);
		 }*/
	}
	
	public static PlayerApp getPlayerApp(String fileName, int numButton){
		if (instance == null){
			instance = new PlayerApp(fileName, numButton);
		}
		return instance;
	}
	
	public void playScenario(){
		int checkpoint = 0;
		for (int counter = 0; counter < story.size();counter++){
			String line = story.get(counter);
			
			if (line.matches("![A-Z]{1,2}[a-z]!")){//skips to next end
				for (; counter<story.size() && !story.get(counter).equals("!end!");counter++){
				}
			}
			else if(line.matches("![A-Z]{1,2}[a-z]{3," + (Integer.parseInt(numButton)+1) +"}!")){
				String identifier = line.replace("!","");
				String question = identifier.replaceAll("[A-Z]", "");
				String label = identifier.replaceAll("[a-z]", "");
				label = "!" + label + this.scenario(question) + "!";
				for (; counter<story.size() && !story.get(counter).equals(label);counter++){
				}
			}
			else if (line.equals("!end!")){
			}
			
			else if (line.equals("!checkpoint!")){
				checkpoint = counter;
			}
			else if (line.equals("!repeat!")){
				if(repeat()){
					counter = checkpoint;
				}
			}
			else if(line.matches("random[2-"+ numButton +"]")){
				int difficulty = Integer.parseInt(line.substring(6, line.length()));
				boolean pass = randomScenario(difficulty);
				if (!pass){
					counter--;
				}
				sim.clearAllCells();
			}
			else if(line.matches("sfx [A-Za-z]+.wav")){
				String [] audioClip = line.split(" ");
				System.out.println(audioClip[1]); //Play Audio here instead
			}
			
			else{
				System.out.println(line); //Text to speech
			}
		}
	}



	private boolean repeat() {
		int buttonPress = getUserInput(2);
		if(buttonPress==1){
			return true;
		}
		return false;
	}
	
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
		for (int counter = 0; counter<option.size();counter++){
			System.out.println("Press Button " + counter + " if the letter display is " + option.get(counter)); //Text to speech
		}
		sim.getCell(0).displayCharacter(correct);
		buttonPressed = this.getUserInput(difficulty);
		if (option.get(buttonPressed) == correct){
			return true;
		}	
		return false;
	}
	
	private String scenario(String question) {
		int buttonPressed;
		sim.getCell(0).displayCharacter(question.charAt(question.length()-1));
		buttonPressed = this.getUserInput(question.length()-1);
		return String.valueOf(question.charAt(buttonPressed));
	}
}