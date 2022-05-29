import java.util.*;

class ttt {

    char[][] board;

    public ttt() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            board[i] = new char[] {'-', '-', '-'};
        }
    }

    public void update(int row, int col, int piece) {
        char temp = 'O';
        if (piece == 1) temp = 'X';
        board[row][col] = temp;
    }

    public void picture() {

        System.out.println(" ");
        System.out.println(String.valueOf(board[0][0]) + " " + String.valueOf(board[0][1]) + " " + String.valueOf(board[0][2]));
        System.out.println(String.valueOf(board[1][0]) + " " + String.valueOf(board[1][1]) + " " + String.valueOf(board[1][2]));
        System.out.println(String.valueOf(board[2][0]) + " " + String.valueOf(board[2][1]) + " " + String.valueOf(board[2][2]));
        System.out.println(" ");
    }


    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        System.out.println("Press 1 to go first, 0 to go second");

        int goFirst = myObj.nextInt();

        int COMPUTER = 2;
        int PLAYER = 1;

        ttt game = new ttt();
        int countMoves = 0;
        int move = 0;
        HashSet<Integer> played = new HashSet();
        HashSet<tttLine> almostWin = new HashSet();
        HashSet<tttLine> almostWinPlayer = new HashSet();
        boolean gameOver = false;
        boolean playerWon = true;

        tttLine l1 = new tttLine(1, 2, 3);
        tttLine l2 = new tttLine(4, 5, 6);
        tttLine l3 = new tttLine(7, 8, 9);
        tttLine l4 = new tttLine(1, 4, 7);
        tttLine l5 = new tttLine(2, 5, 8);
        tttLine l6 = new tttLine(3, 6, 9);
        tttLine l7 = new tttLine(1, 5, 9);
        tttLine l8 = new tttLine(3, 5, 7);

        HashMap<Integer, List<tttLine>> myMap = new HashMap();
        HashMap<Integer, List<Integer>> square = new HashMap();

        for (int i = 1; i <= 9; i++) {
            played.add(i);
            myMap.put(i, new ArrayList());
            square.put(i, new ArrayList());
            square.get(i).add((i-1)/3);
            square.get(i).add((i-1)%3);
        }

        tttLine[] lines = new tttLine[] {l1, l4, l7};
        myMap.get(1).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l1, l5};
        myMap.get(2).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l1, l6, l8};
        myMap.get(3).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l2, l4};
        myMap.get(4).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l2, l5, l7, l8};
        myMap.get(5).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l2, l6};
        myMap.get(6).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l3, l4, l8};
        myMap.get(7).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l3, l5};
        myMap.get(8).addAll(Arrays.asList(lines));

        lines = new tttLine[] {l3, l6, l7};
        myMap.get(9).addAll(Arrays.asList(lines));

        game.picture();
        
        if (goFirst == 0) {  // computer goes first

            move = (int)(Math.random() * (9)) + 1;
            played.remove(move);
            System.out.println("Computer has placed a piece on square " + String.valueOf(move));
            game.update(square.get(move).get(0), square.get(move).get(1), COMPUTER);
            game.picture();

            for (tttLine x : myMap.get(move)) {
                x.add(COMPUTER);
                x.spaces.remove(x.spaces.indexOf(move));
            }
            countMoves++;
        }
        

        while (countMoves < 9) {

            System.out.println(played);
            System.out.println("What move do you want to make?");

            move = myObj.nextInt();
            
            while (!played.contains(move)) {
                System.out.println("This square already occupied");
                move = myObj.nextInt();
            }
            
            played.remove(move);

            game.update(square.get(move).get(0), square.get(move).get(1), PLAYER);

            game.picture();

            for (tttLine x : myMap.get(move)) {
                x.add(PLAYER);
                x.spaces.remove(x.spaces.indexOf(move));
                if (x.player == 2 && x.comp == 0) {
                    almostWinPlayer.add(x);
                }
                if (x.player == 3) {
                    gameOver = true;
                    break;
                }
            }

            if (gameOver == true) break; // game has finished, player won

            countMoves++;

            if (countMoves < 9) { // computer moving

                int curr = move;

                if (!almostWin.isEmpty()) {
                    for (tttLine line : almostWin) {
                        if (line.player == 0 && line.comp == 2) {
                            System.out.println(line.player);
                            move = line.spaces.get(0);
                            almostWin.clear();
                            break;
                        }
                    }

                    if (move == curr) {
                        if (!almostWinPlayer.isEmpty()) {
                            for (tttLine line : almostWinPlayer) {
                                if (line.comp == 0) {
                                    move = line.spaces.get(0);
                                    almostWinPlayer.remove(line);
                                    break;
                                }
                            }
                        }

                        else {
                            move = (int)(Math.random() * (9)) + 1;
                            while (!played.contains(move)) {
                            move = (int)(Math.random() * (9)) + 1;
                        }
                    }
                    }
                }

                else if (!almostWinPlayer.isEmpty()) {
                    for (tttLine line : almostWinPlayer) {
                        if (line.comp == 0) {
                            move = line.spaces.get(0);
                            almostWinPlayer.remove(line);
                            break;
                        }
                    }
                }

                else {
                    move = (int)(Math.random() * (9)) + 1;
                    while (!played.contains(move)) {
                        move = (int)(Math.random() * (9)) + 1;
                    }
                }

                played.remove(move);

                
                
                System.out.println("Computer has placed a piece on square " + String.valueOf(move));
                game.update(square.get(move).get(0), square.get(move).get(1), COMPUTER);
                game.picture();

                for (tttLine x : myMap.get(move)) {
                    x.add(COMPUTER);
                    x.spaces.remove(x.spaces.indexOf(move));
                    if (x.comp == 2 && x.player == 0) {
                        almostWin.add(x);
                    }
                    if (x.comp == 3) {
                        gameOver = true;
                        playerWon = false;
                        break;
                    }
                }

                if (gameOver == true) break; // game has finished, computer won
    
                countMoves++;
            }
        }

        if (countMoves == 9) System.out.println("It's a draw!");
        else if (playerWon) System.out.println("You won!");
        else System.out.println("You lost!");
    }
}