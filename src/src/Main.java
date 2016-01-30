package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MuhammedAbdullah
 */
public class Main {

    static int[][] gameSpace = new int[3][3];
    public static GameFrame gf = new GameFrame();
    public static int state = 0;
    public static Random rnd = new Random();

    public static ArrayList<int[]> mysol = new ArrayList<>();

    public static void main(String[] args) {
        // THESE ARE THE TOTAL GOAL STATES. Robot doesnt know them first.
        // Robot learns the goal states and starts to play according to goal states if robot or we win a round.
       /* mysol.add(new int[]{1, 2, 3});
        mysol.add(new int[]{4, 5, 6});
        mysol.add(new int[]{7, 8, 9});
        mysol.add(new int[]{1, 5, 9});
        mysol.add(new int[]{3, 5, 7});
        mysol.add(new int[]{1, 4, 7});
        mysol.add(new int[]{2, 5, 8});
        mysol.add(new int[]{3, 6, 9});*/
        gf.setVisible(true);
    }

    public static int anyWin(int[][] stateSpace) {
        int[] t;
        for (int i = 0; i < 3; i++) {
            if (stateSpace[i][0] != 0 && stateSpace[i][0] == stateSpace[i][1] && stateSpace[i][1] == stateSpace[i][2]) {
                t = new int[3];
                int p =0;
                if(i==0)
                    p=0;
                else if(i==1)
                    p=3;
                else if(i==2)
                    p=6;
                t[0] = p + 1;
                t[1] = p + 2;
                t[2] = p + 3;
                addToGoals(t);
                return stateSpace[i][0];
            }
        }

        for (int i = 0; i < 3; i++) {
            if (stateSpace[0][i] != 0 && stateSpace[0][i] == stateSpace[1][i] && stateSpace[1][i] == stateSpace[2][i]) {
                t = new int[3];
                t[0] = i+1;
                t[1] = i+1 + 3;
                t[2] = i+1 + 6;
                addToGoals(t);
                return stateSpace[0][i];
            }
        }

        if (stateSpace[0][0] == stateSpace[1][1] && stateSpace[2][2] != 0 && stateSpace[1][1] == stateSpace[2][2]) {
            t = new int[3];
            t[0] = 1;
            t[1] = 5;
            t[2] = 9;
            addToGoals(t);
            return stateSpace[0][0];
        }
        if (stateSpace[0][2] == stateSpace[1][1] && stateSpace[0][2] != 0 && stateSpace[2][0] == stateSpace[1][1]) {
            t = new int[3];
            t[0] = 3;
            t[1] = 5;
            t[2] = 7;
            addToGoals(t);
            return stateSpace[0][2];
        }

        return 0;
    }
    
    public static void makeMostIntelligentMove(ArrayList<int[]> gList) {
        HashMap<int[], Integer> hm = getMap(gList);
        if (gList.size() == 0) {
            int c = rnd.nextInt(3);
            int r = rnd.nextInt(3);
            while (gameSpace[c][r] != 0) {
                c = rnd.nextInt(3);
                r = rnd.nextInt(3);
            }
            play(c, r);
            return;
        }
        int[] tt = gList.get(0);
        int p = 0;
        for (int[] k : hm.keySet()) {
            if (hm.get(k) > p) {
                p = hm.get(k);
                tt = k;
            }
        }
        intMove(tt, p);
    }


    public static int getPoint(int[] array) {
        if(has(array, 1)==2 && has(array, 0)==1){
            return 11;
        }
        else if (has(array, 2) == 2 && has(array, 0) == 1) {
            return 10;
        } else if (has(array, 0) == 2 && has(array, 2) == 1) {
            return 9;
        } else if (has(array, 0) == 3) {
            return 8;
        } else if (has(array, 0) == 1 && has(array, 1) == 1 && has(array, 2) == 1) {
            return 7;
        } else if (has(array, 0) == 1 && has(array, 1) == 2) {
            return 6;
        } else if (has(array, 0) == 2 && has(array, 1) == 1) {
            return 5;
        } else {
            return 0;
        }
    }

    public static int has(int[] array, int a) {
        int c = 0;
        for (int i = 0; i < array.length; i++) {
            if (numToElement(array[i]) == a) {
                c++;
            }
        }
        return c;
    }

