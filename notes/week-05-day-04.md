# Week 5 Day 4 — 2026-09-17 — extra vs notes repo

Thursday.
Part 1 coding + replacement LC-SD done here.

Printed Week 5 bank was already empty.
First LC was Top Interview 150 **#92**.
Then Easy **#160**.

Clock starts after Memorize this.
Medium **25 min**.
Easy **15 min**.
At 0: stop.
Notes say on-time or overtime.

## LC 92 Reverse Linked List II — overtime, coach wrote it

Slice from `left` to `right` (positions, not values).
`1 → 2 → 3 → 4 → 5`, `left = 2`, `right = 4` becomes `1 → 4 → 3 → 2 → 5`.

**Gate:** two workers.
Save before left and after right, then reattach.
Said extra space O(n) and time O(1).
Swapped.
Target is O(n) time / O(1) extra if you rewire.

**Clock:** timeout at 25 min.
He kept going for learning.
Then asked for the code.

**Weak:** `first.val != left` (value, not position).
Dummy was head, not in front of head.
Walk left `first` on 2 and `sec` on 3, so reverse treated 2 as behind.
`3.next = 2` while `2.next` stayed 3.
Cycle.
`while (sec != null)` reverses the whole tail.
`first = sec` moves the finger.
It does not turn node 2 into node 3.

**Memorize:** dummy in front of head.
`before` walks `left - 1`.
`start = before.next`.
`first = null`, `sec = start`.
Same #206 loop, `right - left + 1` times.
`before.next = first`.
`start.next = sec`.
Return `dummy.next`.

**Cousin:** #206 is the inner loop.

## LC 160 Intersection of Two Linked Lists — on-time, he coded

Shared node, not equal `val`.
Return that node or null.

**Gate:** HashSet (the node).
O(n+m) / O(n).
Asked if anything else.
Yes: two walkers, O(1) extra.
He coded the set.

Clock was not armed at start.
Armed late with ~9 min left.
He finished before 0.

**Weak:** one loop on both lists.
Can `add(null)` when one side ends.
Next `contains(null)` returns null even if the other list still has nodes.
Split: walk A into the set, then walk B.

**Cousin:** two walkers hop to the other head.
They meet at the join or both at null.

## Part 1 Design — Chapter 1 scrapped

Coach filled Book / 201 / 409 when he asked what the question meant.
Then asked him to say it back.
He called that.
Do not fill the answer then re-ask.
Rephrase the prompt only.
Ch 1 not run again today.

## Part 1 Design — Chapter 7 service-to-service — done

Replacement for scrapped Ch 1.
Next unused piece from `lc-sd-map` (printed W6 Tue).
Cover was missing the LeetCode URL.
Rule: LC-SD Cover always pastes https://leetcode.com/explore/interview/card/system-design-for-interviews-and-beyond
Same as coding Cover.

Anton: Book calls Event client by HTTP.
Waiting for response or exception.

**Sharpen:** Booking is the caller.
Event answers.
Then Booking answers the phone.

**Trap:** 201 before Event has answered.
Two DBs as one commit (2PC).

**Interview sentence:** Booking calls Event over HTTP and waits.
The phone gets an answer only after that returns.

## Protocol

Timer on LC from this day.
Notes: period means a new line (`.cursor/rules/lc-notes.mdc`).
LC-SD: never fill then re-ask.
If he scraps a chapter, pick leftover or skip.
