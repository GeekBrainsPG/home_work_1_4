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

        do {
            x = random.nextInt(matrixSize);
            y = random.nextInt(matrixSize);
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

}
