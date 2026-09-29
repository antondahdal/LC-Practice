# Week 7 Day 1 — 2026-09-28 — extra vs notes repo

Monday.
Three LCs (Weeks 6–8), then LC-SD.
Week 7 families: heap / intervals / binary search.

Printed Week 7 line has only Mediums (#347, #56, #33, #153, #215).
Easy slot filled from Top Interview 150 in the same families: #35.
#56 Merge Intervals left for Tuesday.

## LC 347 Top K Frequent Elements — on-time, he coded

Return the k values that appear most often.
`[1,1,1,2,2,3]`, k = 2 gives `[1, 2]`.

**Gate:** asked which of the three families it is (bounced, gate is his).
Asked if a `HashMap` is allowed (yes, only the follow-up on time matters: beat O(n log n)).
Offered "count, put counts in an array, sort" — correct but O(n log n), and sorting bare counts loses which value owned each count.
Did not know how to order the counts.
Two hints (a collection that keeps the smallest on top with a comparator; counts can index an array).
Then named heap himself ("Java object Heap").
Coach: Java class is `PriorityQueue`, only the top is ordered.
Time: said O(n) for both, did not know heap cost.
He asked for a small heap demo not tied to the problem (ints min / max, strings by length).
Coach gave: all m in heap O(n log n) worst, capped at k O(n log k), extra O(n).

**Clock:** 25 min Medium, started 9:39.
Done about 9:58.
All 5 green.

**Mid-clock ask:** "can I make the heap order by count?"
Hint: heap holds the keys, comparator looks up the map (walked name → age example).
He wrote `(a, b) -> map.get(b) - map.get(a)`.

**His version:** every key in a max-heap by count, poll k times.
O(n log n), does not beat the follow-up.

**Nits:** two `if`s for counting → `map.getOrDefault(x, 0) + 1`.
Leftover `println` and unused import.

**Cousin:** min-heap capped at k is O(n log k).
Bucket by count (array of lists, index = count, walk from the top) is O(n).

He asked for a heap cheat sheet: now in `notes/heap.md`.

## LC 35 Search Insert Position — time up, coach filled

Sorted distinct ints, return the index of target or where it would go.
Must be O(log n).

**First attempt, untimed:** he coded before the gate.
Linear scan, all tests green, but O(n) breaks the requirement.
Logged untimed (same as #572 on W6 Fri).

**Gate:** said "Binary Tree" (recursion left / right), time log n, space O(n), "not sure how to implement the tree".
Bounced: no tree, just two indices on a sorted array.
Then guessed BFS, then DFS post.
He did not remember what intervals and binary search mean.
Coach explained both in two lines each (no pick).
He named binary search.
Coach gave extra space O(1).

**Clock:** 15 min Easy, started 10:35.

**His bug:** `if (<) { low = mid + 1 }` then a separate `if (==) return mid; else high = mid - 1`.
On the `<` pass the `else` also ran, so `high` moved too.
Hint given: count how many branches run when `nums[mid] < target`.
At the bell he special-cased the answer by the sign of `target` (`mid + 1` vs `mid + 2`) to make one test pass.
Wrong in general: `[-5, -3]`, target -4 gave 2, should be 1 (test added).

**Clock result:** time up, not landed.
He asked coach to fix.
Fix: one `if / else if / else` chain, return `low` after the loop.

**Why `low`:** left of `low` is all smaller than target, right of `high` is all bigger.
Loop ends with `low = high + 1`, which is the insert slot.
The early "target past the end" check is then not needed.

**Weak:**
Splitting one three-way decision into separate `if`s.
Patching the answer to fit a failing test instead of finding the bug.
Pattern names: tree / BFS / DFS for a sorted array.
Heap / intervals / binary search not yet clear as three different tools.

## LC 215 Kth Largest Element in an Array — overtime, coach fixed one char

Return the kth largest, duplicates count.
`[3,2,1,5,6,4]`, k = 2 gives 5.

**Gate:** named heap.
Why: "put in a heap and return the kth".
Time O(log n) (same slip as #347, that is one `offer`), space O(n).
Coach gave: all in max-heap O(n log n) / O(n); capped min-heap O(n log k) / O(k).

**Clock:** 25 min Medium, started 10:56.

**Mid-clock ask:** "what do I replace the `HashMap` with?"
He had copied the #347 shape (map + comparator on counts, `get(a) - get(a)`).
Answer: nothing, drop the map, heap holds the numbers themselves.

**At the bell (11:21):** 5 of 6 green.
Loop `for (i = 0; i <= k; i++)` polls k + 1 times.
When k equals the length the last `poll()` returns null, unboxing throws NPE.
Logged **overtime**.
He asked coach to fix: `i <= k` → `i < k`.

**His version:** everything in a max-heap, poll k times.
O(n log n), not the capped min-heap from Memorize.

**Weak:**
Reusing the last problem's shape without asking what is different.
Off-by-one on a poll loop.
Time of heap work: he says log n for the whole thing.
Rule: n offers × log(size) each.

## Part 1 Design — Ch 10 consistent hashing lite — Done

Hot key / partition lite already done W5 Wed.
Leftover piece today: consistent hashing.

Prompt: Event GET cached across 3 Redis nodes, 10× before a sale, add a 4th node.
He said "I really don't know".
Broke it down with numbers: keys 10–14, `% 3` then `% 4`.
He asked why we do not let only new keys use the new formula.
Answer: on a GET the pod does not know if a key is old or new, it only has the key and today's formula.
Remembering the formula per key is a table of every key.
He counted 14 same, 10 moved (11 also moved).
Coach: over many keys, 3 → 4 nodes moves about 3 of 4 keys with `%`, about 1 of 4 on a ring.

**His answer (good):**
Browse: pod looks in the wrong box, misses, reads again, user sees a bit of latency.
Book: no change, cache is not responsible for Book.

**Trap:** misses land on the DB all at once at the 10× moment.
`%` gives a wave (about 3 of 4 keys), ring gives a bump (about 1 of 4).
Add the node before the sale, not during.

**Interview sentence:** keys go on a hash ring, so adding a cache node moves only its share of keys; the rest stay hot, the DB sees a small bump instead of a wave, and Book still goes to the row.

**Weak:** could not start the design question cold.
Numbers first, then the product, worked.

Anton asked to push before the talk, then again after.

## Calendar

Day 1 Part 1 closed (#347 on-time, #35 time up, #215 overtime, Ch 10 consistent hashing).
**Next:** Week 7 Day 2, start with #56 Merge Intervals.
