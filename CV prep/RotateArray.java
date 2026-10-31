public class RotateArray {

    public void rotate(int[] nums, int k) {
       
        if(nums.length<=1||k<0) return;
        if(k>=nums.length) k=k % nums.length;
        int start=0;
        int finish=nums.length-1;
        while(start<finish){
            int tmp=nums[start];
            nums[start]=nums[finish];
            nums[finish]=tmp;
            start++;
            finish--;
        }
        start=0;
        finish=k-1;
        while(start<finish){
            int tmp=nums[start];
            nums[start]=nums[finish];
            nums[finish]=tmp;
            start++;
            finish--;
        }
        start=k;
        finish=nums.length-1;
        while(start<=finish){
            int tmp=nums[start];
            nums[start]=nums[finish];
            nums[finish]=tmp;
            start++;
            finish--;
        }
        
    }
}
