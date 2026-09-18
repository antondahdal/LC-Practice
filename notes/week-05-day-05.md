# Week 5 Day 5 — 2026-09-18 — extra vs notes repo

Friday.
Coding only.
No LC-SD.

Printed Week 5 bank was already empty.
First LC was Top Interview 150 **#82**.
Then Easy **#234** (Blind 75).

## LC 82 Remove Duplicates from Sorted List II — overtime, then he rewrote

Sorted list.
Delete every value that appears more than once.
Keep only numbers that showed up once.
`1 → 2 → 3 → 3 → 4 → 4 → 5` becomes `1 → 2 → 5`.

**Gate:** first said HashSet.
Wrong.
A set keeps the first copy.
This problem drops every copy of a duplicated value.
List is sorted, so equals sit next to each other.
Then two pointers.
O(n) / O(1).

**Clock:** timeout at 25 min.
He kept going for learning.
Then asked to fix, then rewrote.
His rewrite passed.

**Weak:** infinite loop when `left.val == right.val` and `right` is the last of a run.
`right` did not move every turn.
`dummy` was the first kept node, then `return dummy.next` dropped it.
`left = right` only inside `if (left == null)`, so `left` sat on 1 the whole time.
Changed `while (right.next != null)` to leave the loop instead of skipping the run.
Afraid inner loop is O(n²).
It is not: `right` only walks forward.
Each node once.
Put `left.next = right` in the unique `else`.
`1 → 1` never enters else.
That line belongs after the inner skip.

**Memorize:** dummy in front of head.
`left` on dummy.
`right` on head.
If this value equals the next, walk `right` off that whole run, then `left.next = right`.
Do not sit `left` on a duplicate.
If the value is alone, `left` moves onto `right`, then step `right`.

**Cousin:** #83 keeps one copy.
This one keeps zero.

## LC 234 Palindrome Linked List — on-time, he coded

Same forward and backward.
`1 → 2 → 2 → 1` true.
`1 → 2` false.

**Gate:** stack works, O(n) extra.
Asked if O(1).
Yes: middle, flip back half, compare.
Picked two pointers.
Said connect `a` to `b` and run again.
That is #143 Reorder.
Here you compare, you do not weave.

**Clock:** finished at 15 min.

**Weak:** `fast.next.next` with no `fast.next` check.
One node NPEs.
Flip behind started on `slow`, not null.
`while (slow.next != null)` never puts the last node on `before`.
Asked how to take the middle after the flip.
You do not.
`before` is the start of the back half.
Front is `head`.

**Memorize:** `while (fast.next != null && fast.next.next != null)`.
Then `slow = slow.next`.
Same #206 loop, `while (slow != null)`.
`before` is the new back head.
Walk `head` and `before`.

**Cousin:** stack of values, O(n) extra.
Restore the list by flipping the back half again.

## Part 1 Design

**Off** (Friday).

## Calendar

Day 5 **closed**.
Sat/Sun **off**.
**Next weekday:** Week 6 Day 1 — trees, three LCs.
