package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.BoxLayout.*;
import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.GameState;
import sweeper.Ranges;


public class Minesweeper extends JFrame {
    private JPanel panel;
    private JPanel mainPanel;
    private JButton btn;
    private final int COLS=10;
    private final int ROWS=10;
    private final int IMAGE_SIZE=50;
    private final int BOMBS=10;
    private Game game;

    public static void main(String[] args) {
      new Minesweeper();
    }
 
    private Minesweeper(){
        game=new Game(COLS,ROWS,BOMBS);
        game.start();
        setImages(); 
        initPanel();       
        initFrame();
    }
    
    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper"); //Заголовок формы
        setResizable(false);
        setIconImage(getImage("icon")); //ярлык программы
        setVisible(true);
        pack(); //Устанавливает минмальный размер окна, чтобы поместились все компоненты
        setLocationRelativeTo(null); //Устанавливает окно по центру
    }
   
        private void initPanel(){
        mainPanel=new JPanel();
       mainPanel.setLayout(null);
       mainPanel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE, Ranges.getSize().y*IMAGE_SIZE+100));
       
       
        btn=new JButton();
        btn.setBounds(Ranges.getSize().x*IMAGE_SIZE/2-25,25, 50, 50);
        btn.setIcon(new ImageIcon("res/img/play.png"));
        btn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                game.start();
                btn.setIcon(new ImageIcon("res/img/play.png"));
                panel.repaint();
            }
        });
        mainPanel.add(btn);
        
        
        panel=new JPanel()
        {
            @Override
            public void paintComponent(Graphics g){
            super.paintComponent(g);
            for (Coord coord: Ranges.getAllCoords())
            {
            g.drawImage((Image)game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);          
            }
            }
        };
        
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e){
                int x=e.getX()/IMAGE_SIZE; 
                int y=e.getY()/IMAGE_SIZE; 
                Coord coord=new Coord(x,y);
                if(e.getButton()==MouseEvent.BUTTON1)
                  game.pressLeftButton(coord);
                if(e.getButton()==MouseEvent.BUTTON3)
                  game.pressRightButton(coord);
                switch(game.getState())
                {
                    case WINNER:
                        btn.setIcon(new ImageIcon("res/img/win.png"));
                        break;
                    case PLAYED:
                        btn.setIcon(new ImageIcon("res/img/play.png"));
                        break;
                    case BOMBED:
                        btn.setIcon(new ImageIcon("res/img/lose.png"));
                }               
                panel.repaint();
            }
        });     
        //panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE,Ranges.getSize().y*IMAGE_SIZE));
        panel.setBounds(0,100,Ranges.getSize().x*IMAGE_SIZE,Ranges.getSize().y*IMAGE_SIZE);
        mainPanel.add(panel);
        add(mainPanel);
        
        
    }

       
      private Image getImage(String name){         //создаёт изображение, источником которого является файл
          String filename="res/img/"+name.toLowerCase()+".png";
          ImageIcon icon=new ImageIcon(filename);
          return icon.getImage();
      } 
      private void setImages() {
          for (Box box: Box.values())
              box.image=getImage(box.name());
      }
}
