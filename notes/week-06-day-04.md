# Week 6 Day 4 — 2026-09-24 — extra vs notes repo

Thursday.
Trees fill (printed bank and Day 3 fills done).
Two LCs then LC-SD.
Third Easy skipped (Anton).

Anton opened with "Day 03".
Day 3 was already closed in notes, so coach corrected to Day 4 before the Cover.

Picks from Top Interview 150 trees not yet done: #236 LCA, #101 Symmetric Tree.
Third was #637 Average of Levels — skipped, stub deleted.
Unused fill for a later day: #637.

Look-up sheet added: `notes/dfs-orders.md` (pre / in / post with one tree, how to pick).

## LC 236 Lowest Common Ancestor of a Binary Tree — coach filled before 0

Deepest node that has both `p` and `q` under it.
A node counts as its own descendant.
Example: `3 / 5 1 / 6 2 0 8 / null null 7 4`, `p = 5, q = 1` → `3`; `p = 5, q = 4` → `5`.

**Gate:** first said DFS pre-order.
Reason given was the walk order (3, 5, 6, 2), which is the same for all three orders.
Bounced: the node can only decide after both kids report.
He asked for a visual of the three orders (now in `dfs-orders.md`).
Then named post-order.
Time O(n).
Extra space O(h), O(n) on a chain.

**Did not get the problem at first.**
Coach walked root-to-node paths and "last shared node".

**Clock:** 25 min Medium.
Tried a post-order list and "take the node after both" — breaks on `p = 6, q = 4` (list gives `2`, answer `5`).
Coach traced `p = 6, q = 4` bottom-up with each call's return.
He still asked for code at ~17 min.
Coach wrote it.
Notes: **coach filled**.
Tests green.

**Memorize:** null → null.
Node is `p` or `q` → return node.
`left = recurse(left)`, `right = recurse(right)`.
Both non-null → return node (the split).
Else return whichever is non-null (or null).

**Weak:** "how do I check p and q are connected" — the connection is both sides returning non-null at the same node.
Did not see that the method itself is the recursion (tried a `currRoot` field and a list).
Pre vs in vs post keeps mixing — use `dfs-orders.md`.
Asked what `left != null ? left : right` means (ternary = short if/else).

**Cousin:** #235 LCA of BST — values pick one side, no need to ask both.

## LC 101 Symmetric Tree — overtime, coach filled

Tree is a mirror of itself.
Trap: identical sides (`3 4` / `3 4`) are not mirrored.

**Gate:** first said BFS with one stack and one queue.
Bounced: a stack flips across levels, not only inside one.
Then BFS with two queues, compare outer with outer, inner with inner.
Time O(n).
Extra space O(n).

**Clock:** 15 min Easy.
One short pause (~1 min) counted against the clock.
At 0: 4 / 6 green.
Failed `exampleNotSymmetric` and `identicalSidesAreNotMirrored`.
He asked what he was missing after 0 (review, not a hint).
He said "too many ifs", asked coach to fix.
Notes: **overtime**, coach filled.

**His bugs:** offered right side as `rightNode.left, rightNode.right` (Same Tree order, not mirror).
Checked kids' nulls and values before offering, not the polled pair.
Value check only when all four kids non-null.
`root.left.val` before null check on one-sided root.
Never handled a polled null (would NPE once order was fixed).

**Memorize:** root null → true.
`leftQ` offer `root.left`, `rightQ` offer `root.right` (`LinkedList`, nulls ok).
Poll both.
Both null → continue.
One null or values differ → false.
Offer `a.left` / `b.right`, then `a.right` / `b.left`.

**Weak:** same as Day 3 Same Tree — pre-checking kids instead of the polled pair.
That is the "too many ifs".
Check the pair right after poll, offer kids blind.

**Cousin:** DFS `mirror(a, b)` = nulls, values, `mirror(a.left, b.right) && mirror(a.right, b.left)`.

## Part 1 Design — Chapter 11 rate limiting drill — done

Thursday weak recap.
Retry was already recapped Tuesday (Ch 8), so rate limit.
W4 Day 1 Ch 11 was explained, not drilled.

Prompt: bot on one account fires POST Book 50/s for event 7, seats left.

Anton: count per save / commit.
4XX too many calls.
New error response for it.

Right: 4XX family (exact is 429).
Right: separate response so user can tell it from sold out (409).
Wrong: counting per commit is too late — flood already hit `book()`, Event, DB.
Wrong: failed Books (409) never commit, so a sold-out hammer is never counted.
Where was vague ("on rate limiter").

Fix: count per caller (JWT user, else IP) per window.
Check before the handler (gateway or filter).
Shared counter (Redis) so N instances do not give N × the cap.

**Trap:** count inside `book()` or on commit.
429 must never look like 409 or a ticket.

**Interview sentence:** rate-limit per user per window with a shared counter at the edge, before `book()`.
Over the cap is 429.
Sold out stays 409.

## Calendar

Day 4 **coding + LC-SD closed**.
Part 2/3 next (PENDING poller + test; LLD hotel rooms).
