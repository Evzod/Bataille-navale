import javax.swing.*;
import java.awt.*;

public class Partie {
	int mode; //1 = pvp, 2 = pve
	int difficulte; //1 = facile, 2 = moyen, 3 = difficile
	Grille grillej1;
	Grille grillej2;
	Joueur j1, j2;

	public Partie(JFrame f, int mode, int difficulte) {
		//Paramétrage de la fenêtre
		f.setTitle("Bataille navale -- En jeu");
		JPanel panelj1 = new JPanel(new GridBagLayout());
		JPanel panelj2 = new JPanel(new GridBagLayout());
		f.add(panelj1, BorderLayout.WEST);
		f.add(panelj2, BorderLayout.EAST);
		//Démarrage de la partie;
		Joueur j1 = new Joueur(1, 0, panelj1);
		Joueur j2 = new Joueur(mode, difficulte, panelj2);
		j1.placerBateaux();
		j2.placerBateaux();
	}
}
