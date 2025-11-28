public class Input {
    public Input(){
        System.out.println("constructeur Input");
    }

    public int[][] placerBateaux(int[][] placement) {
        
        placement[4][5] = 4131;
        return placement;
    }
}
