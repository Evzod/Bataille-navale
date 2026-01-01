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
	Grille grillej1, grillej2;
	Joueur j1, j2;
	JWindow window = new JWindow();
	JFrame f;
	JLabel nomJoueur = new JLabel();
	int tempsChangementJoueur;
	JPanel panelMilieu;
	LabelPerso labelEtape, labelTour, labelNomJoueur;
	Joueur gagnant;

	public Partie(JFrame f, int mode, int difficulte) {
		//Paramétrage
		this.f = f;
		this.mode = mode;
		f.setTitle("Bataille navale -- En jeu");
		JPanel panelj1 = new JPanel(new GridBagLayout());
		JPanel panelj2 = new JPanel(new GridBagLayout());
		f.add(panelj1, BorderLayout.WEST);
		f.add(panelj2, BorderLayout.EAST);
		panelMilieu = new JPanel(new GridBagLayout());
		panelMilieu.setSize(400, 650);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridx = 0;
		c.insets = new Insets(0, 20, 200, 20);
		labelEtape = new LabelPerso("<html><div style='text-align: center'>Placement&nbsp;des<br>bateaux</div></html>", 0, 200);
		labelTour = new LabelPerso("Au tour de :", 0, 100);
		labelNomJoueur = new LabelPerso("texte temporaire", 0, 200);
		panelMilieu.add(labelEtape, c);
		c.gridy = 1;
		c.insets = new Insets(0, 20, 20, 20);
		panelMilieu.add(labelTour, c);
		c.gridy = 2;
		c.insets = new Insets(0, 20, 100, 20);
		panelMilieu.add(labelNomJoueur, c);
		f.add(panelMilieu, BorderLayout.CENTER);
		//Démarrage de la partie
		j1 = new Joueur(1, 0, panelj1);
		j2 = new Joueur(mode, difficulte, panelj2);
		j1.nom = "Joueur 1";
		if (mode==1) {
			tempsChangementJoueur = 4000;
			j2.nom = "Joueur 2";
		} else if (mode==2) {
			tempsChangementJoueur = 1000;
			j2.nom = "Ordinateur";
			j2.creerOrdi(j1);
		}
		Menu menuNom1 = new Menu(f, j1);
		menuNom1.setVisible(true);
		Menu menuNom2 = new Menu(f, j2);
		menuNom2.setVisible(true);
		initWindow();
	}

	public void lancerPartie() {
		placement1();
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (j1.bateauxPlaces) {
					placement2();
					((Timer)evt.getSource()).stop();
				}
			}
		});
		timer.start();
	}

	public void placement1() {
		labelNomJoueur.setText(j1.nom);
		j2.grille.update(j2.overlay); //Pour pouvoir voir la grille adverse au placement ia
		j1.placerBateaux();
	}

	public void placement2() {
		j1.grille.update(j1.overlay);
		if (mode==1) {
			changementJoueur(j2);
			labelEtape.setText("<html><div style='text-align: center'>Placement&nbsp;des<br>bateaux</div></html>");
			j2.grille.update(j2.etatCases);
		}
		j2.placerBateaux();
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (j2.bateauxPlaces) {
					lancerJeu();
					((Timer)evt.getSource()).stop();
				}
			}
		});
		timer.start();
	}

	public void lancerJeu() {
		initTirs(j1, j2);
		initTirs(j2, j1);
		changementJoueur(j1);
		j1.tour = true;
		j1.grille.update(j1.etatCases);
		j2.grille.update(j2.overlay);
	}

	public void initTirs(Joueur allie, Joueur ennemi) {
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				final int x = i;
				final int y = j;
				JButton bouton = ennemi.grille.getCase(x, y);

				bouton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						if (!allie.tour) {
							return;
						}
						allie.tour = false;

						if (ennemi.etatCases[x][y]>1000) {
							if (ennemi.etatCases[x][y]%10==0) {
								labelEtape.setText("Touché !");
								ennemi.etatCases[x][y] += 1;
								ennemi.overlay[x][y] = 1;
								ennemi.grille.update(ennemi.overlay);
								Timer timer1 = new Timer(1500, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent evt) {
										ennemi.etatCases[x][y] += 1;
										ennemi.overlay[x][y] += 1;
										ennemi.grille.update(ennemi.overlay);
										if (Partie.bateauCoule(ennemi, x, y)) {
											GameMain.pause(500);
											labelEtape.setText("Bateau coulé !");
											if ((ennemi.etatCases[x][y]/100)%10==1) {
												for (int k=x-((ennemi.etatCases[x][y]/10)%10)+1; //retour à la ligne 
												k<=x+((ennemi.etatCases[x][y]/1000)%10)-((ennemi.etatCases[x][y]/10)%10); k++) {
													ennemi.overlay[k][y]=ennemi.etatCases[k][y];
												}
											} else if ((ennemi.etatCases[x][y]/100)%10==0) {
												for (int k=y-((ennemi.etatCases[x][y]/1000)%10)+((ennemi.etatCases[x][y]/10)%10); //retour à la ligne
												k<=y+((ennemi.etatCases[x][y]/10)%10)-1; k++) {
													ennemi.overlay[x][k]=ennemi.etatCases[x][k];
												}
											}
											ennemi.grille.update(ennemi.overlay);
										}
									}
								});
								timer1.setRepeats(false);
								timer1.start();
							}

							if (!partieFinie()) {
								allie.tour = true;
								//sdrfgygydfgyhiuhgtfdfgyhiuhytfhguy
							} else {
								finPartie();
							}
						} else if (ennemi.etatCases[x][y]==0) {
							labelEtape.setText("À l'eau !");
							ennemi.overlay[x][y] = 4;
							ennemi.grille.update(ennemi.overlay);
							Timer timer2 = new Timer(2000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent evt) {
									ennemi.overlay[x][y] = ennemi.etatCases[x][y];
									ennemi.grille.update(ennemi.overlay);
									allie.grille.update(allie.overlay);
									ennemi.tour = true;
									changementJoueur(ennemi);
									if ((mode!=2)||(ennemi==j1)) {
										ennemi.grille.update(ennemi.etatCases);
									}
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
		boolean j2gagnant = true;
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				if ((j1.etatCases[i][j]>1000)&&(j1.etatCases[i][j]%10==0)) {
					j2gagnant = false;
				}
			}
		}
		if (j2gagnant) {
			gagnant = j2;
			return true;
		} 
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				if ((j2.etatCases[i][j]>1000)&&(j2.etatCases[i][j]%10==0)) {
					return false;
				}
			}
		}
		gagnant = j1;
		return true;
	}

	public static boolean bateauCoule(Joueur joueur, int x, int y) {
		int nCase = joueur.etatCases[x][y];
		if ((nCase/100)%10==1) {
			for (int i=x-((nCase/10)%10)+1; i<=x+((nCase/1000)%10)-((nCase/10)%10); i++) {
				if (joueur.etatCases[i][y]%10==0) {
					return false;
				}
			}
		} else if ((nCase/100)%10==0) {
			for (int i=y-((nCase/1000)%10)+((nCase/10)%10); i<=y+((nCase/10)%10)-1; i++) {
				if (joueur.etatCases[x][i]%10==0) {
					return false;
				}
			}
		}
		return true;
	}

	public void finPartie() {
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
		JDialog ecranFin = new JDialog();
		ecranFin.setSize(700, 400);
		ecranFin.setResizable(false);
		ecranFin.setTitle("Bataille navale -- Fin de partie");
		ecranFin.setLocationRelativeTo(f);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(30, 0, 30, 0);
		LabelPerso label = new LabelPerso("Victoire de :", 300, 100);
		LabelPerso nom = new LabelPerso(gagnant.nom, 300, 100);
		JButton bouton = new JButton("Quitter le jeu");
		bouton.addActionListener(e -> {
			quitterJeu();
			ecranFin.dispose();
		});
		ecranFin.add(panel);
		panel.add(label, c);
		c.gridy = 1;
		panel.add(nom, c);
		c.gridy = 2;
		panel.add(bouton, c);
		Timer timer = new Timer(4000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				f.setVisible(false);
				ecranFin.setVisible(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
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
		nomJoueur = new JLabel("texte temporaire", 0);
		nomJoueur.setPreferredSize(new Dimension(300, 200));
		nomJoueur.setFont(new Font("Arial", Font.BOLD, 50));
		//nomJoueur.setBackground(Color.BLUE);
		nomJoueur.setOpaque(true);
		window.add(nomJoueur, BorderLayout.CENTER);
	}

	public void changementJoueur(Joueur joueur) {
		labelEtape.setText("Choisissez où tirer");
		labelNomJoueur.setText(joueur.nom);
		nomJoueur.setText(joueur.nom);
		window.setVisible(true);
		f.setVisible(false);
		Timer timer1 = new Timer(tempsChangementJoueur, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				f.setVisible(true);
				window.setVisible(false);
			}
		});
		timer1.setRepeats(false);
		timer1.start();
		if (j2.tour) {
			Timer timer2 = new Timer(tempsChangementJoueur+1500, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					j2.ordi.tir();
				}
			});
			timer2.setRepeats(false);
			timer2.start();	
		}
		
	}

	public void quitterJeu() {
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
		timer.start();
	}

}
