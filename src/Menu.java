import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Menu extends JDialog {
	int bAppuye; //1 = pvp, 2 = pve, 3 = quitter
	int dif = 0; //1 = facile, 2 = moyen, 3 = difficile

	public Menu(JFrame f, int mode) {
		//Lignes communes aux deux constructeurs
		super(f, null, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//Création des panels communs
		JPanel panelB = new JPanel();
		JPanel panel = new JPanel();
		//Constructeur différent selon le mode (0 = menu principal, 1 = choix difficulté)
		if (mode==0) {
			//Actions sur la fenêtre
			setTitle("Bataille navale -- Menu Principal");
			setSize(1000, 600);
			setLocationRelativeTo(f);
			//Création des boutons
			JButton b1 = new JButton ("Joueur vs Joueur");
			JButton b2 = new JButton ("Joueur vs Ordinateur");
			JButton b3 = new JButton ("Quitter");
			//Disposition des boutons
			panelB.setLayout(new GridLayout(3,1, 0, 50));
			panelB.add(b1);
			panelB.add(b2);
			panelB.add(b3);
			panel.add(panelB);
			//Ajout des actions aux boutons
			b1.addActionListener(e -> {
				bAppuye = 1;
				dispose();
			});
			b3.addActionListener(e -> {
				bAppuye = 3;
				System.exit(0);
				dispose();
			});
			b2.addActionListener(e -> {
				//Ouvre le menu de choix de difficulté
				Menu menuDif = new Menu(f, 1);
				menuDif.setVisible(true);
				bAppuye = 2;
				dif = menuDif.getbAppuye(); //1 = facile, 2 = moyen, 3 = difficile
				dispose();
			});
			//Création du titre et mise en forme
			JLabel titre = new JLabel("Bataille Navale", JLabel.CENTER);
			titre.setFont(new Font("Arial", Font.BOLD, 30));
			titre.setBorder(new EmptyBorder(100, 0, 100, 0));
			add(titre, BorderLayout.NORTH);
			add(panel, BorderLayout.CENTER);
		} else if (mode==1) {//Sous-menu choix difficulté
			//Actions sur la fenêtre
			setTitle("Bataille navale -- Choix de la Difficulté");
			setSize(700, 400);
			setLocationRelativeTo(f);
			//Création des boutons
			JButton b1 = new JButton ("Facile");
			JButton b2 = new JButton ("Moyen");
			JButton b3 = new JButton ("Difficile");
			//Disposition des boutons
			panelB.setLayout(new GridLayout(3,1, 0, 20));
			panelB.add(b1);
			panelB.add(b2);
			panelB.add(b3);
			panel.add(panelB);
			//Ajout des actions aux boutons
			b1.addActionListener(e -> {
				bAppuye = 1;
				dispose();
			});
			b2.addActionListener(e -> {
				bAppuye = 2;
				dispose();
			});
			b3.addActionListener(e -> {
				bAppuye = 3;
				dispose();
			});
			//Création du titre et mise en forme
			JLabel titre = new JLabel("Choix de la difficulté", JLabel.CENTER);
			titre.setFont(new Font("Arial", Font.BOLD, 20));
			titre.setBorder(new EmptyBorder(50, 0, 50, 0));
			add(titre, BorderLayout.NORTH);
			add(panel, BorderLayout.CENTER);
		}
		}

	public int getbAppuye() {
		return bAppuye;
	}

	public int getDif() {
		return dif;
	}

}

