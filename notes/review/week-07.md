# Week 07 — review sheet

Week 7 covered three families: heap (top k / kth largest), binary search (sorted array, O(log n)), and intervals (sort by start, then one walk).

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 347 | Top K Frequent Elements | Heap (max-heap of all keys by count) | O(n log n) / O(n) | on-time |
| 35 | Search Insert Position | Binary search | O(log n) / O(1) | time up, coach-fixed |
| 215 | Kth Largest Element in an Array | Heap (max-heap of all numbers) | O(n log n) / O(n) | overtime, coach-fixed |
| 56 | Merge Intervals | Intervals (sort by start, one walk) | O(n log n) / O(n) | overtime |
| 228 | Summary Ranges | Intervals (one walk, open run in a stack) | O(n) / O(n) extra for the stack | overtime, coach-fixed |
| 33 | Search in Rotated Sorted Array | Binary search (find the sorted half) | O(log n) / O(1) | on-time, coach gave the fix |
| 153 | Find Minimum in Rotated Sorted Array | Binary search (mid vs high) | O(log n) / O(1) | on-time |
| 74 | Search a 2D Matrix | Binary search (matrix as one flat array) | O(log(m × n)) / O(1) | green, no clock record |
| 452 | Minimum Number of Arrows to Burst Balloons | Intervals (sort by end, one walk) | O(n log n) / O(n) | time up, then overtime, coach-fixed |
| 57 | Insert Interval | Intervals (before / overlap / after, one walk) | O(n) / O(n) for the output | no gate, no clock, coach-fixed |

How to tell the three apart:

| You see | Tool |
|---|---|
| "top k", "kth largest", "k closest" | Heap |
| Sorted array plus "O(log n)" | Binary search |
| Pairs `[start, end]`, or runs of numbers | Intervals |

None of them is a tree walk.

---

## LC 347 Top K Frequent Elements — on-time

https://leetcode.com/problems/top-k-frequent-elements/

### The problem

You get an int array and a number `k`.
Return the `k` values that appear most often, in any order.
`[1,1,1,2,2,3]`, `k = 2` gives `[1, 2]`, because 1 appears three times and 2 appears twice.

### The idea

Pattern: heap, on top of a count map.
First count every value in a `HashMap` (value → count).
Then you need the `k` keys with the biggest counts, which is a "top k" question, so a heap.
The heap holds the **keys**, and its comparator looks up each key's count in the map.
His version is a **max**-heap of every key: the biggest count is on top, so `k` polls give the `k` most frequent keys.
Time O(n log n): counting is O(n), and each of up to n distinct keys costs one log n insert, then k polls.
Extra space O(n): the map and the heap can each hold n distinct keys.

A **min**-heap capped at size `k` (poll when it grows past `k`) is O(n log k) and beats the follow-up.
Bucket sort by count (an array of lists where the index is the count, walked from the top) is O(n).

### How to solve it

1. Count every `x`: if the map has it, add 1; if not, put 1.
2. Make a max-heap of keys with the comparator `map.get(b) - map.get(a)`.
3. After counting, `addAll(map.keySet())` into the heap.
4. Poll `k` times into `ret`.

Trace on `[1,1,1,2,2,3]`, `k = 2`, counts `{1:3, 2:2, 3:1}`:

| step | heap (biggest count on top) | polled | ret |
|---|---|---|---|
| addAll | 1(3), 2(2), 3(1) | — | `[_, _]` |
| i = 0 | 2(2), 3(1) | 1 | `[1, _]` |
| i = 1 | 3(1) | 2 | `[1, 2]` |

Key 3 stays in the heap; `ret` is the answer.

### Holes to patch

**Sorting bare counts loses who owned them.**
His first plan was: count, put the counts in an array, sort.
Sorting `[3, 2, 1]` tells you the biggest count is 3, but not that it belongs to the value 1.
Rule: whatever you sort or heap must hold the **keys**, and compare them by their count.

**Ordering keys by a value in a map.**
He did not know how to make a heap order by count.
The heap stores keys; the comparator reads the map.
Fill the map first, then offer; a count that changes after the key is in the heap does not move it.

```java
PriorityQueue<Integer> max = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
```

**Heap cost: one `offer` is log(size), not the whole solution.**
He said O(n) for the heap work.
Every `offer` and `poll` is O(log size), and you do one per key.
All keys in a max-heap, then k polls (his version) is O(n log n).
With n = 100 000 distinct values and k = 1, that heap is 100 000 deep instead of 1 deep.
Rule: n offers into a heap capped at k is O(n log k); that is what beats the O(n log n) follow-up.

**The class is `PriorityQueue`.**
There is no `Heap` class in Java.
Only the top is ordered, so `System.out.println(pq)` does not print sorted order.

**Counting in two `if`s.**
He wrote a `containsKey` branch plus a second `!containsKey` branch.
One line with `getOrDefault` does the same count.

### Memorize this

1. Count values in a `HashMap`.
2. Max-heap of keys, comparator `map.get(b) - map.get(a)`.
3. After counting, add all keys to the heap.
4. Poll `k` times into the result array.
5. O(n log n) time, O(n) space.
6. Follow-up: min-heap capped at k is O(n log k); bucket by count is O(n).

```java
public int[] topKFrequent(int[] nums, int k) {
    int[] ret=new int[k];

    HashMap<Integer,Integer> map=new HashMap<>();
    PriorityQueue<Integer> max = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
    for(int x:nums){
        if(map.containsKey(x)){
            int tmpval=map.get(x);
            tmpval++;
            map.put(x, tmpval);
        }
        if(!map.containsKey(x)) map.put(x, 1);
    }
    max.addAll(map.keySet());
    System.out.println(max);
    for(int i=0;i<k;i++){
        ret[i]=max.poll();
    }

    return ret;
}
```

Say in the interview: I count with a hash map, put the keys in a max-heap ordered by count, and poll k times; if they want better than n log n, I cap a min-heap at size k instead.

