# Week 6 Day 1 — 2026-09-21 — extra vs notes repo

Monday.
Trees.
Three LCs, then LC-SD.
He asked Easy first (new shape).
Not Medium-first cadence.

Bank: #104 Max Depth, #226 Invert, #102 Level Order, leftover #98 Validate BST, #230 Kth Smallest.

## LC 104 Maximum Depth of Binary Tree — on-time, he coded

Longest root-to-leaf node count.
Empty is 0.
One node is 1.

**Gate:** recursion.
Go left and right, return max.
He said O(n log n) / O(1).
Correct is O(n) / O(h) stack, worst O(n) chain.

**Clock:** 15 min Easy.
He landed it on-time.
Tests green.

**Memorize:** `null` → 0.
`left = maxDepth(left)`.
`right = maxDepth(right)`.
Return `1 + max(left, right)`.

**Cousin:** queue, count levels.

**Refresh mix-up:** he thought left is always smaller and right is always bigger.
That is BST, not every binary tree.
#104 example has 9 left of 3.
Binary = at most two kids per node, not exactly two, not two nodes in the whole tree.

## LC 226 Invert Binary Tree — on-time, he coded

Swap left and right all the way down.

**Gate:** recursion.
Time/space first wrong (n log n / O(1)).
Same as depth: O(n) / O(h).

**Clock:** 15 min Easy.
On-time.

**Weak:** NPE on `twoNodes` was the JUnit helper.
`ArrayDeque` rejects `null`.
After invert, left can be null.
Coach switched helper to `LinkedList`.
His swap + both recursive calls was already right.

**Memorize:** save left, swap, call both sides, return root.
Base `null`.

**Cousin:** queue, swap one node at a time.

## LC 102 Binary Tree Level Order Traversal — overtime, coach filled

Values grouped by depth.
`[[3], [9, 20], [15, 7]]`.

**Gate:** first said recursion with a list, O(n log n) / O(n).
A walk is not level order unless you keep depth.
Then queue, but missed snapshot size.
Name is BFS.
O(n) / O(n).

**Clock:** 25 min Medium.
He asked to fix near 0.
Coach wrote the size loop.
Notes: **overtime**.

**Weak:** `new Queue<>()` does not compile.
`Queue` is an interface.
Use `new ArrayDeque<>()`.
Empty returned `null` then he fixed to empty list.
Peeled one parent and made a new row from that parent’s kids.
`2`’s kids and `3`’s kids are one depth: `[4, 5, 6, 7]`.
He thought binary means only two on a level.
Binary is two children **per node**.
`size = queue.size()` **before** the inner `for`.
Do not loop `queue.size()` while offering kids.
Do not XOR a `rowCount = 2`.
No special first row for root.

**Memorize:** offer root.
While queue not empty: `size = queue.size()`, new row, loop `size` times, poll, add val, offer left/right, then add the row.

**Cousin:** recursion with a `depth` index into `List<List<Integer>>`.

## Part 1 Design — Chapter 5 Queue + at-least-once — done

W4 Wed already did sync vs queue (commit, enqueue, 201).
Today leftover: outbox with the book, and worker retry of the **same** job.

Anton: Event **201** then queue mail.
Same click id → **409**, do not enqueue again.
Different click = different book, even same customer.
That duplicate-**request** path is right.

He then said commit and queue write together.
If those are one commit, **409** skip enqueue is correct.
Do not send a second mail for a second click.

**Hole he missed first:** book saved, queue write as a later hop, crash, retry **409**, skip queue, ticket exists, no job.
Goes away if book + send-later row are one commit.

**Trap:** at-least-once is the **worker** running one row twice, not a second click.
Not exactly-once mail.

**Interview sentence:** commit book and outbox together, then 201.
Duplicate click is 409 and no new row.
The same row may still run twice.

## Calendar

Day 1 **coding + LC-SD + Spring/Part 3 closed**.
**Next weekday:** Week 6 Day 2 — leftover tree Mediums #98, #230 (and bank fill if needed).
