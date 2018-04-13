package tdd.coding.gym;

public class GOLGame {

    GOL gol;
    public GOLGame( GOL gol ){
        this.gol = gol;
    }

    public void step(){
        for (int y = 10; y >=0; y --){
            for (int x = 0; x < 10; x ++){
                String coords =x+","+y;
                System.out.print( gol.cells.contains(new Cell(coords))?"+": "-");
            }
            System.out.println();
        }
        System.out.println("==========================");
    }

    public  static void main (String[] args ){
        GOLGame game = new GOLGame( new GOL().populate("5,5, 6,5, 7,5") );
        int iter = 10;
        if (args!=null && args.length>0)
            iter = Integer.parseInt(args[0]);
        while (iter-- >0)
            game.step();
    }

}
