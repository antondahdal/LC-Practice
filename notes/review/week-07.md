# Week 07 — review sheet

Week 7 covered three families: heap (top k / kth largest), binary search (sorted array, O(log n)), and intervals (sort by start, then one walk).

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 347 | Top K Frequent Elements | Heap (min-heap of size k over counts) | O(n log k) / O(n) | on-time |
| 35 | Search Insert Position | Binary search | O(log n) / O(1) | time up, coach-fixed |
| 215 | Kth Largest Element in an Array | Heap (min-heap of size k) | O(n log k) / O(k) | overtime, coach-fixed |
| 56 | Merge Intervals | Intervals (sort by start, one walk) | O(n log n) / O(n) | overtime |
| 228 | Summary Ranges | Intervals (one walk with a run start) | O(n) / O(1) extra | overtime, coach-fixed |
| 33 | Search in Rotated Sorted Array | Binary search (find the sorted half) | O(log n) / O(1) | on-time, coach gave the fix |
| 153 | Find Minimum in Rotated Sorted Array | Binary search (mid vs high) | O(log n) / O(1) | on-time |

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
Keep a **min**-heap of size `k`: the top is the weakest of the current top `k`, so when the heap grows past `k` you poll it out.
Time O(n log k): counting is O(n), and each of at most n distinct keys costs one `offer` into a heap that never holds more than k + 1 keys.
Extra space O(n): the map can hold n distinct keys, and the heap holds k.

Bucket sort by count (an array of lists where the index is the count, walked from the top) is O(n) and also beats the follow-up.

### How to solve it

1. Count: `count.merge(x, 1, Integer::sum)` for every `x`.
2. Make a min-heap of keys ordered by `count.get(key)`.
3. For each key: `offer` it, and if the size is now `k + 1`, `poll` once.
4. The heap now holds exactly the `k` most frequent keys.
5. Poll them into the result array.

Trace on `[1,1,1,2,2,3]`, `k = 2`, counts `{1:3, 2:2, 3:1}`:

| offer key | its count | heap after offer (smallest count on top) | size > k? | heap after |
|---|---|---|---|---|
| 1 | 3 | 1(3) | no | 1(3) |
| 2 | 2 | 2(2), 1(3) | no | 2(2), 1(3) |
| 3 | 1 | 3(1), 2(2), 1(3) | yes, poll 3 | 2(2), 1(3) |

Left in the heap: keys 2 and 1, the answer.

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
new PriorityQueue<Integer>((a, b) -> count.get(a) - count.get(b)); // lowest count on top
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
One line does it.

```java
map.put(x, map.getOrDefault(x, 0) + 1);
```

### Memorize this

1. Count values in a `HashMap`.
2. Min-heap of keys, comparator = count from the map.
3. Offer each key; if size > k, poll.
4. The heap is the answer; poll it into an array.
5. O(n log k) time, O(n) space.
6. Follow-up O(n): bucket by count, walk buckets from the top.

```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int x : nums) count.merge(x, 1, Integer::sum);

    PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.comparingInt(count::get));
    for (int key : count.keySet()) {
        heap.offer(key);
        if (heap.size() > k) heap.poll();
    }

    int[] res = new int[k];
    for (int i = k - 1; i >= 0; i--) res[i] = heap.poll();
    return res;
}
```

Say in the interview: I count with a hash map, then keep a min-heap of size k keyed by count, so each key costs log k and the heap ends up holding exactly the k most frequent values.

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
You keep two indices, `lo` and `hi`, and the live range is `lo..hi` with both ends included.
Every round keeps a promise: everything left of `lo` is smaller than the target, and everything right of `hi` is bigger.
When the loop ends, `lo = hi + 1`, so `lo` is the first index bigger than the target, which is exactly the insert slot.
Time O(log n), because each round throws away half the range.
Extra space O(1), because it is only three `int`s.

### How to solve it

1. `lo = 0`, `hi = n - 1`.
2. While `lo <= hi`, compute `mid = lo + (hi - lo) / 2`.
3. One decision per round, as one `if / else if / else` chain.
4. If `nums[mid] == target`, return `mid`.
5. If `nums[mid] < target`, the answer is right of `mid`: `lo = mid + 1`.
6. Otherwise it is left of `mid`: `hi = mid - 1`.
7. After the loop, return `lo`.

