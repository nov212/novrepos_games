package sweeper;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState state;
    private boolean firstClick;

    public GameState getState() {
        return state;
    }
    
    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb=new Bomb(bombs);
        flag=new Flag();
    }
    public Box getBox(Coord coord){  //Что изображать на клетке
        if (flag.get(coord)==Box.OPENED)
            return bomb.get(coord);
        else
        return flag.get(coord);
    }
    
    public void start(){
       firstClick=false;
       flag.start();
       state=GameState.PLAYED;
    }
    
    private void checkWinner()
    {
        if (state==GameState.PLAYED)
            if (flag.getCountOfClosedBoxes()==bomb.getTotalBombs())
            {
                state=GameState.WINNER;
                
            }
    }
    
    private void openBox(Coord coord)
    {
       switch(flag.get(coord))
       {
           case OPENED: setOpenedToClosedBoxesAroundNumber(coord);
           break;
           case FLAGED: return;
           case CLOSED:
                   switch(bomb.get(coord))
                   {
                       case ZERO: 
                           openBoxAround(coord);
                           break;
                       case BOMB:
                           openBombs(coord);
                           return;
                       default: flag.setOpenedToBox(coord);
                   }         
       }
    }
    
         void setOpenedToClosedBoxesAroundNumber(Coord coord)
     {
         
         if (bomb.get(coord)!=Box.BOMB)
             if (flag.getCountOfFlagedBoxesAround(coord)==bomb.get(coord).getNumber())
                 for (Coord around: Ranges.getCoordsAround(coord))
                     if (flag.get(around)==Box.CLOSED)
                         openBox(around);
     }
    
    private void openBombs(Coord bombed)
    {
      state=GameState.BOMBED;
      flag.setBombedToBox(bombed);
      for (Coord coord: Ranges.getAllCoords())
          if (bomb.get(coord)==Box.BOMB)
              flag.setOpenedToClosedBombBox(coord); 
      else
              flag.setNoBombToFlagedSafeBox(coord);
    }
    
    private void openBoxAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord around:Ranges.getCoordsAround(coord))
            openBox(around);
    }
          
    public void pressLeftButton(Coord coord){
        if (gameOver())
            return;
        if (firstClick==true)
        openBox(coord);
        else
        { 
            bomb.start(coord);
            openBox(coord);
            firstClick=true;
        }
        checkWinner();
    }
    
     public void pressRightButton(Coord coord){
         if (gameOver())
            return;
        flag.toggleFlagedToBox(coord);
    }
     boolean gameOver()
     {
         if (state==GameState.PLAYED)
             return false;
         return true;
     }
}
