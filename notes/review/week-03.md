# Week 03 — review sheet

Week 3 covered arrays and strings: two pointers from both ends, read/write pointers, sort then two pointers, prefix products, Kadane, a sliding window, and hashing by a sorted key.

Week 3 had no interview clock (the timer started in Week 5 Day 4), so on-time and overtime do not apply.
The result column says what the notes say: passed, parked, or done with no detail recorded.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 167 | Two Sum II | Two pointers, opposite ends | O(n) / O(1) | passed |
| 125 | Valid Palindrome | Two pointers, opposite ends, skip junk | O(n) / O(1) | passed after an `else` fix |
| 283 | Move Zeroes | Read/write pointers | O(n) / O(1) | — (Monday extra, no notes) |
| 238 | Product of Array Except Self | Prefix and suffix products | O(n) / O(1) extra | passed |
| 26 | Remove Duplicates from Sorted Array | Read/write pointers | O(n) / O(1) | passed |
| 11 | Container With Most Water | Two pointers, drop the shorter wall | O(n) / O(1) | parked, then passed next day |
| 15 | 3Sum | Sort, fix one, two pointers | O(n²) / O(1) extra | passed |
| 53 | Maximum Subarray | Kadane | O(n) / O(1) | — (done, no notes) |
| 88 | Merge Sorted Array | Three pointers from the tails | O(m+n) / O(1) | — (done, no notes) |
| 3 | Longest Substring Without Repeating Characters | Sliding window + HashSet | O(n) / O(min(n, alphabet)) | passed |
| 49 | Group Anagrams | HashMap keyed by sorted letters | O(n·k log k) / O(n·k) | passed |

The hard idea of the week: two pointers never send the second index back.
An index `i` plus a worker that restarts at `i+1` every time is a nested loop, O(n²), even though it also has two integer variables.

---

## LC 167 Two Sum II - Input Array Is Sorted — passed

https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

### The problem

You get a sorted array and a target.
Find the two different positions whose values add up to the target.
Return them 1-based, smaller first, using O(1) extra space.
Exactly one answer exists.

Example: `[2,7,11,15]`, target `9` gives `[1,2]`.

### The idea

Pattern: two pointers from opposite ends.

Put `left` on the smallest value and `right` on the largest.
If the sum is too small, even the largest partner cannot save `left`, so `left` is thrown away.
If the sum is too big, even the smallest partner cannot save `right`, so `right` is thrown away.
Each step throws away a value that can never be in the answer, so you never miss the pair.

Time is O(n) because each step moves one pointer inward and neither pointer ever goes back.
Extra space is O(1) because you only keep two indexes.

### How to solve it

1. `left = 0`, `right = n - 1`.
2. While `left < right`, compute `sum`.
3. If `sum == target`, return `{left + 1, right + 1}`.
4. If `sum < target`, move `left` right.
5. Otherwise move `right` left.

Trace on `[1,2,4,6,10]`, target `8` (answer `[2,4]`):

| left | right | values | sum | action |
|---|---|---|---|---|
| 0 | 4 | 1 + 10 | 11 | too big, throw `10`, `right--` |
| 0 | 3 | 1 + 6 | 7 | too small, throw `1`, `left++` |
| 1 | 3 | 2 + 6 | 8 | found, return `[2,4]` |

### Holes to patch

The notes list no gate slips for this problem.
These are the mix-ups the notes flag for it, plus the one he carried in from Week 2.

**Calling a resetting worker "two pointers"**

