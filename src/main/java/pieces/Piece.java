package pieces;

import utility.Icons;
import utility.Move;

import javax.swing.*;

public abstract class Piece {
    private final Colour colour;
    ImageIcon icon;
    // This is not needed by every piece but prevents needing to unnecessarily type-cast
    private boolean hasMoved;

    public Piece(Colour colour) {
        this.colour = colour;
        icon = Icons.loadIcon(this.getIconName());
        this.hasMoved = false;
    }

    public boolean isWhite() {
        return colour == Colour.WHITE;
    }

    public abstract boolean canMove(Move move);

    public abstract PieceName getName();

    public ImageIcon getIcon() {
        return this.icon;
    }

    // Only accessible to other pieces
    void setIcon (String iconFilename) {
        icon = Icons.loadIcon(iconFilename);
    }

    public String getIconName() {
        if (this.isWhite()) {
            return "white" + this.getName() + ".png";
        } else {
            return "black" + this.getName() + ".png";
        }
    }

    public Colour getColour() {
        return colour;
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    public boolean hasNotMoved() {
        return !hasMoved;
    }

    public enum PieceName {
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING,
        EMPTY
    }

    public enum Colour {
        BLACK,
        WHITE,
        NONE;

        // Returns true if this colour is opposite the turn player
        public boolean isTurnPlayerColour(boolean isWhiteTurn) {
            return (this == BLACK && isWhiteTurn) || (this == WHITE && !isWhiteTurn);
        }
    }
}
