import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.*;

public class Joueur {
	public Grille grille;
	public String nom;
	public int[][] etatCases = new int[10][10];
	int[][] placement = new int[10][10];
	Random rand = new Random();
	int mode;
	int dif;
	int orientation = 1;
	int indexBateau = 0;
	public boolean bateauxPlaces = false;
	public boolean tour = false;
	int[][] overlay = new int[10][10];
	Ordinateur ordi;
	JLabel labelNom;

	public Joueur (int mode, int dif, JPanel p) {
		this.mode = mode;
		this.dif = dif;
		p.setPreferredSize(new Dimension(450, 600));
		p.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		Image imageFond = new ImageIcon("img/eau.gif").getImage();//png ou gif, les deux existent
		JPanel panelGrille = new JPanel() { //Création d'un JPanel particulier
			@Override //Redéfinis une méthode existante de JPanel
			protected void paintComponent(Graphics g) { // La méthode en question
				super.paintComponent(g); //Pour bien effacer les anciennes icones
				g.drawImage(imageFond, 0, 0, 400, 400, this); //Dessine l'image dans le panel
			}
		};
		panelGrille.setPreferredSize(new Dimension(400, 400));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		p.add(panelGrille, c);
		grille = new Grille(panelGrille);
		labelNom = new JLabel(nom);
		labelNom.setFont(new Font("Arial", Font.BOLD, 35));
		c.gridy = 0;
		c.insets = new Insets(0, 0, 50, 0);
		p.add(labelNom, c);
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				overlay[i][j] = 3;
			}
		}
	}


	public void placerBateaux() {
		int[] listeBateaux = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
		bateauxPlaces = false;
		for (int i=0; i<10; i++){
			for (int j=0; j<10; j++) {
				int x = i;
				int y = j;
				JButton bouton = grille.getCase(x, y);

				bouton.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseEntered(MouseEvent e) {
						int tailleBateau = listeBateaux[indexBateau];
						if (placementValide(x, y, orientation, tailleBateau)) {
							for (int k=1; k<=tailleBateau; k++){
								if (orientation==1){
									placement[x+k-1][y] = 1000*tailleBateau + 100 + 10*k;
								} else if (orientation==-1) {
									placement[x][y-k+1] = 1000*tailleBateau + 10*k;
								}
							}
						} else {
							for (int k=1; k<=tailleBateau; k++) {
								try {
									if (orientation==1){
										placement[x+k-1][y] = -1;
									} else if (orientation==-1) {
										placement[x][y-k+1] = -1;
									}
								} catch (Exception e2) {};
							}
						}
						fusionGrilles(placement);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						for (int i = 0; i < 10; i++) {
							for (int j = 0; j < 10; j++) {
								placement[i][j] = 0;
							}
						}
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						int tailleBateau = listeBateaux[indexBateau];
						if (SwingUtilities.isRightMouseButton(e)) {
							orientation *= -1;
							mouseExited(e);
							mouseEntered(e);
							return;
						}
						if (!placementValide(x, y, orientation, tailleBateau)) {
							return;
						}
						for (int k=1; k<=tailleBateau; k++){
							if (orientation==1){
								etatCases[x+k-1][y] = 1000*tailleBateau + 100 + 10*k;
							} else if (orientation==-1) {
								etatCases[x][y-k+1] = 1000*tailleBateau + 10*k;
							}
						}
						indexBateau += 1;
						if (indexBateau==listeBateaux.length) { //Fin du placement
							for (int k=0; k<10; k++){
								for (int l=0; l<10; l++){
									JButton bouton = grille.getCase(k, l);
									MouseListener[] mls = bouton.getMouseListeners();
									for (int m=0; m<mls.length; m++) {
										MouseListener ml = mls[m];
										bouton.removeMouseListener(ml);
									}
								}
							}
							bateauxPlaces = true;
						} else {
							mouseExited(e);
							mouseEntered(e);
						}
					}
				});
			}
		}
		if (mode==2) {
			ordi.placementBateaux();
		} 
	}

	public boolean placementValide(int x, int y, int orientation, int tailleBateau) {
		if (orientation==1) {
			for (int i=y-1; i<=y+1; i++) {
				if ((i>=0)&&(i<=9)) {
					for (int j=x-1; j<=x+tailleBateau; j++) {
						try {
							if (etatCases[j][i]>1000) {
								return false;
							}
						} catch (Exception e) {
							if ((x!=0)&&(x!=10-tailleBateau)) {
								return false;
							}
						}
					}
				}
			}
		} else if (orientation==-1) {
			for (int i=x-1; i<=x+1; i++) {
				if ((i>=0)&&(i<=9)) {
					for (int j=y-tailleBateau; j<=y+1; j++) {
						try {
							if (etatCases[i][j]>1000) {
								return false;
							}
						} catch (Exception e) {
							if ((y!=tailleBateau-1)&&(y!=9)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	public void fusionGrilles(int[][] placement) {
		int[][] transfert = new int[10][10];
		for (int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
				transfert[i][j] = etatCases[i][j];
				if ((placement[i][j]>1000)||(placement[i][j]==-1)) {
					transfert[i][j] = placement[i][j];
				}
			}
		}
		grille.update(transfert);
	}

	public void creerOrdi(Joueur ennemi) {
		ordi = new Ordinateur(dif, this, ennemi);
	}
}