In Week 2 (#739) he called "stand on `i`, walk a worker from `i+1`, then restart it" two pointers.
That is a nested loop.
On `[1,2,3,...,100000]` with the pair at the very end, the worker version reads about n²/2 pairs and times out.
Rule: in real two pointers, neither index ever goes back; each step throws one side away.

**Classic trap: reaching for the #1 HashMap**

The HashMap from LC 1 Two Sum works, but it costs O(n) extra space.
This problem asks for O(1) space, and the sorted input is the hint.
Rule: unsorted pair means map; sorted pair means two ends.

**Classic trap: returning 0-based indexes**

On `[2,7,11,15]`, target `9`, returning `{left, right}` gives `[0,1]` instead of `[1,2]`.
Rule: add one to both indexes on return.

```java
return new int[]{left + 1, right + 1};
```

### Memorize this

1. `left` at start, `right` at end.
2. Sum the two.
3. Equal: return 1-based.
4. Too small: `left++`.
5. Too big: `right--`.

```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0, right = numbers.length - 1;
    while (left < right) {
        int sum = numbers[left] + numbers[right];
        if (sum == target) return new int[]{left + 1, right + 1};
        if (sum < target) left++;
        else right--;
    }
    return new int[]{-1, -1};
}
```

Say in the interview: The array is sorted, so I start at both ends and throw away the left when the sum is too small and the right when it is too big, which is one pass, O(n) time and O(1) space, not a worker that resets and not a HashMap.

### How it went

Passed on Week 3 Day 1, the first day of two LCs per weekday.
The notes list no slips on this one.
His code is the clean two-end version with 1-based return.

---

## LC 125 Valid Palindrome — passed after an `else` fix

https://leetcode.com/problems/valid-palindrome/

### The problem

Ignore case and skip every character that is not a letter or a digit.
Does what is left read the same forward and backward?

Example: `"A man, a plan, a canal: Panama"` is `true`.
`"race a car"` is `false`, `" "` is `true`, and `"0P"` is `false`.

### The idea

Pattern: two pointers from opposite ends, skipping junk in place.

`left` walks from the front and `right` walks from the back.
On each step, exactly one thing happens: skip junk on the left, or skip junk on the right, or compare two real characters.
One mismatch means false, and meeting in the middle means true.

Time is O(n) because each character is visited at most once by one pointer.
Extra space is O(1) because a Java `String` cannot be edited, and you skip junk in place instead of building a cleaned copy.

### How to solve it

1. `left = 0`, `right = s.length() - 1`.
2. While `left < right`:
3. If `s[left]` is not a letter or digit, move only `left`.
4. Else if `s[right]` is not a letter or digit, move only `right`.
5. Else if the two lowercased characters differ, return `false`.
6. Else move both inward.
7. Return `true`.

Trace on `"a.,A"` (answer `true`):

| left | right | s[left] | s[right] | branch | action |
|---|---|---|---|---|---|
| 0 | 3 | `a` | `A` | compare | same lowercased, move both |
| 1 | 2 | `.` | `,` | left is junk | only `left++` |
| 2 | 2 | — | — | — | `left == right`, stop, return `true` |

On the second row, only one pointer moves, even though the right side is junk too.

### Holes to patch

**`else` glued to the nearest `if`**

His first code had two separate `if`s, and the `else` attached only to the second one.
So on a step where the left side was junk, the code moved `left`, then still ran the `else` and moved both pointers.
That skipped a real letter without comparing it.
`"A man, a plan, a canal: Panama"` failed.
Rule: one branch per step, so the skips and the compare are one `if / else if / else if / else` chain.

```java
if (!Character.isLetterOrDigit(a)) left++;
else if (!Character.isLetterOrDigit(b)) right--;
```

**Loop condition written backwards**

`while (left > right)` never runs, because `left` starts below `right`.
On `"ab"` the loop is skipped and you return `true`, but the answer is `false`.
`while (left != right)` is also wrong, because two pointers that each move can jump past each other on even lengths and never be equal.
Rule: `while (left < right)`.

**Classic trap: `isWhitespace` or `isLetter` instead of `isLetterOrDigit`**

`:` and `,` are not whitespace, so `isWhitespace` compares them as real characters.
Digits are real, so `isLetter` wrongly skips them.
On `"0P"`, skipping the `0` returns `true`, but the answer is `false`.
Rule: keep letters and digits, skip everything else.

**Classic trap: lowercasing the whole string**

`s.toLowerCase()` builds a second string, which is O(n) extra.
Rule: lowercase only the two characters you compare.

### Memorize this

1. `left` at start, `right` at end, `while (left < right)`.
2. Left is junk: only `left++`.
3. Else right is junk: only `right--`.
4. Else lowercase compare, mismatch returns `false`.
5. Else move both.
6. Loop ends: `true`.

```java
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        char a = s.charAt(left), b = s.charAt(right);
        if (!Character.isLetterOrDigit(a)) left++;
        else if (!Character.isLetterOrDigit(b)) right--;
        else if (Character.toLowerCase(a) != Character.toLowerCase(b)) return false;
        else { left++; right--; }
    }
    return true;
}
```

Say in the interview: Left and right move inward, I skip only the side that is not a letter or digit, and if both are real and differ ignoring case it is not a palindrome, which is one pass with O(1) extra space because I lowercase two characters, not the whole string.

### How it went

Passed on Week 3 Day 1 after fixing the `else` chain and the loop condition.
The dangling `else` was what failed the Panama test.
His final code is the four-branch chain with `isLetterOrDigit` and `toLowerCase` on the two characters.

---

## LC 283 Move Zeroes — — (Monday extra, no notes)

https://leetcode.com/problems/move-zeroes/

### The problem

Move every `0` to the end of the array, in place.
The non-zero values keep their original order.

Example: `[0,1,0,3,12]` becomes `[1,3,12,0,0]`.

### The idea

Pattern: read/write pointers.

`write` is the next slot for a non-zero value.
`read` scans every element.
Each non-zero value is copied to `write`, and `write` moves on.
Because `write` never passes `read`, you only overwrite slots you have already read.
After the scan, every slot from `write` to the end gets `0`.

Time is O(n) because you scan once and fill the tail once.
Extra space is O(1) because you work inside the array.

### How to solve it

1. `write = 0`.
2. For each `read`, if `nums[read] != 0`, copy it to `nums[write]` and step `write`.
3. Fill `nums[write .. n-1]` with `0`.

Trace on `[0,1,0,3,12]`:

| read | nums[read] | action | write | array |
|---|---|---|---|---|
| 0 | 0 | skip | 0 | `[0,1,0,3,12]` |
| 1 | 1 | copy to 0 | 1 | `[1,1,0,3,12]` |
| 2 | 0 | skip | 1 | `[1,1,0,3,12]` |
| 3 | 3 | copy to 1 | 2 | `[1,3,0,3,12]` |
| 4 | 12 | copy to 2 | 3 | `[1,3,12,3,12]` |
| fill | — | zero slots 3 and 4 | 5 | `[1,3,12,0,0]` |

### Holes to patch

The notes record nothing about this problem beyond it being Monday's extra, so these are the classic traps.

**Classic trap: shifting on every zero**

Removing a zero by shifting everything after it one slot left is O(n) per zero.
On an array of all zeros, that becomes O(n²).
Rule: never shift; copy non-zeros forward once, then fill the tail.

**Classic trap: forgetting the tail fill**

Without the fill loop, `[0,1]` becomes `[1,1]` instead of `[1,0]`.
Rule: the copy pass only moves non-zeros, so the tail must be zeroed explicitly.

```java
while (write < nums.length) nums[write++] = 0;
```

### Memorize this

1. `write = 0`.
2. Scan; non-zero goes to `nums[write++]`.
3. Fill the rest with `0`.

```java
public void moveZeroes(int[] nums) {
    int write = 0;
    for (int x : nums) {
        if (x != 0) nums[write++] = x;
    }
    while (write < nums.length) nums[write++] = 0;
}
```

Say in the interview: A write pointer collects the non-zero values in order at the front, and then I zero the tail, which is one O(n) pass with O(1) extra space.

### How it went

Done as an extra on Week 3 Day 1; the notes record no detail.
His code is the copy-then-fill version.
The next day's #26 uses the same write/read shape.

---

## LC 238 Product of Array Except Self — passed

https://leetcode.com/problems/product-of-array-except-self/

### The problem

For each index, return the product of every other value.
Do it in O(n) without division.

Example: `[1,2,3,4]` gives `[24,12,8,6]`.

### The idea

Pattern: prefix and suffix products (a running product).

The answer at `i` is (product of everything left of `i`) times (product of everything right of `i`).
First pass: walk forward and store in `out[i]` the product of the people already passed.
Second pass: walk back with a running `right` product and multiply it into `out[i]`.

Time is O(n) because there are two linear passes.
Extra space is O(1) besides the output array, which the problem does not count, because the suffix is one running variable.

### How to solve it

1. `out[0] = 1`.
2. For `i` from `1`, `out[i] = out[i-1] * nums[i-1]` (left product).
3. `right = 1`.
4. For `i` from `n-1` down to `0`, multiply `out[i]` by `right`, then fold `nums[i]` into `right`.

Trace on `[2,3,0,4]` (answer `[0,0,24,0]`):

Forward pass:

| i | out[i] = out[i-1] * nums[i-1] | out |
|---|---|---|
| 0 | 1 | `[1,_,_,_]` |
| 1 | 1 * 2 = 2 | `[1,2,_,_]` |
| 2 | 2 * 3 = 6 | `[1,2,6,_]` |
| 3 | 6 * 0 = 0 | `[1,2,6,0]` |

Backward pass:

| i | right before | out[i] * right | right after | out |
|---|---|---|---|---|
| 3 | 1 | 0 * 1 = 0 | 4 | `[1,2,6,0]` |
| 2 | 4 | 6 * 4 = 24 | 0 | `[1,2,24,0]` |
| 1 | 0 | 2 * 0 = 0 | 0 | `[1,0,24,0]` |
| 0 | 0 | 1 * 0 = 0 | 0 | `[0,0,24,0]` |

### Holes to patch

**Calling it two pointers**

Two passes with an index each look like two pointers, and that is the mix-up the notes flag here.
Two pointers throws one side away on each step.
Here nothing can be thrown away: on `[1,2,3,4]`, `out[0] = 24` needs every value to its right, `2 * 3 * 4`.
Rule: a running product carried forward, then another carried back, is prefix, not two pointers; the two-ends picture is #11.

**Classic trap: total product divided by `nums[i]`**

Division is not allowed, and it also breaks on zeros.
On `[2,3,0,4]`, the total is `0`, and `0 / 0` at index 2 throws `ArithmeticException` instead of giving `24`.
Rule: left product times right product, no division.

**Classic trap: folding `nums[i]` into `right` before using it**

If you update `right` first, `out[i]` includes `nums[i]` itself.
On `[1,2,3,4]`, index 3 becomes `6 * 4 = 24` instead of `6`.
Rule: multiply `out[i]` by `right`, then update `right`.

```java
out[i] *= right;
right *= nums[i];
```

### Memorize this

1. `out[0] = 1`.
2. Forward: `out[i] = out[i-1] * nums[i-1]`.
3. `right = 1`.
4. Backward: `out[i] *= right`, then `right *= nums[i]`.

```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] out = new int[n];
    out[0] = 1;
    for (int i = 1; i < n; i++) out[i] = out[i - 1] * nums[i - 1];
    int right = 1;
    for (int i = n - 1; i >= 0; i--) {
        out[i] *= right;
        right *= nums[i];
    }
    return out;
}
```

Say in the interview: Each answer is the product of everything to its left times everything to its right, so I store left products in the output and multiply in a running right product on the way back, which is O(n) time and O(1) extra space with no division.

### How it went

Passed on Week 3 Day 2.
The only slip the notes flag is the pattern name (two pointers instead of prefix).
His code is the two-pass version with one `right` variable, multiplying before updating.

---

## LC 26 Remove Duplicates from Sorted Array — passed

https://leetcode.com/problems/remove-duplicates-from-sorted-array/

### The problem

The array is sorted.
Move the unique values to the front, in order, in place.
Return how many unique values there are.
Whatever is after that count does not matter.

Example: `[1,1,2]` returns `2`, and the front is `[1,2]`.

### The idea

Pattern: read/write pointers (two pointers, not ends).

Because the array is sorted, a duplicate always sits next to the last unique you kept.
`write` points at the last unique value you kept.
`read` scans forward, and each time it sees a value different from `nums[write]`, that value is new.
You step `write` and copy it there.
Neither index ever goes back.

Time is O(n) because `read` visits each element once.
Extra space is O(1) because you overwrite the same array.

### How to solve it

1. If the array is empty, return `0`.
2. `write = 0` (the first value is always kept).
3. For `read` from `1` to the end, if `nums[read] != nums[write]`, step `write` and copy.
4. Return `write + 1`.

Trace on `[0,0,1,1,1,2]` (answer `3`, front `[0,1,2]`):

| read | nums[read] | nums[write] | action | write | front so far |
|---|---|---|---|---|---|
| 1 | 0 | 0 | same, skip | 0 | `[0]` |
| 2 | 1 | 0 | new, `write++`, copy | 1 | `[0,1]` |
| 3 | 1 | 1 | same, skip | 1 | `[0,1]` |
| 4 | 1 | 1 | same, skip | 1 | `[0,1]` |
| 5 | 2 | 1 | new, `write++`, copy | 2 | `[0,1,2]` |

Return `write + 1 = 3`.

### Holes to patch

**A worker that resets instead of a write pointer**

The mix-up the notes flag here is the week's big one: for each `i`, send a worker forward to find the next different value, then restart.
On `[1,1,1,...,1,2]` that re-reads the long run for every `i` and is O(n²).
Rule: the write pointer only moves forward, and the read pointer only moves forward.

**Reaching for a HashSet**

A HashSet is the tool when the array is unsorted (#217).
Here the input is sorted, so equal values are neighbors, and a set only adds O(n) space without giving you the in-place front.
Rule: sorted plus "remove duplicates in place" means compare with the last kept value.

**Classic trap: returning `write` instead of `write + 1`**

`write` is the index of the last kept value, not the count.
On `[1,1,2]` you would return `1` instead of `2`.
Rule: count is last index plus one.

```java
return write + 1;
```

### Memorize this

1. Empty: return 0.
2. `write = 0`.
3. `read` from 1.
4. New value: `++write`, copy.
5. Return `write + 1`.

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    int write = 0;
    for (int read = 1; read < nums.length; read++) {
        if (nums[read] != nums[write]) nums[++write] = nums[read];
    }
    return write + 1;
}
```

Say in the interview: Since the array is sorted, duplicates are neighbors, so a write pointer on the last kept value and a read pointer scanning ahead compact the uniques in O(n) time and O(1) space.

### How it went

Passed on Week 3 Day 2, same shape as Monday's #283.
The notes flag no code bug, only the worker-vs-pointer and HashSet mix-ups.
Cousin: #80 keeps up to two copies by comparing with `nums[write - 1]`.

---

## LC 11 Container With Most Water — parked, then passed next day

https://leetcode.com/problems/container-with-most-water/

### The problem

`height[i]` is a wall at position `i`.
Pick two walls.
The water they hold is the shorter wall times the distance between them.
Return the most water.

Example: `[1,8,6,2,5,4,8,3,7]` gives `49` (walls `8` at index 1 and `7` at index 8, `min(8,7) * 7`).

### The idea

Pattern: two pointers from opposite ends, always dropping the shorter wall.

Start with the widest container.
The shorter wall caps the height.
Width only shrinks from here, so the only hope of more water is a taller short side.
If you drop the taller wall, the same short wall still caps you and the width is smaller, so the area can only go down.
So the shorter wall is done, and you drop it.

Time is O(n) because one pointer moves inward on every step.
Extra space is O(1) because you keep two indexes and the best area.

### How to solve it

1. `left = 0`, `right = n - 1`, `best = 0`.
2. While `left < right`, area is `min(height[left], height[right]) * (right - left)`.
3. Update `best`.
4. Then, as a separate step, move the pointer on the shorter wall inward.
5. On a tie, move either one.

Trace on `[1,2,4,3]` (answer `4`):

| left | right | heights | area | best | move |
|---|---|---|---|---|---|
| 0 | 3 | 1, 3 | 1 * 3 = 3 | 3 | left is shorter, `left++` |
| 1 | 3 | 2, 3 | 2 * 2 = 4 | 4 | left is shorter, `left++` |
| 2 | 3 | 4, 3 | 3 * 1 = 3 | 4 | right is shorter, `right--` |
| 2 | 2 | — | — | 4 | stop |

Every row moves a pointer, including the row that set a new best.

### Holes to patch

**Seeing a prefix problem instead of two walls**

On the first try he reached for a running value like the day's #238, and the problem was parked.
A running product or running min walks one way and never compares two chosen positions.
On `[1,8,6,2,5,4,8,3,7]`, no single running value gives `49`; you need the pair at index 1 and index 8 and their distance.
Rule: two walls plus width means two ends; prefix (#238) and running min (#121) do not pick two walls.

**Move glued to the `else if` of the best update**

His code moved a pointer only when the area was not a new best.
On `[1,8,6,2,5,4,8,3,7]`, the first lap sets `best = 8` and does not move, so the next lap recomputes the same area before it moves.
Tests passed because the next lap moved, but with `>=` instead of `>` it would loop forever.
Rule: compute the area, update `best`, and then always drop the shorter wall, as two separate steps.

```java
best = Math.max(best, area);
if (height[left] < height[right]) left++; else right--;
```

**Naming the indexes `min` and `max`**

`left` and `right` are positions, not the smallest and largest heights.
Calling them `min` and `max` makes you think in values and hides the width.
Rule: say `left` and `right`.

### Memorize this

1. Pointers at both ends.
2. Area = shorter wall * width.
3. Keep the max.
4. Always move the shorter wall inward.
5. Stop when they meet.

```java
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1, best = 0;
    while (left < right) {
        int area = Math.min(height[left], height[right]) * (right - left);
        best = Math.max(best, area);
        if (height[left] < height[right]) left++;
        else right--;
    }
    return best;
}
```

Say in the interview: I start at both ends, the area is the short wall times the width, and since width only shrinks I always drop the shorter wall and keep the taller one, which is one O(n) pass.

### How it went

Started on Week 3 Day 2 and parked after he read it as a prefix problem.
Came back on Day 3 and passed.
The `else if` on the move was a real slip that tests did not catch.

---

## LC 15 3Sum — passed

https://leetcode.com/problems/3sum/

### The problem

Find every triplet at three different positions whose values sum to `0`.
The same set of values may appear only once in the answer.

Example: `[-1,0,1,2,-1,-4]` gives `[[-1,-1,2],[-1,0,1]]`.
`[0,0,0]` gives `[[0,0,0]]`.

### The idea

Pattern: sort, fix the first number, then two pointers on the rest (Two Sum II inside a loop).

After sorting, fix `nums[i]` and look for a pair in `i+1 .. n-1` that sums to `-nums[i]`.
That inner search is exactly LC 167, with the same throw-one-side rule.
Sorting also puts equal values next to each other, so you avoid duplicate triplets by skipping equal neighbors instead of using a set.

Time is O(n²) because each of the n anchors runs one O(n) two-pointer pass, and the O(n log n) sort is smaller.
Extra space is O(1) beyond the output list, apart from the sort's O(log n) stack.

### How to solve it

1. Sort `nums`.
2. For each `i`, skip it if `nums[i] == nums[i-1]` (that anchor was already done).
3. `left = i + 1`, `right = n - 1`.
4. If the sum is below `0`, `left++`.
5. If it is above `0`, `right--`.
6. If it is `0`, record it, move both, then skip repeated values at `left` and at `right`.

Trace on `[-4,0,1,2,3]` (answer `[[-4,1,3]]`, and the pair is not neighbors):

| i | nums[i] | left | right | sum | action | output so far |
|---|---|---|---|---|---|---|
| 0 | -4 | 1 | 4 | -4+0+3 = -1 | too small, `left++` | `[]` |
| 0 | -4 | 2 | 4 | -4+1+3 = 0 | record, move both | `[[-4,1,3]]` |
| 0 | -4 | 3 | 3 | — | `left == right`, stop | `[[-4,1,3]]` |
| 1 | 0 | 2 | 4 | 0+1+3 = 4 | too big, `right--` | `[[-4,1,3]]` |
| 1 | 0 | 2 | 3 | 0+1+2 = 3 | too big, `right--`, then stop | `[[-4,1,3]]` |
| 2 | 1 | — | — | — | anchor is positive, no triplet can sum to 0, stop | `[[-4,1,3]]` |

### Holes to patch

**First guess: index plus worker**

He first named "for each `i`, walk every `j`, then every `k`".
That is the brute cousin, O(n³), and the worker restarts on every `i`.
On n = 3000 it is billions of steps.
Rule: sort, fix `i`, and two-pointer the rest, O(n²).

**Neighbor scan after sorting**

His next version only tried `j` and `j+1` after the sort.
Five tests passed by luck.
On `[-4,0,1,2,3]`, the answer `[-4,1,3]` uses values that are not neighbors, so it returned `[]`.
Rule: the pair is found by two ends that throw one side away, not by looking at adjacent values.

**Hang from `left = 0` and a `left != i` guard**

He started `left` at `0` and wrapped every branch in `left != i`.
When `i` is `0`, `left == i`, so no branch runs and nothing moves.
On `[-1,0,1]` the loop never ends.
Rule: `left` starts at `i + 1`, and the loop is just `while (left < right)`.

```java
int left = i + 1, right = nums.length - 1;
```

**No duplicate skipping**

His first passing code had no skip, and the tests put results in a `Set`, so repeated triplets were hidden.
On `[0,0,0,0]` it records `[0,0,0]` twice.
Rule: skip an anchor equal to the previous anchor, and after a hit skip equal values at `left` and `right`; do not call `contains` on the result list.

```java
if (i > 0 && nums[i] == nums[i - 1]) continue;
```

### Memorize this

1. Sort.
2. For each `i`, skip if equal to the previous anchor.
3. Two pointers on `i+1 .. n-1`.
4. Sum below zero: `left++`, above: `right--`.
5. Zero: record, move both, skip equal values.

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> out = new ArrayList<>();
    for (int i = 0; i < nums.length - 2 && nums[i] <= 0; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue;
        int left = i + 1, right = nums.length - 1;
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum < 0) left++;
            else if (sum > 0) right--;
            else {
                out.add(List.of(nums[i], nums[left], nums[right]));
                left++;
                right--;
                while (left < right && nums[left] == nums[left - 1]) left++;
                while (left < right && nums[right] == nums[right + 1]) right--;
            }
        }
    }
    return out;
}
```

