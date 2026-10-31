# Week 11 — review sheet

Week 11 is one problem you already did, then one new problem.
The family stays hidden until you name it.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 217 | Contains Duplicate | HashSet | O(n) / O(n) | on-time |
| 189 | Rotate Array | Reverse, three times | O(n) / O(1) | on-time. One subtraction is not the remainder. |
| 121 | Best Time to Buy and Sell Stock | Running min | O(n) / O(1) | on-time. Name was given before the clock. |
| 80 | Remove Duplicates from Sorted Array II | Two pointers | O(n) / O(1) | on-time, coach-fixed. The count never wrote into the array. |

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

---

## LC 121 Best Time to Buy and Sell Stock — on-time

https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

### The problem

You get one price per day.
Buy on one day and sell on a later day.
Return the biggest profit.
If prices only fall, return 0.
`[7, 1, 5, 3, 6, 4]` returns 5.
Buy at 1, sell at 6.

### The idea

Pattern: running min.
You keep the cheapest price already seen.
Today's profit is today's price minus that cheapest price.
You also keep the best profit.
Each day is visited once, so time is O(n).
You store two numbers, so extra space is O(1).
The sell day has to be after the buy day.

### How to solve it

1. A list of 0 or 1 price returns 0.
2. The cheapest starts higher than any price.
3. Walk each price once.
4. A cheaper price becomes the new cheapest.
5. If today's price minus that cheapest beats the best profit, keep it.
6. Return the best profit.

`[7, 1, 5, 3, 6, 4]`:

| price | cheapest | best |
|---|---|---|
| 7 | 7 | 0 |
| 1 | 1 | 0 |
| 5 | 1 | 4 |
| 3 | 1 | 4 |
| 6 | 1 | 5 |
| 4 | 1 | 5 |

### Holes to patch

**Keeping the highest price.**
`[7, 1, 5, 3, 6, 4]` has 7 on day 0.
That 7 never gets beaten, so a max-price tracker stays at 7.
The profit is still 5, from buying at 1 and selling at 6.
The 7 is already behind you.
Keep the cheapest price so far.
Today's profit is today's price minus that cheapest price.

### How it went

On time.
5 of 5.
You coded it.
The name was given before the clock.

### Memorize this

1. A list of 0 or 1 price returns 0.
2. The cheapest starts higher than any price.
3. Walk each price once.
4. A cheaper price becomes the new cheapest.
5. If today's price minus that cheapest beats the best profit, keep it.
6. Return the best profit.

```java
public int maxProfit(int[] prices) {
    if (prices.length <= 1) return 0;
    int min = Integer.MAX_VALUE;
    int maxProfit = 0;
    for (int x : prices) {
        if (min > x) min = x;
        if (x > min && x - min > maxProfit) maxProfit = x - min;
    }
    return maxProfit;
}
```

Say in the interview: I keep the cheapest price so far, and today's profit is today's price minus that price.

---

## LC 80 Remove Duplicates from Sorted Array II — on-time, coach-fixed

https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/

### The problem

You get a sorted array.
Each value may stay at most twice.
Change that same array.
Return how many values you kept.
The tail after that count does not matter.
`[1, 1, 1, 2, 2, 3]` keeps 5 values: `1, 1, 2, 2, 3`.

### The idea

Pattern: two pointers.
You keep a write index for the next allowed spot, and you scan with the value itself.
A value is written when fewer than two copies of it are already kept.
Equal values sit together because the array is sorted.
Each index is visited once, so time is O(n).
You write into the same array, so extra space is O(1).

### How to solve it

1. An empty array returns 0.
2. The current value starts as the first value.
3. The copy count starts at 0. The write index starts at 0.
4. Walk each value once.
5. Same value and fewer than 2 copies already kept: write it, count it, move the write index.
6. A new value is the first copy: write it, set the copy count to 1, move the write index.
7. A third copy is skipped.
8. Return the write index.

`[1, 1, 1, 2, 2, 3]`:

| value | copies kept | write |
|---|---|---|
| 1 | 1 | 1 |
| 1 | 2 | 2 |
| 1 | skip | 2 |
| 2 | 1 | 3 |
| 2 | 2 | 4 |
| 3 | 1 | 5 |

### Holes to patch

**Counting without writing.**
The returned number can be right while the array is still the original.
The tests read the first `k` slots.
When you keep a value, store it at `nums[retCounter]` before you move that index.

**`counter <= 2` on the third copy.**
`[1, 1, 1]` arrives at the third `1` with `counter` already 2.
`2 <= 2` is true, so the third copy is counted.
The check is `counter < 2`.
The third copy falls through to `continue`.

**A new value counted only when the previous run had length 1.**
`[1, 1, 2]` kept the two `1`s and then skipped the `2`.
The first copy of a new value is always kept.
`current` starts at `nums[0]`, so a real `0` in the array is not a fake starting value.

### How it went

On time.
5 of 5.
The count never wrote into the array, and the third copy still passed `counter <= 2`.
The fix stayed on your variables and your `if / else`.

### Memorize this

1. An empty array returns 0.
2. The current value starts as `nums[0]`. The copy count and the write index start at 0.
3. Same value and fewer than 2 copies: write it and count it.
4. A new value is the first copy: write it and set the copy count to 1.
5. A third copy is skipped.
6. Return the write index.

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    int current = nums[0];
    int counter = 0;
    int retCounter = 0;
    for (int x : nums) {
        if (counter < 2 && x == current) {
            nums[retCounter] = x;
            counter++;
            retCounter++;
        } else if (current != x) {
            current = x;
            nums[retCounter] = x;
            counter = 1;
            retCounter++;
        } else continue;
    }
    return retCounter;
}
```

Say in the interview: I write into the same array, and I skip a value once two copies of it are already kept.

