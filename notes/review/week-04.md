# Week 04 — review sheet

Week 4 covered hashing and windows: a HashSet for value runs and board checks, HashMaps for last-index, one-to-one glue and letter counts, a sliding window with a budget, a list-plus-map design class, and marking rows and columns in a matrix.

The interview clock only started in Week 5 Day 4, so no Week 4 problem is marked on-time or overtime.
"Passed" below means his tests went green in the session with no clock.
Skipped that week and not in the folder: #76 Minimum Window Substring (Hard) and #202 Happy Number.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 128 | Longest Consecutive Sequence | HashSet, start only at a run's first value | O(n) / O(n) | passed (no clock) |
| 424 | Longest Repeating Character Replacement | Sliding window + 26 counts | O(n) / O(1) | passed (no clock), off-list |
| 209 | Minimum Size Subarray Sum | Sliding window, peel while sum ≥ target | O(n) / O(1) | passed (no clock) |
| 219 | Contains Duplicate II | HashMap value → last index | O(n) / O(n) | passed (no clock) |
| 36 | Valid Sudoku | HashSet with row / col / box keys | O(1) / O(1) (fixed 9×9) | go-over (coach walked it) |
| 205 | Isomorphic Strings | HashMap s → t, `containsValue` for the reverse | O(n) / O(1) (bounded alphabet) | passed (no clock) |
| 380 | Insert Delete GetRandom O(1) | ArrayList + HashMap value → index | O(1) avg per op / O(n) | passed (no clock) |
| 383 | Ransom Note | Letter counts, spend the magazine | O(m + n) / O(1) | passed (no clock) |
| 73 | Set Matrix Zeroes | Mark rows and cols, then wipe | O(m·n) / O(m + n) | passed (no clock) |
| 290 | Word Pattern | HashMap letter → word, `containsValue` for the reverse | O(L) / O(L) | passed (no clock) |

---

## LC 128 Longest Consecutive Sequence — passed (no clock)

https://leetcode.com/problems/longest-consecutive-sequence/

### The problem

You get an unsorted array of integers.
Return the length of the longest run of values that follow each other by one, like `1,2,3,4`.
The values can sit anywhere in the array.
You must do it in O(n) time.

Example: `[100,4,200,1,3,2]` gives `4` (the run `1,2,3,4`).
Empty array gives `0`.

### The idea

Pattern: HashSet, and only start counting at the first value of a run.

Put every number in a set so "is `x + 1` here?" is an O(1) question.
A value `x` is the start of a run only when `x - 1` is missing.
From each start, walk `x + 1`, `x + 2`, … while they exist, and keep the longest length.
Because you only walk from starts, each value is visited by at most one walk.

Time is O(n) because building the set is O(n) and all the walks together touch each value once.
Extra space is O(n) for the set.

### How to solve it

1. Empty array returns `0`.
2. Add every number to a `HashSet`.
3. Loop over the set, not the array, so duplicates do not start extra walks.
4. Only when `x - 1` is missing, start a walk: `cur = x`, `len = 1`.
5. While `cur + 1` is in the set, do `cur++` and `len++`.
6. If `len > best`, save it.

Trace on `[2,-1,10,0,1,11]` (two runs and a negative, answer `4`):

| x | `x - 1` in set? | start? | walk | len | best |
|---|---|---|---|---|---|
| -1 | no (-2) | yes | 0, 1, 2 | 4 | 4 |
| 0 | yes | no | — | — | 4 |
| 1 | yes | no | — | — | 4 |
| 2 | yes | no | — | — | 4 |
| 10 | no (9) | yes | 11 | 2 | 4 |
| 11 | yes | no | — | — | 4 |

### Holes to patch

**One global counter for every neighbour pair**

The wrong thinking was: every time `x + 1` exists, add one to a single counter.
That adds up neighbour pairs from different runs as if they were one run.
On `[1,2,10,11]` there are two runs of 2, but the counter gives `3`.
Rule: count per run, starting from a run's first value, and reset for each new start.

**Walking down toward zero**

His second try treated `0` as the end of a run and skipped values that were not positive.
Consecutive means value plus or minus one, not "toward zero".
On `[-1,0,1]` the answer is `3`, and a positive-only walk misses the `-1`.
Rule: the only stop condition is "the next value is not in the set".

**Sorting first**

Sort then scan gives the right answer, but it costs O(n log n) and the problem asks for O(n).
Rule: sort is the fallback only if the interviewer drops the O(n) requirement.

### Memorize this

1. Empty → `0`.
2. All numbers into a `HashSet`.
3. Loop over the set.
4. Start only if `x - 1` is missing: `cur = x`, `len = 1`.
5. While `cur + 1` is present: `cur++`, `len++`.
6. Keep the max length.

