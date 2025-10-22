import javax.swing.*;

public class GameMain {
	public static void main (String[] args) {
		JFrame f = new JFrame("Bataille navale");
		f.setSize(1200, 700);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		Menu menu1 = new Menu(f, 0);
		menu1.setVisible(true);
		System.out.println(menu1.getbAppuye());
		System.exit(0);
	}
}