### How it went

On-time, all 5 green, he coded it himself.
He needed help to pick the tool (heap) and to write a comparator that reads the map.
His version put every key in a max-heap, which is O(n log n) and does not beat the follow-up.

---

## LC 35 Search Insert Position — time up, coach-fixed

https://leetcode.com/problems/search-insert-position/

### The problem

You get a sorted array of distinct ints and a target.
Return the index of the target, or the index where it would go to keep the array sorted.
It must run in O(log n).
`[1,3,5,6]`, target 2 gives 1, because 2 goes between 1 and 3.
`[1,3,5,6]`, target 7 gives 4.

### The idea

Pattern: binary search.
The array is sorted, so one look at the middle tells you which half the answer is in.
You keep two indices, `low` and `high`, and the live range is `low..high` with both ends included.
Every round keeps a promise: everything left of `low` is smaller than the target, and everything right of `high` is bigger.
When the loop ends, `low = high + 1`, so `low` is the first index bigger than the target, which is exactly the insert slot.
Time O(log n), because each round throws away half the range.
Extra space O(1), because it is only three `int`s.

### How to solve it

1. Guards: null or empty returns -1; target bigger than the last value returns `nums.length`.
2. `low = 0`, `high = n - 1`.
3. While `low <= high`, compute `mid = low + (high - low) / 2`.
4. One decision per round, as one `if / else if / else` chain.
5. If `nums[mid] == target`, return `mid`.
6. If `nums[mid] < target`, the answer is right of `mid`: `low = mid + 1`.
7. Otherwise it is left of `mid`: `high = mid - 1`.
8. After the loop, return `low`.

Trace on `[-5, -3]`, target -4 (the case his sign patch got wrong):

| low | high | mid | nums[mid] | decision |
|---|---|---|---|---|
| — | — | — | — | guard: -3 < -4? no, go on |
| 0 | 1 | 0 | -5 | -5 < -4, so `low = 1` |
| 1 | 1 | 1 | -3 | -3 > -4, so `high = 0` |
| 1 | 0 | — | — | `low > high`, return `low` = 1 |

Left of index 1 is -5 (smaller), index 1 onwards is -3 (bigger), so -4 goes at 1.
The sign of the target never matters; only the compare does.

### Holes to patch

**Linear scan when the problem says O(log n).**
He coded a loop over every index before the gate.
All tests were green, but it is O(n), which fails the stated requirement.
Rule: "sorted" plus "O(log n)" means binary search, always.

**Calling it a tree, BFS, or DFS.**
He named binary tree, then BFS, then DFS, and said extra space O(n).
No tree is built: `lo` and `hi` are just two indices on the same array.
BFS and DFS visit every node, which is O(n), the opposite of what the problem wants.
Rule: binary search is two indices closing in on a sorted array, O(1) extra.

**Splitting one three-way decision into separate `if`s.**
He wrote `if (<) lo = mid + 1;` and then a separate `if (==) return mid; else hi = mid - 1;`.
When `nums[mid] < target`, `lo` moves, then `==` is false, so the `else` also moves `hi`.
Breaks on `[1,3,5,6]`, target 5: `mid = 1`, 3 < 5 sets `lo = 2`, then the `else` sets `hi = 0`, and the range is empty before index 2 is ever checked.
Rule: exactly one branch runs per round.

```java
if(nums[mid]==target) return mid;
else if(nums[mid]<target) low=mid+1;
else high= mid-1;
```

**Patching the answer to fit a failing test.**
At the bell he returned `mid + 1` or `mid + 2` depending on the sign of the target, to make one test pass.
Breaks on `[-5, -3]`, target -4: it returned 2, the right answer is 1.
Rule: when one test fails, trace it and find the bug; never shape the return value around the test.

**Special cases that `return lo` already covers.**
His final code still checks "target past the end" before the loop and returns -1 for an empty array.
On target 7 in `[1,3,5,6]`, `low` walks to 4 on its own, and on an empty array `low` stays 0, which is the correct insert slot.
His -1 for an empty array is wrong in principle (the insert slot is 0); LeetCode never sends an empty array, so the tests do not catch it.
Rule: trust the invariant; `return low` handles before-the-start, past-the-end, and empty.

### Memorize this

1. Guards: empty → -1, target past the last value → `nums.length`.
2. `low = 0`, `high = n - 1`, loop while `low <= high`.
3. `mid = low + (high - low) / 2` (no overflow).
4. One chain: equal → return, less → `low = mid + 1`, else → `high = mid - 1`.
5. `mid` was checked, so it always leaves the range (`± 1`).
6. Not found → return `low`.

```java
public int searchInsert(int[] nums, int target) {
    if (nums==null||nums.length==0) return -1;
    if(nums[nums.length-1]<target) return nums.length;
    int low=0,high=nums.length-1;
    while(low<=high){
        int mid = low + (high - low) / 2;
        if(nums[mid]==target) return mid;
        else if(nums[mid]<target) low=mid+1;
        else high= mid-1;
    }
    return low;
}
```

Say in the interview: it is a standard binary search, and when the target is missing the loop ends with `low` at the first element bigger than the target, which is the insert position.

### How it went

Time up, not landed; the coach fixed it at his request.
The split `if`s moved both ends in one round, and the sign patch at the bell hid the bug instead of fixing it.
The gate also cost time because heap, intervals, and binary search were not yet clear as three different tools.

---

## LC 215 Kth Largest Element in an Array — overtime, coach-fixed

https://leetcode.com/problems/kth-largest-element-in-an-array/

### The problem

You get an int array and a number `k`.
Return the kth largest element in sorted order; duplicates count separately.
`[3,2,1,5,6,4]`, `k = 2` gives 5.
`[3,2,3,1,2,4,5,5,6]`, `k = 4` gives 4.

### The idea

Pattern: heap, holding the numbers themselves (no map).
His version is a **max**-heap (`Collections.reverseOrder()`) of every number.
The biggest is on top, so the kth `poll` returns the kth largest.
Time O(n log n): n offers at log n each, then k polls.
Extra space O(n), because the heap holds every number.

