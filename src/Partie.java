import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;

public class Partie {
	int mode; //1 = pvp, 2 = pve
	int difficulte; //1 = facile, 2 = moyen, 3 = difficile
	Grille grillej1;
	Grille grillej2;
	Joueur j1, j2;
	JWindow window = new JWindow();
	JFrame f;

	public Partie(JFrame f, int mode, int difficulte) {
		//Paramétrage de la fenêtre
		this.f = f;
		f.setTitle("Bataille navale -- En jeu");
		JPanel panelj1 = new JPanel(new GridBagLayout());
		JPanel panelj2 = new JPanel(new GridBagLayout());
		f.add(panelj1, BorderLayout.WEST);
		f.add(panelj2, BorderLayout.EAST);
		//Démarrage de la partie;
		j1 = new Joueur(1, 0, panelj1);
		j2 = new Joueur(mode, difficulte, panelj2);
		j1.nom = "Joueur 1";
		j2.nom = "Joueur 2";
		initWindow();
	}

	public void lancerPartie() {
		j1.placerBateaux();
		j2.placerBateaux();
		while ((j1.bateauxPlaces==false)||(j2.bateauxPlaces==false)) {
			System.out.println("Bateaux en cours de placement...");
			GameMain.pause(1000);
			System.out.println("...");
			GameMain.pause(1000);
		}
		System.out.println("Tous les bateaux sont placés !");

		initTirs(j1, j2);
		initTirs(j2, j1);
		j1.tour = true;
		j2.grille.update(j2.overlay);
	}

	public void initTirs(Joueur allie, Joueur ennemi) {
		for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
				ennemi.overlay[i][j] = 3;
                final int x = i;
                final int y = j;
                JButton bouton = ennemi.grille.getCase(x, y);

                bouton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
						
						if (allie.tour == false) {
							System.out.println("Erreur : mauvaise grille");
							return;
						}
						allie.tour = false;

                    	if (ennemi.etatCases[x][y]>1000) {
							if (ennemi.etatCases[x][y]%10==0) {
								System.out.println("Touché !");
								ennemi.etatCases[x][y] += 1;
								ennemi.overlay[x][y] = 1;
								ennemi.grille.update(ennemi.overlay);
								Timer timer1 = new Timer(1500, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent evt) {
										ennemi.etatCases[x][y] += 1;
										ennemi.overlay[x][y] += 1;
										ennemi.grille.update(ennemi.overlay);
										if (bateauCoule(ennemi, x, y)) {
											GameMain.pause(500);
											System.out.println("Bateau coulé !");
											if ((ennemi.etatCases[x][y]/100)%10==1) {
												for (int k=x-((ennemi.etatCases[x][y]/10)%10)+1; //retour à la ligne 
												k<=x+((ennemi.etatCases[x][y]/1000)%10)-((ennemi.etatCases[x][y]/10)%10); k++) {
													ennemi.overlay[k][y]=ennemi.etatCases[k][y];
												}
											} else if ((ennemi.etatCases[x][y]/100)%10==0) {
												for (int k=y+((ennemi.etatCases[x][y]/10)%10)-1; //retour à la ligne
												k<=y-((ennemi.etatCases[x][y]/1000)%10)+((ennemi.etatCases[x][y]/10)%10); k++) {
													ennemi.overlay[x][k]=ennemi.etatCases[x][k];
												}
											}
											ennemi.grille.update(ennemi.overlay);
										}
									}
								});
								timer1.setRepeats(false);
								timer1.start();
							} else {
								System.out.println("Erreur : Tir invalide; Veuillez rejouer");
							}

							if (!partieFinie()) {
								allie.tour = true;
							} else {
								finPartie();
							}
						} else if (ennemi.etatCases[x][y]==0) {
							System.out.println("PLOUF!!!");
							ennemi.overlay[x][y] = 4;
							ennemi.grille.update(ennemi.overlay);
							Timer timer2 = new Timer(2000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent evt) {
									ennemi.overlay[x][y] = ennemi.etatCases[x][y];
									ennemi.grille.update(ennemi.overlay);
									allie.grille.update(allie.overlay);
									changementJoueur(ennemi);
									ennemi.grille.update(ennemi.etatCases);
									ennemi.tour = true;
								}
							});
							timer2.setRepeats(false);
							timer2.start();
						}
                    }
                });
            }
        }
	}

	public boolean partieFinie() {
		boolean fin = true;
		for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
				if ((j1.etatCases[i][j]>1000)&&(j1.etatCases[i][j]%10==0)) {
					fin = false;
				}
			}
		}
		if (fin) {
			return fin;
		} 
		for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
				if ((j2.etatCases[i][j]>1000)&&(j2.etatCases[i][j]%10==0)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean bateauCoule(Joueur joueur, int x, int y) {
		int nCase = joueur.etatCases[x][y];
		if ((nCase/100)%10==1) {
			for (int i=x-((nCase/10)%10)+1; i<=x+((nCase/1000)%10)-((nCase/10)%10); i++) {
				if (joueur.etatCases[i][y]%10==0) {
					return false;
				}
			}
		} else if ((nCase/100)%10==0) {
			for (int i=y+((nCase/10)%10)-1; i<=y-((nCase/1000)%10)+((nCase/10)%10); i++) {
				if (joueur.etatCases[x][i]%10==0) {
					return false;
				}
			}
		}
		return true;
	}

	public int nChiffre(int nombre, int rang) { //ne sert à rien
		String s = Integer.toString(nombre);
		int n = Integer.parseInt(s.substring(rang, rang+1));
		return n;
	}

	public void finPartie() {
		System.out.println("La partie est finie !");
		for (int k=0; k<10; k++){
            for (int l=0; l<10; l++){
                JButton bouton1 = j1.grille.getCase(k, l);
                MouseListener[] mls1 = bouton1.getMouseListeners();
            	for (int m=0; m<mls1.length; m++) {
                    MouseListener ml = mls1[m];
                    bouton1.removeMouseListener(ml);
                }
				JButton bouton2 = j2.grille.getCase(k, l);
                MouseListener[] mls2 = bouton2.getMouseListeners();
				for (int m=0; m<mls2.length; m++) {
                    MouseListener ml = mls2[m];
                    bouton2.removeMouseListener(ml);
                }
            }
        }
	}

	public void initWindow() {
		window.setSize(1000, 600);
		window.setLocationRelativeTo(f);
		window.setAlwaysOnTop(true);
		window.setLayout(new BorderLayout());
		JLabel label = new JLabel("Changement de joueur, au tour de :", 0);
		label.setPreferredSize(new Dimension(300, 200));
		label.setFont(new Font("Arial", Font.BOLD, 50));
		//label.setBackground(Color.RED);
		label.setOpaque(true);
		window.add(label, BorderLayout.NORTH);
	}

	public void changementJoueur(Joueur joueur) {
		JLabel nomJoueur = new JLabel(joueur.nom, 0);
		nomJoueur.setPreferredSize(new Dimension(300, 200));
		nomJoueur.setFont(new Font("Arial", Font.BOLD, 40));
		//nomJoueur.setBackground(Color.BLUE);
		nomJoueur.setOpaque(true);
		window.add(nomJoueur, BorderLayout.CENTER);
		window.setVisible(true);
		f.setVisible(false);
		Timer timer3 = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				f.setVisible(true);
			window.setVisible(false);
			}
		});
		timer3.setRepeats(false);
		timer3.start();		
	}
}
