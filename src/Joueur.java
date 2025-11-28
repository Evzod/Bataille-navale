import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.*;

public class Joueur {
    public Grille grille;
    public int[][] etatCases = new int[10][10]; //0 = eau, 1 = bateau, 2 = touché, 3 = placement interdit
    int[][] placement = new int[10][10];
    Random rand = new Random();
    Input input;
    int mode;
    int orientation = 1;
    int indexBateau = 0;

    public Joueur (int mode, int dif, JPanel p) {
        this.mode = mode;
        p.setPreferredSize(new Dimension(450, 10));
        p.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        Image imageFond = new ImageIcon("img/eau.png").getImage();
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
        c.gridy = 0;
        p.add(panelGrille, c);
        grille = new Grille(panelGrille);
        /*{etatCases[0][0] = 0;
        etatCases[2][5] = 4110;
        etatCases[3][5] = 4120;
        etatCases[4][5] = 4130;
        etatCases[5][5] = 4140;
        etatCases[2][3] = 3110;
        etatCases[3][3] = 3120;
        etatCases[4][3] = 3130;
        etatCases[3][7] = 2110;
        etatCases[4][7] = 2120;
        etatCases[3][2] = 1010;
        etatCases[5][2] = 1010;}
        grille.update(etatCases);*/
    }


    public void placerBateaux() {
        int[] listeBateaux = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                final int x = i;
                final int y = j;
                JButton bouton = grille.getCase(x, y);

                bouton.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseEntered(MouseEvent e1) {
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
                                        placement[x+k-1][y] = 1;
                                    } else if (orientation==-1) {
                                    placement[x][y-k+1] = 1;
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
                        /*for (int i = 1; i <= tailleBateau; i++) {
                            try {
                                if (orientation==1){
                                    placement[x+i-1][y] = 0;
                                } else if (orientation==-1) {
                                    placement[x][y-i+1] = 0;
                                }
                            } catch (Exception e3) {};
                        }*/
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            orientation *= -1;
                            mouseExited(e);
                            mouseEntered(e);
                            return;
                        }
                        
                        int tailleBateau = listeBateaux[indexBateau];
                        for (int k=1; k<=tailleBateau; k++){
                            if (orientation==1){
                                etatCases[x+k-1][y] = 1000*tailleBateau + 100 + 10*k;
                            } else if (orientation==-1) {
                                etatCases[x][y-k+1] = 1000*tailleBateau + 10*k;
                            }
                        }
                        indexBateau += 1;
                        if (indexBateau==listeBateaux.length) {
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
                            
                        }
                    }
                });
            }
        }
    }

    public boolean placementValide(int x, int y, int orientation, int tailleBateau) {
        if (x>6||y<4) { // aucun sens, à modifier selon les conditions de placement
            return false;
        } else {
            return true;
        }
    }

    public void fusionGrilles(int[][] placement) {
        int[][] transfert = new int[10][10];
        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                transfert[i][j] = etatCases[i][j];
                if ((placement[i][j]>1000)||(placement[i][j]==1)) {
                    transfert[i][j] = placement[i][j];
                }
            }
        }
        grille.update(transfert);
    }

    public void changerOrientation() {
        orientation *= -1;
    }
    
}
