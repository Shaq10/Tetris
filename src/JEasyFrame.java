import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//importing libraries

public class JEasyFrame extends JFrame {
    public static boolean isGameRunning = false;
    JButton End = new JButton("END GAME");
    JButton Start= new JButton("START GAME");
    JLabel label = new JLabel("Your score is: "+score+ " points");
    public TetrisView comp = null;
    public Block block;
    public static int score = 0;
    public static ScheduledThreadPoolExecutor EXECUTOR_DOWN = new ScheduledThreadPoolExecutor(1);
//new frame, buttons and label created

    public JEasyFrame(TetrisView comp, String title) {
        super(title);
        this.comp = comp;
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        panel.add(Start);
        panel.add(End);
        panel1.add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
        add(panel1, BorderLayout.EAST);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        EXECUTOR_DOWN.scheduleWithFixedDelay(new moveDown(), 300, 400, TimeUnit.MILLISECONDS);
        repaint();
        addMouseListener(new TMouseListener());
//Components added to frame. Timer for falling of blocks is also created

        End.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGameRunning=true;
            }
        });
//Action listeners for start and end button. End button ends the process and the start button gets the game running
    }

    class TMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (isGameRunning) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    block.shiftLeft();
                    getNextX();
                    //left mouse button
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    block.rotate();
                    getNextX();
                    //middle mouse button
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    block.shiftRight();
                    getNextX();
                    //right mouse button
                }
                repaint();
            }
        }

    }
//mouse event received

    private void letsBegin() {
        for (int i = 0; i < block.getAxis().length; i++) {
            try {
                comp.dim2[block.getAxis()[i].x][block.getAxis()[i].y] = 0;
                comp.dim1[block.getAxis()[i].x][block.getAxis()[i].y] = 0;
            } catch (Exception e) {

            }
        }
        for (int i = 0; i < block.getTemp().length; i++) {
            try {
                comp.dim2[block.getTemp()[i].x][block.getTemp()[i].y] = 1;
                comp.dim1[block.getTemp()[i].x][block.getTemp()[i].y] = block.getColor();
            } catch (Exception e) {

            }
        }
        Block.transAxis(block.getTemp(), block.getAxis());
    }
//gets next block

    private boolean getNext() {
        boolean flag = true;
        for (int i = 0; i < block.getTemp().length; i++) {
            if (block.getTemp()[i].x < 0 || block.getTemp()[i].x >= comp.w || block.getTemp()[i].y < 0) {
                flag = false;
                break;
            }
            if (block.getTemp()[i].y < comp.h) {
                if (comp.dim2[block.getTemp()[i].x][block.getTemp()[i].y] == 2) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
//gets next block and analyses it to make sure the dimensions are correct

    private void checkDim() {
        if (getNext()) {
            letsBegin();
        } else {
            block.setType(2);

            for (int i = 0; i < block.getAxis().length; i++) {
                try {
                    comp.dim2[block.getAxis()[i].x][block.getAxis()[i].y] = 2;
                    comp.dim1[block.getAxis()[i].x][block.getAxis()[i].y] = block.getColor();
                    boolean flag = true;
                    for (int x = 0; x < comp.w; x++) {
                        if (comp.dim2[x][block.getAxis()[i].y] != 2) {
                            flag = false;
                            break;
                        }
                    }
//checks dimensions are fine for grid
                    if (flag) {
                        score += 10;
                        label.setText("YOUR SCORE IS: "+score+ " POINTS");
                        for (int x = 0; x < comp.w; x++) {
                            for (int y = block.getAxis()[i].y; y <= comp.h - 1; y++) {
                                if (comp.dim2[x][y] == 2) {
                                    if (y == comp.h) {
                                        comp.dim2[x][y] = 0;
                                        comp.dim1[x][y] = comp.dim1[x][y] = 0;
                                    } else {
                                        comp.dim2[x][y] = comp.dim2[x][y + 1];
                                        comp.dim1[x][y] = comp.dim1[x][y + 1];

                                    }
                                }
                            }
                        }
                    }//Score increased by 10 when there is a horizontal line of squares without a gap and line is cleared
                } catch (Exception e) {
                }
            }
            for (int i = 0; i < block.getAxis().length; i++) {
                if (block.getAxis()[i].y >= comp.h) {
                    isGameRunning = false;
                    EXECUTOR_DOWN.shutdownNow();
                    displayScore();
                    //Start.setVisible(false);
                }
            }

        }
    }
//game is ended when height of blocks stacked exceed the height of the grid

    class moveDown implements Runnable {
        @Override
        public void run() {
            if (isGameRunning) {
                if (block == null || block.getType() != 1) {
                    block = Block.createBlock(new Axis(comp.w / 2 - 1, comp.h));
                }
                block.shiftDown();
                checkDim();
                repaint();
            }
        }

    }
//block created and shifts down if the game is currently running

    private void getNextX() {
        if (getNext()) {
            letsBegin();
        } else {
            Block.transAxis(block.getAxis(), block.getTemp());
        }
    }

    private void displayScore() {
        JOptionPane.showMessageDialog(null, "Your final score was: "+score+" points");
    }
//dialog message displayed when game has ended
}