A **min**-heap capped at size `k` (poll when it grows past `k`, return `peek()`) is O(n log k) time and O(k) space.

### How to solve it

1. Guards: empty or `k > nums.length` returns 0; one number returns it.
2. Make a max-heap: `new PriorityQueue<>(Collections.reverseOrder())`.
3. Offer every number.
4. Poll `k` times (`i < k`).
5. On the last poll (`i == k - 1`), keep the value in `retVal` and return it.

Trace on `[3,2,1,5,6,4]`, `k = 2`:

| step | heap (biggest on top) | polled | retVal |
|---|---|---|---|
| offer all | 6 5 4 3 2 1 | — | 0 |
| i = 0 | 5 4 3 2 1 | 6 | 0 |
| i = 1 (= k - 1) | 4 3 2 1 | 5 | 5 |

`retVal` is 5, the 2nd largest.

### Holes to patch

**Copying the last problem's shape.**
He started from the #347 code: a `HashMap` and a comparator that reads counts, written as `get(a) - get(a)`.
There is nothing to count here; the heap holds the numbers directly.
The comparator `get(a) - get(a)` is also always 0, so the heap has no order at all.
Rule: before reusing a shape, ask what the heap holds this time (#347: keys by count; #215: the numbers).

**Off-by-one on the poll loop.**
He polled with `for (int i = 0; i <= k; i++)`, which runs k + 1 times.
Breaks on `[1, 2]`, `k = 2`: the third `poll()` on an empty heap returns `null`, and unboxing it into an `int` throws `NullPointerException`.
Rule: to take k items, loop `i < k`.

**Heap cost for the whole solution.**
At the gate he said time O(log n) and space O(n).
O(log n) is one `offer`, not n of them.
All n numbers into a max-heap then k polls (his version) is O(n log n) time and O(n) space, which is close to sorting, the thing the problem asks you to avoid.
Rule: n offers × log(heap size); cap the heap at k to get O(n log k) and O(k).

### Memorize this

1. Max-heap with `Collections.reverseOrder()`.
2. Offer every number.
3. Poll `k` times with `i < k`.
4. The last poll is the answer.
5. O(n log n) time, O(n) space.
6. Better: min-heap capped at k is O(n log k) / O(k); quickselect is O(n) on average.

```java
public int findKthLargest(int[] nums, int k) {
    if(nums.length==0||k>nums.length) return 0;
    if(nums.length==1) return nums[0];
    int retVal=0;
    PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
    for(int x:nums){
        max.offer(x);
    }

    for(int i=0;i<k;i++){
        int tmp=max.poll();
        if(i==k-1){
            retVal=tmp;
            System.out.println(retVal);
        }
    }
    return retVal;
}
```

Say in the interview: I put every number in a max-heap and poll k times, so the kth poll is the kth largest; to get n log k I would keep a min-heap of size k instead.

### How it went

Overtime: at the bell 5 of 6 were green, and the coach fixed the loop from `i <= k` to `i < k` at his request.
Early time went to the copied #347 map shape before he dropped the map.
His version is the heap-everything O(n log n) one, not the capped min-heap.

---

## LC 56 Merge Intervals — overtime

https://leetcode.com/problems/merge-intervals/

### The problem

You get a list of `[start, end]` pairs.
Merge every pair that overlaps and return what is left.
`[[1,3],[2,6],[8,10],[15,18]]` gives `[[1,6],[8,10],[15,18]]`.
Touching pairs count as overlapping: `[[1,4],[4,5]]` gives `[[1,5]]`.

### The idea

Pattern: intervals (sort by start, then one walk).
Once the pairs are sorted by start, anything that overlaps the last merged pair must come right after it.
So you only ever look at one thing while scanning: the **last kept** pair in the result list.
Time O(n log n) because of the sort; the walk after it is O(n).
Extra space O(n) for the result list.

### How to solve it

1. Sort by start: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]))`.
2. Walk `i` over the sorted pairs; if `list` is empty, add the pair.
3. Otherwise look at the last kept one, `tmpPair = list.get(list.size()-1)`.
4. If `tmpPair[1] >= pair[0]` (next start ≤ last end), they overlap.
5. On overlap, build `newPair = [tmpPair[0], max(tmpPair[1], pair[1])]`, remove the last pair, and add `newPair`.
6. Otherwise it is a new group: add the pair.
7. Return `list.toArray(new int[list.size()][])`.

Trace on `[[1,10],[2,3],[4,12],[15,18]]` (already sorted):

| pair | tmpPair | overlap? (tmpPair[1] >= pair[0]) | action | list |
|---|---|---|---|---|
| `[1,10]` | — | — | list empty, add | `[1,10]` |
| `[2,3]` | `[1,10]` | 10 ≥ 2 yes | replace last with `[1, max(10, 3)]` | `[1,10]` |
| `[4,12]` | `[1,10]` | 10 ≥ 4 yes | replace last with `[1, max(10, 12)]` | `[1,12]` |
| `[15,18]` | `[1,12]` | 12 ≥ 15 no | add | `[1,12]`, `[15,18]` |

Row 2 is the whole trap: a pair can sit fully inside the last one.

### Holes to patch

**Sorting by start does not sort the ends.**
He thought sorting makes both start and end go up.
Sort orders only slot 0; the ends ride along.
Breaks on `[[1,10],[2,3]]`: taking the new end gives `[1,3]` and throws away 4..10.
Rule: merged end is `Math.max(last[1], pair[1])`, always.

**Overlap check on the wrong ends.**
He compared `pair[1] >= last[0]` (next end vs last start).
Breaks on `[[1,2],[3,4]]`: 4 ≥ 1 is true, so two separate pairs get merged.
Rule: overlap means **next start ≤ last end**.

**Which start the merge keeps.**
He took the new pair's start.
Breaks on `[[1,3],[2,6]]`: gives `[2,6]` and loses 1.
Rule: sorted by start, so the last kept start is already the smallest; keep it.

**Reaching for a `HashMap` when the shape feels vague.**
There is no key to look up here.
Overlap is about order on the number line, so the tool is sort, not a map.

**Updating the last pair.**
His code builds a `newPair`, removes the last pair, and adds `newPair` back.
That works, but `tmpPair` is the same array object that sits in the list.
`tmpPair[1] = Math.max(tmpPair[1], pair[1])` is enough; no new array, no remove and re-add.
His `newPair[1]=newPair[1] = Math.max(...)` assigns twice; one `=` is enough.

### Memorize this

1. Sort by start with `Integer.compare`.
2. Walk once; compare each pair only with the last kept pair.
3. Overlap when last end ≥ next start.
4. On overlap, replace the last pair with `[last start, max of the two ends]`.
5. Otherwise add the pair as a new group.
6. O(n log n) time, O(n) space.

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    List<int[]> list = new ArrayList<>();
    for(int i=0;i<intervals.length;i++){
        int[] pair=intervals[i];

        if(list.isEmpty()) list.add(pair);
        else{
            int[] tmpPair=list.get(list.size()-1);
            if(tmpPair[1]>=pair[0]) {
                int[] newPair= new int[2];
                newPair[0]=tmpPair[0];
                newPair[1]=newPair[1] = Math.max(tmpPair[1], pair[1]);
                list.remove(list.size()-1);
                list.add(newPair);
            }
            else{
                list.add(pair);
            }
        }
    }

    return list.toArray(new int[list.size()][]);
}
```