Say in the interview: I sort, fix each distinct first value, and run a Two Sum II pass on the rest for minus that value, throwing the left away when too small and the right when too big and skipping equal neighbors, which is O(n²) time and O(1) extra space.

### How it went

Passed on Week 3 Day 3, but it took the most rewrites of the week.
The neighbor scan and the `left = 0` hang cost the most time.
His final code starts `left` at `i + 1` and skips duplicates on the anchor and on both sides after a hit.

---

## LC 53 Maximum Subarray — — (done, no notes)

https://leetcode.com/problems/maximum-subarray/

### The problem

Find the contiguous stretch with the largest sum and return that sum.
The stretch must have at least one element.

Example: `[-2,1,-3,4,-1,2,1,-5,4]` gives `6` (`[4,-1,2,1]`).

### The idea

Pattern: Kadane (current stretch vs best photo).

`current` is the best sum of a stretch that ends exactly at `i`.
At each value you have two choices: extend the stretch that ended at `i-1`, or start fresh at `i`.
If the old stretch has a negative sum, it only drags you down, so you start fresh.
`best` is a photo of the largest `current` you have seen.

Time is O(n) because you make one pass.
Extra space is O(1) because you keep two numbers.

### How to solve it

1. `current = best = nums[0]`.
2. For `i` from `1`, `current = max(nums[i], current + nums[i])`.
3. `best = max(best, current)`.
4. Return `best`.

