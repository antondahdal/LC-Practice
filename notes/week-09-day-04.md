# Week 9 Day 4 — 2026-10-15 — extra vs notes repo

Thursday.
Old LC, then new LC, same family.
Then S5 and S6.

Today: #35 Search Insert Position, #34 Find First and Last Position.
Spring left: S5–S8, then Friday R1–R8.
Wednesday’s event search is still open.
Today’s Part 2 is Kubernetes.
Today’s Part 3 is the deploy board.

## LC 35 Search Insert Position

Full write-up: `notes/review/week-09.md`.

`mid` is the index.
`nums[mid]` is the value.
`[1, 3, 5, 6]`, target `1`, `mid == 1` returns `1`.
The answer is `0`.

## LC 34 Find First and Last Position

Full write-up: `notes/review/week-09.md`.

A match stores an index only when a neighbor is also the target.
`[5, 7, 7, 8, 8, 10]`, target `5`, comes back `[-1, -1]`.
The answer is `[0, 0]`.

`mid - 1 > 0` skips index `0`.
`[8, 8, 8, 8]` stores the first index as `1`.
The answer is `[0, 3]`.
The left check is `mid - 1 >= 0`.
After close, those two lines were patched in his method.
A lone match stores `mid` in both slots first.
The sideways `for` loops are still there, so a full run of the target is still O(n).

## Calendar

Day 4 coding closed (#35 on-time, equal-check hole then fixed; #34 overtime, stopped, 6 of 8).
S5 through S8 taught the same day in the Spring session. Write-up is in the notes repo `week-09.md` Day 4.
