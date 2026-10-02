# Week 7 Day 5 — 2026-10-02 — extra vs notes repo

Friday, coding only.
Day 4 (#74 Search a 2D Matrix, #69 Sqrt(x)) was cut short for personal reasons.
#74 is green on disk.
#69 on disk is still an unfinished loop (never moves `low` / `high`, the test hangs).
Week 7 printed bank was already done on Wednesday, so today's picks are Top Interview 150 from the same families.

## LC 162 Find Peak Element — dropped

Binary search again after three days of it; he was fed up and dropped it.
Do not offer binary search again this week.

## LC 452 Minimum Number of Arrows to Burst Balloons — time up, then overtime, coach-fixed

Full write-up (problem, idea, trace, holes, Memorize): `notes/review/week-07.md`.

Key idea: sort by end; one arrow at the end of the first unpopped balloon; `start <= arrow` means already popped.

**Holes:**
Did not get the question at first (one arrow vs fewest arrows, each pair is one balloon).
Sorted by start instead of end.
Used `<` where the range is closed (`<=`).
Added popped balloons to the list, so the list counted balloons, not arrows.

**How it went:**
Named intervals, time and space right.
4 of 7 at the bell.
Asked for code after the bell; coach patched his loop in his own if / else shape; 7 of 7.

## LC 57 Insert Interval — no gate, no clock, coach-fixed

Full write-up: `notes/review/week-07.md`.

Key idea: three groups (before / overlap / after); grow the new range on overlap; place it once.

**Holes:**
Empty input returned empty instead of `[new]`.
First range added twice (`if` without `else`).
Overlap check missed a new range inside an old one; merged end ignored the old end.
`remove(last)` on merge; new range never added when nothing overlapped.

**How it went:**
Skipped the gate and clock, coded about 45 minutes, 2 of 9.
Asked the coach to fix his code in place; 9 of 9.

## Coaching notes (Anton 2026-10-02)

Do not hint the pattern, even indirectly, before he names it.
On #162 the coach explained the "why" (the climb rule) after he said he did not know, and that read as giving it away.
Explain the question with a picture first (number line, small table); he needs the prompt clear before he can think.
When he asks for code, give full code in his own shape (his variables, his if / else), not a rewrite.
Do not move files between weeks without asking exactly what he means.

## Calendar

Day 5 Part 1 closed (#452 time up then overtime, coach-fixed; #57 untimed, coach-fixed; #162 dropped).
Friday, so no LC-SD.
#69 Sqrt(x) from Day 4 is still open on disk.
Rules and `AI-TEACHING.md` updated with today's coaching notes.
Not pushed; push after Part 3 when Anton says.

## Rest of today — Thu + Fri combined (Anton 2026-10-02)

Runs in the booking project, not here.
Part 2: seat hold + confirm on Event (`HELD` row with `expiresAt`, Booking confirms after commit, Event `@Scheduled` expires holds and gives seats back), plus the test for the expiry job.
Part 2 recap (Fri) folds into that.
Part 3: notification outbox LLD critique, rate limit (429) + queue for spikes board (Thu), then classic HLD URL shortener (Fri, longer).
After Part 3: Anton says push; push LC-Practice with these notes.
