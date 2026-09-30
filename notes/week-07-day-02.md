# Week 7 Day 2 — 2026-09-29 — extra vs notes repo

Tuesday.
Three LCs (Weeks 6–8), then LC-SD.
Starts with #56 Merge Intervals (left over from Monday).

## LC 56 Merge Intervals

**Before the gate:** he asked for heap / binary search / intervals to be explained again.
Coach gave the short version of all three with no pick for #56.
Added as "Short version" at the top of the topics sheet.
Then he asked for one file: `heap-cheatsheet.md` renamed to `notes/heap.md`, with all three topics inside.
`week-07-topics.md` removed.

**Gate:** named intervals ("clearly intervals").
Said he did not know the coding shape, asked for a code example.
Coach gave Memorize (steps in words + Java pieces: `Arrays.sort` with comparator, `List<int[]>`, last kept, `Math.max`, `toArray`).
Time O(n log n), space O(n), both right.

**Clock:** 25 min Medium, started 10:13.

**Mid-clock (10:19):** planned a `HashMap` + overlap check.
Coach asked what the key would be and how the map finds the overlapping pair.
He went back to sort + walk but said intervals still felt vague.
Hints: the "Result so far" column of the merge table is the list, "Last kept" is always `list.get(size - 1)`.
Then a pass-by-pass trace of how each Memorize piece fits (`pair` must come from a loop).

**10:34, first run:** 1 of 7 green.
Overlap check compared `pair[1] >= last[0]` (wrong ends).
Merged pair took the new start and the old end.
Hints: say the rule out loud (next start ≤ last end), try `[1,2]` then `[3,4]`; whose start does the merge keep, which end wins.

**At the bell (10:38):** 6 of 7 green.
Overlap check and start fixed.
End is `pair[1]` instead of the bigger of both ends, so `[1,10]` then `[2,3]` gives `[1,3]`.
**Clock result:** time up, not landed.
He asked coach to say the fix: `newPair[1] = Math.max(tmpPair[1], pair[1])`.
Sorted by start, the next pair can still end earlier (inside the last kept one).

**Nit:** remove + add a new array works, but changing `last[1]` in place is enough (same array as in the list).

**Weak:**
Could not turn the intervals idea into a loop without a trace.
Reached for a `HashMap` again when the shape felt vague.
Compared the wrong ends in the overlap check.
Forgot `max` on the end, the exact trap from the notes table.
Why he missed it: thought sorting makes both start and end go up.
Sort by start orders only slot 0, ends ride along (`[1,10]` then `[2,3]`).

10-minute break at 10:40, before the #228 gate.
Back 11:06, `max` fix in, all 7 green (overtime).

## LC 228 Summary Ranges

Easy slot from Top Interview 150, Intervals section (printed Week 7 line has only Mediums).

**Gate:** "not sure", then "loop, two pointers, current and next value".
Accepted: one index holds the group start, the other walks, current vs next ends a group.
Coach gave Memorize with Java pieces.
He pushed back: he did not ask for code.
Rule added: Memorize is steps in words only, Java only if he asks on that problem.
Time O(n), space O(n), right (the output list; O(1) besides the output).

**Clock:** 15 min Easy, started 11:14.

**11:38 (after the 11:29 bell):** 1 of 7 green (only empty).
Loop runs to `length - 1`, compares `nums[i]` with `nums[i + 1]`, keeps the current run in a `Stack` (first / last element).
The last group is never written, in every test.
`new ArrayList<>(nums[0])` for one number is a capacity, not an element, so the list is empty.
**Clock result:** time up, not landed.

He asked why the last index is never written.
Answer: loop stops at `length - 1`, so the last number is only ever `nums[i + 1]`.
Groups are written only on a gap after `nums[i]`, and there is no gap after the last one.
He tried a close-after-loop block (read `last` before declaring it), then asked coach to fix.

**Coach fix (his structure kept):**
After the loop, if the stack is not empty the last number continues the run, so push it and write `first->last`, else write it alone.
Stack non-empty there means `nums[n-2] + 1 == nums[n-1]`, no extra compare needed.
Length 1: `new ArrayList<>(List.of(String.valueOf(nums[0])))`.
All 7 green (overtime, coach-fixed).

**Nits:** a `Stack` to hold a run only needs its first element; one `int start` index is enough.

**Weak:**
Closing the last group after a loop (same family as "last run" bugs).
`new ArrayList<>(int)` is capacity, not contents.
Frustrated when the question was not answered straight — answer the exact question asked first.

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
