package com.tictactoy.game;

import java.util.Random;
import java.util.Scanner;

public class TicTacToy {

    private static int matrixSize = 3;

    private static final char DOT_EMPTY = '•';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';

    private TicTacToy() {}

    public static void play() {
        System.out.println("Укажите размер матрицы");
        Scanner scanner = new Scanner(System.in);
        matrixSize = scanner.nextInt();
        char[][] map = initMap();

        printMap(map);

        while (true) {
            humanTurn(map);
            printMap(map);

            if (checkWin(map, DOT_X)) {
                System.out.println("Победил человек");
                break;
            }

            if (isMapFull(map)) {
                System.out.println("Ничья");
                break;
            }

            aiTurn(map);
            printMap(map);

            if (checkWin(map, DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }

            if (isMapFull(map)) {
                System.out.println("Ничья");
                break;
            }
        }

        System.out.println("Игра закончена");


        System.out.println("Хотите повторить: Y/N");
        scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if (answer.toUpperCase().equals("Y")) {
            play();
        }
    }

    private static char[][] initMap() {
        char[][] map = new char[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }

        return map;
    }

    private static void printMap(char[][] map) {
        for (int i = 0; i <= matrixSize; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < matrixSize; i++) {
            System.out.print((i + 1) + " ");

            for (int j = 0; j < matrixSize; j++) {
                System.out.print(map[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    private static void humanTurn(char[][] map) {
        final Scanner scanner = new Scanner(System.in);
        int x, y;

        do {
            System.out.println("Введите координаты в формате X Y");

            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(map, x, y));

        map[y][x] = DOT_X;
    }

    private static void aiTurn(char[][] map) {
        Random random = new Random();
        int x, y;

        TicTacDot diagonalTicTacDot = getDiagonalPrediction(map, matrixSize - 1);
        TicTacDot horizontalTicTacDot = getHorizontalPrediction(map, matrixSize - 1);
        TicTacDot verticalTicTacDot = getVerticalPrediction(map, matrixSize - 1);
        System.out.println(diagonalTicTacDot + " " + horizontalTicTacDot + " " + verticalTicTacDot);
        do {
            if (diagonalTicTacDot != null) {
                x = diagonalTicTacDot.getX();
                y = diagonalTicTacDot.getY();
            } else if (horizontalTicTacDot != null) {
                x = horizontalTicTacDot.getX();
                y = horizontalTicTacDot.getY();
            } else if (verticalTicTacDot != null) {
                x = verticalTicTacDot.getX();
                y = verticalTicTacDot.getY();
            } else {
                x = random.nextInt(matrixSize);
                y = random.nextInt(matrixSize);
            }
        } while (!isCellValid(map, x, y));

        System.out.printf("Компьютер походил в точку %d %d", (x + 1), (y + 1));

        map[y][x] = DOT_O;
    }

    private static boolean isCellValid(char[][] map, int x, int y) {
        return map[y][x] == DOT_EMPTY;
    }

    private static boolean isMapFull(char[][] map) {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    private static boolean checkWin(char[][] map, char symbol) {
        if (isDiagonalCheckTrue(map, symbol)) return true;
        if (isHorizontalCheckTrue(map, symbol)) return true;
        if (isVerticalCheckTrue(map, symbol)) return true;

        return false;
    }
    
    private static boolean isDiagonalCheckTrue(char[][] map, char symbol) {
        boolean isFirstDiagonalChecked = true;
        boolean isSecondDiagonalChecked = true;

        for (int i = 0; i < matrixSize; i++) {
            if (map[i][i] != symbol) {
                isFirstDiagonalChecked = false;
            }

            if (map[(matrixSize - 1) - i][i] != symbol) {
                isSecondDiagonalChecked = false;
            }
        }

        return isFirstDiagonalChecked || isSecondDiagonalChecked;
    }

    private static boolean isHorizontalCheckTrue(char[][] map, char symbol) {
        for (int i = 0; i < matrixSize; i++) {
            boolean isAllChecked = true;

            for (int j = 0; j < matrixSize; j++) {
                if (map[i][j] != symbol) {
                    isAllChecked = false;
                    break;
                }
            }

            if (isAllChecked) return true;
        }

        return false;
    }

    private static boolean isVerticalCheckTrue(char[][] map, char symbol) {
        for (int i = 0; i < matrixSize; i++) {
            boolean isAllChecked = true;

            for (int j = 0; j < matrixSize; j++) {
                if (map[j][i] != symbol) {
                    isAllChecked = false;
                    break;
                }
            }

            if (isAllChecked) return true;
        }

        return false;
    }

    private static TicTacDot getDiagonalPrediction(char[][] map, int predictionAccuracy) {
        TicTacDot[] firstDiagonalAvailableDot = new TicTacDot[matrixSize];
        TicTacDot[] secondDiagonalAvailableDot = new TicTacDot[matrixSize];
        int firstDiagonalMarkCounter = 0;
        int secondDiagonalMarkCounter = 0;
        int firstDiagonalAvailableDotCounter = 0;
        int secondDiagonalAvailableDotCounter = 0;
        boolean isFirstDiagonalAvailable = true;
        boolean isSecondDiagonalAvailable = true;

        for (int i = 0; i < matrixSize; i++) {
            if (map[i][i] == DOT_X && isFirstDiagonalAvailable) {
                firstDiagonalMarkCounter++;
            } else if (map[i][i] == DOT_O) {
                isFirstDiagonalAvailable = false;
            } else if (isFirstDiagonalAvailable) {
                firstDiagonalAvailableDot[firstDiagonalAvailableDotCounter] = new TicTacDot(i, i);
                firstDiagonalAvailableDotCounter++;
            }

            if (map[(matrixSize - 1) - i][i] == DOT_X && isSecondDiagonalAvailable) {
                secondDiagonalMarkCounter++;
            } else if (map[(matrixSize - 1) - i][i] == DOT_O) {
                isSecondDiagonalAvailable = false;
            } else if (isSecondDiagonalAvailable) {
                secondDiagonalAvailableDot[secondDiagonalAvailableDotCounter] = new TicTacDot(i, (matrixSize - 1) - i);
                secondDiagonalAvailableDotCounter++;
            }
        }

        if (isFirstDiagonalAvailable && predictionAccuracy <= firstDiagonalMarkCounter) {
            return firstDiagonalAvailableDot[0];
        }

        if (isSecondDiagonalAvailable && predictionAccuracy <= secondDiagonalMarkCounter) {
            return secondDiagonalAvailableDot[0];
        }

        return null;
    }

    private static TicTacDot getHorizontalPrediction(char[][] map, int predictionAccuracy) {
        int maxRowMarkedCounter = 0;
        TicTacDot[] horizontalAvailableDot = new TicTacDot[matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            int maxRowAvailableCounter = 0;
            int tempMaxRowMarkedCounter = 0;
            TicTacDot[] tempHorizontalAvailableDot = new TicTacDot[matrixSize];

            for (int j = 0; j < matrixSize; j++) {
                if (map[i][j] == DOT_X) {
                    tempMaxRowMarkedCounter++;
                  } else if (map[i][j] == DOT_O) {
                    break;
                } else {
                    tempHorizontalAvailableDot[maxRowAvailableCounter] = new TicTacDot(j, i);
                    maxRowAvailableCounter++;
                }
            }

            if (tempMaxRowMarkedCounter > maxRowMarkedCounter) {
                maxRowMarkedCounter = tempMaxRowMarkedCounter;
                horizontalAvailableDot = tempHorizontalAvailableDot;
            }
        }

        if (predictionAccuracy <= maxRowMarkedCounter) {
            return horizontalAvailableDot[0];
        }

        return null;
    }

    private static TicTacDot getVerticalPrediction(char[][] map, int predictionAccuracy) {
        int maxColumnMarkedCounter = 0;
        TicTacDot[] verticalAvailableDot = new TicTacDot[matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            int maxColumnAvailableCounter = 0;
            int tempMaxColumnMarkedCounter = 0;
            TicTacDot[] tempVerticalAvailableDot = new TicTacDot[matrixSize];

            for (int j = 0; j < matrixSize; j++) {
                if (map[j][i] == DOT_X) {
                    maxColumnMarkedCounter++;
                } else if (map[j][i] == DOT_O) {
                    break;
                } else {
                    verticalAvailableDot[maxColumnAvailableCounter] = new TicTacDot(i, j);
                    maxColumnAvailableCounter++;
                }
            }

            if (tempMaxColumnMarkedCounter > maxColumnMarkedCounter) {
                maxColumnMarkedCounter = tempMaxColumnMarkedCounter;
                verticalAvailableDot = tempVerticalAvailableDot;
            }
        }

        if (predictionAccuracy <= maxColumnMarkedCounter) {
            return verticalAvailableDot[0];
        }

        return null;
    }

}
