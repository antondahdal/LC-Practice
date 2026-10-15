package questions.week09;

/**
 * 34. Find First and Last Position of Element in Sorted Array
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 *
 * Given a sorted array and a target, return the first and last index of that target.
 * If it is missing, return [-1, -1].
 *
 * Example: [5,7,7,8,8,10], target 8 -> [3, 4].
 * [5,7,7,8,8,10], target 6 -> [-1, -1].
 */
public class FindFirstAndLastPosition {

    public int[] searchRange(int[] nums, int target) {
        int[] retarr=new int[2];
        retarr[0]=-1;
        retarr[1]=-1;
        if (nums==null||nums.length==0) return retarr;
        if(nums.length==1&&nums[0]==target){
            retarr[0]=0;
            retarr[1]=0;
            return retarr;
        }
        int low=0,high=nums.length-1;
        
        while(low<=high){
            int mid=low+(high-low)/2;
           
            if(nums[mid]==target) {
               retarr[0]=mid;
               retarr[1]=mid;
               if(mid-1>=0 && nums[mid-1]==target){
                retarr[1]=mid;
                for(int i=0;i<mid;i++){
                    if(nums[i]==target) {
                        retarr [0]=i;
                        break;
                }
               }
            
            }
            if(mid+1<nums.length && nums[mid+1]==target){
                if(retarr[0]==-1){
                    retarr[0]=mid;
                }
                for(int i=mid;i<nums.length;i++){
                    if(nums[i]==target) {
                        retarr [1]=i;
                }
               }
            }
           
            return retarr;
           
             }
        else if (nums[mid]<target) low=mid+1;
        else high=mid-1;
        
    }
    return retarr;
}
}


