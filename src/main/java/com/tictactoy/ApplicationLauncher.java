package com.tictactoy;

import com.tictactoy.game.MineSweeper;
import com.tictactoy.game.TicTacToy;

import java.util.Scanner;

public class ApplicationLauncher {

    private static final String TIC_TAC_TOY = "tic_tac_toy";
    private static final String MINE_SWEEPER = "mine_sweeper";

    public static void main(String[] args) {
        System.out.println("Выберите игру: tic_tac_toy или mine_sweeper");

        Scanner scanner = new Scanner(System.in);
        String gameName = scanner.nextLine();

        if (gameName.equals(TIC_TAC_TOY)) {
            TicTacToy.play();
        }

        if (gameName.contains(MINE_SWEEPER)) {
            MineSweeper.play();
        }

        scanner.close();
    }

}
