package player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;


public class PlayerAppTest {

	PlayerApp p;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPlayerApp() {
		ArrayList <String> expected = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		expected.add("");
		expected.add("Adventure on the High Seas Narrator Z Welcome! Today we set out on the high seas with Captain A, First Mate B, and their  crew. I’m  skipper  Z  and  I’ll  be  here  during  the  journey  to  describe everything. I’m just joining the crew today as well, so I’ll be learning along with you here. Let’s meet the Captain A and the First Mate B.  Captain A Ahoy skipper Z! I’m Captain A. I’m excited for some new crew members for this journey. We hope to find a magnificent treasure at the end of it! Has First Mate B introduced himself?");
		expected.add("First Mate B Not yet, Captain A!I’m First Mate B, first in command to Captain A. We’re setting off on our journey now, so do you understand our names?   Captain A Alright, then we’re off!  Skipper Z Wow, the air feels so soft and cool, I can smell the salt! Do you hear that music?");
		expected.add("dwdw.wav");
		expected.add("Hold on, what’s that sound, down in the water?");
		expected.add("dwdw.wav");
		expected.add("MerKing E Hello, sailors! I am the MerKing E, lord of the seven seas! This is my right hand man, Merman K. We come to you with a request.  Merman K You see, MerKing E’s daughter, the Mermaid P, has been captured by an evil pirate crew, and we need your help to get her back. Whaddya say?");
		expected.add("First Mate B Well, let’s get to it. We will need to fight them using what we know now of the Braille alphabet. So, how do you feel about our names up to now? There’s A, B, E, K, and P. Got it?  Merman K Thank you, kind pirates! Set sail for the evil pirates, and rescue the Mermaid P!  Skipper Z Off we go, then! The sails have been lowered");
		expected.add("dwdw.wav");
		expected.add("and the ship’s started to skip over the waves");
		expected.add("dwdw.wav");
		expected.add("Whale +Meeeeeeeeeeeeeeeeeeu");
		
		p=new PlayerApp("input.txt");
		p.playScenario();
		System.out.println("Showing...");
		 
		for (int i=0;i<p.ActionList.size();i++) {
			System.out.println(i + " : " + p.ActionList.get(i));
		}
		assertEquals(p.ActionList.size(),expected.size());
		for (int i=0;i<p.ActionList.size();i++){
			//System.out.println(i);
			assertEquals(p.ActionList.get(i),expected.get(i));
		}
	}

	@Test
	public void testPlayScenario() {
		StringBuilder sb = new StringBuilder();
		//input does not specify number of cells and buttons properly
		ArrayList <String> expected1 = new ArrayList<String>();
		expected1.add("First keyword did not specify number of cells and buttons properly.");
		PlayerApp p1=new PlayerApp("input1.txt");
		p1.playScenario();
		for (int i=0;i<p1.ActionList.size();i++) {
			System.out.println(i + " : " + p1.ActionList.get(i));
		}
		assertEquals(p1.ActionList.size(),expected1.size());
		for (int i=0;i<p1.ActionList.size();i++){
			//System.out.println(i);
			assertEquals(p1.ActionList.get(i),expected1.get(i));
			
		}
        //input 2 has button number less than 2
		ArrayList <String> expected2 = new ArrayList<String>();
		expected2.add("Button number is less than two or is greater than thirty.");
		PlayerApp p2=new PlayerApp("input2.txt");
		p2.playScenario();
		for (int i=0;i<p2.ActionList.size();i++) {
			System.out.println(i + " : " + p2.ActionList.get(i));
		}
		assertEquals(p2.ActionList.size(),expected2.size());
		for (int i=0;i<p2.ActionList.size();i++){
			//System.out.println(i);
			assertEquals(p2.ActionList.get(i),expected2.get(i));			
		}
		//input 3 has cell number less than 1
		ArrayList <String> expected3 = new ArrayList<String>();
		expected3.add("Cell number is less than one or is greater than thirty.");
		PlayerApp p3=new PlayerApp("input3.txt");
		p3.playScenario();
		for (int i=0;i<p3.ActionList.size();i++) {
			System.out.println(i + " : " + p3.ActionList.get(i));
		}
		assertEquals(p3.ActionList.size(),expected3.size());
		for (int i=0;i<p3.ActionList.size();i++){
			//System.out.println(i);
			assertEquals(p3.ActionList.get(i),expected3.get(i));
		}
		//input 4 does not exist
		ArrayList <String> expected4 = new ArrayList<String>();
		expected4.add("Error. Please get an adult. File not found.");
		PlayerApp p4=new PlayerApp("input4.txt");
		p4.playScenario();
		for (int i=0;i<p4.ActionList.size();i++) {
			System.out.println(i + " : " + p4.ActionList.get(i));
		}
		assertEquals(p4.ActionList.size(),expected4.size());
		for (int i=0;i<p4.ActionList.size();i++){
			//System.out.println(i);
			assertEquals(p4.ActionList.get(i),expected4.get(i));
		}
		//testing the repeat function
		ArrayList <String> expected5 = new ArrayList<String>();
		expected5.add("");
		expected5.add("Testing Hello! We are testing repeat function.");
		expected5.add("User Please press 1 to repeat first then after repeat press 0 to continue and finish this test scenerio.");
		expected5.add("User Please press 1 to repeat first then after repeat press 0 to continue and finish this test scenerio.");
		expected5.add("");
		PlayerApp p5=new PlayerApp("input5.txt");
		p5.playScenario();
		for (int i=0;i<p5.ActionList.size();i++) {
			System.out.println(i + " : " + p5.ActionList.get(i));
		}
		assertEquals(p5.ActionList.size(),expected5.size());
		for (int i=0;i<p5.ActionList.size();i++){
			//System.out.println(i);
			assertEquals(p5.ActionList.get(i),expected5.get(i));
		}
		
	}
}
