import java.util.Random;
import java.util.Scanner;

public class Test {

    private static final char EMPTY = ' ';

    private static final char USER = 'X';

    private static final char COMPUTER = 'O';

    private static final char[][] GAME_TABLE = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    public static void main(String[] args) {
        printGameRules();
        if (new Random().nextBoolean()) {
            makeComputerProgress();
            printGameTable();
        }
        while (true) {
            int number = readUserInput();
            makeUserProgress(number);
            printGameTable();
            if (isWinner(USER)) {
                System.out.println("YOU WIN!");
                break;
            }
            if (isDraw()) {
                System.out.println("Sorry, DRAW!");
                break;
            }
            makeComputerProgress();
            printGameTable();
            if (isWinner(COMPUTER)) {
                System.out.println("COMPUTER WIN!");
                break;
            }
            if (isDraw()) {
                System.out.println("Sorry, DRAW!");
                break;
            }
        }
        printGameTable();
        System.out.println("GAME OVER!");
    }

    private static void printGameRules() {
        char[][] data = {
                {'7', '8', '9'},
                {'4', '5', '6'},
                {'1', '2', '3'}
        };
        printData(data);
    }

    private static void printGameTable() {
        printData(GAME_TABLE);
    }

    private static void printData(char[][] data) {
        for (int i = 0; i < 3; i++) {
            printHorizontalLine();
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + data[i][j] + " ");
                if (j == 2) {
                    System.out.println("|");
                }
            }
        }
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        for (int i = 0; i < 3; i++) {
            System.out.print("----");
        }
        System.out.println("-");
    }

    private static int readUserInput() {
        while (true) {
            System.out.println("Please type number between 1 and 9:");
            var userInput = new Scanner(System.in).nextLine();
            if (userInput.length() == 1) {
                var ch = userInput.charAt(0);
                if (ch >= '1' && ch <= '9') {
                    var number = Integer.parseInt(userInput);
                    if (isCellFree(number)) {
                        return number;
                    } else {
                        System.out.println("Cell with number=" + number + " is not free!");
                    }
                }
            }
        }
    }

    private static boolean isCellFree(int number) {
        int[] indexes = toIndexes(number);
        return GAME_TABLE[indexes[0]][indexes[1]] == EMPTY;
    }

    private static int[] toIndexes(int number) {
        if (number == 1) {
            return new int[]{2, 0};
        } else if (number == 2) {
            return new int[]{2, 1};
        } else if (number == 3) {
            return new int[]{2, 2};
        } else if (number == 4) {
            return new int[]{1, 0};
        } else if (number == 5) {
            return new int[]{1, 1};
        } else if (number == 6) {
            return new int[]{1, 2};
        } else if (number == 7) {
            return new int[]{0, 0};
        } else if (number == 8) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, 2};
        }
    }

    private static void makeUserProgress(int number) {
        System.out.println("User made a progress.");
        makeProgress(number, USER);
    }

    private static void makeComputerProgress() {

        int number = findTwoCharToProgress(COMPUTER);
        if (number == 0) {
            number = findTwoCharToProgress(USER);
            if (number == 0) {
                number = findOneCharToProgress();
                if (number == 0) {
                    if (GAME_TABLE[1][1] == EMPTY) {
                        number = 5;
                    } else {
                        while (true) {
                            number = new Random().nextInt(9) + 1;
                            break;
                            }
                        }
                    }
                }
            }
        if (isCellFree(number)) {
            System.out.println("Computer made a progress.");
            makeProgress(number, COMPUTER);
        }
    }

    private static int findOneCharToProgress() {
        int res = findFullHorizontalCell();
        if (res != 0) {
            return res;
        }
        res = findFullVerticalCell();
        return res;
    }

    private static int findFullVerticalCell() {
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[0][i] == COMPUTER &&
                    GAME_TABLE[1][i] == EMPTY &&
                    GAME_TABLE[2][i] == EMPTY) {
                return getNumberByIndexes(1, i);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[1][i] == COMPUTER &&
                    GAME_TABLE[0][i] == EMPTY &&
                    GAME_TABLE[2][i] == EMPTY) {
                return getNumberByIndexes(0, i);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[2][i] == COMPUTER &&
                    GAME_TABLE[0][i] == EMPTY &&
                    GAME_TABLE[1][i] == EMPTY) {
                return getNumberByIndexes(0, i);
            }
        }
        return 0;
    }

    private static int findFullHorizontalCell() {
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[i][0] == COMPUTER &&
                    GAME_TABLE[i][1] == EMPTY &&
                    GAME_TABLE[i][2] == EMPTY) {
                return getNumberByIndexes(i, 1);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[i][1] == COMPUTER &&
                    GAME_TABLE[i][0] == EMPTY &&
                    GAME_TABLE[i][2] == EMPTY) {
                return getNumberByIndexes(i, 0);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[i][2] == COMPUTER &&
                    GAME_TABLE[i][0] == EMPTY &&
                    GAME_TABLE[i][1] == EMPTY) {
                return getNumberByIndexes(i, 0);
            }
        }
        return 0;
    }

    private static int findTwoCharToProgress(char ch) {
        int res = findEmptyHorizontalCell(ch);
        if (res != 0) {
            return res;
        }
        res = findEmptyVerticalCell(ch);
        if (res != 0) {
            return res;
        }
        res = findEmptyDiaonalCell1(ch);
        if (res != 0) {
            return res;
        }
        res = findEmptyDiaonalCell2(ch);
        return res;
    }

    private static int findEmptyHorizontalCell(char ch) {
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[i][0] == GAME_TABLE[i][1] &&
                    GAME_TABLE[i][0] == ch) {
                int res = getNumberByIndexes(i, 2);
                if (isCellFree(res)) {
                    return res;
                }
            }
            if (GAME_TABLE[i][0] == GAME_TABLE[i][2] &&
                    GAME_TABLE[i][0] == ch) {
                int res = getNumberByIndexes(i, 1);
                if (isCellFree(res)) {
                    return res;
                }
            }
            if (GAME_TABLE[i][1] == GAME_TABLE[i][2] &&
                    GAME_TABLE[i][1] == ch) {
                int res = getNumberByIndexes(i, 0);
                if (isCellFree(res)) {
                    return res;
                }
            }
        }
        return 0;
    }

    private static int findEmptyVerticalCell(char ch) {
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[0][i] == GAME_TABLE[1][i] &&
                    GAME_TABLE[0][i] == ch) {
                int res = getNumberByIndexes(2, i);
                if (isCellFree(res)) {
                    return res;
                }
            }
            if (GAME_TABLE[0][i] == GAME_TABLE[2][i] &&
                    GAME_TABLE[0][i] == ch) {
                int res = getNumberByIndexes(1, i);
                if (isCellFree(res)) {
                    return res;
                }
            }
            if (GAME_TABLE[1][i] == GAME_TABLE[2][i] &&
                    GAME_TABLE[1][i] == ch) {
                int res = getNumberByIndexes(0, i);
                if (isCellFree(res)) {
                    return res;
                }
            }
        }
        return 0;
    }

    private static int findEmptyDiaonalCell1(char ch) {
        if (GAME_TABLE[0][0] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == ch) {
            int res = 3;
            if (isCellFree(res)) {
                return res;
            }
        }
        if (GAME_TABLE[0][0] == GAME_TABLE[2][2] &&
                GAME_TABLE[0][0] == ch) {
            int res = 5;
            if (isCellFree(res)) {
                return res;
            }
        }
        if (GAME_TABLE[1][1] == GAME_TABLE[2][2] &&
                GAME_TABLE[1][1] == ch) {
            int res = 7;
            if (isCellFree(res)) {
                return res;
            }
        }
        return 0;
    }

    private static int findEmptyDiaonalCell2(char ch) {
        if (GAME_TABLE[0][2] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == ch) {
            int res = 1;
            if (isCellFree(res)) {
                return res;
            }
        }
        if (GAME_TABLE[1][1] == GAME_TABLE[2][0] &&
                GAME_TABLE[2][0] == ch) {
            int res = 9;
            if (isCellFree(res)) {
                return res;
            }
        }
        if (GAME_TABLE[2][0] == GAME_TABLE[0][2] &&
                GAME_TABLE[0][2] == ch) {
            int res = 5;
            if (isCellFree(res)) {
                return res;
            }
        }
        return 0;
    }

    private static int getNumberByIndexes(int row, int col) {
        int number = 0;
        if (row == 0 && col == 0) {
            return 7;
        }
        if (row == 0 && col == 1) {
            return 8;
        }
        if (row == 0 && col == 2) {
            return 9;
        }
        if (row == 1 && col == 0) {
            return 4;
        }
        if (row == 1 && col == 1) {
            return 5;
        }
        if (row == 1 && col == 2) {
            return 6;
        }
        if (row == 2 && col == 0) {
            return 1;
        }
        if (row == 2 && col == 1) {
            return 2;
        }
        if (row == 2 && col == 2) {
            return 3;
        }
        return number;
    }

    private static void makeProgress(int number, char ch) {
        var indexes = toIndexes(number);
        GAME_TABLE[indexes[0]][indexes[1]] = ch;
    }

    private static boolean isWinner(char ch) {
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[i][0] == GAME_TABLE[i][1] &&
                    GAME_TABLE[i][1] == GAME_TABLE[i][2] &&
                    GAME_TABLE[i][1] == ch) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (GAME_TABLE[0][i] == GAME_TABLE[1][i] &&
                    GAME_TABLE[1][i] == GAME_TABLE[2][i] &&
                    GAME_TABLE[1][i] == ch) {
                return true;
            }
        }
        if (GAME_TABLE[0][0] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == GAME_TABLE[2][2] &&
                GAME_TABLE[1][1] == ch) {
            return true;
        } else if (GAME_TABLE[2][0] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == GAME_TABLE[0][2] &&
                GAME_TABLE[1][1] == ch) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (GAME_TABLE[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
/*if (isCellFree(number)) {
            return number;
        } else {
            number = 0;
        }
                if (GAME_TABLE[i][j] == ch) {
                    if (count == 0) {
                        firstRow = i;
                        firstColumn = j;
                        count++;
                    }
                    if (count == 1) {
                        if (firstRow == i) {
                            row = i;
                        } else {
                            row = 3 - i - firstRow;
                        }
                        if (firstColumn == j) {
                            col = j;
                        } else {
                            col = 3 - j - firstRow;
                        }

                        }
                    }
                }*/