Say in the interview: after sorting by start, an overlap can only be with the last merged interval, so one pass that stretches its end with `max` or starts a new interval is enough.

### How it went

Time up on the 25-minute clock with 6 of 7 green.
The only thing left was the missing `max` on the merged end, the "sorted starts means sorted ends" hole.
After a break he added the `max` himself and got 7 of 7 in overtime.

---

## LC 228 Summary Ranges — overtime, coach-fixed

https://leetcode.com/problems/summary-ranges/

### The problem

You get a sorted array of unique ints.
Write each run of consecutive numbers as `"a->b"`, or `"a"` if the run is one number.
`[0,1,2,4,5,7]` gives `["0->2","4->5","7"]`.

### The idea

Pattern: one walk with a run start (intervals family: you are building `[start, end]` ranges).
A run keeps going while the next number is exactly current + 1.
A run ends in two cases: the next number jumps, **or there is no next number**.
His version keeps the open run in a `Stack`: `firstElement()` is the run's start and `lastElement()` its end.
The loop only looks at pairs `nums[i]`, `nums[i+1]`, so the last number is handled once after the loop.
Time O(n), because each index is visited once.
Extra space O(n) besides the output list, because one long run puts every number in the stack.

Keeping only an `int start` instead of the stack makes the extra space O(1).

### How to solve it

1. Guards: length 0 returns an empty list; length 1 returns that one number.
2. Walk `i` from 0 to `n - 2`, comparing `nums[i]` with `nums[i+1]`.
3. No jump (`nums[i]+1 == nums[i+1]`): push `nums[i]` onto the stack.
4. Jump and stack empty: `nums[i]` is a run of one; write it alone.
5. Jump and stack not empty: push `nums[i]`, write `first->last`, and start a new empty stack.
6. After the loop the last number is still open: if the stack is not empty, push it and write `first->last`; else write it alone.

Trace on `[0,1,2,4,5,7]`:

| i | nums[i] | next | jump? | stack after | output |
|---|---|---|---|---|---|
| 0 | 0 | 1 | no, push | [0] | |
| 1 | 1 | 2 | no, push | [0, 1] | |
| 2 | 2 | 4 | yes, stack not empty: push, write, reset | [] | `"0->2"` |
| 3 | 4 | 5 | no, push | [4] | |
| 4 | 5 | 7 | yes, stack not empty: push, write, reset | [] | `"4->5"` |
| after loop | 7 | none | stack empty | [] | `"7"` |

The after-loop row is the one his first version never had.

### Holes to patch

**The last run is never written.**
His loop ran to `length - 1` and wrote a run only when there was a gap after `nums[i]`.
The last number has no gap after it, so its run never closes.
Breaks on `[0,1,2]`: output is empty.
Rule: "end of array" counts as a gap.
Either put `i == n - 1 ||` in the close check, or close the open run once after the loop.
Same family as every "last group / last run" bug: after the loop, ask what is still open.

**`new ArrayList<>(nums[0])` is a capacity, not an element.**
The `int` constructor sets the starting size of the backing array; the list is still empty.
Rule: `new ArrayList<>(List.of(x))`, or make the list and `add(x)`.

**Too much state for a run.**
He kept the run in a `Stack` and read `firstElement()` / `lastElement()`.
The last element is always `nums[i]` at close time, so only the start is needed.
One `int start` removes the stack and most of the branches.

**Special case for length 1.**
Only needed because the loop skipped the last index.
With the close check including `i == n - 1`, length 1 and length 0 fall out of the normal loop.

### Memorize this

1. Guards for length 0 and 1.
2. Loop `i` to `n - 2`; no jump → push `nums[i]`.
3. Jump with an empty stack → write `nums[i]` alone.
4. Jump with a run open → push `nums[i]`, write `first->last`, new stack.
5. After the loop, close the last number: push and write the run, or write it alone.
6. O(n) time, O(n) extra for the stack.

