package questions.week04;

import java.util.HashSet;
import java.util.Set;

/**
 * 73. Set Matrix Zeroes
 * https://leetcode.com/problems/set-matrix-zeroes/
 *
 * If a cell is 0, zero that whole row and column. Mutate the matrix.
 *
 * Example: [[1,1,1],[1,0,1],[1,1,1]] -> [[1,0,1],[0,0,0],[1,0,1]]
 */
public class SetMatrixZeroes {

    public void setZeroes(int[][] matrix) {
        Set<Integer> rows=new HashSet<>();
        Set<Integer> cols=new HashSet<>();
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    rows.add(i);
                    cols.add(j);
                }
            }
        }
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(rows.contains(i) || cols.contains(j)){
                    matrix[i][j]=0;
                }
            }
        }
    }
}