Trace on `[-3,-1,-2]` (answer `-1`):

| i | nums[i] | extend | start fresh | current | best |
|---|---|---|---|---|---|
| 0 | -3 | — | — | -3 | -3 |
| 1 | -1 | -3 + -1 = -4 | -1 | -1 | -1 |
| 2 | -2 | -1 + -2 = -3 | -2 | -2 | -1 |

### Holes to patch

The notes only record that this was done on Thursday, so these are the classic traps.

**Classic trap: starting `best` or `current` at `0`**

Starting at `0` pretends an empty stretch is allowed.
On `[-3,-1,-2]` you return `0`, but the answer is `-1`.
Rule: seed both with `nums[0]` and loop from index 1.

```java
int current = nums[0], best = nums[0];
```

**Classic trap: resetting to `0` before updating `best`**

The "if `current < 0` then `current = 0`" version returns `0` on all-negative input when the reset happens before `best` is updated.
Rule: choose between extending and starting fresh at `nums[i]`, then update `best`.

### Memorize this

1. Seed `current` and `best` with `nums[0]`.
2. From index 1: `current = max(x, current + x)`.
3. `best = max(best, current)`.
4. Return `best`.

```java
public int maxSubArray(int[] nums) {
    int current = nums[0], best = nums[0];
    for (int i = 1; i < nums.length; i++) {
        current = Math.max(nums[i], current + nums[i]);
        best = Math.max(best, current);
    }
    return best;
}
```

