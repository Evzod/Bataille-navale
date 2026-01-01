import javax.swing.*;
public class GameMain {
	public static void main (String[] args) {
		//Création de la fenêtre principale
		JFrame f = new JFrame("Bataille navale");
		f.setSize(1300, 750); //à changer
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		Menu menu1 = new Menu(f, 0);
		menu1.setVisible(true);
		Partie partie = new Partie(f, menu1.getbAppuye(), menu1.getDif());
		f.setVisible(true);
		partie.lancerPartie();
	}

	public static void pause(int ms) {
		try {
			Thread.sleep(ms);
		} catch(InterruptedException ex) {}
	}

}
