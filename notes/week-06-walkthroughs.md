# Week 6 walk-throughs — one small tree per problem

Anton asked for these on 2026-09-25, after the #236 trace.
Each one follows **his code** in `src/main/java/questions/week06/`, call by call.
Orders and BFS basics are in `notes/dfs-orders.md`.

How to read a DFS trace: every call answers one question about **its own subtree**.
Write down what each call returns.
The parent only uses those returns.

How to read a BFS trace: write the queue after every poll.
Freeze `size` at the start of each level.

---

## 104 Maximum Depth — post-order

https://leetcode.com/problems/maximum-depth-of-binary-tree/

```
        1
       / \
      2   3
     / \
    4   5
```

Each call answers: how tall is the tree under me?

Call on 4: both kids are null, so both return 0.
4 returns `max(0, 0) + 1 = 1`.

Call on 5: same as 4.
5 returns 1.

Back at 2: left = 1, right = 1.
2 returns `max(1, 1) + 1 = 2`.

Call on 3: both kids null.
3 returns 1.

Back at 1: left = 2, right = 1.
1 returns `max(2, 1) + 1 = 3`.

| Call | left | right | returns |
|---|---|---|---|
| 4 | 0 | 0 | 1 |
| 5 | 0 | 0 | 1 |
| 2 | 1 | 1 | 2 |
| 3 | 0 | 0 | 1 |
| 1 | 2 | 1 | **3** |

Post-order because 1 cannot know its height until both kids report.

---

## 226 Invert Binary Tree — pre-order

https://leetcode.com/problems/invert-binary-tree/

```
    before             after
        1                1
       / \              / \
      2   3            3   2
     / \                  / \
    4   5                5   4
```

Each call: swap my two kids, then let each kid fix its own subtree.

Call on 1: swap.
Now 1.left = 3, 1.right = 2.

Call on 1.left, which is now 3: both kids null, swapping nulls does nothing.

Call on 1.right, which is now 2: swap.
Now 2.left = 5, 2.right = 4.

Calls on 5 and 4: kids null, nothing to swap.

Return 1.

Note: after the swap, `root.left` is the **old** right.
That is fine, because both sides get visited anyway.

Swapping after the two calls (post-order) also works.
Swapping between them (in-order) breaks: the same side gets inverted twice.

---

## 112 Path Sum — pre-order

https://leetcode.com/problems/path-sum/

```
          5
         / \
        4   8
       / \
      11   3
```

`targetSum = 12`.
Answer true: 5 + 4 + 3.

Each call takes `remaining` from the parent, subtracts its own value, and passes the rest down.

`dfs(5, 12)`: remaining = 7.
Not a leaf.
Go left first.

`dfs(4, 7)`: remaining = 3.
Not a leaf.
Go left first.

`dfs(11, 3)`: remaining = -8.
Leaf.
Return `-8 == 0`, which is false.

Back at 4: left said false, so `||` tries the right.

`dfs(3, 3)`: remaining = 0.
Leaf.
Return true.

Back at 4: returns true.

Back at 5: left said true, so `||` stops.
8 is never visited.

Why 4 still had 3 after the 11 branch failed: `int` is passed by value.
Each call has its own copy of `remaining`.
Nothing needs to be "undone".

Pre-order because the node needs the running sum from **above**.

---

## 100 Same Tree — BFS with two queues

https://leetcode.com/problems/same-tree/

```
    p        q
    1        1
   /          \
  2            2
```

Answer false.
Same values, different shape.

Start: `queueP = [1]`, `queueQ = [1]`.

Poll 1 and 1.
Both non-null.
Values match.

Your code then checks the kids before offering them.
`p.right` is null, `q.right` is 2, so it returns false here.

The shorter way: offer the kids without checking.
`queueP = [2, null]`, `queueQ = [null, 2]` (left then right).

Poll 2 and null.
One is null, the other is not.
Return false.

Same answer, one poll later, and no kid checks.
That is the "too many ifs" fix: check the **polled pair**, offer kids blind.

DFS cousin, same check: both null → true.
One null or values differ → false.
Else `same(p.left, q.left) && same(p.right, q.right)`.

---

## 101 Symmetric Tree — BFS with two queues, mirror order

https://leetcode.com/problems/symmetric-tree/

```
          1
        /   \
      2a     2b
     / \     / \
    3   4   4   3
```

Answer true.

Start: `left = [2a]`, `right = [2b]`.

Poll 2a and 2b.
Values match.
Offer outer pair: `2a.left` (3) and `2b.right` (3).
Offer inner pair: `2a.right` (4) and `2b.left` (4).
Now `left = [3, 4]`, `right = [3, 4]`.

Poll 3 and 3.
Match.
Offer four nulls, two per queue.

Poll 4 and 4.
Match.
Offer four more nulls.

Poll null and null four times.
Each time `continue`.

Queues empty.
Return true.

### The trap tree

```
          1
        /   \
      2a     2b
     / \     / \
    3   4   3   4
```

Poll 2a and 2b.
Match.
Offer outer pair: `2a.left` (3) and `2b.right` (4).

Next poll: 3 and 4.
Values differ.
Return false.

