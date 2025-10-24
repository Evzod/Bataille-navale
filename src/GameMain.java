import javax.swing.*;


public class GameMain {
	public static void main (String[] args) {
		//Création de la fenêtre principale
		JFrame f = new JFrame("Bataille navale");
		f.setSize(1200, 700);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		//Appel du menu
		Menu menu1 = new Menu(f, 0);
		menu1.setVisible(true);
		//Traitement du choix du menu
		Partie partie = new Partie(f, menu1.getbAppuye(), menu1.getDif());
		//Temporaire
		System.out.println(menu1.getbAppuye());
		System.out.println(menu1.getDif());
		System.exit(0);
	}

}
