# Week 8 Day 1 — 2026-10-05 — extra vs notes repo

Monday.
Three LCs (Weeks 6–8), then LC-SD.
Week 8 families: graph / DP classics (#200, #207, #70, #198, #322, #146).

Today: #200 Number of Islands, #70 Climbing Stairs, #207 Course Schedule.
Left in the bank: #198 House Robber, #322 Coin Change, #146 LRU Cache.

## LC 200 Number of Islands — coach-written, untimed

Full write-up (problem, idea, trace, holes, Memorize): `notes/review/week-08.md`.

Key idea: scan every cell; on land, count one and sink the whole island to `'0'` so it is never counted again.

**Holes:**
Named "graph" and the right time and space.
Did not know what to keep so land is not counted twice (mark visited / sink).
Did not know how to walk the four neighbours.
Asked how the walk is still O(N): each cell is sunk once, and later checks on it return at once.

**How it went:**
Did not want to swap; asked the coach to write it "the human way" so he could study it.
Coach wrote it, 5 of 5.

## LC 70 Climbing Stairs — on-time, he coded

Full write-up: `notes/review/week-08.md`.

Key idea: the last move came from `n - 1` or `n - 2`, so `ways(n) = ways(n-1) + ways(n-2)`.

**Holes:**
Named DP and Fibonacci, and got the rule from the small numbers, not from "where did the last move come from".
Said O(N) time because of the call stack; plain recursion without memory is O(2ⁿ).
Used an `int[n+1]` table (O(n) space); two variables give O(1).

**How it went:**
On-time, about 7 minutes, 5 of 5, he coded it.

## LC 207 Course Schedule — coach-written, untimed

Full write-up: `notes/review/week-08.md`.

Key idea: the answer is false only when the prerequisites form a loop; take every course that waits on nothing, unlock the next ones, and check that all courses were taken (Kahn's topological sort).

**Holes:**
Named "graph" but did not see that `false` means a loop.
Did not know how to turn pairs into an adjacency list plus a waiting count.

**How it went:**
Asked the coach to write it like Islands so he could study it.
Coach wrote it, 8 of 8.

## Part 1 Design — Ch 9 batching leftover — Done

Map slot W8 Mon: recap a weak piece.
Week 6 Day 3 had Ch 9 with the batching half weak; the timeout half was already right, so only batching was asked.

Question: on the booking app, one thing you would batch and one you would never batch, with the reason.

He picked notifications to batch and Book never to batch, which is right.
His reason was "notifications are not sensitive like booking", which is too vague.

**Right reason:**
Notifications run after commit, can wait a few seconds, and the worker retries each one on its own.
Each Book has its own user, seats, and outcome; a batch lets one failure or slow seat check hit good Books, or make a failed one look booked.

**Interview sentence:** I batch work that can wait and retry per item, like notifications after commit.
I never batch Book, because each one needs its own 201 or 409 right away.

## Coaching notes

When he does not know the "why" on a graph problem, he wants to learn it, not swap.
Offer the swap once; if he says no, explain it with a picture and Memorize steps, and write it if he asks.
Coach-written code counts as coach-written in the notes, not on-time.

## Calendar

Day 1 Part 1 closed (#200 coach-written, #70 on-time, #207 coach-written, Ch 9 batching leftover).
Not pushed; push after Part 3 when Anton says.
**Next:** Week 8 Day 2, from #198 House Robber, #322 Coin Change, #146 LRU Cache.
