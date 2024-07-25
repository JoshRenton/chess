package pieces;

import utility.Icons;
import utility.Move;

import javax.swing.*;

public abstract class Piece {
    private final boolean isWhite;
    private final ImageIcon icon;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        icon = Icons.loadIcon(this.getIconName());
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean canMove(Move move);

    public abstract String asString();

    public ImageIcon getIcon() {
        return this.icon;
    }

    public String getIconName() {
        if (this.isWhite) {
            return "white" + this.asString() + ".png";
        } else {
            return "black" + this.asString() + ".png";
        }
    }
}
