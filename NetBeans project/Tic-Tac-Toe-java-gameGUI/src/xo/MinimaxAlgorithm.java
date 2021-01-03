package xo;


import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.Arrays;
import static jdk.nashorn.internal.objects.Global.Infinity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ola Abaza
 */
public class MinimaxAlgorithm {

    char[][] board;

    public MinimaxAlgorithm() {
        board = new char[3][3];
        emptyBoard();

    }

    public void fillBoard(int row, int col) {
        board[row][col] = 'X';

    }

    public int toNumber(int row, int col) {
        int num = -1;
        if (row == 0 && col == 0) {
            num = 0;
        } else if (row == 0 && col == 1) {
            num = 1;
        } else if (row == 0 && col == 2) {
            num = 2;
        } else if (row == 1 && col == 0) {
            num = 3;
        } else if (row == 1 && col == 1) {
            num = 4;
        } else if (row == 1 && col == 2) {
            num = 5;
        } else if (row == 2 && col == 0) {
            num = 6;
        } else if (row == 2 && col == 1) {
            num = 7;
        } else if (row == 2 && col == 2) {
            num = 8;
        }
        return num;

    }

    public void emptyBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }

    }

    public int bestMove() {
        int row = -1, col = -1;
        int bestScore = -10000;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                    int score = minimax(board, 0, false);
                    board[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        board[row][col] = 'O';
        return toNumber(row, col);
    }

    ///////////////////////////////
    public char checkWinner() {
        char winner = '-';

        // horizontal
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != '-') {
                winner = board[i][0];
            }
        }

        // Vertical
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != '-') {
                winner = board[0][i];
            }
        }

        // Diagonal
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != '-') {
            winner = board[0][0];
        }
        if (board[2][0] == board[1][1] && board[2][0] == board[0][2] && board[2][0] != '-') {
            winner = board[2][0];
        }

        int openSpots = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    openSpots++;
                }
            }
        }
        if (winner == '-' && openSpots == 0) {
            return 'T';
        } else {
            return winner;
        }
    }

    ////////////////////////////////////
    public int minimax(char board[][], int depth, Boolean isMax) {
        char result = checkWinner();
        switch (result) {
            case 'O':
                return 10;
            case 'X':
                return -10;
            case 'T':
                return 0;
            default:
                break;
        }

        if (isMax) {
            int bestScore = -100000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = '-';
                        bestScore = max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = '-';
                        bestScore = min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

}