```java
public List<String> summaryRanges(int[] nums) {
    if(nums.length==0) return new ArrayList<>();
    if(nums.length==1) return new ArrayList<>(List.of(String.valueOf(nums[0])));
    ArrayList<String> ret=new ArrayList<>();
    Stack<Integer> tmpStack=new Stack<>();
    for(int i=0;i<nums.length-1;i++){
        if(nums[i]+1!=nums[i+1]&&tmpStack.isEmpty()) ret.add(String.valueOf(nums[i]));
        if(nums[i]+1!=nums[i+1]&&!tmpStack.isEmpty()) {
            tmpStack.push(nums[i]);
            ret.add(tmpStack.firstElement()+"->"+tmpStack.lastElement());
            tmpStack=new Stack<>();
        }
        if (nums[i]+1==nums[i+1]){
            tmpStack.push(nums[i]);
        }
    }

    if(!tmpStack.isEmpty()){
        tmpStack.push(nums[nums.length-1]);
        ret.add(tmpStack.firstElement()+"->"+tmpStack.lastElement());
    }
    else{
        ret.add(String.valueOf(nums[nums.length-1]));
    }

    return ret;
}
```

Say in the interview: I walk once and keep the open run, extend it while the next number is one bigger, and close it on a jump or at the end of the array.

### How it went

Time up on the 15-minute clock with 1 of 7 green (only the empty test).
The hole that cost the clock was the last run never closing, because the loop stopped before the last index.
The coach fixed it at his request, keeping his stack: after the loop, close the open run with the last number; then all 7 were green (overtime, coach-fixed).

---

## LC 33 Search in Rotated Sorted Array — on-time, coach gave the fix

https://leetcode.com/problems/search-in-rotated-sorted-array/

### The problem

A sorted array of unique ints was cut at an unknown point and the two pieces swapped.
`[0,1,2,4,5,6,7]` becomes `[4,5,6,7,0,1,2]`.
Return the index of `target`, or -1 if it is not there, in O(log n).
Target 0 gives 4.
Target 3 gives -1.

### The idea

Pattern: binary search.
The array is not fully sorted, so "is `nums[mid]` bigger than target?" does not tell you which way to go.
What always holds: cut the range at any `mid` and **one of the two halves is fully sorted** (the cut can only be on one side).
A range check ("is target between the two ends?") is only trustworthy on the sorted half.
So each step asks two questions: which half is sorted, and is the target inside it.
Time O(log n): every step throws away half the range.
Extra space O(1): only `low`, `high`, `mid`.

### How to solve it

1. Null or empty: return -1; then `low = 0`, `high = n - 1`, loop while `low <= high`.
2. `mid = low + (high - low) / 2`; if `nums[mid] == target`, return `mid`.
3. If `nums[low] <= nums[mid]`, the left half is sorted.
   If target is in `[nums[low], nums[mid])`, go left (`high = mid - 1`), else go right (`low = mid + 1`).
4. Otherwise the right half is sorted.
   If target is in `(nums[mid], nums[high]]`, go right, else go left.
5. Loop ends: return -1.

Trace on `[4,5,6,7,0,1,2]`, target 0:

| low | high | mid (value) | sorted half | target inside it? | move |
|---|---|---|---|---|---|
| 0 | 6 | 3 (7) | left, 4..7 | 0 in [4, 7)? no | low = 4 |
| 4 | 6 | 5 (1) | left, 0..1 | 0 in [0, 1)? yes | high = 4 |
| 4 | 4 | 4 (0) | — | found | return 4 |

### Holes to patch

**Plain sorted-array binary search on a rotated array.**
His first version went left whenever `nums[mid] > target`.
Breaks on `[4,5,6,7,0,1,2]`, target 0: `mid` is 7, 7 > 0, so it goes left, but 0 is on the right; it returns -1.
Rule: first find the sorted half, then range-check the target on that half only.

**Thinking the tests were wrong.**
Some tests check the same array twice (target at index 0 and at the last index).
Returning a fixed number always fails one of the two, so "expected 0 / expected -1" flipping means the logic, not the test.

**Special case for length 2.**
He added an `if (nums.length == 2)` block.
Not needed: the general loop already handles 1 and 2 elements.
Special cases for small sizes usually hide a missing rule in the loop.

**`<=` in `nums[low] <= nums[mid]`.**
When `low == mid` (two elements left), the left half is one element and counts as sorted.
With `<` instead, `[3,1]` target 1 checks the wrong half.

### Memorize this

1. `low`, `high`, loop while `low <= high`, `mid` in the middle.
2. Hit: return `mid`.
3. `nums[low] <= nums[mid]` means the left half is sorted, else the right is.
4. Target inside the sorted half: go there.
5. Otherwise go to the other half.
6. O(log n) time, O(1) space.

```java
public int search(int[] nums, int target) {
    if (nums==null||nums.length==0) return -1;

    int low=0,high=nums.length-1;
    while(low<=high){
        int mid = low + (high - low) / 2;
        if(nums[mid]==target) return mid;
        if (nums[low] <= nums[mid]) {
            if (target >= nums[low] && target < nums[mid]) high = mid - 1;
            else low = mid + 1;
        } else {
            if (target > nums[mid] && target <= nums[high]) low = mid + 1;
            else high = mid - 1;
        }}
    return -1;
}
```

Say in the interview: at any mid one half of a rotated sorted array is still sorted, so I check whether the target falls inside that sorted half and discard the other half, which keeps it O(log n).

### How it went

Named binary search, O(log n) and O(1) right; the "why" needed the sorted-half idea added.
Coded a plain binary search; 6 of 9 green, the 3 rotated cases failed.
Asked for the direction logic as code; all 9 green at about 16 minutes (on-time, coach gave the fix).
Said binary search feels dry; framing that helped: one loop, only the "higher or lower" question changes per problem.

---

## LC 153 Find Minimum in Rotated Sorted Array — on-time

https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/

### The problem

Same kind of array as #33: sorted, unique, cut and swapped somewhere.
Return the smallest number in O(log n).
`[3,4,5,1,2]` gives 1.
`[11,13,15,17]` (rotated all the way round) gives 11.

### The idea

