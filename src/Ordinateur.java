import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Ordinateur {
    int x = -1;
    int xTouche = -1;
    int y, yTouche;
    Random rand = new Random();
    Joueur allie;
    Joueur ennemi;
    int dif;
    int [][] Proba = new int [10][10];
    int nbCases;

    public Ordinateur(int dif, Joueur allie, Joueur ennemi) {
        this.allie = allie;
        this.ennemi = ennemi;
        this.dif = dif;
        tableau();
        nbCases = 0;
    }

    public void placementBateaux() {
        int i = 0;
        while(i<10) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            int etat = allie.etatCases[x][y];
            if (rand.nextInt(2) == 1) {
                clicDroit(allie, x, y);
            }
            clicGauche(allie, x, y);
            if (etat != allie.etatCases[x][y]) {
                i = i+1;
            }
        }
    }

    public void clicGauche(Joueur joueur, int x, int y) {
        JButton bouton = joueur.grille.getCase(x, y);
        MouseEvent clic = new MouseEvent(bouton, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
        0, 20, 20, 1, false, MouseEvent.BUTTON1);
        bouton.dispatchEvent(clic);
    }

    public void clicDroit(Joueur joueur, int x, int y) {
        JButton bouton = joueur.grille.getCase(x, y);
        MouseEvent clic = new MouseEvent(bouton, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
        0, 20, 20, 1, false, MouseEvent.BUTTON3);
        bouton.dispatchEvent(clic);
    }


    public void facile() {
        aleatoire();
        int etat = ennemi.etatCases[x][y];
        while (etat == ennemi.etatCases[x][y]) {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            clicGauche(ennemi, x, y);
        }
    }

    public void moyen() {
        if (Partie.bateauCoule(ennemi, x, y)) {
            aleatoire();
        } else {
            if ((ennemi.overlay[x][y]%10==2)||(ennemi.overlay[x][y]%10==1)) {
                xTouche = x;
                yTouche = y;
                if ((x!=9)&&(ennemi.overlay[x+1][y] == 3)) {
                    x = x+1;
                } else if ((y!=9)&&(ennemi.overlay[x][y+1] == 3)) {
                    y=y+1;
                } else if ((x!=0)&&(ennemi.overlay[x-1][y] == 3)) {
                    x=x-1;
                } else if ((y!=0)&&(ennemi.overlay[x][y-1] == 3)) {
                    y=y-1;
                } else {
                    aleatoire();
                }
            } else {
                if (xTouche!=-1) {
                    x = xTouche;
                    y = yTouche;
                    if ((x!=9)&&(ennemi.overlay[xTouche+1][yTouche] == 3)) {
                        x = x+1;
                    } else if ((y!=9)&&(ennemi.overlay[xTouche][yTouche+1] == 3)) {
                        y=y+1;
                    } else if ((x!=0)&&(ennemi.overlay[xTouche-1][yTouche] == 3)) {
                        x=x-1;
                    } else if ((y!=0)&&(ennemi.overlay[xTouche][yTouche-1] == 3)) {
                        y=y-1;
                    } else {
                        aleatoire();
                    }
                } else {
                    aleatoire();
                }
            }
        }
        int etat = ennemi.etatCases[x][y];
        clicGauche(ennemi, x, y);
        while (etat == ennemi.etatCases[x][y]) {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            clicGauche(ennemi, x, y);
        }
    }

    public void aleatoire() {
        x = rand.nextInt(10);
        y = rand.nextInt(10);
        while (ennemi.overlay[x][y]==0) {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
        }
    }

    public void difficile() {
        int v = 0;
        lecture();
        if (nbCases == 0) {
            while (v==0) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
                if (Proba[x][y]==1) {
                    v=1;
                }
            }
        } else {
            while (v==0) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
                if(Proba[x][y]==3) {
                    v=1;
                }
            }
        }
        clicGauche(ennemi, x, y);
        if((Partie.bateauCoule(ennemi, x, y))&&ennemi.etatCases[x][y]!=0) {
            Proba[x][y] = 4;
            setProba(x+1, y, 3);
            setProba(x-1, y, 3);
            setProba(x, y+1, 3);
            setProba(x, y-1, 3);
            setProba(x+1, y+1, 3);
            setProba(x-1, y-1, 3);
            setProba(x-1, y+1, 3);
            setProba(x+1, y-1, 3);
            coulee();
        } else { 
            if((ennemi.etatCases[x][y]%10 == 1) || (ennemi.etatCases[x][y]%10 == 2)) {
                Proba[x][y] = 4;
                trouve(x, y);
            } else {
                if(ennemi.overlay[x][y]%10 == 4) {
                    Proba[x][y] = 0;
                }
            }
        } 
    }

    public void tableau() {
        int i, j;
        for(i=0;i<10;i++) {
            for(j=0;j<10;j++) {
                Proba[i][j]=1;
            }
        }

    }

    public void lecture() {
        int i, j;
        nbCases = 0;
        for(i=0;i<10;i++) {
            for(j=0;j<10;j++) {
                if(Proba[i][j]==3) {
                    nbCases = nbCases+1;
                }
            }
        }
    }

    public void coulee() {
        int i, j;
        for(i=0;i<10;i++) {
            for(j=0;j<10;j++) {
                if(Proba[i][j]>2) {
                    Proba[i][j] = 0;
                }
            }
        }
    }

    public void trouve(int x, int y) {
        if ((x!=9)&&(Proba[x+1][y] == 4)) {
            setProba(x, y+1 , 0);
            setProba(x, y-1 , 0);
            setProba(x-1, y , 3);
        } else if ((x!=0)&&(Proba[x-1][y] == 4)) {
            setProba(x, y+1 , 0);
            setProba(x, y-1 , 0);
            setProba(x+1, y , 3);
        } else if ((y!=9)&&(Proba[x][y+1] == 4)) {
            setProba(x+1, y , 0);
            setProba(x-1, y , 0);
            setProba(x, y-1 , 3);
        } else if ((y!=0)&&(Proba[x][y-1] == 4)) {
            setProba(x+1, y , 0);
            setProba(x-1, y , 0);
            setProba(x, y+1 , 3);
        } else {
            setProba(x+1, y , 3);
            setProba(x-1, y , 3);
            setProba(x, y+1 , 3);
            setProba(x, y-1 , 3);
        }
    }

    public void tir() {
        if ((x==-1)&&(dif!=3)) {
            aleatoire();
            clicGauche(ennemi, x, y);
        } else {
            switch (dif) {
                case 1:
                    facile();
                break;
                case 2:
                    moyen();
                break;
                case 3:
                    difficile();
                break;
            }
        }
        Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
                if ((ennemi.etatCases[x][y]%10 == 1) || (ennemi.etatCases[x][y]%10 == 2)) {
                    tir();
                }
			}
		});
		timer.setRepeats(false);
		timer.start();
    }

    public void setProba(int x, int y, int val) {
        if ((x>=0)&&(x<=9)&&(y>=0)&&(y<=9)) {
            Proba[x][y] = val;
        }
    }
}
