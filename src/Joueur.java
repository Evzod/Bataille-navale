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
        JLabel image = new JLabel(new ImageIcon("img/0.png"));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        JPanel panelGrille = new JPanel();
		panelGrille.setPreferredSize(new Dimension(400, 400));
        p.add(panelGrille, c);
        p.add(image, c);
        grille = new Grille(panelGrille);
        etatCases[2][5] = 4110;
        etatCases[3][5] = 4120;
        etatCases[4][5] = 4130;
        etatCases[5][5] = 4140;
        etatCases[2][3] = 3110;
        etatCases[3][3] = 3120;
        etatCases[4][3] = 3130;
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
