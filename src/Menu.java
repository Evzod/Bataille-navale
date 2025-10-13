import javax.swing.*;
import java.awt.*;

public class Menu {
	/*public static void main (String[] args) { // uniquement pour tester le fichier
		System.out.println("Menu de test lancé");
		JFrame f = new JFrame("Bataille navale -- Menu");
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}*/

	// constructeur
	public Menu() {
		JFrame f = new JFrame("Bataille navale -- Menu");
		f.setSize(900, 600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panelBoutons = new JPanel();
		panelBoutons.setSize(300, 450);
		panelBoutons.setLayout(BorderLayout);
		JButton bPvp = new JButton ("Joueur vs Joueur");
		JButton bPve = new JButton ("Joueur vs Ordinateur");
		JButton bQuitter = new JButton ("Quitter");
		panelBoutons.add(bPvp);
		panelBoutons.add(bPve);
		panelBoutons.add(bQuitter);
		f.add(panelBoutons, BorderLayout.CENTER);
		f.setVisible(true);
		panelBoutons.setVisible(true);
	}


}