Pattern: binary search.
The minimum sits right after the one place where the order drops.
Compare `nums[mid]` with `nums[high]`:
If `nums[mid] > nums[high]`, the drop is between `mid` and `high`, and `mid` is not the minimum (`nums[high]` is already smaller), so go right past `mid`.
Otherwise `mid..high` goes up, so the minimum is `mid` or left of it; keep `mid` in the range.
Time O(log n): half the range is gone each step.
Extra space O(1).

### How to solve it

1. Empty: return 0; then `low = 0`, `high = n - 1`.
2. Loop while `low < high` (stop when they meet, not after).
3. `mid = low + (high - low) / 2`.
4. `nums[mid] > nums[high]`: `low = mid + 1`.
5. Else: `high = mid`.
6. Return `nums[low]`.

Trace on `[4,5,6,7,0,1,2]`:

| low | high | mid | nums[mid] | nums[high] | mid bigger? | move |
|---|---|---|---|---|---|---|
| 0 | 6 | 3 | 7 | 2 | yes | low = 4 |
| 4 | 6 | 5 | 1 | 2 | no | high = 5 |
| 4 | 5 | 4 | 0 | 1 | no | high = 4 |
| 4 | 4 | — | — | — | stop | return 0 |

### Holes to patch

**Index vs value.**
He read `high` as 2 (the last value) instead of 6 (the last index), and `low = mid + 1` as 7 + 1.
`low`, `high`, `mid` are always positions; values only come through `nums[...]`.
Rule: say "index" or "value" out loud for every number in a trace.

**What `mid = low + (high - low) / 2` means.**
He did not get the formula, which made every binary search feel shaky.
It is the middle: start at `low`, walk half the distance to `high`.
Same as `(low + high) / 2`, but `low + high` can overflow past `Integer.MAX_VALUE`.
Integer division rounds down, so with two elements left `mid` is the left one.

**Classic trap: `high = mid - 1`.**
When `nums[mid] <= nums[high]`, `mid` itself may be the minimum.
Breaks on `[4,1,2]`: `mid = 1` (value 1), 1 < 2, `high = mid - 1 = 0` throws the answer away and returns 4.
Rule: `high = mid` (keep it), `low = mid + 1` (it is proven not the answer).

**Classic trap: `while (low <= high)` here.**
With `high = mid`, `low == high` never moves, so `<=` loops forever.
Rule: when one side is `high = mid`, loop on `low < high` and return `nums[low]`.

**Classic trap: comparing with `nums[low]`.**
On an array that is not rotated, `nums[mid] > nums[low]` is always true and sends you right, away from the minimum at index 0.
`nums[high]` has no such case.

### Memorize this

1. `low = 0`, `high = n - 1`, loop `while (low < high)`.
2. `mid = low + (high - low) / 2`.
3. `nums[mid] > nums[high]`: the drop is right, `low = mid + 1`.
4. Else: `high = mid` (mid may be the answer).
5. Return `nums[low]`.
6. O(log n) time, O(1) space.

```java
public int findMin(int[] nums) {
    if (nums.length==0) return 0;
    int low=0;
    int high=nums.length-1;
    while (low<high){
        int mid =low+(high-low) /2 ;
        if(nums[mid]>nums[high]) {
            low=mid+1;
        }
        else {
            high=mid;
        }
    }

    return  nums[low];
}
```

Say in the interview: I compare mid with the right end; if mid is bigger the drop and the minimum are to the right, otherwise mid to the end is sorted so the minimum is mid or left of it, and I shrink until low meets high.

### How it went

Named binary search; time and space same as #33.
Before coding, the `mid` formula and index vs value had to be cleared up with a single-array table.
Coded it himself from the six Memorize lines; all 7 green in about 8 minutes (on-time).
Short step lists worked better than paragraphs today.

---

## LC 74 Search a 2D Matrix — green, no clock record

https://leetcode.com/problems/search-a-2d-matrix/

### The problem

You get an `m × n` matrix.
Each row is sorted, and each row starts bigger than the row above ends.
Return `true` if `target` is in the matrix, in O(log(m × n)).

```
 1  3  5  7
10 11 16 20
23 30 34 60
target 3 -> true, target 13 -> false
```

### The idea

Pattern: binary search over the matrix as one flat sorted array.
Read row by row, the matrix is one sorted list of `m × n` cells.
Flat index `i` lives at row `i / cols`, column `i % cols`.
Run normal binary search on `0 .. m × n - 1` and turn `mid` into a cell each round.
Time O(log(m × n)): one halving search over all cells.
Extra space O(1).

### How to solve it

1. If the matrix is empty, return `false`.
2. `low = 0`, `high = rows × cols - 1`.
3. While `low <= high`: `mid`, read `matrix[mid / cols][mid % cols]`.
4. Equal → `true`.
5. Smaller than target → `low = mid + 1`, else `high = mid - 1`.
6. Loop ends → `false`.

Trace on the example, target 3, `cols = 4`:

| low | high | mid | cell | value | move |
|---|---|---|---|---|---|
| 0 | 11 | 5 | (1,1) | 11 | too big, high = 4 |
| 0 | 4 | 2 | (0,2) | 5 | too big, high = 1 |
| 0 | 1 | 0 | (0,0) | 1 | too small, low = 1 |
| 1 | 1 | 1 | (0,1) | 3 | found |

### Holes to patch

**The extra check on `low` does nothing.**
His condition also checks `matrix[low / cols][low % cols] < target`.
`low <= mid` and the flat list is sorted, so when the `mid` value is below target, the `low` value is too.
Rule: compare only the `mid` value, like plain binary search.

**Row is `/ cols`, column is `% cols`.**
Using `rows` instead of `cols` breaks on a non-square matrix: `3 × 4`, flat index 5 must be `(1,1)`, not `(1,2)`.

### Memorize this

1. Empty → `false`.
2. `low = 0`, `high = rows × cols - 1`, `cols = matrix[0].length`.
3. While `low <= high`: `mid`, value at `[mid / cols][mid % cols]`.
4. Equal → `true`.
5. Below target → `low = mid + 1`, else `high = mid - 1`.
6. Return `false`.

```java
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
```