```java
public int longestConsecutive(int[] nums) {
    if(nums.length==0) return 0;
    Set<Integer> set=new HashSet<>();
    for(int x:nums){
        set.add(x);
    }
    int best=0;
    for(int x:set){
        if(!set.contains(x-1)){
            int cur=x;
            int len=1;
            while(set.contains(cur+1)){
                cur++;
                len++;
            }
            if(len>best) best=len;
        }
    }
    return best;
}
```

Say in the interview: I put every number in a set, only start counting at a value whose predecessor is missing, walk forward while the next value exists, and keep the longest run, which touches each value once for O(n).

### How it went

Passed on Week 4 Day 1, with no clock.
The first version used one global counter and failed the two-runs test.
The second version walked down to zero and lost negatives.
His final code is the clean start-only-at-`x - 1`-missing version.

---

## LC 424 Longest Repeating Character Replacement — passed (no clock), off-list

https://leetcode.com/problems/longest-repeating-character-replacement/

### The problem

You get an uppercase string `s` and a number `k`.
You may change at most `k` letters.
Return the length of the longest contiguous stretch that can be made into one repeated letter.

Example: `"AABABBA"`, `k = 1` gives `4` (change the middle `B` in `"AABA"` to get `"AAAA"`).

### The idea

Pattern: sliding window with 26 letter counts.

For the window `[left, right]`, the cheapest way to make it one letter is to keep its most common letter and change the rest.
So the window is legal when `length - maxCount <= k`.
Grow `right` every step.
When the window becomes illegal, move `left` one step and drop that letter's count.
Never restart the window.

`maxCount` is allowed to stay at an old high value when `left` moves.
That is safe because the answer only grows when a new, higher `maxCount` appears, so a stale value never reports a length that is too big.

Time is O(n) because `right` and `left` each move forward at most n times.
Extra space is O(1) because the counts array always has 26 slots.

### How to solve it

1. Keep `int[26] count`, `left = 0`, `maxCount = 0`, `best = 0`.
2. For each `right`, add `s[right]` to the counts and update `maxCount`.
3. While `(right - left + 1) - maxCount > k`, drop `s[left]` from the counts and move `left` right.
4. Record `right - left + 1` in `best`.

Trace on `"AABABBA"`, `k = 1` (answer `4`):

| right | letter | counts (A, B) | maxCount | window before peel | peel? | window after | best |
|---|---|---|---|---|---|---|---|
| 0 | A | 1, 0 | 1 | `A` | no | `A` | 1 |
| 1 | A | 2, 0 | 2 | `AA` | no | `AA` | 2 |
| 2 | B | 2, 1 | 2 | `AAB` | no | `AAB` | 3 |
| 3 | A | 3, 1 | 3 | `AABA` | no | `AABA` | 4 |
| 4 | B | 3, 2 | 3 | `AABAB` (5 - 3 = 2) | drop A | `ABAB` | 4 |
| 5 | B | 2, 3 | 3 | `ABABB` (5 - 3 = 2) | drop A | `BABB` | 4 |
| 6 | A | 2, 3 | 3 | `BABBA` (5 - 3 = 2) | drop B | `ABBA` | 4 |

### Holes to patch

**Locking the stretch to `s[left]`**

The wrong thinking was: the window is "about" its first letter, and `k` pays for everything that differs from it.
The best letter to keep is the most common one in the window, not the first one.
On `"ABBB"`, `k = 1`, the answer is `4` (change the `A`), but locking to `A` runs out of budget at the second `B`.
Rule: the cost of a window is `length - maxCount`, whatever letter that is.

**Checking only the two ends**

He compared `s[left]` with `s[right]` to decide if the stretch was fine.
Two matching ends say nothing about the letters in the middle.
On `"AABA"`, `k = 0`, both ends are `A`, but the `B` in the middle is unpaid, so the answer is `2`, not `4`.
Rule: keep counts for the whole window in `int[26]`.

**Restarting the window**

He reset the length and jumped `left` to `right` when the budget ran out.
A restart throws away letters that could still belong to the next good window.
On `"BAAAB"`, `k = 2`, the answer is the whole string (`5`), and a restart after spending the budget on the first letters never finds it.
Rule: move `left` one step at a time and drop that one letter's count.

```java
count[s.charAt(left)-'A']--;
left++;
```

### Memorize this

1. `int[26]` counts, `left = 0`, `maxCount = 0`.
2. Add `s[right]`, update `maxCount`.
3. While `length - maxCount > k`, drop `s[left]`, `left++`.
4. `best = max(best, length)`.
5. `maxCount` may go stale; that is fine.

```java
public int characterReplacement(String s, int k) {
    int[] count=new int[26];
    int left=0;
    int best=0;
    int maxCount=0;
    for(int right=0;right<s.length();right++){
        int idx=s.charAt(right)-'A';
        count[idx]++;
        if(count[idx]>maxCount){
            maxCount=count[idx];
        }
        while((right-left+1)-maxCount>k){
            count[s.charAt(left)-'A']--;
            left++;
        }
        int len=right-left+1;
        if(len>best) best=len;
    }
    return best;
}
```