Say in the interview: I track the best sum of a stretch ending at each index, extending it only when that helps and otherwise starting fresh, and keep the overall max, which is O(n) time and O(1) space and handles all-negative arrays because I seed with the first element.

### How it went

Done on Week 3 Day 4; the notes record no detail.
His code seeds both values with `nums[0]`, so the all-negative case is safe.
He wrote the choice as `if (current + nums[i] > nums[i])`, which is the same as `current > 0`.

---

## LC 88 Merge Sorted Array — — (done, no notes)

https://leetcode.com/problems/merge-sorted-array/

### The problem

`nums1` holds `m` sorted values followed by `n` empty slots.
`nums2` holds `n` sorted values.
Merge `nums2` into `nums1` so all `m + n` values are sorted, in place.

Example: `nums1 = [1,2,3,0,0,0]`, `m = 3`, `nums2 = [2,5,6]`, `n = 3` gives `[1,2,2,3,5,6]`.

### The idea

Pattern: three pointers filling from the tails.

The empty slots are at the end of `nums1`.
If you fill from the front, you overwrite values of `nums1` you have not placed yet.
Filling from the back is safe: the slot `k` you write is always at or after the last unread value of `nums1`.
At each step, the bigger of the two tails goes into `nums1[k]`.

Time is O(m + n) because each value is placed once.
Extra space is O(1) because you write into `nums1` directly.

