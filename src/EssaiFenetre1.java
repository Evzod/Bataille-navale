import java.awt.*;
public class EssaiFenetre1
{
public static void main(String[] args)
{
Frame f =new Frame("Ma première fenêtre"); //Création d’une fenêtre (un objet de la classe Frame) avec un titre
Button b= new Button("un bouton"); //Création du bouton ayant pour label « un bouton »
f.add(b); //Ajout du bouton dans la fenêtre
f.pack();
f.setVisible(true);
//On demande à la fenêtre de choisir la taille minimum avec pack() et de se rendre visible avec show()
}
}
