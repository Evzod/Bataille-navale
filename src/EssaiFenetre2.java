import java.awt.*;
import java.awt.event.*;
public class EssaiFenetre2 implements ActionListener
{
public static void main(String[] args)
{
Frame f =new Frame("Fenêtre"); //Création d’une fenêtre (un objet de la classe Frame) avec un titre
Button b= new Button("cliquer"); //Création du bouton ayant pour label « cliquer »
f.add(b/*, BorderLayout.CENTER*/); //Ajout du bouton dans la fenêtre
b.setSize(50,50);
b.addMouseListener();
/*f.pack();*/
f.setSize(500,500);
f.setVisible(true);
//On demande à la fenêtre de choisir la taille minimum avec pack() et de se rendre visible avec show() mais obsolète donc remplacé par setvisible
}
}
