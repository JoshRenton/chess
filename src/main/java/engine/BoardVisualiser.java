package engine;
import board.Board;
import utility.Square;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class BoardVisualiser {
    private static JFrame gameWindow;
    private static Square[][] boardGui;

    public static void initialise(Board board) {
        createGameWindow();
        createBoardGUI(board);
    }

    private static void createGameWindow() {
        gameWindow = new JFrame("Chess");
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameWindow.setLocationRelativeTo(null);
    }

    public static void showWindow() {
        gameWindow.pack();
        gameWindow.setVisible(true);
    }

    private static void createBoardGUI(Board board) {
        boardGui = new Square[8][8];
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBorder(new LineBorder(Color.BLACK));

        gameWindow.add(boardPanel);

        int boardSize = board.getBoardSize();

        Color color;

        for (int row = 0; row < boardSize; row ++) {
            if (row % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.WHITE;
            }
            for (int column = 0; column < boardSize; column++) {
                Square square = createSquare(color, board.getPiece(row, column).asString(), new int[]{row, column});
                square.addActionListener(GameEngine.getSquareListener());
                color = swapColor(color);

                boardGui[row][column] = square;
            }
        }

        // Add squares in reverse order so that white is at the bottom.
        for (int row = boardGui.length - 1; row >= 0; row--) {
            for (int column = 0; column < boardGui[row].length; column++) {
                boardPanel.add(boardGui[row][column]);
            }
        }
    }

    public static void updateButtonText(String text, int[] coordinates) {
        Square square = boardGui[coordinates[0]][coordinates[1]];
        square.setText(text);
        if (GameEngine.isWhiteTurn()) {
            square.setForeground(Color.RED);
        } else {
            square.setForeground(Color.BLUE);
        }
    }

    private static Color swapColor(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private static Square createSquare(Color bg, String text, int[] coordinates) {
        Square square = new Square(text, coordinates);
        square.setFont(new Font("Arial", Font.PLAIN, 40));
        square.setBorder(new LineBorder(Color.BLACK));
        square.setBackground(bg);
        if (coordinates[0] == 0 || coordinates[0] == 1) {
            square.setForeground(Color.RED);
        } else {
            square.setForeground(Color.BLUE);
        }
        return square;
    }
}
