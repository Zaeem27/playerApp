package player;

public class Client {

	public static void main(String[] args) {
		
		PlayerApp app = PlayerApp.getPlayerApp("Input.txt", 4);
		app.playScenario();
	}

}