Say in the interview: the rows chain into one sorted list, so I binary search flat indexes 0 to m·n−1 and map each mid to row mid / cols and column mid % cols, O(log(m·n)) time and O(1) space.

### How it went

Week 7 Day 4 was cut short for personal reasons, so there is no gate or clock record.
His code is on disk and all 9 tests are green.

---

## LC 452 Minimum Number of Arrows to Burst Balloons — time up, then overtime, coach-fixed

https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/

### The problem

Each pair `[xStart, xEnd]` is one balloon, a segment on the x-axis.
An arrow is one point `x`.
It pops every balloon with `xStart <= x <= xEnd` (ends count).
Return the fewest arrows so that every balloon is popped.
One arrow does not have to pop them all; you count how many you need in total.

`[[10,16],[2,8],[1,6],[7,12]]` gives 2.
An arrow at 6 pops `[1,6]` and `[2,8]`.
An arrow at 11 pops `[7,12]` and `[10,16]`.

```text
x:      1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
[1,6]   |--------------|
[2,8]      |-----------------|
[7,12]                    |--------------|
[10,16]                            |-----------------|
              arrow at 6 ^          ^ arrow at 11
```

### The idea

Pattern: intervals, sorted by **end**, then one walk.
Take the balloon that ends first.
Some arrow has to hit it, and the furthest-right spot that still hits it is its end.
Shooting there pops it and as many later balloons as possible.
Every balloon that starts at or before that end is popped by the same arrow.
The first balloon that starts after it needs a new arrow, fired at its own end.
What you keep while scanning: the position of the last arrow.
His version keeps a list `ret` of the balloons that got a new arrow; the last one's end (`tmp[1]`) is where the last arrow is.
Time O(n log n) for the sort; the walk is O(n).
Extra space O(n): the `ret` list can hold every balloon, and Java's TimSort on an `int[][]` can use up to n extra slots.

### How to solve it

1. Guards: empty returns 0; one balloon returns 1.
2. Sort by end: `Integer.compare(a[1], b[1])`.
3. Walk every balloon `x`; if `ret` is empty, add `x` (first arrow at its end).
4. Else look at the last arrow balloon, `tmp = ret.get(ret.size()-1)`.
5. `x[0] <= tmp[1]`: already popped, do nothing.
6. Else: new arrow at this balloon's end, `ret.add(x)`.
7. Return `ret.size()`.

Trace on the example, sorted by end: `[1,6] [2,8] [7,12] [10,16]`.

| x | tmp | x[0] <= tmp[1]? | ret | ret.size() |
|---|---|---|---|---|
| `[1,6]` | — | ret empty, add | `[1,6]` | 1 |
| `[2,8]` | `[1,6]` | 2 <= 6, popped | `[1,6]` | 1 |
| `[7,12]` | `[1,6]` | 7 > 6, new arrow | `[1,6] [7,12]` | 2 |
| `[10,16]` | `[7,12]` | 10 <= 12, popped | `[1,6] [7,12]` | 2 |

### Holes to patch

**Did not get the question.**
He read "fewest arrows to burst all" as "one arrow pops all of them," and did not see that each pair is one balloon.
Rule: each pair is one segment, an arrow is one point, the answer is how many points you need.
Draw the number line before coding.

**Sorted by start instead of end.**
Sorting by start breaks on nested balloons: `[[1,10],[2,3],[4,5]]`.
Sorted by start, `[1,10]` comes first and its end (10) looks like a good arrow spot, but `[2,3]` and `[4,5]` do not overlap each other, so one arrow cannot pop both.
Sorted by end, the order is `[2,3] [4,5] [1,10]`, giving an arrow at 3 and an arrow at 5, which is 2.
Rule: when the question is "fewest points that hit every interval," sort by end.

```java
Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
```

**Strict `<` on a closed range.**
He used `x[0] < tmp[1]`.
Breaks on `[[1,2],[2,3],[3,4],[4,5]]`: the arrow at 2 also pops `[2,3]`, but `2 < 2` is false, so it fired again (returned 4, answer 2).
Rule: ends count, so the check is `start <= arrow`.

**Kept adding balloons the arrow already popped.**
His list grew in both branches: popped meant remove-then-add, not popped meant add.
So `ret.size()` was close to the number of balloons, not arrows.
Rule: the list (or a counter) means "arrows fired."
A popped balloon changes nothing.
A counter plus an `int arrow` does the same job as the list with no extra space besides the sort.