Say in the interview: I grow a window and keep letter counts; it is legal while its length minus its most frequent letter is at most `k`, and when it is not, I drop one letter from the left, so each index moves in and out once for O(n).

### How it went

Passed on Week 4 Day 1, with no clock.
His first shape locked to `s[left]`, compared only the ends, and restarted the window; all three had to be replaced with the counts rule.
This problem is NeetCode-only, so it was the last off-list pick.

---

## LC 209 Minimum Size Subarray Sum — passed (no clock)

https://leetcode.com/problems/minimum-size-subarray-sum/

### The problem

You get an array of positive integers and a `target`.
Return the length of the shortest contiguous subarray whose sum is at least `target`.
If there is none, return `0`.

Example: `target = 7`, `[2,3,1,2,4,3]` gives `2` (the subarray `[4,3]`).

### The idea

Pattern: sliding window that grows on the right and peels on the left.

All numbers are positive, so adding a number always makes the sum bigger and removing one always makes it smaller.
That means once the window reaches `target`, you should shrink it from the left as far as it stays at or above `target`, recording the length each time.
You keep `left`, `sum`, and the best length.

Time is O(n) because each index is added once and removed at most once.
Extra space is O(1) because you only keep a few integers.

### How to solve it

1. `left = 0`, `sum = 0`, `best = Integer.MAX_VALUE`.
2. For each `right`, add `nums[right]` to `sum`.
3. While `sum >= target`: record `right - left + 1`, subtract `nums[left]`, move `left` right.
4. At the end, return `0` if `best` never changed, else `best`.

Trace on `[2,3,1,2,4,3]`, `target = 7` (answer `2`):

| right | add | sum after add | peels (length recorded) | left after | best |
|---|---|---|---|---|---|
| 0 | 2 | 2 | none | 0 | MAX |
| 1 | 3 | 5 | none | 0 | MAX |
| 2 | 1 | 6 | none | 0 | MAX |
| 3 | 2 | 8 | 4 | 1 | 4 |
| 4 | 4 | 10 | 4, then 3 | 3 | 3 |
| 5 | 3 | 9 | 3, then 2 | 5 | 2 |

### Holes to patch

**Moving `right` inside the peel loop and never saving the length**

The wrong thinking was: the inner loop is where the window moves, so `right` moves there too.
`right` belongs only to the outer `for`, and the inner loop only moves `left`.
Without recording the length inside the peel, the shortest window is never saved.
On `[2,3,1,2,4,3]`, `target = 7`, the length-2 window `[4,3]` exists only for a moment during a peel.
Rule: outer `for` grows `right`; inner `while` records the length, then drops `nums[left]`.

```java
while(sum>=target){
    int len=right-left+1;
    if(len<best) best=len;
    sum=sum-nums[left];
    left++;
}
```

**Peeling with `if` instead of `while`**

One new number on the right can make several left numbers unnecessary.
On `[1,1,1,1,7]`, `target = 7`, the answer is `1`, but one peel per step only reaches length `4`.
Rule: the peel is a `while`, so one step of `right` can peel zero, one, or many.

**Classic trap: returning `MAX_VALUE` when nothing works**

On `[1,1,1]`, `target = 11`, no window reaches the target.
Rule: convert the untouched `Integer.MAX_VALUE` to `0` before returning.

### Memorize this

1. Outer `for right`: add `nums[right]`.
2. Inner `while sum >= target`: record `right - left + 1`.
3. Still inside: subtract `nums[left]`, `left++`.
4. Return `0` if best is still `MAX_VALUE`.
5. Negative numbers break this; use prefix sums instead.

```java
public int minSubArrayLen(int target, int[] nums) {
    int left=0;
    int sum=0;
    int best=Integer.MAX_VALUE;
    for(int right=0;right<nums.length;right++){
        sum=sum+nums[right];
        while(sum>=target){
            int len=right-left+1;
            if(len<best) best=len;
            sum=sum-nums[left];
            left++;
        }
    }
    if(best==Integer.MAX_VALUE) return 0;
    return best;
}
```

Say in the interview: With positive numbers, I grow the window on the right and peel the left while the sum is still enough, recording the length each time, so every index enters and leaves once for O(n).

### How it went

Passed on Week 4 Day 2, with no clock.
The first version moved `right` inside the peel and never saved the length.
After the outer-grow and inner-peel split, it passed.

---

## LC 219 Contains Duplicate II — passed (no clock)

https://leetcode.com/problems/contains-duplicate-ii/

### The problem

You get an array and a number `k`.
Return `true` if the same value appears at two indexes `i` and `j` with `|i - j| <= k`.

Example: `[1,2,3,1]`, `k = 3` gives `true` (indexes 0 and 3).
`[1,2,3,1,2,3]`, `k = 2` gives `false`.

### The idea

Pattern: HashMap from value to the last index where you saw it.

When you reach index `i`, the closest earlier copy of `nums[i]` is the most recent one.
So you only need the last index per value, and you check it before you overwrite it.

