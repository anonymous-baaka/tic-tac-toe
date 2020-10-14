
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;


public class tictac extends JFrame
{
    private JButton resetButton;
    private JButton exitButton;
    private JButton undoButton;
    private JButton[] board;
    private int turn;
    private int[] undoStack;
    private String displayTurn;
    private int undoIndex;

    public tictac()
    {
        turn=1;
        displayTurn="";
        undoStack=new int[9];
        setTitle("Tic Tac Toe");
        int x=getResolution().x;
        int y=getResolution().y;
        int frameHeight=400;
        int frameWidth=400;
        setBounds(x/2-frameWidth/2,y/2-frameHeight/2,frameWidth,frameHeight);
        undoIndex=-1;
        resetButton=new JButton("RESET");
        exitButton=new JButton("EXIT");
        undoButton=new JButton("UNDO");    

        resetButton.addActionListener(new ButtonListener());
        exitButton.addActionListener(new ButtonListener());
        undoButton.addActionListener(new ButtonListener());

        undoButton.setEnabled(false);

        if(turn==1)
        displayTurn="Player 1's turn";
        else
        displayTurn="Player 2's turn";

        JPanel bottomPanel=new JPanel();
        bottomPanel.add(resetButton);
        bottomPanel.add(exitButton);
        bottomPanel.add(undoButton);
        //bottomPanel.add(displayTurn);


        JPanel boardPanel=new JPanel();
        boardPanel.setLayout(new GridLayout(3,3));
        board=new JButton[9];

        for(int i=0;i<9;i++)
        {
            board[i]=new JButton();
            board[i].setFont(new Font("Arial",Font.BOLD,72));
            board[i].addActionListener(new ButtonListener());
            boardPanel.add(board[i]);
        }
        add(bottomPanel,BorderLayout.SOUTH);
        add(boardPanel,BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponents(g);
        g.drawString(displayTurn, 30,30);
    }
    Point getResolution()
        {
            Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
            double width=screenSize.getWidth();
            double height=screenSize.getHeight();
            Point point=new Point((int)width,(int)height);
            return point;
        }
    private class ButtonListener extends JPanel implements ActionListener
    {
        //super.repaint();
        //super.repaint();
        public void actionPerformed(ActionEvent e)
        {
            if(undoIndex>=-1)
            undoButton.setEnabled(true);
            else
            undoButton.setEnabled(false);

            if(e.getSource()==resetButton)
            {
            
                restart();
            
            }
            else if(e.getSource()==exitButton)
            {
                System.exit(0);
            }
            else if(e.getSource()==undoButton)
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                board[undoStack[undoIndex]].setText("");
                board[undoStack[undoIndex]].setEnabled(true);
                undoIndex--;
                turn=(turn+1)%2;
                if(undoIndex==-1)
                undoButton.setEnabled(false);
            }
            else
            {
                
                for(int i=0;i<9;i++)
                {
                    if(e.getSource()==board[i])
                    {
                        undoStack[++undoIndex]=i;
                        if(turn==1)
                            board[i].setText("X");
                        else
                            board[i].setText("0");
                        board[i].setEnabled(false);
                        
                        if(checkWin())
                        {
                            JOptionPane dialog=new JOptionPane();
                            if(turn==1)
                             JOptionPane.showMessageDialog(null, "Game Over. Player 1 wins!!");
                             else
                             JOptionPane.showMessageDialog(null, "Game Over. Player 2 wins!!");
                            int choice=JOptionPane.showConfirmDialog(null,"Do you want to Restart?","Restart?",JOptionPane.YES_NO_OPTION);
                            if(choice==0)
                            restart();
                            else if(choice==1)
                            System.exit(0);
                        }
                        else if(checkDraw())
                        {
                            JOptionPane.showMessageDialog(null,"Draw!");
                            restart();
                        }
                        turn=(turn+1)%2;

                        if(turn==1)
                            displayTurn="Player 1's turn";
                        else
                            displayTurn="Player 2's turn";

                        return;
                    }
                }
                
            }
        }
        public void restart()
        {
            for(int i=0;i<9;i++)
            {
                board[i].setText("");
                board[i].setEnabled(true);
                turn=1;
                undoIndex=-1;
                undoButton.setEnabled(false);
            }
        }
        public boolean checkWin()
        {
            if(board[0].getText().equals(board[1].getText()))
                {
                    if(board[1].getText().equals(board[2].getText()) && !board[0].getText().equals(""))
                    return true;
                }
            if(board[3].getText().equals(board[4].getText()))
            {
                if(board[4].getText().equals(board[5].getText()) && !board[3].getText().equals(""))
                return true;
            }
            if(board[6].getText().equals(board[7].getText()))
            {
                if(board[7].getText().equals(board[8].getText()) && !board[6].getText().equals(""))
                return true;
            }
            if(board[0].getText().equals(board[3].getText()))
                {
                    if(board[3].getText().equals(board[6].getText()) && !board[0].getText().equals(""))
                    return true;
                }
            if(board[1].getText().equals(board[4].getText()))
            {
                if(board[4].getText().equals(board[7].getText()) && !board[1].getText().equals(""))
                return true;
            }
            if(board[2].getText().equals(board[5].getText()))
            {
                if(board[5].getText().equals(board[8].getText()) && !board[2].getText().equals(""))
                return true;
            }
            if(board[0].getText().equals(board[4].getText()))
            {
                if(board[4].getText().equals(board[8].getText()) && !board[0].getText().equals(""))
                return true;
            }
            if(board[2].getText().equals(board[4].getText()))
            {
                if(board[2].getText().equals(board[6].getText()) && !board[2].getText().equals(""))
                return true;
            }
            return false;
        }
        public boolean checkDraw()
        {
            boolean isdraw=true;
            for(int i=0;i<9;i++)
            {
                if(board[i].getText().equals(""))
                isdraw=false;
            }
            return isdraw;
        }
        
    }
    public static void main(String[] args)
    {
        tictac tic=new tictac();
    }
}
