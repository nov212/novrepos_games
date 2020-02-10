package sweeper;


public class Bomb {
    private Matrix bombMap;  //клетки с бомбами
    private int totalBombs;//Сколько всего бомб
    
    Bomb (int totalBombs){
        this.totalBombs=totalBombs;
        fixBombsCount();
    }
    
    void start(Coord coord) {
        bombMap=new Matrix(Box.ZERO);
        for (int i=0;i<totalBombs;i++)
            placeBomb(coord);
        
    }
    
    private void placeBomb(Coord firstCoord){
        while (true) {
           Coord coord=Ranges.getRandomCoord();
           if (Box.BOMB==bombMap.get(coord) || coord.equals(firstCoord))
               continue;
           bombMap.set(coord, Box.BOMB);
           incNumbersAround(coord);
           break;
        }
}
    
    Box get(Coord coord){
        return bombMap.get(coord);
    }
    
    private void incNumbersAround(Coord coord)
    {
        for (Coord around: Ranges.getCoordsAround(coord))
            if(Box.BOMB!=bombMap.get(around))
            bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }
    
    private void fixBombsCount(){       //Меняет количество бомб, если их больше числа клеток
        int maxBombs=Ranges.getSize().x*Ranges.getSize().y/2;
        if (totalBombs>maxBombs)
            totalBombs=maxBombs;
    }
    
    int getTotalBombs()
    {
        return totalBombs;
    }
}
