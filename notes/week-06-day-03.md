# Week 6 Day 3 — 2026-09-23 — extra vs notes repo

Wednesday.
Trees fill (printed bank done).
Three LCs then LC-SD.

Bank fill: #199 Right Side View, #100 Same Tree, #112 Path Sum.
Yesterday deferred #100 / #112 while he restudied BFS/DFS.

## LC 199 Binary Tree Right Side View — overtime, coach filled

Values visible from the right, top to bottom.
Example: `1 / 2 3 / null 5 null 4` → `[1, 3, 4]`.

**Gate:** first right-spine only.
Wrong when left is deeper (`1 / 2 3 / 4` misses `4`).
Then BFS.
Same size loop as #102.
Add only when `i == size - 1`.
Offer both kids.
Time O(n).
Extra space O(n).

**Clock:** 25 min Medium.
He asked for code near the end.
Coach wrote the size loop.
Notes: **overtime**.
Tests green.

**Memorize:** offer root.
While queue not empty: `size = queue.size()`, loop `size` times, poll, if last index add val, offer left/right if not null.

**Weak:** right-only walk.
`while (nextNode != null)` without advancing (infinite).
Prefer right, take left only if right null — still loses deeper left under a live right sibling.
`Queue` via `LinkedList` (null-safe) vs `ArrayDeque` (rejects null).

**Cousin:** DFS right-first, first visit per depth.

## LC 100 Same Tree — overtime, he coded

Same shape and values for `p` and `q`.

**Gate:** BFS with two queues.
Paired walk, not two separate value lists.
Return false, do not throw.
Time O(n).
Extra space O(n).

**Clock:** 15 min Easy.
Notes: **overtime**.
Tests green after he added both-null `continue`.

**Memorize (BFS):** both null → true.
One null → false.
Offer both roots.
Poll both.
Both null → continue.
One null or values differ → false.
Offer lefts and rights (nulls ok on `LinkedList`).

**Weak:** pre-check kids’ null/value before offer (redundant if poll compares).
`ArrayDeque` rejects null — do not offer null there.
Cousin DFS is shorter: nulls, values, `left` pair && `right` pair.

## LC 112 Path Sum — overtime, coach filled

Root-to-leaf sum equals `targetSum`.
Leaf = no children.

**Gate:** DFS pre-order (not post-order).
Time O(n).
Extra space O(h) stack.

**Clock:** no clean on-time start (gate talk long).
He coded with a class-field `sum`.
Undo placed wrong (before right).
Leaf check after undo returned true for `1` vs target `0`.
Ignored recursive returns.
NPE on null root.
He asked for code.
Coach wrote remaining-as-argument.
Notes: **overtime**.
Tests green.

**Memorize:** `dfs(node, remaining)`.
Null → false.
`remaining -= node.val`.
Leaf → `remaining == 0`.
Else `dfs(left, remaining) || dfs(right, remaining)`.
No shared field.
Each call’s `remaining` is a copy — left cannot spoil right.

**Weak:** shared `sum` without undo after both kids.
Check sum mid-path, not only at leaf.
`sum -=` before the right call.

**Cousin:** field `sum`, undo once at the end of the frame after both kids.

## Part 1 Design — Chapter 9 batching / timeout — done

CDN / edge **skip**.
Batching + timeout on this app.
Not YouTube.

Anton: batch when many Books; timeout when Book is slow.
Timeout half is right (Event wait).
Batch half is weak: do not merge separate user Books into one call.
Each Book keeps its own click / seats / outcome.
Batch seat checks or mail sends, not every Book POST.

**Trap:** timeout or a half-batch must not look like a ticket.

**Interview sentence:** time out the Event wait.
Batch only work that shares one ask — never fake a Book.

## Calendar

Day 3 **coding + LC-SD closed**.
Part 2/3 next (carry Factory + library LLD, then metrics).
