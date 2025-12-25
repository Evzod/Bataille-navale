import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;

public class Ordinateur {
	Joueur joueur;
	Random rand = new Random();

	public Ordinateur(int dif, Joueur joueur) {
		this.joueur = joueur;
	}

	public void placerBateaux() {
		int i = 1;
		while (i<10) {
			int x = rand.nextInt(10);
			int y = rand.nextInt(10);
			int etat = joueur.etatCases[x][y];
			if (rand.nextBoolean()) {
				clicDroit(x, y);
			}
			clicGauche(x, y);
			if (etat!=joueur.etatCases[x][y]) {
				i+=1;
			}
		}
	}
	

	public void clicGauche(int x, int y) {
		JButton bouton = joueur.grille.getCase(x, y);
		MouseEvent clic = new MouseEvent(bouton, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
		0, 20, 20, 1, false, MouseEvent.BUTTON1);
		bouton.dispatchEvent(clic);
	}

	public void clicDroit(int x, int y) {
		JButton bouton = joueur.grille.getCase(x, y);
		MouseEvent clic = new MouseEvent(bouton, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
		0, 20, 20, 1, false, MouseEvent.BUTTON2);
		bouton.dispatchEvent(clic);
	}
}