### How to solve it

1. `i = m - 1` (tail of `nums1` data), `j = n - 1` (tail of `nums2`), `k = m + n - 1` (write slot).
2. While `j >= 0`:
3. If `i >= 0` and `nums1[i] > nums2[j]`, put `nums1[i]` at `k` and step `i`.
4. Otherwise put `nums2[j]` at `k` and step `j`.
5. Step `k`.
6. When `j` runs out, the rest of `nums1` is already in place.

Trace on `nums1 = [4,5,0,0]`, `m = 2`, `nums2 = [1,2]`, `n = 2` (answer `[1,2,4,5]`):

| i | j | k | compare | write | nums1 |
|---|---|---|---|---|---|
| 1 | 1 | 3 | 5 > 2 | `nums1[3] = 5` | `[4,5,0,5]` |
| 0 | 1 | 2 | 4 > 2 | `nums1[2] = 4` | `[4,5,4,5]` |
| -1 | 1 | 1 | `nums1` empty | `nums1[1] = 2` | `[4,2,4,5]` |
| -1 | 0 | 0 | `nums1` empty | `nums1[0] = 1` | `[1,2,4,5]` |

This input shows the leftover case: `nums1` runs out first, and `nums2` still has values to copy.

### Holes to patch

The notes only record that this was done on Thursday, so these are the classic traps.