**Mixed up merge (#56) and arrows (#452).**
#56 merges overlapping intervals and extends the end.
#452 counts points, and the arrow never moves once fired.
Same family, different thing to keep.

**Classic trap: `a[1] - b[1]` in the comparator.**
With values near `Integer.MIN_VALUE` and `Integer.MAX_VALUE`, the subtraction overflows and sorts in the wrong order.
Rule: always `Integer.compare`.

### Memorize this

1. Empty: return 0; one balloon: return 1.
2. Sort by end with `Integer.compare(a[1], b[1])`.
3. First balloon goes in `ret` (first arrow at its end).
4. For each next balloon: `x[0] <= tmp[1]` (last arrow balloon's end) means popped, do nothing.
5. Else add it to `ret` (new arrow at its end).
6. Return `ret.size()`.
7. O(n log n) time, O(n) space for the list and the sort.

```java
public int findMinArrowShots(int[][] points) {
    if(points.length==0) return 0;
    if(points.length==1) return 1;

    ArrayList<int[]> ret=new ArrayList<>();
    Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
    for(int[] x:points){
        System.out.println("X is "+x[0]+"---"+x[1]);
        if(ret.isEmpty()) ret.add(x);

        else{
            int[] tmp=ret.get(ret.size()-1);
            System.out.println("TMP IS "+tmp[0]+"---"+tmp[1]);
            if(x[0]<=tmp[1]){
                // tmp's arrow already pops x, keep nothing
            }
            else{
                ret.add(x);
            }
        }
    }
    return ret.size();
}
```

Say in the interview: I sort the balloons by end and shoot each arrow at the end of the first balloon it has to pop, because that point covers the most later balloons; any balloon that starts at or before the last arrow is already popped, otherwise I fire a new one.

### How it went

Dropped #162 Find Peak Element (more binary search after three days) for this one.
Named intervals and sorting, O(n log n) and O(n) right.
The question needed a number-line picture before it made sense, which ate part of the clock.
Coded sort by start with a merge-style list; 4 of 7 green at the bell (time up).
After the bell he asked for code; the coach patched his own loop (sort by end, `<=`, add only on a new arrow); all 7 green (overtime, coach-fixed).

---

## LC 57 Insert Interval — no gate, no clock, coach-fixed

https://leetcode.com/problems/insert-interval/

### The problem

You get ranges `[start, end]`, already sorted by start, with no overlaps.
Put one new range in so the list stays sorted with no overlaps.
Any ranges the new one touches or overlaps join into one.

`[[1,3],[6,9]]` plus `[2,5]` gives `[[1,5],[6,9]]`.
`[[1,2],[3,5],[6,7],[8,10],[12,16]]` plus `[4,8]` gives `[[1,2],[3,10],[12,16]]`.

### The idea

Pattern: intervals, one walk, no sort needed (the input is already sorted).
Every existing range falls into exactly one of three groups compared to the new range:
fully before it (`x.end < new.start`), fully after it (`x.start > new.end`), or overlapping.
Before: copy it.
Overlapping: grow the new range to `[min(starts), max(ends)]` and do not copy `x`.
After: the new range is final, so add it once, then copy `x`.
What you keep while scanning: the new range as it grows, and whether it was already placed.
Time O(n): one pass.
Extra space O(n) for the output list.

### How to solve it

1. Empty result list, `placed = false`.
2. For each range `x`:
3. `x[1] < new[0]`: add `x`.
4. `x[0] > new[1]`: if not placed, add `new` and mark placed; then add `x`.
5. Else (overlap): build `tmp = [min(x[0], new[0]), max(x[1], new[1])]` and set `newInterval = tmp`.
6. After the loop: if not placed, add `new`.
7. Copy the list into `int[][] retarr` with a loop.

Trace on `[[1,2],[3,5],[6,7],[8,10],[12,16]]` with new `[4,8]`:

| x | group | new | result so far |
|---|---|---|---|
| `[1,2]` | before (2 < 4) | `[4,8]` | `[1,2]` |
| `[3,5]` | overlap | `[3,8]` | `[1,2]` |
| `[6,7]` | overlap | `[3,8]` | `[1,2]` |
| `[8,10]` | overlap (8 <= 8) | `[3,10]` | `[1,2]` |
| `[12,16]` | after (12 > 10), place new | `[3,10]` | `[1,2] [3,10] [12,16]` |

### Holes to patch

**Empty input returned empty.**
`intervals = []`, new `[5,7]` must give `[[5,7]]`, not `[]`.
Rule: the new range always ends up in the answer.

**`if (ret.isEmpty()) ret.add(x);` without `else`.**
The first range was added, then the code below added it again.
Rule: one range, one branch; use `if / else if / else`.

**Overlap check only caught one shape.**
He tested `new.start <= x.end && new.end >= x.end`, which misses a new range sitting inside `x`.
Breaks on `[[1,10]]` plus `[3,4]`: returned two ranges instead of `[[1,10]]`.
Rule: two ranges overlap when neither ends before the other starts: `new.start <= x.end && new.end >= x.start`.

**Merged end was always `new.end`.**
Breaks on the same `[1,10]` plus `[3,4]`: the end must stay 10.
Rule: merged range is `[min(starts), max(ends)]`.

```java
tmp[0] = Math.min(x[0], newInterval[0]);
tmp[1] = Math.max(x[1], newInterval[1]);
```

**`ret.remove(ret.size() - 1)` on merge.**
It deletes whatever was last, even a range that had nothing to do with the new one.
Rule: do not put the new range in the list until it stops growing (the first "after" range, or the end of the loop).

**New range never added when nothing overlapped it.**
Breaks on `[[1,2],[7,8]]` plus `[4,5]`: the answer was missing `[4,5]`.
Rule: a `placed` flag; add before the first "after" range, or after the loop.

### Memorize this

1. Result list, `placed = false`.
2. `x.end < new.start`: copy `x`.
3. `x.start > new.end`: place `new` once, then copy `x`.
4. Else: `tmp = [min starts, max ends]`, `newInterval = tmp`.
5. After the loop, place `new` if not placed.
6. Copy the list into an `int[][]`.
7. O(n) time, O(n) output.

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    ArrayList <int[]> ret=new ArrayList<>();
    boolean placed=false;

    for(int[] x:intervals){
        if(x[1]<newInterval[0]){
            ret.add(x);
        }
        else if(x[0]>newInterval[1]){
            if(!placed){
                ret.add(newInterval);
                placed=true;
            }
            ret.add(x);
        }
        else{
            int [] tmp=new int[2];
            tmp[0]=Math.min(x[0],newInterval[0]);
            tmp[1]=Math.max(x[1],newInterval[1]);
            newInterval=tmp;
        }
    }
    if(!placed) ret.add(newInterval);

    int[][] retarr=new int[ret.size()][];
    for(int i=0;i<ret.size();i++){
        retarr[i]=ret.get(i);
    }
    return retarr;
}
```

Say in the interview: the list is already sorted, so I walk it once; ranges that end before the new one are copied, ranges that overlap are folded into it with min start and max end, and the first range that starts after it is where I drop the merged range in.

### How it went

Skipped the gate and the clock and went straight to code (about 45 minutes).
2 of 9 green: empty input, a double add on the first range, a one-shape overlap check, and the new range missing when nothing overlapped.
Asked the coach to fix his code; the patch kept his list, `tmp`, and `newInterval = tmp`; all 9 green (coach-fixed, untimed).