    public static int[] numToIndex(int x) {
        int[] a = new int[2];
        if (x == 1) {
            a[0] = 0;
            a[1] = 0;
        } else if (x == 2) {
            a[0] = 0;
            a[1] = 1;
        } else if (x == 3) {
            a[0] = 0;
            a[1] = 2;
        } else if (x == 4) {
            a[0] = 1;
            a[1] = 0;
        } else if (x == 5) {
            a[0] = 1;
            a[1] = 1;
        } else if (x == 6) {
            a[0] = 1;
            a[1] = 2;
        } else if (x == 7) {
            a[0] = 2;
            a[1] = 0;
        } else if (x == 8) {
            a[0] = 2;
            a[1] = 1;
        } else if (x == 9) {
            a[0] = 2;
            a[1] = 2;
        }
        return a;
    }

    public static int numToElement(int a) {
        if (a == 1) {
            return gameSpace[0][0];
        } else if (a == 2) {
            return gameSpace[0][1];
        } else if (a == 3) {
            return gameSpace[0][2];
        } else if (a == 4) {
            return gameSpace[1][0];
        } else if (a == 5) {
            return gameSpace[1][1];
        } else if (a == 6) {
            return gameSpace[1][2];
        } else if (a == 7) {
            return gameSpace[2][0];
        } else if (a == 8) {
            return gameSpace[2][1];
        } else {
            return gameSpace[2][2];
        }
    }

    public static HashMap<int[], Integer> getMap(ArrayList<int[]> aList) {
        HashMap<int[], Integer> m = new HashMap<int[], Integer>();

        for (int i = 0; i < aList.size(); i++) {
            int a = getPoint(aList.get(i));
            m.put(aList.get(i), getPoint(aList.get(i)));
        }
        return m;
    }

    public static void intMove(int[] array, int point) {
        if (point == 11 || point == 10 || point == 7 || point == 6) {
            for (int i = 0; i < array.length; i++) {
                int[] a = numToIndex(array[i]);
                if (gameSpace[a[0]][a[1]] == 0) {
                    play(a[0], a[1]);
                    return;
                }
            }
        } else if (point == 9 || point == 5) {
            boolean f = false;
            int s = rnd.nextInt(2);
            if (s == 1) {
                f = true;
            }
            for (int i = 0; i < array.length; i++) {
                int[] a = numToIndex(array[i]);
                if (gameSpace[a[0]][a[1]] == 0 && !f) {
                    f = true;
                } else if (gameSpace[a[0]][a[1]] == 0) {
                    play(a[0], a[1]);
                    return;
                }
            }
        } else if (point == 8) {
            int rc = rnd.nextInt(3);
            int[] abc = numToIndex(array[rc]);
            play(abc[0], abc[1]);
            return;
        }
        else{
            int c = rnd.nextInt(3);
            int r = rnd.nextInt(3);
            while (gameSpace[c][r] != 0) {
                c = rnd.nextInt(3);
                r = rnd.nextInt(3);
            }
            play(c, r);
            return;
        }
    }

    public static void makeMove(int i) {
        if (i == 1) {
            play(0, 0);
        } else if (i == 2) {
            play(0, 1);
        } else if (i == 3) {
            play(0, 2);
        } else if (i == 4) {
            play(1, 0);
        } else if (i == 5) {
            play(1, 1);
        } else if (i == 6) {
            play(1, 2);
        } else if (i == 7) {
            play(2, 0);
        } else if (i == 8) {
            play(2, 1);
        } else if (i == 9) {
            play(2, 2);
        }
    }

    public static void addToGoals(int[] a) {
        int[] b;
        for (int i = 0; i < mysol.size(); i++) {
            b = mysol.get(i);
            if (a[0] == b[0] && a[1] == b[1] && a[2] == b[2]) {
                return;
            }
        }
        mysol.add(a);
    }


    public static void printGame(int[][] arr) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println("");
        }
    }

    public static void robot() {
        if (!GameFrame.flag) {
            return;
        }

        makeMostIntelligentMove(mysol);
        state = anyWin(gameSpace);
        GameFrame.state = state;
        gf.controlState(state);
    }

    public static void play(int x, int y) {
        gameSpace[x][y] = 1;
        if (x == 0 && y == 0) {
            gf.play1();
        } else if (x == 0 && y == 1) {
            gf.play2();
        } else if (x == 0 && y == 2) {
            gf.play3();
        } else if (x == 1 && y == 0) {
            gf.play4();
        } else if (x == 1 && y == 1) {
            gf.play5();
        } else if (x == 1 && y == 2) {
            gf.play6();
        } else if (x == 2 && y == 0) {
            gf.play7();
        } else if (x == 2 && y == 1) {
            gf.play8();
        } else if (x == 2 && y == 2) {
            gf.play9();
        }
    }

    public static boolean userMove(int x, int y) {
        if (gameSpace[x][y] == 0) {
            gameSpace[x][y] = 2;
            state = anyWin(gameSpace);
            GameFrame.state = state;
            GameFrame.flag = true;
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCompleted() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameSpace[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
