import javax.swing.*;
import java.awt.*;

public class Partie {

	public Partie(JFrame f, int mode, int difficulte) {
		//Paramétrage de la fenêtre
		Jpanel wPanel1 = new JPanel();
		JPanel panelGrille1 = new JPanel();
		JPanel panelGrille2 = new JPanel();
		panelGrille1.setPreferredSize(new Dimension(400, 400));
		panelGrille1.setBackground(Color.RED);
		panelGrille2.setPreferredSize(new Dimension(400, 400));
		wPanel1.setPreferredSize(new Dimension(400, 400));
		wPanel1.add(panelGrille1);
		f.add(wPanel1, BorderLayout.WEST);
		f.add(panelGrille2, BorderLayout.EAST);
	}

}