Trace on `[-5, -3]`, target -4 (the case his sign patch got wrong):

| lo | hi | mid | nums[mid] | decision |
|---|---|---|---|---|
| 0 | 1 | 0 | -5 | -5 < -4, so `lo = 1` |
| 1 | 1 | 1 | -3 | -3 > -4, so `hi = 0` |
| 1 | 0 | — | — | `lo > hi`, return `lo` = 1 |

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
if (nums[mid] == target) return mid;
else if (nums[mid] < target) lo = mid + 1;
else hi = mid - 1;
```

**Patching the answer to fit a failing test.**
At the bell he returned `mid + 1` or `mid + 2` depending on the sign of the target, to make one test pass.
Breaks on `[-5, -3]`, target -4: it returned 2, the right answer is 1.
Rule: when one test fails, trace it and find the bug; never shape the return value around the test.

**Special cases that `return lo` already covers.**
His final code still checks "target past the end" before the loop and returns -1 for an empty array.
On target 7 in `[1,3,5,6]`, `lo` walks to 4 on its own, and on an empty array `lo` stays 0, which is the correct insert slot.
Rule: trust the invariant; `return lo` handles before-the-start, past-the-end, and empty.

### Memorize this

1. `lo = 0`, `hi = n - 1`, loop while `lo <= hi`.
2. `mid = lo + (hi - lo) / 2` (no overflow).
3. One chain: equal → return, less → `lo = mid + 1`, else → `hi = mid - 1`.
4. `mid` was checked, so it always leaves the range (`± 1`).
5. Not found → return `lo`.

```java
public int searchInsert(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return lo;
}
```

Say in the interview: it is a standard binary search, and when the target is missing the loop ends with `lo` at the first element bigger than the target, which is the insert position.

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
Keep a **min**-heap of the `k` biggest numbers seen so far.
When it grows past `k`, poll: that throws out the smallest, the weakest of the strong ones.
At the end the top of the heap is the smallest of the `k` biggest, which is the kth largest.
Time O(n log k), because each of the n numbers costs one `offer` (and at most one `poll`) on a heap of size k + 1.
Extra space O(k), because the heap never holds more than k + 1 numbers.

### How to solve it

1. Make a min-heap (`new PriorityQueue<>()`, the default).
2. For each number: `offer` it.
3. If the size is now `k + 1`, `poll` once.
4. Return `peek()`.

Trace on `[3,2,1,5,6,4]`, `k = 2`:

| offer | heap after offer (smallest on top) | size > 2? | heap after |
|---|---|---|---|
| 3 | 3 | no | 3 |
| 2 | 2 3 | no | 2 3 |
| 1 | 1 2 3 | yes, poll 1 | 2 3 |
| 5 | 2 3 5 | yes, poll 2 | 3 5 |
| 6 | 3 5 6 | yes, poll 3 | 5 6 |
| 4 | 4 5 6 | yes, poll 4 | 5 6 |

`peek()` is 5, the 2nd largest.

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

1. Min-heap, default order.
2. Offer each number.
3. If size > k, poll (drops the smallest).
4. Return `peek()`.
5. O(n log k) time, O(k) space.
6. Quickselect is O(n) on average if they push further.

```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int x : nums) {
        heap.offer(x);
        if (heap.size() > k) heap.poll();
    }
    return heap.peek();
}
```

Say in the interview: I keep a min-heap of the k largest numbers, so the top is always the kth largest, and each number costs log k.

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
2. Put the first pair in a `List<int[]>`.
3. For every next pair, look at the last kept one (`list.get(list.size() - 1)`).
4. If next start ≤ last end, they overlap: last end becomes the **bigger** of the two ends.
5. Otherwise it is a new group: add the pair.
6. Return `list.toArray(new int[0][])`.

Trace on `[[1,10],[2,3],[4,12],[15,18]]` (already sorted):

| next pair | last kept | overlap? (next start ≤ last end) | action | result |
|---|---|---|---|---|
| `[1,10]` | — | — | add | `[1,10]` |
| `[2,3]` | `[1,10]` | 2 ≤ 10 yes | end = max(10, 3) = 10 | `[1,10]` |
| `[4,12]` | `[1,10]` | 4 ≤ 10 yes | end = max(10, 12) = 12 | `[1,12]` |
| `[15,18]` | `[1,12]` | 15 ≤ 12 no | add | `[1,12]`, `[15,18]` |

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
`last` is the same array object that sits in the list.
`last[1] = Math.max(last[1], pair[1])` is enough; no remove and re-add.

### Memorize this

1. Sort by start with `Integer.compare`.
2. Walk once; compare each pair only with the last kept pair.
3. Overlap when next start ≤ last end.
4. On overlap, stretch: last end = max of the two ends.
5. Otherwise add the pair as a new group.
6. O(n log n) time, O(n) space.

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    List<int[]> res = new ArrayList<>();
    for (int[] cur : intervals) {
        if (res.isEmpty() || res.get(res.size() - 1)[1] < cur[0]) {
            res.add(cur);
        } else {
            int[] last = res.get(res.size() - 1);
            last[1] = Math.max(last[1], cur[1]);
        }
    }
    return res.toArray(new int[0][]);
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
You only keep the start of the current run; the end is whatever `nums[i]` is when the run closes.
Time O(n), because each index is visited once.
Extra space O(1) besides the output list, because only `start` is kept.

### How to solve it

1. Walk `i` from 0 to `n - 1` (the whole array, including the last index).
2. When a run starts, remember `start = nums[i]`.
3. The run closes at `i` if `i == n - 1` or `nums[i + 1] != nums[i] + 1`.
4. On close, write `start` alone if `start == nums[i]`, else `start + "->" + nums[i]`.
5. The next index starts a new run.

Trace on `[0,1,2,4,5,7]`:

| i | nums[i] | next | run start | closes here? | output |
|---|---|---|---|---|---|
| 0 | 0 | 1 | 0 | no | |
| 1 | 1 | 2 | 0 | no | |
| 2 | 2 | 4 | 0 | yes, jump | `"0->2"` |
| 3 | 4 | 5 | 4 | no | |
| 4 | 5 | 7 | 4 | yes, jump | `"4->5"` |
| 5 | 7 | none | 7 | yes, end of array | `"7"` |

The last row is the one his code never reached.

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

1. For each `i`, remember `start = nums[i]`.
2. Move `i` forward while the next number is exactly `nums[i] + 1`.
3. Now `nums[i]` is the run's end (a jump or the end of the array).
4. Write `"start"` if start equals end, else `"start->end"`.
5. O(n) time, O(1) extra.

```java
public List<String> summaryRanges(int[] nums) {
    List<String> res = new ArrayList<>();
    int n = nums.length;
    for (int i = 0; i < n; i++) {
        int start = nums[i];
        while (i + 1 < n && nums[i + 1] == nums[i] + 1) i++;
        res.add(start == nums[i] ? String.valueOf(start) : start + "->" + nums[i]);
    }
    return res;
}
```

Say in the interview: I walk once, remember where the current run started, extend it while the next number is one bigger, and close it on a jump or at the end of the array.

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

1. `low = 0`, `high = n - 1`, loop while `low <= high`.
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
    int low = 0, high = nums.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) return mid;
        if (nums[low] <= nums[mid]) {
            if (target >= nums[low] && target < nums[mid]) high = mid - 1;
            else low = mid + 1;
        } else {
            if (target > nums[mid] && target <= nums[high]) low = mid + 1;
            else high = mid - 1;
        }
    }
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

1. `low = 0`, `high = n - 1`.
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
    int low = 0, high = nums.length - 1;
    while (low < high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] > nums[high]) low = mid + 1;
        else high = mid;
    }
    return nums[low];
}
```

Say in the interview: I compare mid with the right end; if mid is bigger the drop and the minimum are to the right, otherwise mid to the end is sorted so the minimum is mid or left of it, and I shrink until low meets high.

### How it went

Named binary search; time and space same as #33.
Before coding, the `mid` formula and index vs value had to be cleared up with a single-array table.
Coded it himself from the six Memorize lines; all 7 green in about 8 minutes (on-time).
Short step lists worked better than paragraphs today.
