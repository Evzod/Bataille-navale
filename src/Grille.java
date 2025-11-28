import javax.swing.*;
import java.awt.*;

public class Grille {
    JButton[][] cases = new JButton[10][10];
    
    public Grille(JPanel p) {
        p.setLayout(new GridLayout(10,10));
        for (int x = 0; x <= 9; x++) {//L'origine est dans le case en haut à gauche,
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
                } else if (matrice[x][y] != 0) {
                    cases[x][y].setIcon(new ImageIcon("img/rouge.png"));
                } else {
                    cases[x][y].setIcon(null);
                }
            }
        }
    }
}
