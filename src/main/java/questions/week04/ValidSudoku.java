package questions.week04;

import java.util.HashSet;
import java.util.Set;

/**
 * 36. Valid Sudoku
 * https://leetcode.com/problems/valid-sudoku/
 *
 * Check the board is valid so far. Do not solve it.
 *
 * Example: filled digits must be unique in row / col / 3x3 box.
 */
public class ValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        Set<String> set=new HashSet<>();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                char c=board[i][j];
                if(c=='.') continue;
                String row=c+" in row "+i;
                String col=c+" in col "+j;
                int box=(i/3)*3+(j/3);
                String b=c+" in box "+box;
                if(!set.add(row) || !set.add(col) || !set.add(b)){
                    return false;
                }
            }
        }
        return true;
    }
}
