import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.Segment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Panel extends JPanel {

    private JLabel scoreLabel;
    private JLabel bestLabel;
    private int score,best = 0;
    public Grid grid;
    private Snake snake;
    private Apple apple;
    private static String direction = "DOWN";

    final static private int cellSize = 15;

    public Panel() {
        
        scoreLabel = new JLabel("Score:0");
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);

        bestLabel = new JLabel("Best Score:0");
        bestLabel.setForeground(Color.WHITE);
        add(bestLabel);

        grid = new Grid(40, 40);
        snake = new Snake(this);

        apple = new Apple(0,0);
        apple.moveApple();

        setFocusable(true);
        requestFocus();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                    direction = "LEFT";
                } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                    direction = "RIGHT";
                } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    direction = "UP";
                } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                    direction = "DOWN";
                }
            }
        });
    }
    public static int Randint(int max, int min){
        Random randInt = new Random();
        int n = randInt.nextInt(max-min+1)+min;
        return n;
    }
    
    public class Apple{
        private int row,column;

        public Apple(int column, int row){
            this.column=column;
            this.row=row;
        }

        public void moveApple(){

            grid.setValue(row,column, 0);
            
            int new_column = Randint(39, 0);
            int new_row = Randint(39, 0);
            int gridValue = grid.getValue(new_row, new_column);
            
            if(gridValue!=0){
                moveApple();
            }else{
                column=new_column;
                row=new_row;
                grid.setValue(row, column, 2);
                gridValue = grid.getValue(new_row, new_column);
            }
            

            
            

        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(column*cellSize, row*cellSize, cellSize, cellSize);
        }


    }

    public class Snake implements ActionListener {
        private List<SnakeSegment> segments;
        private int headRow = 20;
        private int headColumn = 10;
        private int tailRow;
        private int tailColumn;
        private SnakeSegment tailSegment;
        int randomReset = Randint(1000, 0);

        Timer timer;
        Panel panel;

        public Snake(Panel panel) {
            this.panel = panel;
            segments = new ArrayList<>();
            segments.add(new SnakeSegment(headRow, headColumn));

            timer = new Timer(25, this);
            timer.start();

            
        }

        public void actionPerformed(ActionEvent e) {
            move();
            panel.repaint();
        }
        public void EatApple(){
                 grow();
                 apple.moveApple();
         }

        private void updateHeadRow(){
            switch (direction) {
                case "UP":
                    if (headRow > 0) {
                        headRow--;
                    } else {
                        headRow = 39;
                    }
                    break;
                case "DOWN":
                    if (headRow + 1 < 40) {
                        headRow++;
                    } else {
                        headRow = 0;
                    }
                    break;
                case "RIGHT":
                    if (headColumn + 1 < 40) {
                        headColumn++;
                    } else {
                        headColumn = 0;
                    }
                    break;
                case "LEFT":
                    if (headColumn > 0) {
                        headColumn--;
                    } else {
                        headColumn = 39;
                    }
                    break;
                default:
                    headRow++;
                    break;
            }
        }

        public void move() {
            updateHeadRow();
            int gridValue = grid.getValue(headRow, headColumn);
            switch (gridValue) {
                case 0:

                    segments.add(new SnakeSegment(headRow, headColumn));
                    
                    removeSegment(0);

                    grid.setValue(headRow, headColumn, 1);

                    break;
                case 1:
                    reset();
                    break;
                case 2:
                    EatApple();
                    break;
                default:
                    reset();
                    break;
            }

            

            
        }
        public void reset(){
            int numberOfSegments = segments.size();
            for(int i=numberOfSegments-1; i>=0; i--){
                removeSegment(i);
            }
            segments.add(new SnakeSegment(headRow, headColumn));
            apple.moveApple();
            score=0;
            scoreLabel.setText("Score:"+score);
        }
        public void removeSegment(int index){
            tailSegment=segments.get(index);
            tailColumn=tailSegment.getColumn();
            tailRow=tailSegment.getRow();
            grid.setValue(tailRow, tailColumn, 0);
            segments.remove(index);
        }
        public void grow() {
            segments.add(new SnakeSegment(headRow, headColumn));
            score++;
            scoreLabel.setText("Score:"+score);
            if(score>best){
                best++;
                bestLabel.setText("Best Score:"+best);
            }
        }

        public void draw(Graphics g) {
            for (SnakeSegment segment : segments) {
                segment.draw(g);
            }
        }
    }

    private static class SnakeSegment {
        private int x, y, row, column;

        public SnakeSegment(int row, int column) {
            this.row=row;
            this.column=column;
            this.y = row * cellSize;
            this.x = column * cellSize;
        }

        public int getRow(){
            return row;
        }
        public int getColumn(){
            return column;
        }
        public void draw(Graphics g) {
            // Snake segment
            g.setColor(Color.GREEN);
            g.fillRect(x, y, cellSize, cellSize);
            // Outline
            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellSize, cellSize);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        apple.draw(g);

    }
}
