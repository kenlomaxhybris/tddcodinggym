package tdd.coding.gym;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class GOL {

    public Set<Cell> allCells = new TreeSet();
    public Set<Cell> allNeighbours = new TreeSet();
    public SortedMap<Cell, Integer> mappingOfCellsToLiveNeighbours = new TreeMap();

    public int getPopulationSize(){
        return allCells.size();
    }

    public GOL populate(String xys){
        allCells = Cell.cells( xys );
        return this;
    }

    public String show( int radius  ){
        StringBuilder sb= new StringBuilder();
        for (int y = radius; y >= -radius; y --){
            for (int x = -radius; x < radius; x ++){
                String coords =x+","+y;
                sb.append( allCells.contains(new Cell(coords))?"+": "-");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private GOL locateEmbryos(){
        allNeighbours.clear();
        Set<Cell> neighboursOfLiveCells = new HashSet<>();
        allCells.stream().forEach(cell -> {
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
        neighboursOfLiveCells.removeAll(allCells);
        allNeighbours = neighboursOfLiveCells;
        return this;
    }

    private GOL setNeighbourMapping(){
        allCells.stream().forEach(cell ->  setAllNeighbours(cell) );
        allNeighbours.stream().forEach(cell ->  setAllNeighbours(cell) );
        return this;
    }

    private void setAllNeighbours(Cell cell) {
        int liveNeighbours = 0;
        for (int dx=-1; dx<=1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;
                Cell neighbourCoords = new Cell((cell.x + dx) + "," + (cell.y + dy));
                if (allCells.contains(neighbourCoords))
                    liveNeighbours++;
            }
        }
        mappingOfCellsToLiveNeighbours.put( cell, liveNeighbours );
   }

    public GOL evolve(){
        locateEmbryos();
        setNeighbourMapping();

        Set<Cell> nextGeneration = new HashSet<>();
        allCells.forEach(cell -> {
                    int n = mappingOfCellsToLiveNeighbours.get(cell);
                    if (n==2 || n==3)
                        nextGeneration.add(cell);
                }
        );
        allNeighbours.forEach(cell -> {
                    int n = mappingOfCellsToLiveNeighbours.get(cell);
                    if (n==3)
                        nextGeneration.add( cell);
                }
        );
        allCells = nextGeneration;
        return this;
    }

    public void play ( int repeats, int radius ) throws InterruptedException {
        while (repeats-- >0) {
            System.out.println( show(radius));
            evolve();
           // sleep(500);
        }
    }
}

class Cell implements Comparable{
    int x;
    int y;

    public static Set<Cell>cells(String xys){
        return new TreeSet( Stream.of(xys.split(", ")).map(xy -> new Cell(xy)).collect(
                Collectors.toSet()));
    }

    public Cell(String xy){
        x = Integer.parseInt(xy.substring(0, xy.indexOf(",")));
        y = Integer.parseInt(xy.substring(xy.indexOf(",")+1));
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

    @Override
    public int compareTo(Object o) {
        Cell c2 = (Cell)o;
        if (this.x == c2.x )
            return this.y-c2.y;
        return this.x-c2.x;
    }

    public String toString(){
        return x+","+y;
    }
}

class MapFactory {
    public static Map<Cell, Integer>  map( Set<Cell> cells, int ... neighbours ){
        Set<Cell> orderedSet = new TreeSet(cells);
        Map<Cell, Integer>map = new TreeMap();
        int i=0;
        for (Cell c: orderedSet){
            map.put(c, neighbours[i++]);
        }
        return map;
    }
}
