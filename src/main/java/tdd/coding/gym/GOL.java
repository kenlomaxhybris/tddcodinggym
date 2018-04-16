package tdd.coding.gym;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class GOL {

    Set<Cell> cells = new TreeSet();
    Engine engine = new Engine();

    public GOL populate(String xys){
        cells = Stream.of(xys.split(", ")).map(xy -> new Cell(xy)).collect(Collectors.toSet());
        return this;
    }

    public  Set<Cell> census (){
        return cells;
    }

    public int populationSize(){
        return cells.size();
    }

    public GOL evolve(){
        cells = engine.populate(cells).evolve();
        return this;
    }

    public void show( int radius ){
        for (int y = radius; y >= -radius; y --){
            for (int x = -radius; x < radius; x ++){
                String coords =x+","+y;
                System.out.print( cells.contains(new Cell(coords))?"+": "-");
            }
            System.out.println();
        }
        System.out.println("==========================");
    }

    public  static void main (String[] args ) throws InterruptedException {
        GOL gol = new GOL().populate("-1,0, 0,0, 1,0");
        int iter = 10;
        if (args!=null && args.length>0)
            iter = Integer.parseInt(args[0]);
        while (iter-- >0) {
            gol.show(5);
            sleep(500);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

}

class Cell{
    int x;
    int y;

    public Cell(String xy){
        x = Integer.parseInt(xy.substring(0, xy.indexOf(",")));
        y = Integer.parseInt(xy.substring(xy.indexOf(",")+1));
    }

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return x+","+y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Engine {
    public Set<Cell> liveCells = new TreeSet();
    public Set<Cell> embryos = new TreeSet();
    public Map<Cell, Integer> neighbourMapping = new HashMap();
    public Set<Cell> nextGeneration = new TreeSet();

    public Engine(){}
    public Engine populate(Set<Cell> cells ){
        this.liveCells = cells;
        setEmbryos();
        setNeighbourMapping();
        return this;
    }

    private Engine setNeighbourMapping(){
        liveCells.stream().forEach( cell ->  setNeighbours(cell) );
        embryos.stream().forEach( cell ->  setNeighbours(cell) );
        return this;
    }

    private void setNeighbours(Cell cell) {
        int liveNeighbours = 0;
        for (int dx=-1; dx<=1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;
                Cell neighbourCoords = new Cell((cell.x + dx) + "," + (cell.y + dy));
                if (liveCells.contains(neighbourCoords))
                    liveNeighbours++;
            }
        }
        neighbourMapping.put( cell, liveNeighbours );
    }

    private Engine setEmbryos(){
        Set<Cell> neighboursOfLiveCells = new HashSet<>();
        liveCells.stream().forEach(  cell ->
                {
                    int x = cell.x;
                    int y = cell.y;
                    neighboursOfLiveCells.add( new Cell((x-1)+","+(y-1)));
                    neighboursOfLiveCells.add( new Cell((x-1)+","+(y-0)));
                    neighboursOfLiveCells.add( new Cell((x-1)+","+(y+1)));
                    neighboursOfLiveCells.add( new Cell((x-0)+","+(y-1)));
                    neighboursOfLiveCells.add( new Cell((x-0)+","+(y+1)));
                    neighboursOfLiveCells.add( new Cell((x+1)+","+(y-1)));
                    neighboursOfLiveCells.add( new Cell((x+1)+","+(y-0)));
                    neighboursOfLiveCells.add( new Cell((x+1)+","+(y+1)));
                }
        );
        neighboursOfLiveCells.removeAll(liveCells);
        embryos = neighboursOfLiveCells;
        return this;
    }

    public Set<Cell> evolve(){
        Set<Cell> nextGeneration = new HashSet<>();
        liveCells.forEach(cell -> {
            int n = neighbourMapping.get(cell);
            if (n==2 || n==3)

                nextGeneration.add( new Cell(cell.x, cell.y));
            }
        );
        embryos.forEach(cell -> {
            int n = neighbourMapping.get(cell);
            if (n==3)
                nextGeneration.add( new Cell(cell.x, cell.y));
            }
        );
        return nextGeneration;
   }

}