# Week 9 Day 2 — 2026-10-13 — extra vs notes repo

Tuesday.
Two LCs, then the booking app, then the waiting-room board.
S3 and S4 were not reached.

Today: #22 Generate Parentheses, #108 Convert Sorted Array to BST.
Left in the LC bank: #46, #392, #39, #14, #208, #530.
Spring left: S3–S8, then Friday R1–R8.

## LC 22 Generate Parentheses — coach-written, untimed

Full write-up: `notes/review/week-09.md`.

Key idea: backtracking.
One partial string, plus an open count and a close count.
Add `(` while opens are still under `n`.
Add `)` only while closes are still under opens.
Length `2n` saves the string, then the last character is deleted.

**Holes:**
He did not know the pattern name and stayed to learn it.
`)` whenever closes are under `n` writes `)(` for `n = 1`.
The close is legal only while closes are still under opens.

**How it went:**
Untimed.
Coach wrote the method.
3 of 3.

## LC 108 Convert Sorted Array to Binary Search Tree — coach-written, untimed

Full write-up: `notes/review/week-09.md`.

Key idea: the middle value of the current slice becomes the root.
That is the Week 7 middle index on a sorted array.
The new piece is that this value is a node, and the same split then runs on each side.
There was no earlier lesson under the name divide and conquer.

**Holes:**
He named heap, then BFS, then intervals.
None of those build a tree from a sorted array of single values.
The name was asked before that new piece was said.

**How it went:**
Untimed.
Coach wrote the method.
4 of 4.

## Part 1 Spring — S3 + S4 — not done

The LC session ended after #108.
Proxies and AOP were not asked.
Bean scopes, lifecycle, and circular dependencies were not asked.

## Part 2 — event list query count + Book transaction — Done

Full write-up is in the notes repo, `interview-notes/week-09.md`.

The event list loads a page of ids, then those events and their venues in one query.
Five events, page of two, is three SQL statements.
`getEvent` and the list are `@Transactional(readOnly = true)`.
"My tickets" calls auth first, then reads in its own read-only transaction.
`writeBook` opens the transaction.
The ticket and both outbox rows commit or roll back together.
A plain `@Transactional` is enough.
`REQUIRED` is already the default.
`book()` has no transaction of its own, so `writeBook` starts one.

**Hole:**
He asked why `Propagation.REQUIRED` is written when no transaction is already open.
Writing the default changes nothing.
The method that carries the annotation is the one that starts the transaction.

## Part 3 — waiting room — Done

Full write-up is in the notes repo, `interview-notes/week-09.md`.
The AI-for-developers slot was scrapped the same day.

The line is outside Booking.
The entry token lasts a few minutes and does not take a seat.
`book()` with 0 seats is **409**.
A phone that never reaches `book()` times out.
Ten more Booking servers still sell 5,000 seats, because the seats are one event row.

**Holes:**
He said **429** when `book()` saw 0 seats.
**429** is the line turning a phone away before `book()` runs.
He then said a phone still in the line hears **409**.
That phone never calls `book()`, so it hears a timeout.

## Calendar

Day 2 closed (#22 coach-written, untimed; #108 coach-written, untimed; S3 and S4 not reached; Part 2 query count and Book transaction done; Part 3 waiting room done).
Friday, as he set it on this day, closes only what is still unfinished in the app.