The sides are **identical**, not mirrored.
If you offered `rightNode.left, rightNode.right` (Same Tree order), you would compare 3 with 3 and wrongly return true.

DFS cousin: `mirror(a, b)` = nulls, values, then `mirror(a.left, b.right) && mirror(a.right, b.left)`.

---

## 102 Level Order Traversal — BFS

https://leetcode.com/problems/binary-tree-level-order-traversal/

```
        3
       / \
      9   20
         /  \
        15   7
```

Answer `[[3], [9, 20], [15, 7]]`.

Start: queue = `[3]`.

Level 1: `size = 1`.
Poll 3, add to level list.
Add 9 and 20.
Queue = `[9, 20]`.
Level list `[3]` goes in the answer.

Level 2: `size = 2`.
Poll 9, no kids.
Poll 20, add 15 and 7.
Queue = `[15, 7]`.
Level list `[9, 20]`.

Level 3: `size = 2`.
Poll 15 and 7, no kids.
Queue empty.
Level list `[15, 7]`.

Why `size` is read once before the `for`: the queue grows while you poll.
If you used `queue.size()` inside the loop condition, 15 and 7 would land in level 2.

---

## 199 Right Side View — BFS, last in each level

https://leetcode.com/problems/binary-tree-right-side-view/

```
        1
       / \
      2   3
     /
    4
```

Answer `[1, 3, 4]`.

Level 1: queue `[1]`, size 1.
Index 0 is the last, so add 1.
Queue becomes `[2, 3]`.

Level 2: size 2.
Poll 2 at index 0, not last.
Add 4.
Poll 3 at index 1, last, so add 3.
Queue becomes `[4]`.

Level 3: size 1.
Poll 4 at index 0, last, so add 4.

The trap: 4 is on the **left** side.
"Always walk right" would stop at 3 and miss it.
You see the last node of each level, whichever side it hangs from.

---

## 230 Kth Smallest in a BST — in-order

https://leetcode.com/problems/kth-smallest-element-in-a-bst/

```
        3
       / \
      1   4
       \
        2
```

`k = 2`.
In-order is `1 2 3 4`, so the answer is 2.

`counter` is a field.
It counts nodes visited in sorted order.

`inorder(3)`: go left first.

`inorder(1)`: go left, null returns 0.
`counter` is 0, not k.
Visit 1: `counter = 1`, not k.
Go right.

`inorder(2)`: go left, null returns 0.
`counter` is 1, not k.
Visit 2: `counter = 2`, equals k.
Return 2.

Back at 1: right returned 2.
`counter == k`, so return 2.

Back at 3: left returned 2.
`counter == k`, so return 2 right away.
3 and 4 are never counted.

The `counter == k` checks after each call are the "already found, pass it up" signal.
Same idea as `left != null ? left : right` in #236.

---

## 98 Validate BST — pre-order with a range

https://leetcode.com/problems/validate-binary-search-tree/

```
        5
       / \
      3   7
         /
        4
```

Answer false.
4 is smaller than 7 (fine for its parent) but also smaller than 5 (breaks the grandparent).

Each call gets a range `(low, max)` from the parent.
Its value must sit strictly inside.

`check(5, -inf, +inf)`: 5 fits.
Your code goes right first.

`check(7, 5, +inf)`: 7 fits.
Right first: null returns true.
Then left.

`check(4, 5, 7)`: 4 is not greater than 5.
Return false.

Back at 7: false.
Back at 5: `&&` stops.
3 is never checked.

The trap: only comparing a node with its parent.
7 → 4 looks fine locally.
The range carries the grandparent's rule down.

Going right: the node's value becomes the new `low`.
Going left: the node's value becomes the new `max`.

`long` bounds so a node with `Integer.MIN_VALUE` or `Integer.MAX_VALUE` still fits.

---

## 236 Lowest Common Ancestor — post-order

https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

```
        1
       / \
      2   3
     / \
    4   5
```

### p = 4, q = 3 → 1

Each call answers: did I find `p` or `q` under me?
It returns the node it found, or null.

Call on 1: not `p` or `q`, ask left.

Call on 2: not `p` or `q`, ask left.

Call on 4: it is `p`.
Return 4 right away.

Call on 5: not `p` or `q`.
Both kids null.
Return null.

Back at 2: left = 4, right = null.
Only one side found something, so pass it up.
Return 4.

Call on 3: it is `q`.
Return 3 right away.

Back at 1: left = 4, right = 3.
Both non-null, so `p` and `q` split here.
Return 1.

| Call | left | right | returns |
|---|---|---|---|
| 4 | stops, it is `p` | | 4 |
| 5 | null | null | null |
| 2 | 4 | null | 4 |
| 3 | stops, it is `q` | | 3 |
| 1 | 4 | 3 | **1** |

### p = 2, q = 5 → 2

Call on 1, ask left.

Call on 2: it is `p`.
Return 2 right away.
It never looks at 5.

Call on 3: not `p` or `q`, kids null.
Return null.

Back at 1: left = 2, right = null.
Return 2.

Why it is right without seeing 5: the problem says both exist.
If `q` is not on the other side of anything, it must be under `p`.
So `p` is the answer.

`left != null ? left : right` is a short if/else.
It means: I am not the split, pass up whatever was found.
