package pieces;

import utility.Icons;
import utility.Move;

import javax.swing.*;

public abstract class Piece {
    private final Colour colour;
    private final ImageIcon icon;

    public Piece(Colour colour) {
        this.colour = colour;
        icon = Icons.loadIcon(this.getIconName());
    }

    public boolean isWhite() {
        return colour == Colour.WHITE;
    }

    public abstract boolean canMove(Move move);

    public abstract PieceName getName();

    public ImageIcon getIcon() {
        return this.icon;
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
