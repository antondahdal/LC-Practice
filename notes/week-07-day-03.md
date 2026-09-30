# Week 7 Day 3 — 2026-09-30 — extra vs notes repo

Wednesday.
Week 7 bank leftovers: #33, #153 (both Medium, no Easy on the printed line).
LC-SD skipped today (map: W7 Wed skip when Part 3 is cache).

Protocol change this morning: LC notes explain the problem and patch holes, not a replay of the session.
Every LC also goes into `notes/review/week-NN.md` with a Memorize block (steps + one reference Java solution).
Review sheets for Weeks 3–7 written and pushed.

## LC 33 Search in Rotated Sorted Array — on-time, coach gave the fix

Full write-up (problem, idea, trace, holes, Memorize): `notes/review/week-07.md`.

Key idea: at any `mid`, one half is sorted; range-check the target on that half only.

**Holes:**
Wrote a plain sorted-array binary search (`nums[mid] < target` decides the side), which goes the wrong way across the cut.
Thought the tests were wrong when a fixed return flipped between "expected 0" and "expected -1".
Added a `length == 2` special case instead of fixing the loop rule.

**How it went:**
Did not get the prompt at first; a plain "cut and swap" rephrase landed it.
Named binary search, time and space right.
Asked for the fix as code before the bell; green at about 16 minutes.
Said binary search feels dry and difficult.
Framing that helped: one loop, only the "higher or lower" question changes per problem (#35, #33, #153).

## LC 153 Find Minimum in Rotated Sorted Array — on-time

Full write-up: `notes/review/week-07.md`.

Key idea: `nums[mid] > nums[high]` means the drop is right (`low = mid + 1`), else `high = mid`.

**Holes:**
Did not understand `mid = low + (high - low) / 2`.
Mixed index and value (`high` read as 2 instead of 6, `mid + 1` read as 7 + 1).

**How it went:**
Cleared with one array shown as an index / value table.
Coded it himself, all 7 green in about 8 minutes.

**Coaching note:**
He got angry at a long walkthrough.
Give Memorize as six short lines, one trace table, nothing else.
Answer his exact question in two or three lines.

## Calendar

Day 3 Part 1 closed (#33 on-time with coach fix, #153 on-time, LC-SD skipped per map).
Week 7 printed bank is done.
Part 3 today: chat critique + traffic board (LB + cache, longer).
