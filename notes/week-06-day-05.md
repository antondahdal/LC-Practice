# Week 6 Day 5 — 2026-09-25 — extra vs notes repo

Friday.
Coding only.
No LC-SD.
Three LCs (Weeks 6–8).

Picks from Top Interview 150 / Blind 75 trees not done and not booked for Week 9: #103, #572, #222.
#637 stays on Week 9 Monday.

## LC 103 Binary Tree Zigzag Level Order Traversal — on-time, he coded

Rows alternate left-to-right and right-to-left.
`3 / 9 20 / 15 7` gives `[[3], [20, 9], [15, 7]]`.

He asked for a deeper visual first (9 with two more levels under it).

**Gate:** said "BST" and meant BFS.
Bounced the name: BST is a kind of tree, not a walk.
Why: walk each row, frozen `size` tells where the row ends.
Flag starts false at root, reverse the row list when true.
Time O(n), extra space O(n).

**Clock:** 25 min Medium.
Done in about 11 min.
All 5 green.

**Nits:** single-node special case not needed (loop handles it).
Flip with `flag = !flag` once instead of in both branches.
Unused imports.

**Memorize:** same as #102 Level Order.
Always offer left then right.
Only the row list flips, never the queue order.

**Cousin:** `LinkedList<Integer>` row with `addLast` / `addFirst` by direction, no reverse.

## LC 572 Subtree of Another Tree — untimed, he coded with hints

Some node in `root` plus everything under it must equal `subRoot` exactly, down to the leaves.

**Gate:** said "DFS pre?!".
Accepted: at each node, check "does it match from here" before the kids.
Then asked if BFS outside + DFS match inside is ok.
Yes.
Wanted to throw on mismatch.
No: mismatch is a normal `false`, keep scanning.
One failed 4 does not end the search, another 4 may match lower.
Time / space not answered before coding.
Coach gave: O(n · m) time, O(n + m) extra (queue + recursion on `subRoot`).

**Clock:** none.
He started coding before Memorize, so this one is logged **untimed**.

**His bugs (three):**
Kids offered only inside `if (tmp.val == subRoot.val)`, so root 3 never enqueued its kids.
`pre` returned false when `node.left` or `node.right` was null, so two matching leaves failed.
Recursive `pre(left)` / `pre(right)` results dropped, method returned true (then `node.val == subRoot.val`, always true there).
He fixed each one after a where-hint.

**Weak:** same as Day 3 Same Tree and Day 4 Symmetric.
Checking kids before the pair.
Dropping recursive returns.
Rule: check only the pair you were handed (both null, one null, values differ), then `return left && right`.

**Cousin:** all recursive: null root → false, else `pre(root, sub) || isSubtree(root.left, sub) || isSubtree(root.right, sub)`.

## LC 222 Count Complete Tree Nodes — on-time, he coded (BFS O(n))

Complete tree: every row full except maybe the last, last row fills from the left.
Return the node count.

He saw LeetCode asks for less than O(n).
Chose to think about the fast one first.
Offered "+2 if both kids, +1 if one" recursion — that still visits every node, O(n).
Mixed "walk only the edges" with "recurse into both kids everywhere".
Did not land the `2^h − 1` formula after two hints.
Asked coach to put the fast version in notes, did BFS on the clock.

**Gate (BFS):** O(n) time, O(n) extra (queue at widest row).

**Clock:** 15 min Easy.
Done in about 2 min.
All 5 green.

**Nit:** stored every value in an `ArrayList` just to call `.size()`.
Use an `int count`.

**Weak:** did not see that under O(n) means skipping whole subtrees without visiting them.
Formula for a perfect tree not on hand.

### Fast version (he asked for it here)

A perfect tree with h rows has `2^h − 1` nodes.
In Java that is `(1 << h) - 1`.
1 row → 1, 2 rows → 3, 3 rows → 7, 4 rows → 15.

In a complete tree, the left edge is the longest path and the right edge is the shortest.
Walk only the left edge (go left until null) and count.
Walk only the right edge (go right until null) and count.

Equal → this subtree is perfect.
Return `(1 << h) - 1` and do not recurse.

Different → return `1 + count(left) + count(right)`.

One of the two kids is always perfect, so that call stops after its edge walk.
Only one side goes deep.

Trace on `1 / 2 3 / 4 5 6`:

```
        1
       / \
      2   3
     / \  /
    4  5 6
```

Root: left edge 1-2-4 is 3, right edge 1-3 is 2.
Different, recurse.
Node 2: left 2-4 is 2, right 2-5 is 2.
Equal, return `(1 << 2) - 1 = 3` without visiting 4 and 5 one by one.
Node 3: left 3-6 is 2, right edge is 1.
Different, recurse into 6 (returns 1) and null (returns 0).
Node 3 returns `1 + 1 + 0 = 2`.
Root returns `1 + 3 + 2 = 6`.

Time: about log n calls go deep, each does an edge walk of about log n steps.
O(log² n).
Extra space: O(log n) recursion.

**Interview sentence:** perfect subtree gives its count from its height, so only one side per level needs real work.

## Also asked

DFS vs BFS time on a tree: both O(n).
Space: BFS O(widest row), DFS O(height).
Graph: O(V + E).

## Part 1 Design

**Off** (Friday).

## Calendar

Day 5 **coding closed**.
Week 6 LC done.
Sat/Sun **off**.
**Next weekday:** Week 7 Day 1.