Time is O(n) because each index does one map lookup and one put.
Extra space is O(n) in the worst case, one entry per distinct value.

### How to solve it

1. Make an empty map value → last index.
2. For each `i`, look up `nums[i]`.
3. If it is there and `i - last <= k`, return `true`.
4. Always put `nums[i] → i`, so the map holds the newest index.
5. Return `false` after the loop.

Trace on `[1,0,1,1]`, `k = 1` (answer `true`):

| i | value | last index in map | `i - last` | result | map after |
|---|---|---|---|---|---|
| 0 | 1 | — | — | keep going | {1:0} |
| 1 | 0 | — | — | keep going | {1:0, 0:1} |
| 2 | 1 | 0 | 2 > 1 | keep going | {1:2, 0:1} |
| 3 | 1 | 2 | 1 ≤ 1 | return `true` | — |

### Holes to patch

**Filling the map with last indexes first, then scanning**

The wrong thinking was: build the whole value → last-index map in one pass, then compare each index against it.
The last index in the whole array is not the closest copy to an early index.
On `[1,1,3,4,5,1]`, `k = 1`, the answer is `true` (indexes 0 and 1), but the prebuilt map says the last `1` is at 5, so the two-pass version misses it.
Rule: one pass; check against the map, then overwrite.

**Classic trap: only putting on a miss**

If you only put a value the first time you see it, the map keeps the oldest index.
In the trace above, index 3 would compare against index 0 and miss the pair at 2 and 3.
Rule: put on every index, not only new values.

### Memorize this

