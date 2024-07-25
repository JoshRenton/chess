package pieces;

import utility.Move;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.hasMoved = false;
    }

    public boolean canMove(Move move) {
        int rowDiff = move.getEndRow() - move.getStartRow();
        int columnDiff = move.getEndColumn() - move.getStartColumn();

        if (columnDiff == 0 && rowDiff == 1 && isWhite()) { // Pawn moving forward one square.
            return true;
        } else if (columnDiff == 0 && rowDiff == 2 && isWhite() && !hasMoved) { // First pawn move can be two squares.
            return true;
        } else if (columnDiff == 0 && rowDiff == -1 && !isWhite()) {
            return true;
        } else
            return columnDiff == 0 && rowDiff == -2 && !isWhite() && !hasMoved;

        // TODO: Capturing will have to be figured out externally for this.
    }

    public void setMoved() {
        hasMoved = true;
    }

    public String asString() {
        return "P";
    }
}