**Classic trap: stopping when either side runs out**

A loop of `while (i >= 0 && j >= 0)` alone leaves values of `nums2` unplaced.
On the trace input it stops with `[4,5,4,5]` instead of `[1,2,4,5]`.
Rule: loop until `nums2` is empty; leftovers of `nums1` are already in place.

```java
while (j >= 0) nums1[k--] = nums2[j--];
```

**Classic trap: merging from the front**

Writing the smaller value at the front overwrites `nums1` values you still need.
On `[4,5,0,0]` with `[1,2]`, writing `1` at index 0 destroys the `4`.
Rule: fill from the back, where the slots are empty.

### Memorize this

1. `i` at end of `nums1` data, `j` at end of `nums2`, `k` at the very end.
2. While `j >= 0`, bigger tail goes to `k`.
3. Guard `i >= 0` before reading `nums1[i]`.
4. Leftover `nums1` stays where it is.

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1, j = n - 1, k = m + n - 1;
    while (j >= 0) {
        if (i >= 0 && nums1[i] > nums2[j]) nums1[k--] = nums1[i--];
        else nums1[k--] = nums2[j--];
    }
}
```

Say in the interview: I fill `nums1` from the back with the larger of the two tails, so I never overwrite unread data, and I stop when `nums2` is empty because the rest of `nums1` is already in place, which is O(m + n) time and O(1) space.

### How it went

Done on Week 3 Day 4; the notes record no detail.
His code has a main loop while both sides have values, then a leftover loop for `nums2`.
That is correct and equivalent to the single loop above.

---

## LC 3 Longest Substring Without Repeating Characters — passed

https://leetcode.com/problems/longest-substring-without-repeating-characters/

### The problem

Find the longest contiguous piece of the string with no repeated character.
Return its length.

Example: `"abcabcbb"` gives `3` (`"abc"`).
`"pwwkew"` gives `3` (`"wke"`), and `"pwke"` does not count because it is a subsequence, not a substring.
`"dvdf"` gives `3` (`"vdf"`).

### The idea

Pattern: sliding window, with a HashSet holding the characters currently inside the window.

The window `left .. right` always has no repeats.
`right` eats the next character.
If that character is already in the window, `left` walks forward, removing characters, until the old copy is gone.
Then the new character goes in.
The set only describes what sits between `left` and `right`; `left` itself is an index on the string, not something stored in the set.

Time is O(n) because each index enters the window once and leaves at most once.
Extra space is O(min(n, alphabet)) because the set holds at most one copy of each distinct character.

### How to solve it

1. `left = 0`, `best = 0`, empty set.
2. For each `right`, let `c = s[right]`.
3. While `c` is in the set, remove `s[left]` and step `left`.
4. Add `c`.
5. `best = max(best, right - left + 1)`.

Trace on `"dvdf"` (answer `3`):

| right | c | peel from left | left | window | best |
|---|---|---|---|---|---|
| 0 | d | — | 0 | `d` | 1 |
| 1 | v | — | 0 | `dv` | 2 |
| 2 | d | remove `d`, `left++` | 1 | `vd` | 2 |
| 3 | f | — | 1 | `vdf` | 3 |

At `right = 2`, only the first `d` leaves; the `v` stays, and that is what makes `"vdf"` possible.

### Holes to patch

**Wiping the set on a repeat (prefix / Kadane thinking)**

His first pattern guess was "reset the set when you see a duplicate" with `maxLen = set.size()`.
That throws away letters that still belong with what comes next.
On `"dvdf"`, the reset at the second `d` drops the `v`, so the windows are `"dv"` then `"df"` and you return `2`.
The answer is `3` (`"vdf"`).
Rule: peel from the left only until the repeat is gone; never wipe the window.

**Removing only the duplicate and keeping letters to its left**

He suggested "just remove the old `v` and keep `d`".
That leaves a hole in the middle, so the "window" is no longer contiguous.
On `"pwwkew"`, removing only the first `w` keeps `p` and grows to `{p,w,k,e}`, size `4`.
The answer is `3`, because `"pwke"` is a subsequence.
Rule: to kick an old character out, `left` must pass it, so everything before it leaves too.

**Naming HashSet as the pattern**

The set is the box; the pattern is the sliding window.
A HashSet as the pattern means "have I ever seen this anywhere" (#217), which is a different question.
Rule: say "sliding window with a set of the current window".

**"The set has no indexes"**

He got stuck because the set does not store positions.
It does not need to: `left` is an `int` on the string, and you remove `s.charAt(left)`.
Rule: the set holds characters, `left` and `right` hold positions.

### Memorize this

1. Outer `for right` grows the window.
2. Inner `while` peels `left` while `s[right]` is already inside.
3. Add `s[right]`.
4. `best = max(best, right - left + 1)`.
5. Never reset the set.

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, best = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        while (window.contains(c)) {
            window.remove(s.charAt(left));
            left++;
        }
        window.add(c);
        best = Math.max(best, right - left + 1);
    }
    return best;
}
```

