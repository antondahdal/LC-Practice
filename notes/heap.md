# Week 7 topics — heap, binary search, intervals

Look-up sheet.
Anton asked for the heap cheat sheet 2026-09-28 (Week 7 Day 1, after #347).
Same day he did not remember what intervals and binary search mean, and did not get how `mid` works on #35.
2026-09-29 (Week 7 Day 2) he asked for all three in one file.

## Short version

### Heap

A box that always knows its best element.
Min-heap keeps the smallest on top, max-heap keeps the biggest.
In Java it is `PriorityQueue`.
You use it when the question says "top k", "kth largest", or "next most urgent".
Each `offer` or `poll` is O(log size), so n offers into a heap of size k is O(n log k).

### Binary search

The array is already sorted.
Look at the middle, decide which half the answer is in, throw the other half away.
Two indices `lo` and `hi` close in until they cross.
You use it when you see "sorted" plus "O(log n)", or a rotated sorted array.
Time O(log n), extra O(1).

### Intervals

The input is pairs `[start, end]`, like meetings or bookings.
The questions ask how the ranges touch: merge them, count rooms, find a gap, insert a new one.
Almost always you sort by start first, then walk once and compare each pair with the last one you kept.
Sort O(n log n) plus one walk O(n).

## How to tell the three apart

Read the input and the question first.

| You see | Tool |
|---|---|
| "top k", "kth largest", "k closest", "next most urgent" | Heap |
| Sorted array, "O(log n)", rotated sorted array | Binary search |
| Pairs `[start, end]`, meetings, bookings, time slots | Intervals |

None of them is a tree walk.
BFS and DFS visit every node, which is O(n).

---

## 1. Heap — keep the best few

### What it is

A heap is a box that always knows its top element.
Min-heap: the smallest is on top.
Max-heap: the biggest is on top.

In Java it is `PriorityQueue`.
There is no class called `Heap`.

Only the top is in order.
The rest is loosely arranged.
`System.out.println(pq)` and a for-each loop do **not** print in sorted order.
To get sorted order you `poll()` one at a time.

### How it works inside

It is a tree stored in an array.
Node at index `i` has kids at `2i + 1` and `2i + 2`.
Its parent is at `(i - 1) / 2`.
Every parent is smaller than its kids (min-heap).

Min-heap `[1, 3, 5, 7]`:

```
      1
     / \
    3   5
   /
  7
```

**Offer 2 (sift up).**
Put 2 at the end, index 4.
Parent is index 1, value 3.
3 > 2, swap.
Now 2 is at index 1, parent is index 0, value 1.
1 < 2, stop.

```
[1, 2, 5, 7, 3]

      1
     / \
    2   5
   / \
  7   3
```

**Poll (sift down).**
Take the top (1) out.
Move the last element (3) to the top: `[3, 2, 5, 7]`.
Kids of 3 are 2 and 5, the smaller is 2.
2 < 3, swap.
Now 3 is at index 1, its kid is 7.
7 > 3, stop.

```
[2, 3, 5, 7]
```

Each move walks one path from top to bottom or bottom to top.
The tree has about log n levels.
So `offer` and `poll` are O(log n).
`peek` just reads index 0, O(1).

### Cost per call (heap with s elements)

| Call | Does | Time |
|---|---|---|
| `offer(x)` / `add(x)` | insert | O(log s) |
| `poll()` | remove top (null if empty) | O(log s) |
| `peek()` | look at top (null if empty) | O(1) |
| `size()` / `isEmpty()` | count | O(1) |
| `addAll(c)` | c times `offer` | O(c log s) |
| `remove(x)` / `contains(x)` | search | O(s) |

### Cost of a whole solution

One `offer` is log(size).
You do it once per element.
n elements into a heap of size n → O(n log n).
n elements into a heap you keep at size k → O(n log k).

Saying "O(log n)" for the whole solution is the slip from #347 and #215.

### Comparator rule

The comparator returns negative → `a` goes on top first.

`a - b` → smallest on top (min-heap).

`b - a` → biggest on top (max-heap).

Memory trick: the one written first wins when it is small.

### Short cuts

```java
// min-heap (default)
new PriorityQueue<Integer>();

// max-heap
new PriorityQueue<Integer>(Collections.reverseOrder());
new PriorityQueue<Integer>((a, b) -> b - a);

// keys ordered by their value in a map
new PriorityQueue<Integer>((a, b) -> map.get(a) - map.get(b)); // lowest count on top
new PriorityQueue<Integer>((a, b) -> map.get(b) - map.get(a)); // highest count on top

// map entries
new PriorityQueue<Map.Entry<Integer, Integer>>((a, b) -> a.getValue() - b.getValue());
// then: pq.addAll(map.entrySet());  entry.getKey() / entry.getValue()

// int[] pairs, by first slot, tie by second
new PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

// strings by length
new PriorityQueue<String>((a, b) -> a.length() - b.length());

// safe version (no overflow)
new PriorityQueue<Integer>((a, b) -> Integer.compare(a, b));
new PriorityQueue<Integer>(Comparator.comparingInt(map::get));
```

### Traps

`b - a` can overflow when the numbers are huge or negative (for example `Integer.MIN_VALUE`).
Counts are small, so subtraction is fine on #347.
For raw values use `Integer.compare(a, b)`.

If the comparator reads a map, fill the map **first**, then offer.
Changing a count after the key is inside the heap does not move it.

`poll()` on an empty heap returns `null`.
Assigning that to an `int` throws `NullPointerException`.

### Top k with a small heap

Want the k **biggest** → keep a **min**-heap of size k.
Offer each number.
If the size goes over k, poll once.
The poll throws out the smallest, the weakest of the strong ones.
O(n log k) time, O(k) heap.

The 3 largest of `[5, 1, 9, 3, 7, 2]`:

| Offer | Heap (smallest on top) | Over 3? |
|---|---|---|
| 5 | 5 | no |
| 1 | 1 5 | no |
| 9 | 1 5 9 | no |
| 3 | 1 3 5 9 | poll 1 → 3 5 9 |
| 7 | 3 5 7 9 | poll 3 → 5 7 9 |
| 2 | 2 5 7 9 | poll 2 → 5 7 9 |

Left: `5 7 9`.
Top is 5, which is the 3rd largest (#215).
The whole heap is the top 3 (#347 shape, with counts instead of values).

Want the k **smallest** → keep a **max**-heap of size k, same moves.

Putting everything in a max-heap and polling k times also works.
That is O(n log n) worst case, which does not beat the #347 follow-up.

### Week 7 Day 1

#347: counts in a map, heap holds the **keys**, comparator reads the map.
#215: no map, heap holds the **numbers** themselves.
Ask what is different before copying the last problem's shape.

---

## 2. Binary search — sorted, so cut in half

### What it is

The array is sorted.
Look at the middle.
The answer is either left of it or right of it.
Throw the other half away.
Repeat.

1 000 000 elements → about 20 looks.
O(log n) time, O(1) extra (just three `int`s).

No tree is built.
`lo` and `hi` are indices on the same array.

### The three variables

`lo` = leftmost index still possible.
`hi` = rightmost index still possible.
`mid` = the one you look at this round.

The live range is `lo .. hi`, both ends included.

### How `mid` is computed

```java
int mid = lo + (hi - lo) / 2;
```

`hi - lo` is how wide the range is.
Half of that is how far to step from `lo`.
Java `int` division drops the fraction, so it rounds down.

| lo | hi | hi - lo | / 2 | mid |
|---|---|---|---|---|
| 0 | 3 | 3 | 1 | 1 |
| 2 | 3 | 1 | 0 | 2 |
| 0 | 0 | 0 | 0 | 0 |
| 3 | 9 | 6 | 3 | 6 |

With two elements left, `mid` is the left one.
With one element left, `mid` is that element.

### Why not `(lo + hi) / 2`

It gives the same number on small arrays.
On huge indices `lo + hi` can pass `Integer.MAX_VALUE` (2 147 483 647) and wrap to a negative number.

`lo = 1 500 000 000`, `hi = 2 000 000 000`:
`lo + hi` = 3 500 000 000 → wraps to −794 967 296 → `mid` negative → `ArrayIndexOutOfBoundsException`.
`lo + (hi - lo) / 2` = 1 500 000 000 + 250 000 000 = 1 750 000 000.

Interviewers ask about this.

### One decision per round

```java
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] == target) return mid;       // found
    else if (nums[mid] < target) lo = mid + 1; // answer is to the right
    else hi = mid - 1;                         // answer is to the left
}
```

Exactly one branch runs each round.

On #35 the `<` check and the `==` check were two separate `if`s.
When `nums[mid] < target`, `lo` moved.
Then `==` was false, so its `else` ran too and `hi` moved.
Both ends moved in one round and the range skipped the answer.

### Why `mid + 1` and `mid - 1`

`mid` was already checked this round.
It is not the answer, so it leaves the range.

If you write `lo = mid`, the loop can stop moving.
`[1, 3]`, target 3: `lo = 0`, `hi = 1`, `mid = 0`.
1 < 3, so `lo = mid` = 0.
Same `lo`, same `hi`, same `mid` forever.
Infinite loop.

### Why `lo <= hi`

When `lo == hi` there is still one element to check.
`lo < hi` would skip it.
The loop ends when `lo` passes `hi` (`lo = hi + 1`): nothing left.

### #35 — why return `lo`

The loop keeps a promise every round:
Everything left of `lo` is **smaller** than target.
Everything right of `hi` is **bigger** than target.

When the loop ends, `lo = hi + 1`.
There is nothing in between.
So `lo` is the first index whose value is bigger than target.
That is exactly where target goes.

### #35 traces on `[1, 3, 5, 6]`

Indices:

```
index:  0  1  2  3
value:  1  3  5  6
```

**Target 5 (found).**

| lo | hi | mid | nums[mid] | compare | move |
|---|---|---|---|---|---|
| 0 | 3 | 1 | 3 | 3 < 5 | lo = 2 |
| 2 | 3 | 2 | 5 | 5 == 5 | return 2 |

**Target 2 (goes in the middle).**

| lo | hi | mid | nums[mid] | compare | move |
|---|---|---|---|---|---|
| 0 | 3 | 1 | 3 | 3 > 2 | hi = 0 |
| 0 | 0 | 0 | 1 | 1 < 2 | lo = 1 |
| 1 | 0 | — | — | lo > hi | return 1 |

Left of index 1 is `1` (smaller than 2).
Index 1 onwards is `3 5 6` (bigger).
2 goes at index 1.

**Target 7 (past the end).**

| lo | hi | mid | nums[mid] | compare | move |
|---|---|---|---|---|---|
| 0 | 3 | 1 | 3 | 3 < 7 | lo = 2 |
| 2 | 3 | 2 | 5 | 5 < 7 | lo = 3 |
| 3 | 3 | 3 | 6 | 6 < 7 | lo = 4 |
| 4 | 3 | — | — | lo > hi | return 4 |

`lo` walks off the end to 4 = `nums.length`.
No special "past the end" check needed.

**Target 0 (before the start).**

| lo | hi | mid | nums[mid] | compare | move |
|---|---|---|---|---|---|
| 0 | 3 | 1 | 3 | 3 > 0 | hi = 0 |
| 0 | 0 | 0 | 1 | 1 > 0 | hi = −1 |
| 0 | −1 | — | — | lo > hi | return 0 |

`lo` never moved, so the answer is 0.

**The `[-5, -3]`, target −4 case (the sign patch got this wrong).**

| lo | hi | mid | nums[mid] | compare | move |
|---|---|---|---|---|---|
| 0 | 1 | 0 | −5 | −5 < −4 | lo = 1 |
| 1 | 1 | 1 | −3 | −3 > −4 | hi = 0 |
| 1 | 0 | — | — | lo > hi | return 1 |

The sign of the target never matters.
Only the compare does.

### Final #35

```java
public int searchInsert(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) low = mid + 1;
        else high = mid - 1;
    }
    return low;
}
```

### Coming this week

#33 Search in Rotated Sorted Array and #153 Find Minimum in Rotated Sorted Array.
Same `lo` / `hi` / `mid`.
The only new part is how you decide which half to throw away.

---

## 3. Intervals — ranges with a start and an end

### What it is

Input is a list of pairs `[start, end]`.
Example: `[[1,3], [8,10], [2,6]]`.
Each pair is a range, like a meeting from 1 to 3.

Questions are about how the ranges touch:
merge the overlapping ones, count rooms needed, find free time, insert a new one.

### Step one is almost always: sort by start

Unsorted, an overlap can hide anywhere in the list.
Sorted by start, a range can only overlap the one you kept just before it.
Then one walk is enough.

Sort is O(n log n).
The walk is O(n).
Total O(n log n).

### When do two ranges overlap

Sorted by start, take the last kept range and the next one.

They overlap when **next start ≤ last end**.

```
[1,3]  and [2,6]:  2 ≤ 3 → overlap
1---3
  2-------6

[1,6]  and [8,10]: 8 > 6 → no overlap
1-----------6
                8----10
```

Touching (`[1,4]` and `[4,5]`) counts as overlap when the rule is `≤`.
Read the problem to see if touching counts.

### Merge example

Input `[[1,3], [8,10], [2,6], [9,12]]`.

Sorted by start: `[1,3] [2,6] [8,10] [9,12]`.

| Next | Last kept | next start ≤ last end? | Result so far |
|---|---|---|---|
| [1,3] | — | — | [1,3] |
| [2,6] | [1,3] | 2 ≤ 3, yes | stretch end to max(3, 6) → [1,6] |
| [8,10] | [1,6] | 8 ≤ 6, no | add → [1,6] [8,10] |
| [9,12] | [8,10] | 9 ≤ 10, yes | stretch end to max(10, 12) → [1,6] [8,12] |

Answer `[[1,6], [8,12]]`.

Use `max` for the new end.
`[1,10]` then `[2,3]`: the merged end stays 10, not 3.

### Why it fits this role

Booking time slots, seat holds with an expiry, meeting rooms.
"Does this new booking clash with an existing one?" is the same overlap check.