1. One `for i`.
2. Map value → last index.
3. Hit and `i - last <= k` → `true`.
4. Always put `nums[i] → i`.
5. Drop `k` and it is LC 217 with a plain set.

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Map<Integer,Integer> map=new HashMap<>();
    for(int i=0;i<nums.length;i++){
        if(map.containsKey(nums[i])){
            int last=map.get(nums[i]);
            if(i-last<=k) return true;
        }
        map.put(nums[i],i);
    }
    return false;
}
```

Say in the interview: I store the last index of each value, and a repeat counts only if the current index is within `k` of that last one, which is O(n) time and O(n) space.

### How it went

Passed on Week 4 Day 2, with no clock.
The weak spot was the two-pass "fill last indexes first" idea; his final code checks then overwrites in one pass.

---

## LC 36 Valid Sudoku — go-over (coach walked it)

https://leetcode.com/problems/valid-sudoku/

### The problem

You get a 9×9 board with digits `'1'`–`'9'` and empty cells `'.'`.
Return `true` if no filled digit repeats in any row, any column, or any 3×3 box.
You do not solve the puzzle.

Example: a `5` at `(0,0)` and another `5` at `(2,2)` makes the board invalid, because both are in the top-left box.

### The idea

Pattern: HashSet of "seen" tickets.

Each filled digit belongs to three groups: its row, its column, and its box.
Write one string ticket per group, like `"5 in row 0"`, `"5 in col 0"`, `"5 in box 0"`, into one set.
If `add` returns `false`, that digit already appeared in that group, so the board is invalid.
The box number is `(i / 3) * 3 + (j / 3)`, which gives 0 to 8 left to right, top to bottom.

Time is O(1) because the board is always 81 cells (O(n²) for an n×n board).
Extra space is O(1) because the set holds at most 3 × 81 tickets.

### How to solve it

1. One set of strings.
2. Loop `i` over rows and `j` over columns, skipping `'.'`.
3. Build the tickets `row`, `col`, and `b` (box) for the digit `c`.
4. If any `add` fails, return `false`.
5. After the loop, return `true`.

Trace on three cells of a board:

| i | j | c | box | tickets added | result |
|---|---|---|---|---|---|
| 0 | 0 | 5 | 0 | `5 in row 0`, `5 in col 0`, `5 in box 0` | keep going |
| 1 | 4 | 3 | 1 | `3 in row 1`, `3 in col 4`, `3 in box 1` | keep going |
| 2 | 2 | 5 | 0 | `5 in row 2`, `5 in col 2`, `5 in box 0` fails | return `false` |

### Holes to patch

This was a go-over, so the notes record no mistakes of his own.

**Classic trap: a wrong box formula**

`i / 3 + j / 3` looks right but collides.
Cell `(0,3)` is in box 1 and cell `(3,0)` is in box 3, but both give `1` with that formula.
Rule: `box = (i / 3) * 3 + (j / 3)`.

**Classic trap: tickets without the group name**

If the tickets are just `digit + index`, a row ticket and a column ticket can look the same.
A `5` at `(0,3)` makes `"50"` (row) and `"53"` (col), and a `5` at `(3,0)` makes `"53"` and `"50"`, so a valid board is called invalid.
Rule: put the group type in the ticket, like `"5 in row 0"` and `"5 in col 0"`.

**Classic trap: trying to solve the board**

The question only asks if the filled cells break a rule.
Filling empty cells is backtracking, which is a different problem (LC 37).

### Memorize this

1. One `HashSet<String>`.
2. Skip `'.'`.
3. Three tickets: `c + " in row " + i`, `c + " in col " + j`, `c + " in box " + box`.
4. `box = (i / 3) * 3 + (j / 3)`.
5. Any failed `add` → `false`.

```java
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
```

Say in the interview: I do not solve it; I stamp each filled digit into its row, column, and box in one set, and a second stamp in the same group means the board is invalid.

### How it went

Week 4 Day 3, a go-over, not a grind.
The coach walked the pattern and Anton wrote the three-ticket version that is in the repo.

---

## LC 205 Isomorphic Strings — passed (no clock)

https://leetcode.com/problems/isomorphic-strings/

### The problem

You get two strings `s` and `t` of the same length.
They are isomorphic if you can replace letters in `s` to get `t`, where each `s` letter always becomes the same `t` letter and no two `s` letters become the same `t` letter.

Example: `"egg"`, `"add"` gives `true`.
`"foo"`, `"bar"` gives `false` (`o` would need to become both `a` and `r`).
`"ab"`, `"aa"` gives `false` (`a` and `b` would both become `a`).

### The idea

Pattern: HashMap that glues each `s` letter to one `t` letter, checked in both directions.

Walk both strings at the same index.
Keep one map `s[i] → t[i]`.
The forward rule: if `s[i]` is already glued to a different letter, the answer is `false`.
The reverse rule: if `s[i]` is new but `t[i]` is already a value in the map, another `s` letter owns it, so the answer is `false`.
This is a translation check, not a count check.

Time is O(n) because each index does a few map operations, and `containsValue` scans at most one entry per alphabet letter.
Extra space is O(1) because the map holds at most one entry per character in the alphabet.

### How to solve it

1. If the lengths differ, return `false`.
2. Keep one `map` (s letter → t letter).
3. For each `i`, take `a = s[i]`, `b = t[i]`.
4. If `a` is not a key and `b` is not a value, glue `a → b`.
5. Else if `a` is a key and `map.get(a) != b`, return `false`.
6. Else if `a` is not a key but `b` is already a value, return `false`.
7. After the loop, return `true`.

Trace on `"abab"`, `"aabb"` (same letter counts, answer `false`):

| i | a | b | key `a`? | `containsValue(b)`? | result | map after |
|---|---|---|---|---|---|---|
| 0 | a | a | no | no | glue | {a:a} |
| 1 | b | a | no | yes (owned by `a`) | return `false` | — |

### Holes to patch

**Thinking in counts instead of glue**

His first "why" was about how many times each letter appears.
That is LC 242 Valid Anagram, not this problem.
`"abab"` and `"aabb"` both have two of each letter, and the answer is still `false` because the letters do not line up by position.
Rule: compare letters at the same index, not totals.

**`containsValue` for the reverse rule (upgrade)**

His code uses one map and `containsValue(b)` to check if `b` is already taken.
`containsValue` scans every value in the map, so each step costs the size of the map instead of O(1).
Here the map is capped by the alphabet, so it is still linear, but in an interview a scan inside the loop sounds like O(n²) thinking.
Upgrade: keep a second map `ts` (`t → s`), so both checks are O(1).

**Classic trap: dropping the reverse check**

With only `s → t`, the input `"ab"`, `"aa"` passes, because `a → a` and `b → a` never clash on the `s` side.
Rule: check both directions.

### Memorize this

1. Same length or `false`.
2. One map `s → t`.
3. `a` new and `b` free: glue `a → b`.
4. `a` mapped to something other than `b`: `false`.
5. `a` new but `b` already a value: `false`.

```java
public boolean isIsomorphic(String s, String t) {
    if(s.length()!=t.length()) return false;
    Map<Character,Character> map=new HashMap<>();
    for(int i=0;i<s.length();i++){
        char a=s.charAt(i);
        char b=t.charAt(i);
        if(!map.containsKey(a) && !map.containsValue(b)){
            map.put(a,b);
        }
        else if(map.containsKey(a) && map.get(a)!=b){
            return false;
        }
        else if(!map.containsKey(a) && map.containsValue(b)){
            return false;
        }
    }
    return true;
}
```

Say in the interview: I glue each `s` letter to one `t` letter and refuse a second glue in either direction, which is O(n) time and constant space for a fixed alphabet, and a second `t → s` map makes the reverse check O(1).

### How it went

Passed on Week 4 Day 3, with no clock.
The counts idea was corrected at the gate, before he coded.
His code is one map with `containsValue` and three branches; the two-map version is the upgrade to mention out loud.

---

## LC 380 Insert Delete GetRandom O(1) — passed (no clock)

https://leetcode.com/problems/insert-delete-getrandom-o1/

### The problem

Design a class `RandomizedSet` with three methods, each average O(1):

- `insert(val)`: add `val` if it is not there; return `true` if added, `false` if it was already there.
- `remove(val)`: remove `val` if it is there; return `true` if removed.
- `getRandom()`: return a random element, each with equal chance; it does not remove anything.

Example: `insert(1)` → `true`, `insert(1)` → `false`, `remove(1)` → `true`, `remove(1)` → `false`.

### The idea

Pattern: an `ArrayList` of values plus a `HashMap` from value to its index in the list.

The list gives O(1) random pick by index.
The map gives O(1) "is it here, and where?".
Removing from the middle of a list is O(n), so instead you move the last element into the removed slot and shrink the list by one.
Only one map entry changes: the value that moved.

Time is average O(1) per operation because every step is a map operation or a list operation at the tail or a known index.
Extra space is O(n) for the list and the map.

### How to solve it

1. `insert`: if the map has `val`, return `false`; else put `val → list.size()`, append `val`.
2. `remove`: if the map does not have `val`, return `false`.
3. Take `idx = map.get(val)` and `last` = the last value in the list.
4. `list.set(idx, last)` and `map.put(last, idx)`.
5. Remove the list tail, then `map.remove(val)`.
6. `getRandom`: `list.get(rand.nextInt(list.size()))`.

Trace on `insert(1)`, `insert(2)`, `insert(3)`, `remove(1)`, `remove(3)`:

| op | list after | map after | returns |
|---|---|---|---|
| insert(1) | [1] | {1:0} | true |
| insert(2) | [1,2] | {1:0, 2:1} | true |
| insert(3) | [1,2,3] | {1:0, 2:1, 3:2} | true |
| remove(1) | [3,2] | {3:0, 2:1} | true (3 moved into slot 0) |
| remove(3) | [2] | {2:0} | true (2 moved into slot 0) |

### Holes to patch

**Using `list.contains` to check membership**

His first version checked `list.contains(val)` in `insert` and `remove`.
`contains` walks the whole list, so it is O(n), which breaks the whole point of the problem.
Rule: membership is `map.containsKey(val)`.

**Mixing it up with a count map**

He started thinking of it like Ransom Note, as if `insert(2)` twice should store two copies and need two removes.
This class is a set: the second `insert(2)` returns `false`, and one `remove(2)` clears it.
Rule: no duplicates here; the bag-with-counts version is LC 381.

**Removing from the front and rebuilding the map**

`list.remove(0)` shifts every element, and then every index in the map is wrong.
Rule: swap the victim with the last element, update that one index, drop the tail.

**Classic trap: removing the last element with the wrong order**

If `val` is itself the last element and you do `map.remove(val)` before `map.put(last, idx)`, you put `val` back into the map.
Rule: update the moved value first, then remove `val`, which is the order his code uses.

### Memorize this

1. List = values, map = value → index.
2. Insert: `containsKey` → false; else put `size`, append.
3. Remove: `containsKey` miss → false; take `idx`, move `last` into `idx`, `map.put(last, idx)`.
4. Drop the tail, then `map.remove(val)`.
5. Random: `list.get(rand.nextInt(size))`.

```java
class RandomizedSet {

