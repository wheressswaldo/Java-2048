/*
 * Kong Huang
 * CS1213
 * 2048 in swing
 */

package Final;

import java.awt.*;

import javax.swing.*;

// main class for running the game
public class main2048 extends JFrame {

	private static final long serialVersionUID = 1L;

	JLabel statusBar;
    private static final String TITLE = "2048";
    // win and lose messages
    public static final String WIN_MSG = "Winner, continue?";
    public static final String LOSE_MSG = "Loser, R to reset the game";
    
    public static void main(String[] args) {

        main2048 game = new main2048();
        Grid board = new Grid(game);
        if (args.length != 0 && args[0].matches("[0-9]*")) {
            Grid.goal = NumbersAndColors.of(Integer.parseInt(args[0]));
        }
        KeyPress kb = KeyPress.getKeyPress(board);
        board.addKeyListener(kb);
        game.add(board);
        
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }

    // constructor
    public main2048() {
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(340, 400);
        setResizable(false);

        statusBar = new JLabel("");
        add(statusBar, BorderLayout.SOUTH);
    }

    void win() {
        statusBar.setText(WIN_MSG);
    }
    void lose() {
        statusBar.setText(LOSE_MSG);
    }
}
