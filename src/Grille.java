import javax.swing.*;
import java.awt.*;

public class Grille {
    JButton[][] cases = new JButton[10][10];
    
    public Grille(JPanel p) {
        p.setLayout(new GridLayout(10,10));
        for (int x = 0; x <= 9; x++) {//L'origine est dans la case en haut à gauche,
            for (int y = 0; y <= 9; y++) {//x vers le bas, y vers la droite
                JButton b = new JButton();
                b.setRolloverEnabled(false);
                b.setContentAreaFilled(false);
                cases[x][y] = b;
                p.add(b);
            }
        }
    }

    public JButton getCase(int x, int y) {
        return cases[x][y];
    }

    public void update(int[][] matrice) {
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                if (matrice[x][y] >= 1000) {
                    cases[x][y].setIcon(new ImageIcon("img/"+Integer.toString(matrice[x][y])+".png"));
                } else {
                    switch (matrice[x][y]) {
                        case -1 : //Pour le placement non valide
                            cases[x][y].setIcon(new ImageIcon("img/rouge.png"));
                            break;
                        case 1 : //Premier stade d'explosion
                            cases[x][y].setIcon(new ImageIcon("img/explosion1.png"));
                            break;
                        case 2 : //Deuxième stade d'explosion
                            cases[x][y].setIcon(new ImageIcon("img/explosion2.png"));
                            break;
                        case 3 : //Overlay gris sur la grille adverse
                            cases[x][y].setIcon(new ImageIcon("img/gris.png"));
                            break;
                        case 4 : //PLOUF!!!
                            cases[x][y].setIcon(new ImageIcon("img/explosion_eau.png"));
                            break;
                        default : //Transparent pour voir l'eau
                            cases[x][y].setIcon(null);
                            break;
                    }
                }
            }
        }
    }
}
