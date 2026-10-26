# Week 11 — review sheet

Week 11 is one problem you already did, then one new problem.
The family stays hidden until you name it.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 217 | Contains Duplicate | HashSet | O(n) / O(n) | on-time |
| 189 | Rotate Array | Reverse, three times | O(n) / O(1) | on-time. One subtraction is not the remainder. |

---

## LC 217 Contains Duplicate — on-time

https://leetcode.com/problems/contains-duplicate/

### The problem

You get an array of integers.
Return true if any value appears at least twice.
Return false if every value appears once.
`[1, 2, 3, 1]` is true.
`[1, 2, 3, 4]` is false.

### The idea

Pattern: HashSet.
You keep each value you have already passed.
A second sighting is the duplicate.
Each value is visited once, so time is O(n).
The set can hold every value, so extra space is O(n).
The problem gives no small range that would shrink the set.
Sorting the array in place makes extra space O(1) and time O(n log n).

### How to solve it

1. Start with an empty set.
2. Walk each value once.
3. If the set already has it, return true.
4. Otherwise add it.
5. If the walk ends, return false.

`[1, 2, 3, 1]`:

| value | set before | action |
|---|---|---|
| 1 | empty | add 1 |
| 2 | 1 | add 2 |
| 3 | 1, 2 | add 3 |
| 1 | 1, 2, 3 | already there, return true |

### Holes to patch

**Looking for a special case that removes the extra space.**
The set still stores every distinct value.
`[1, 2, 3, 4]` fills the set with four numbers and returns false.
A length check for 0 or 1 is fine.
It does not change the O(n) extra space of the walk.

### How it went

On time.
5 of 5.
You coded it.
No coach fix.

### Memorize this

1. Start with an empty set.
2. Walk each value once.
3. If the set already has it, return true.
4. Otherwise add it.
5. If the walk ends, return false.

```java
public boolean containsDuplicate(int[] nums) {
    if (nums.length <= 1) return false;
    HashSet<Integer> set = new HashSet<>();
    for (int x : nums) {
        if (set.contains(x)) return true;
        else set.add(x);
    }
    return false;
}
```

Say in the interview: I keep each value I have already passed, and a second sighting is the duplicate.

---

## LC 189 Rotate Array — on-time

https://leetcode.com/problems/rotate-array/

### The problem

You get an array and a step count `k`.
Move the values to the right by `k` steps.
Change that same array.
`[1, 2, 3, 4, 5, 6, 7]` with `k = 3` becomes `[5, 6, 7, 1, 2, 3, 4]`.

### The idea

Pattern: reverse.
Reverse the whole array, then reverse the first `k` values, then reverse the rest.
Each reverse puts a block in place and turns it forward again.
You keep the two ends of the block and swap them as they walk inward.
Time is O(n).
Extra space is O(1).
The step count is the remainder of `k` divided by the length.

### How to solve it

1. `k` becomes `k % nums.length`.
2. If that remainder is 0, stop.
3. Reverse the whole array.
4. Reverse the first `k` values.
5. Reverse everything after that.

`[1, 2, 3, 4, 5, 6, 7]`, `k = 3`:

| step | array |
|---|---|
| reverse all | 7, 6, 5, 4, 3, 2, 1 |
| reverse the first 3 | 5, 6, 7, 4, 3, 2, 1 |
| reverse the rest | 5, 6, 7, 1, 2, 3, 4 |

### Holes to patch

**Subtracting the length once.**
`k = k - nums.length` fixes a `k` that is only a little too big.
`[1, 2]` with `k = 5` becomes `k = 3`.
The second reverse then reads index 2, and the array only has indexes 0 and 1.
The step count is the remainder.
`5 % 2` is 1, so the array becomes `[2, 1]`.

```java
k = k % nums.length;
if (k == 0) return;
```

### How it went

On time.
5 of 5.
You coded the three reverses.
The five tests never send a `k` that is at least twice the length.

### Memorize this

1. `k` becomes `k % nums.length`.
2. If that remainder is 0, stop.
3. Reverse the whole array.
4. Reverse the first `k` values.
5. Reverse everything after that.
6. A reverse swaps the two ends of that block and walks them inward until they meet.

```java
public void rotate(int[] nums, int k) {
    if (nums.length <= 1) return;
    k = k % nums.length;
    if (k == 0) return;
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, nums.length - 1);
}

private void reverse(int[] nums, int left, int right) {
    while (left < right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        left++;
        right--;
    }
}
```

Say in the interview: I reverse the whole array, then each block, and the step count is `k` modulo the length.

