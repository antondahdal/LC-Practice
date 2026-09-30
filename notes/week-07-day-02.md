# Week 7 Day 2 — 2026-09-29 — extra vs notes repo

Tuesday.
Three LCs (Weeks 6–8), then LC-SD.
Starts with #56 Merge Intervals (left over from Monday).

Before the gate he asked for heap, binary search, and intervals to be explained again.
All three now live in `notes/heap.md` ("Short version" at the top).

## LC 56 Merge Intervals — time up, green in overtime

https://leetcode.com/problems/merge-intervals/

### The problem

You get a list of `[start, end]` pairs.
Merge every pair that overlaps and return what is left.
`[[1,3],[2,6],[8,10],[15,18]]` gives `[[1,6],[8,10],[15,18]]`.

### The idea

Pattern: intervals (sort by start, then one walk).
Once the pairs are sorted by start, anything that overlaps the last merged pair must come right after it.
So you only ever look at one thing while scanning: the **last kept** pair in the result list.
Time O(n log n) because of the sort.
Extra space O(n) for the result list.

### How to solve it

1. Sort by start: `Arrays.sort(intervals, (a, b) -> a[0] - b[0])`.
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

### How it went

25 min Medium clock.
At the bell 6 of 7 green; only the missing `max` on the end was left.
After a break he put the `max` in and got 7 of 7 (overtime).
Memorize that day came with Java pieces because he asked for code on this problem.

## LC 228 Summary Ranges — time up, coach-fixed in overtime

https://leetcode.com/problems/summary-ranges/

Easy slot from Top Interview 150, Intervals section (printed Week 7 line has only Mediums).

### The problem

You get a sorted array of unique ints.
Write each run of consecutive numbers as `"a->b"`, or `"a"` if the run is one number.
`[0,1,2,4,5,7]` gives `["0->2","4->5","7"]`.

### The idea

Pattern: one walk with a run start (two indexes: where the run began, where you are now).
A run keeps going while the next number is exactly current + 1.
A run ends in two cases: the next number jumps, **or there is no next number**.
You only keep the start of the current run; the end is whatever `nums[i]` is when the run closes.
Time O(n).
Extra space O(1) besides the output list.

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

### How it went

15 min Easy clock.
At the bell 1 of 7 green (only the empty test).
Coach fixed it at his request, keeping his stack: after the loop, if the stack is not empty push the last number and write `first->last`, else write the last number alone.
All 7 green (overtime, coach-fixed).
Coaching note: when he asks a direct "why", answer that exact question first.
Rule added this day: Memorize is steps in words only; Java only if he asks on that problem.

## Third LC

Skipped by Anton ("continue to LC Design").

## Part 1 Design — Ch 11 leftover: how the counter counts — Done

Map slot W7 Tue: "Ch 11 recap".
Rate limit already drilled W6 Thu (per user per window, before `book()`, shared counter, 429 not 409).
W7 Thu Part 3 board is also rate limit.
Coach asked; Anton picked the leftover only: fixed window vs token bucket.

Prompt: cap 10 Books / min / user, fixed window.
Bot sends 10 at 12:00:59 and 10 at 12:01:00.
Then same bot against a token bucket of 10, one token every 6 s.

**His answer:**
Fixed window: all 20 pass (right).
Event 7 with fewer than 20 seats: bot sells it out (right).
Token bucket: did not get it after one rephrase.
Coin-jar example with small numbers (jar 3, one coin per 10 s) landed the mechanism.
Then he answered with the toy numbers (3 pass) and "same next minute".

**Coach corrected:**
Jar is 10: first 10 pass, 1 s later no coin has dripped, second 10 all 429.
No minute reset in a bucket, only the drip (one Book per 6 s after the burst).
20 vs 10 in two seconds.

**Trap:** fixed window lets 2× the cap through at the window edge, which is when a bot grabs the last seats.

**Interview sentence:** token bucket per user in Redis, because a fixed window lets double the cap through at the edge; the bucket allows a burst up to its size, then only the refill rate; over the limit is 429, sold out stays 409.

**Weak:**
Token bucket words alone did not land; a table with small numbers did.
Carried the toy example's numbers into the real question.
Thought every limiter resets on the minute.

## Calendar

Day 2 Part 1 closed (#56 time up then overtime, #228 time up then coach-fixed, third LC skipped, Ch 11 fixed window vs token bucket).
Part 3 today: carried Mon (food-delivery critique + services split board), then Tue (split-bill critique + read replica / index / shard).
**Next weekday:** Week 7 Day 3.
Leftover LCs in the Week 7 bank: #33, #153.
