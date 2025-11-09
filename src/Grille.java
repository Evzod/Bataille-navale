import javax.swing.*;
import java.awt.*;

public class Grille {
    JButton[][] cases = new JButton[10][10];
    //int[][] etatCases = new int[10][10]; //0 = eau, 1 = bateau, 2 = touché, 3 = placement interdit

    public Grille(JPanel p) {
        p.setLayout(new GridLayout(10,10/*, 2, 2*/));
        for (int x = 0; x <= 9; x++) {//L'origine est dans le case en haut à gauche,
            for (int y = 0; y <= 9; y++) {//x vers le bas, y vers la droite
                JButton b = new JButton();
                b.setOpaque(true);
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
                switch (matrice[x][y]){
                    case 1 : cases[x][y].setBackground(Color.GRAY); break;
                    case 2 : cases[x][y].setBackground(Color.RED); break;
                    default : cases[x][y].setIcon(new ImageIcon("img/eau.png")); break;
                }
            }
        }
    }
}
