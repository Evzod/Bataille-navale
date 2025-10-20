import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Menu extends JDialog {
	/*public static void main (String[] args) { // uniquement pour tester le fichier
		System.out.println("Menu de test lancé");
		JFrame f = new JFrame("Bataille navale -- Menu");
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}*/

	//Variables
	int bAppuye; //1 = pvp, 2 = pve, 3 = quitter

	// Constructeur
	public Menu(JFrame f) {
		super(f, "Bataille navale -- Menu", true );
		/*Création de la fenêtre
		JFrame f = new JFrame("Bataille navale -- Menu");
		f.setSize(1200, 700);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);*/
		//Création des panels imbriqués et des boutons
		JPanel panelB = new JPanel();
		JPanel panel = new JPanel();
		JButton bPvp = new JButton ("Joueur vs Joueur");
		JButton bPve = new JButton ("Joueur vs Ordinateur");
		JButton bQuitter = new JButton ("Quitter");
		//Disposition des boutons
		panelB.setLayout(new GridLayout(3,1, 0, 50));
		panelB.add(bPvp);
		panelB.add(bPve);
		panelB.add(bQuitter);
		panel.add(panelB);
		//Ajout des des actions aux boutons
		bPvp.addActionListener(e -> {
			bAppuye = 1;
			f.dispose();
		});
		bPve.addActionListener(e -> {
			bAppuye = 2;
			f.dispose();
		});
		bQuitter.addActionListener(e -> {
			bAppuye = 3;
			f.dispose();
		});
		//Création du titre et mise en forme
		JLabel titre = new JLabel("Bataille Navale", JLabel.CENTER);
		titre.setFont(new Font("Arial", Font.BOLD, 30));
		titre.setBorder(new EmptyBorder(100, 0, 100, 0));
		//Ajout des éléments à la fenêtre
		f.add(titre, BorderLayout.NORTH);
		f.add(panel, BorderLayout.CENTER);
		f.setVisible(true);
	}

	public int getbAppuye() {
		return bAppuye;
	}

}

