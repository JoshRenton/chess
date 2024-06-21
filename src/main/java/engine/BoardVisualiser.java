package engine;
import board.Board;
import utility.Square;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardVisualiser {
    JFrame gameWindow;
    JPanel boardPanel;

    public BoardVisualiser(Board board) {
        createGameWindow();
        createBoardGUI(board);
    }

    private void createGameWindow() {
        gameWindow = new JFrame("Chess");
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameWindow.setLocationRelativeTo(null);
    }

    public void showWindow() {
        gameWindow.pack();
        gameWindow.setVisible(true);
    }

    private void createBoardGUI(Board board) {
        boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBorder(new LineBorder(Color.BLACK));

        gameWindow.add(boardPanel);

        int boardSize = board.getBoardSize();

        Color color;

        JButton button;
        for (int row = 0; row < boardSize; row ++) {
            if (row % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.WHITE;
            }
            for (int column = 0; column < boardSize; column++) {
                button = createSquare(color, String.valueOf(board.getPiece(row, column).asChar()),
                        new int[]{row, column});
                color = swapColor(color);

                boardPanel.add(button);
            }
        }
    }

    private Color swapColor(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private JButton createSquare(Color bg, String text, int[] coordinates) {
        Square square = new Square(text, coordinates);
        square.setFont(new Font("Arial", Font.PLAIN, 40));
        square.setBorder(new LineBorder(Color.BLACK));
        square.setBackground(bg);
        square.setForeground(swapColor(bg));
        return square;
    }
}