Say in the interview: I grow `right`, and when the new character is already in the window I slide `left` until it is not, keeping a set of only the current substring, so each index enters and leaves once and it is O(n).

### How it went

Passed on Week 3 Day 5.
The hard part was the gate: reset-the-set thinking and "keep the `d`, drop the `v`" both had to go before the window made sense.
His code uses one `while` loop with an `if / else` (peel on repeat, else add and move `right`), which is the same as the inner `while` above.
Cousin: a map from character to last index lets `left` jump instead of peel, but then it must never move backward (`"abba"`).

---

## LC 49 Group Anagrams — passed

https://leetcode.com/problems/group-anagrams/

### The problem

Group words that use exactly the same letters.
The order of the groups and the order inside a group do not matter.

Example: `["eat","tea","tan","ate","nat","bat"]` gives `[["eat","tea","ate"],["tan","nat"],["bat"]]`.

### The idea

Pattern: HashMap from a letter-pattern key to the list of original words.

Two words are anagrams exactly when their sorted letters are equal.
So the sorted letters are a key that every word in a group shares.
Walk the words once, compute each key, and append the original word to that key's list.

Time is O(n · k log k), where n is the number of words and k the longest word, because each word is sorted once.
Extra space is O(n · k) because the map stores every word and every key.

### How to solve it

1. Empty map from `String` to `List<String>`.
2. For each word, sort its characters and turn them back into a `String` key.
3. Append the word to the list for that key, creating the list if needed.
4. Return the map's values as a list.

Trace on `["eat","tea","tan","ate"]`:

| word | key | map after |
|---|---|---|
| eat | aet | `aet: [eat]` |
| tea | aet | `aet: [eat, tea]` |
| tan | ant | `aet: [eat, tea]`, `ant: [tan]` |
| ate | aet | `aet: [eat, tea, ate]`, `ant: [tan]` |

### Holes to patch

**Returning `null` for empty input**

His first version returned `null` when `strs` was empty.
The tests did not hit it, but a caller that loops over the result gets a `NullPointerException`.
Rule: an empty input returns an empty list.

```java
if (strs == null || strs.length == 0) return List.of();
```

**Classic trap: using the `char[]` itself as the key**

Java arrays use identity for `equals` and `hashCode`.
The sorted arrays from `"eat"` and `"tea"` are different keys, so every word lands in its own group.
`arr.toString()` has the same problem, because it prints something like `[C@1b6d3586`.
Rule: turn the sorted array into a real `String`.

```java
String key = String.valueOf(arr);
```

**Classic trap: sorting the list of words instead of each word**

Sorting `strs` puts `ate`, `bat`, `eat`, `nat`, `tan`, `tea` in order, and the anagrams are no longer next to each other.
Rule: sort the letters inside each word to build its key.

### Memorize this

1. Map of key to list.
2. Key = sorted letters as a `String`.
3. `computeIfAbsent(key, k -> new ArrayList<>()).add(word)`.
4. Return `new ArrayList<>(map.values())`.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();
    for (String s : strs) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        groups.computeIfAbsent(new String(c), k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(groups.values());
}
```

Say in the interview: I sort each word into a signature key and append the original word into that key's bucket in a HashMap, which is O(n · k log k) time and O(n · k) space.

### How it went

Passed on Week 3 Day 5, the last LC of the week.
The only code slip was `null` for empty input, which is now `List.of()`.
His code uses `String.valueOf(arr)` as the key and `containsKey` then `put` in place of `computeIfAbsent`.
