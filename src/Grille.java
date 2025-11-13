import javax.swing.*;
import java.awt.*;

public class Grille {
    JButton[][] cases = new JButton[10][10];
    
    public Grille(JPanel p) {
        p.setBackground(new Color(0, 0, 0, 0));
        p.setLayout(new GridLayout(10,10));
        for (int x = 0; x <= 9; x++) {//L'origine est dans le case en haut à gauche,
            for (int y = 0; y <= 9; y++) {//x vers le bas, y vers la droite
                JButton b = new JButton();
                b.setRolloverEnabled(false);
                b.setOpaque(false);
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
                if (matrice[x][y]!=0) {
                    cases[x][y].setIcon(new ImageIcon("img/"+Integer.toString(matrice[x][y])+".png"));
                }
                /*switch (matrice[x][y]){//Chiffres : taille, partie, orientation, explosé
                    case 41 : cases[x][y].setIcon(new ImageIcon("img/bat4/bat4_1.png")); 
                    break;
                    case 42 : cases[x][y].setIcon(new ImageIcon("img/bat4/bat4_2.png")); 
                    break;
                    case 43 : cases[x][y].setIcon(new ImageIcon("img/bat4/bat4_3.png")); 
                    break;
                    case 44 : cases[x][y].setIcon(new ImageIcon("img/bat4/bat4_4.png")); 
                    break;
                    case 1 : cases[x][y].setIcon(new ImageIcon("img/avion/avion.png")); 
                    break;
                }*/
            }
        }
    }
}
