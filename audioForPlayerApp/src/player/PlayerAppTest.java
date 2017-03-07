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
		expected.add("First Mate B Not yet, Captain A!I’m First Mate B, first in command to Captain A. We’re setting off on our journey now, so do you understand our names?");
		expected.add("Captain A Alright, then we’re off!  Skipper Z Wow, the air feels so soft and cool, I can smell the salt! Do you hear that music?");
		expected.add("dwdw.wav");
		expected.add("Hold on, what’s that sound, down in the water?");
		expected.add("dwdw.wav");
		expected.add("MerKing E Hello, sailors! I am the MerKing E, lord of the seven seas! This is my right hand man, Merman K. We come to you with a request.  Merman K You see, MerKing E’s daughter, the Mermaid P, has been captured by an evil pirate crew, and we need your help to get her back. Whaddya say?");
		expected.add("First Mate B Well, let’s get to it. We will need to fight them using what we know now of the Braille alphabet. So, how do you feel about our names up to now? There’s A, B, E, K, and P. Got it?");
		expected.add("Merman K Thank you, kind pirates! Set sail for the evil pirates, and rescue the Mermaid P!  Skipper Z Off we go, then! The sails have been lowered");
		expected.add("dwdw.wav");
		expected.add("and the ship’s started to skip over the waves");
		expected.add("dwdw.wav");
		expected.add("!  First Mate B But there’s something in our way! What is that?  Whale Meeeeeeeeeu.  First Mate B Oh no! It’s a whale! What a meanie, getting in our way! C’mon, crew, let’s talk him into going!  Whale Meeeeeeeeeeeu!");
		expected.add("MerKing E Of course! Help me out with this, will you, sailor ? Press button 0 if letter A is displayed, or button 1 if letter Z is displayed.");
		expected.add("");
		expected.add("MerKing E Help me with another one.");
		expected.add("Press button 0 if dog is displayed, or button 1 if cat is displayed.");
		expected.add("Incorrect try again.");
		expected.add("Press button 0 if dog is displayed, or button 1 if cat is displayed.");
		expected.add("");
		expected.add("The last problem changes if you get it wrong.");
		expected.add("Merman K Good job! You did it!  Whale Meeeeeeeeeeeeeeeeeeu");
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
		//fail("Not yet implemented");
	}

}
