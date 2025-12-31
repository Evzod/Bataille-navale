import java.awt.*;
import javax.swing.*;

public class LabelPerso extends JLabel{
    public LabelPerso(String text, int width, int height) {
        super(text, SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(width, height));
        this.setFont(new Font("Arial", Font.BOLD, 35));
    }
}
