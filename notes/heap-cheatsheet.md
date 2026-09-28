# Heap cheat sheet (Java `PriorityQueue`)

Look-up sheet.
Anton asked for it 2026-09-28 (Week 7 Day 1, after #347).

## What it is

In Java a heap is `PriorityQueue`.
There is no class called `Heap`.

Only the top is guaranteed.
`System.out.println(pq)` and a for-each loop do **not** print in sorted order.
To get sorted order you `poll()` one at a time.

## Cost (heap with s elements)

| Call | Does | Time |
|---|---|---|
| `offer(x)` / `add(x)` | insert | O(log s) |
| `poll()` | remove top (null if empty) | O(log s) |
| `peek()` | look at top (null if empty) | O(1) |
| `size()` / `isEmpty()` | count | O(1) |
| `addAll(c)` | c times `offer` | O(c log s) |
| `remove(x)` / `contains(x)` | search | O(s) |

## Comparator rule

The comparator returns negative → `a` goes on top first.

`a - b` → smallest on top (min-heap).

`b - a` → biggest on top (max-heap).

Memory trick: the one written first wins when it is small.

## Short cuts

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

## Traps

`b - a` can overflow when the numbers are huge or negative (for example `Integer.MIN_VALUE`).
Counts are small, so subtraction is fine on #347.
For raw values use `Integer.compare(a, b)`.

If the comparator reads a map, fill the map **first**, then offer.
Changing a count after the key is inside the heap does not move it.

`poll()` on an empty heap returns `null`.
Assigning that to an `int` throws `NullPointerException`.

## Top k pattern

Want the k **biggest** → keep a **min**-heap of size k.
Offer, and if `size() > k`, `poll()` the weakest.
What is left is the answer.
O(n log k) time, O(k) heap.

Want the k **smallest** → keep a **max**-heap of size k, same moves.

Putting everything in a max-heap and polling k times also works.
That is O(n log n) worst case, which does not beat the #347 follow-up.