    private List<Integer> list=new ArrayList<>();
    private Map<Integer,Integer> map=new HashMap<>();
    private Random rand=new Random();

    public RandomizedSet() {
    }

    public boolean insert(int val) {
        if(map.containsKey(val)) return false;
        map.put(val,list.size());
        list.add(val);
        return true;
    }

    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;
        int idx=map.get(val);
        int last=list.get(list.size()-1);
        list.set(idx,last);
        map.put(last,idx);
        list.remove(list.size()-1);
        map.remove(val);
        return true;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}
```

Say in the interview: A list gives me O(1) random pick, a map gives me O(1) "where is this value", and remove swaps the victim with the last slot so nothing ever shifts.

### How it went

Passed on Week 4 Day 3, with no clock.
The shape was right, but it first used `list.contains` and mixed in the count-map idea.
His code in the repo now checks `map.containsKey` and handles the tail correctly through the order of its map updates.

---

## LC 383 Ransom Note — passed (no clock)

https://leetcode.com/problems/ransom-note/

### The problem

You get two lowercase strings, `ransomNote` and `magazine`.
Return `true` if you can build the note by cutting letters out of the magazine, using each magazine letter at most once.
Extra letters in the magazine are fine.

Example: `"aa"`, `"aab"` gives `true`.
`"aa"`, `"ab"` gives `false`.

### The idea

Pattern: letter counts, spend the magazine.

Count every letter in the magazine in a `HashMap<Character,Integer>`.
Then walk the note and spend one count per letter.
If the letter is missing or its count is already zero, the magazine ran out of that letter.

Time is O(m + n) because each string is walked once.
Extra space is O(1) because the map holds at most 26 letters.

### How to solve it

1. One `map` letter → count.
2. For each magazine letter, `map.put(c, map.getOrDefault(c, 0) + 1)`.
3. For each note letter, if `!map.containsKey(c) || map.get(c) == 0`, return `false`.
4. Otherwise spend one: `map.put(c, map.get(c) - 1)`.
5. After the note, return `true`.

Trace on note `"aa"`, magazine `"aaa"` (answer `true`, extras allowed):

| step | letter | count of `a` after | result |
|---|---|---|---|
| magazine | a, a, a | 3 | — |
| note 0 | a | 2 | keep going |
| note 1 | a | 1 | keep going |
| end | — | 1 left over | return `true` |

### Holes to patch

**Requiring equal counts**

His first check returned `false` when the note count and the magazine count were different (`!=`).
That is the LC 242 Anagram rule, where both sides must match exactly.
On note `"aa"`, magazine `"aaa"`, the answer is `true`, but the equal-count check says `false`.
Rule: the note count must be less than or equal to the magazine count.

```java
if(!map.containsKey(c) || map.get(c)==0) return false;
```

**Lighter option: `int[26]` instead of a map**

The letters are only `'a'`–`'z'`, so an `int[26]` with `count[c - 'a']` does the same job without boxing.
Rule: either is O(1) space; mention the array if they ask for something faster.

### Memorize this

1. Count the magazine in a `HashMap`.
2. Walk the note.
3. Missing or zero → `false`.
4. Else spend one.
5. Leftovers are fine.

```java
public boolean canConstruct(String ransomNote, String magazine) {
    Map<Character,Integer> map=new HashMap<>();
    for(int i=0;i<magazine.length();i++){
        char c=magazine.charAt(i);
        map.put(c,map.getOrDefault(c,0)+1);
    }
    for(int i=0;i<ransomNote.length();i++){
        char c=ransomNote.charAt(i);
        if(!map.containsKey(c) || map.get(c)==0) return false;
        map.put(c,map.get(c)-1);
    }
    return true;
}
```

Say in the interview: I count the magazine letters once, then spend one per note letter, and if a letter is missing or already at zero the magazine ran out, which is O(m + n) time and O(1) space.

### How it went

Passed on Week 4 Day 4, with no clock.
The equal-counts mistake failed `"aa"` / `"aaa"` and was fixed to "less than or equal".
He used two maps; his final repo version uses one map from the magazine and spends it on the note.

---

## LC 73 Set Matrix Zeroes — passed (no clock)

https://leetcode.com/problems/set-matrix-zeroes/

### The problem

You get an `m × n` integer matrix.
If a cell is `0`, set its whole row and whole column to `0`.
Change the matrix in place.

Example: `[[1,1,1],[1,0,1],[1,1,1]]` becomes `[[1,0,1],[0,0,0],[1,0,1]]`.

### The idea

Pattern: mark the zero rows and columns first, then wipe in a second pass.

If you wipe while scanning, the zeros you write look like original zeros and spread further.
So the first pass only records which rows and columns had a real zero.
The second pass sets a cell to zero if its row or its column is marked.

Time is O(m·n) because each pass visits every cell once.
Extra space is O(m + n) for the two marker sets.
The follow-up asks for O(1) extra space by storing the marks in row 0 and column 0 of the matrix itself.

### How to solve it

1. Make two `HashSet<Integer>`: `rows` and `cols`.
2. First pass: for every `0` at `(i, j)`, `rows.add(i)` and `cols.add(j)`.
3. Second pass: for every cell, if `rows.contains(i) || cols.contains(j)`, set it to `0`.

Trace on `[[1,0,3],[4,5,6],[0,8,9]]`:

| pass | what happens | rows marked | cols marked | matrix |
|---|---|---|---|---|
| 1 | zero at (0,1) | {0} | {1} | unchanged |
| 1 | zero at (2,0) | {0, 2} | {1, 0} | unchanged |
| 2 | row 0 marked | — | — | `[0,0,0]` |
| 2 | row 1: cols 0 and 1 marked | — | — | `[0,0,6]` |
| 2 | row 2 marked | — | — | `[0,0,0]` |

The `5` at `(1,1)` becomes `0` only because column 1 is marked, which is the case people miss.

### Holes to patch

**Reading "in place" as "no extra memory"**

In place means you change the given matrix instead of returning a new one.
It does not mean O(1) extra space.
Two sets of row and column indexes are fine for the base problem; O(1) extra is the follow-up.
Rule: in place = mutate the input; O(1) extra = the row-0 / column-0 trick.

**Storing `int[]` pairs in a `HashSet`**

He first stored each zero as `new int[]{i, j}` in a `HashSet<int[]>`.
Arrays in Java use identity for `equals` and `hashCode`, so the set cannot tell that two pairs are equal and `contains(new int[]{i, j})` is always `false`.
It passed only because he iterated the set instead of looking pairs up.
Rule: store rows and columns in two `HashSet<Integer>` (or two `boolean` arrays).

**Classic trap: wiping while scanning**

On `[[0,1],[1,1]]`, the answer is `[[0,0],[0,1]]`.
Wiping at `(0,0)` right away turns `(0,1)` into `0`, and the scan then treats it as a real zero and wipes column 1, giving `[[0,0],[0,0]]`.
Rule: record first, wipe in a second pass.

### Memorize this

1. Two sets: `rows` and `cols`.
2. Pass 1: mark every real zero.
3. Pass 2: zero a cell if its row or column is marked.
4. Never wipe during pass 1.
5. O(1) follow-up: marks in row 0 and column 0.

```java
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
```

Say in the interview: I first record which rows and columns contain a real zero, then wipe in a second pass so my own zeros never spread, which is O(m·n) time and O(m + n) extra space, with the row-0 trick if they want O(1).

### How it went

Passed on Week 4 Day 4, with no clock.
The first version stored `int[]` pairs in a set and passed; the repo version is the cleaner two-set shape.

---

## LC 290 Word Pattern — passed (no clock)

https://leetcode.com/problems/word-pattern/

### The problem

You get a `pattern` of letters and a string `s` of words separated by single spaces.
Return `true` if each letter maps to exactly one word and each word maps back to exactly one letter.
The number of letters must equal the number of words.

Example: `"abba"`, `"dog cat cat dog"` gives `true`.
`"abba"`, `"dog cat cat fish"` gives `false`.
`"abba"`, `"dog dog dog dog"` gives `false` (two letters, one word).

### The idea

Pattern: HashMap letter → word, checked in both directions, the same glue as LC 205.

Split `s` into words and walk letters and words at the same index.
A letter that is already glued must meet the same word again.
A new letter must not take a word that another letter already owns, which `containsValue` checks.
Words are `String`s, so compare them with `.equals`.

Time is O(L), where L is the total length of `pattern` and `s`, because the map has at most one entry per pattern letter, so each `containsValue` scans a bounded number of words.
Extra space is O(L) for the words array and the map.

### How to solve it

1. Split `s` on spaces into `arr`.
2. If the pattern length differs from the word count, return `false`.
3. Keep one `map` (letter → word).
4. If the letter is not a key and `arr[i]` is not a value, glue them.
5. Else if the letter is a key and its word does not `.equals` `arr[i]`, return `false`.
6. Else if the letter is not a key but `arr[i]` is already a value, return `false`.
7. After the loop, return `true`.

Trace on `"abba"`, `"dog dog dog dog"` (answer `false`):

| i | letter | word | letter a key? | `containsValue(word)`? | result | map after |
|---|---|---|---|---|---|---|
| 0 | a | dog | no | no | glue | {a:dog} |
| 1 | b | dog | no | yes (owned by `a`) | return `false` | — |

### Holes to patch

**Comparing `String`s with `!=`**

He first wrote `map.get(c) != word`.
`!=` compares references, and every word from `split` is a new `String` object, so `"dog" != "dog"` is `true`.
On `"abba"`, `"dog cat cat dog"`, index 2 compares two different `"cat"` objects and returns `false` on a valid input.
Rule: compare strings with `.equals`.

```java
!map.get(pattern.charAt(i)).equals(arr[i])
```

**Returning `false` on a match**

After switching to `.equals`, the branch returned `false` when the words were equal.
A match is success, and the loop must keep going.
On `"abba"`, `"dog cat cat dog"`, the first repeat (`b` meets `cat` again) would wrongly end the check.
Rule: fail only when the mapped word is different.

**`containsValue` for the reverse rule (upgrade)**

His code uses one map and `containsValue(arr[i])` to check if a word is taken.
`containsValue` scans every value, but the map is keyed by pattern letters, so it never holds more than 26 words and the loop stays linear.
In an interview a scan inside the loop still sounds like O(n²) thinking.
Upgrade: keep a second map word → letter so the reverse check is O(1).

### Memorize this

1. Split `s` into `arr`; counts must match.
2. One map: letter → word.
3. Letter new and word free: glue.
4. Letter mapped to a word that does not `.equals`: `false`.
5. Letter new but word already a value: `false`.
6. A match means keep going.

```java
public boolean wordPattern(String pattern, String s) {
    String[] arr=s.split(" ");
    if(pattern.length()!=arr.length) return false;
    HashMap<Character,String> map= new HashMap<>();
    for(int i=0;i<pattern.length();i++){
        if(!map.containsKey(pattern.charAt(i))&&!map.containsValue(arr[i])){
            map.put(pattern.charAt(i), arr[i]);
        }
        else if(map.containsKey(pattern.charAt(i))&&!map.get(pattern.charAt(i)).equals(arr[i])){
            return false;
        }
        else if(!map.containsKey(pattern.charAt(i))&&map.containsValue(arr[i])){
            return false;
        }
    }
    return true;
}
```

Say in the interview: I split the words and glue each letter to one word, refusing a second glue in either direction and comparing words with `.equals`, which is linear in the input size, and a second word → letter map makes the reverse check O(1).

### How it went

Passed on Week 4 Day 5, with no clock; it replaced the skipped #202 Happy Number.
The `!=` on `String` and the inverted match branch were the two bugs before it went green.
His final code is one map with `containsValue` and three branches; the two-map version is the upgrade to mention out loud.
