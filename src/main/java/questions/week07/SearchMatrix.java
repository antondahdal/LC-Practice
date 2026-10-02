package questions.week07;

/**
 * 74. Search a 2D Matrix
 * https://leetcode.com/problems/search-a-2d-matrix/
 *
 * m x n matrix. Each row is sorted left to right.
 * The first value of each row is bigger than the last value of the row above.
 * Return true if target is in the matrix.
 * Must run in O(log(m * n)).
 *
 * Example: [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target 3 -> true.
 * Same matrix, target 13 -> false.
 */
public class SearchMatrix {

    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length==0) return false;

        int low=0;
        int high=(matrix.length*matrix[0].length )-1;
       int cols = matrix[0].length;
        while(low<=high){
          int   mid=low+(high-low)/2;
          int tmpVal=(matrix[mid / cols][mid % cols]);
        if(tmpVal==target) return true;
        if((matrix[low / cols][low % cols])<target&&tmpVal<target){
            low=mid+1;
        }
        else {
            high=mid-1;
        }
        
        }
        return false;
    }
}
