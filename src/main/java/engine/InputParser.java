package engine;

import utility.Move;

public class InputParser {
    public InputParser() {

    }

    public static Move parse(String input, boolean isWhiteTurn) {
        input = input.trim();
        String[] splitInput = input.split(" "); //TODO: Currently does not work if multiple spaces between squares
        if (splitInput.length != 2) {
            return null;
        }

        if (splitInput[0].length() != 2 || splitInput[1].length() != 2) {
            return null;
        }

        int startColumn = convertColumnCharToInt(splitInput[0].charAt(0));
        int endColumn = convertColumnCharToInt(splitInput[1].charAt(0));

        if (startColumn == -1 || endColumn == -1) {
            return null;
        }

        try {
            int startRow = Integer.parseInt(String.valueOf(splitInput[0].charAt(1)));
            int endRow = Integer.parseInt(String.valueOf(splitInput[1].charAt(1)));

            return new Move(isWhiteTurn, startRow - 1, startColumn, endRow - 1, endColumn);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int convertColumnCharToInt(char columnChar) {
        int columnInt;
        return switch (columnChar) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            default -> -1;
        };
    }
}
