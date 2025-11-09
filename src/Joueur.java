import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Joueur {
    int coupX, coupY;
    Grille grille;
    int[][] etatCases = new int[10][10]; //0 = eau, 1 = bateau, 2 = touché, 3 = placement interdit
    Random rand = new Random();

    public Joueur (int mode, int dif, JPanel p) {
        p.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        JPanel panelGrille = new JPanel();
		panelGrille.setPreferredSize(new Dimension(400, 400));
        p.add(panelGrille);
        grille = new Grille(panelGrille);
        etatCases[5][2] = 1;
        etatCases[5][3] = 2;
        etatCases[5][4] = 1;
        grille.update(etatCases);
    }

    public int getCoupX() {
        return /*coupX*/2;
    }
    public int getCoupY() {
        return /*coupY*/3;
    }

    public void placerBateaux() {
        System.out.println("Placement des bateaux");
    }
    
}
