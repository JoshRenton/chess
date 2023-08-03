package engine;

import utility.Move;

public class InputParser {
    public InputParser() {

    }

    public static Move parse(String input) {
        input = input.trim();
        String[] splitInput = input.split(" ");
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

            return new Move(startRow - 1, startColumn, endRow - 1, endColumn);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int convertColumnCharToInt(char columnChar) {
        int columnInt;
        switch(columnChar) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return -1;
        }
    }
}
