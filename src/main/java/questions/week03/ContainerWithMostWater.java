package questions.week03;

/**
 * 11. Container With Most Water
 * https://leetcode.com/problems/container-with-most-water/
 *
 * Two walls. Water = shorter wall * distance. Biggest water.
 *
 * Example: [1,8,6,2,5,4,8,3,7] -> 49
 */
public class ContainerWithMostWater {

    public int maxArea(int[] height) {
        int left=0;
        int right=height.length-1;
        int best=0;
        while(left<right){
            int h=Math.min(height[left],height[right]);
            int area=h*(right-left);
            if(area>best){
                best=area;
            }
            if(height[left]<height[right]){
                left++;
            }
            else{
                right--;
            }
        }
        return best;
    }
}
