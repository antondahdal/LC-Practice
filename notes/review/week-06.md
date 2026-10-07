# Week 06 — review sheet

Week 6 covered binary trees: DFS recursion (pre-order, in-order, post-order), BFS level by level with a queue, BST ranges and sorted walks, and paired walks over two trees.

Tree basics (full sheet: [`notes/dfs-orders.md`](../dfs-orders.md), traced examples: [`notes/week-06-walkthroughs.md`](../week-06-walkthroughs.md)):

- Pre-order = node, left, right: the node uses something passed down from **above** (a running sum, a range).
- In-order = left, node, right: on a BST this visits values in sorted order.
- Post-order = left, right, node: the node combines what its **kids returned** (height, split point).
- BFS = a queue, and `size = queue.size()` frozen before the inner `for` marks where one level ends.
- DFS extra space is O(h), the height; BFS extra space is O(w), the widest level (up to about n / 2).

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 104 | Maximum Depth of Binary Tree | DFS post-order | O(n) / O(h) | on-time |
| 226 | Invert Binary Tree | DFS pre-order (swap, then recurse) | O(n) / O(h) | on-time |
| 102 | Binary Tree Level Order Traversal | BFS with frozen level size | O(n) / O(n) | overtime, coach-fixed |
| 98 | Validate Binary Search Tree | DFS with a `(low, high)` range | O(n) / O(h) | overtime |
| 230 | Kth Smallest Element in a BST | In-order with a shared counter | O(h + k) / O(h) | overtime |
| 199 | Binary Tree Right Side View | BFS, last node of each level | O(n) / O(n) | overtime, coach-fixed |
| 100 | Same Tree | Paired walk (BFS two queues or DFS) | O(n) / O(n) BFS, O(h) DFS | overtime |
| 112 | Path Sum | DFS pre-order, `remaining` as an argument | O(n) / O(h) | overtime, coach-fixed |
| 236 | Lowest Common Ancestor of a Binary Tree | DFS post-order | O(n) / O(h) | coach-fixed |
| 101 | Symmetric Tree | Paired walk in mirror order | O(n) / O(n) BFS, O(h) DFS | overtime, coach-fixed |
| 103 | Binary Tree Zigzag Level Order Traversal | BFS, reverse every other row | O(n) / O(n) | on-time |
| 572 | Subtree of Another Tree | Scan every node + Same Tree check | O(n · m) / O(n + m) | untimed |
| 222 | Count Complete Tree Nodes | BFS count (his); edge heights + `2^h − 1` (fast) | O(n) / O(n); fast O(log² n) / O(log n) | on-time (O(n) version) |

---

## LC 104 Maximum Depth of Binary Tree — on-time

https://leetcode.com/problems/maximum-depth-of-binary-tree/

### The problem

Return the number of nodes on the longest path from the root down to a leaf.
An empty tree has depth `0`, and a single node has depth `1`.

```
    3
   / \
  9   20
     /  \
    15   7
```

Answer: `3` (the path `3 → 20 → 15`).

### The idea

Pattern: DFS post-order.

Each call answers one question about its own subtree: how tall am I?
A node cannot know that until both kids have reported their heights, so the work happens after the two recursive calls.
The answer is `1 + max(leftHeight, rightHeight)`, and `null` is height `0`.

Time is O(n) because every node is visited exactly once and does O(1) work.
Extra space is O(h) for the recursion stack, which is O(log n) on a balanced tree and O(n) on a chain.

### How to solve it

1. If the node is `null`, return `0`.
2. Ask the left subtree for its height.
3. Ask the right subtree for its height.
4. Return `max(left, right) + 1`.

Trace on a lopsided tree, where the deeper side is on the left:

```
      1
     / \
    2   3
   /
  4
```

| Call | left returns | right returns | returns |
|---|---|---|---|
| 4 | 0 | 0 | 1 |
| 2 | 1 | 0 | 2 |
| 3 | 0 | 0 | 1 |
| 1 | 2 | 1 | **3** |

### Holes to patch

**Complexity said as O(n log n) / O(1)**

The wrong thinking was that recursion on a tree is somehow `log n` per node, and that recursion costs no memory.
Each node is visited once, so time is O(n), not O(n log n).
Every open call sits on the stack until its kids return, so the stack is as deep as the tree.
On a chain `1 → 2 → 3 → ... → n`, that is n frames.
Rule: tree DFS is O(n) time and O(h) extra space, worst O(n).

**Thinking left is always smaller and right is always bigger**

That is the rule for a BST, not for every binary tree.
In the example, `9` sits to the left of `3` even though `9 > 3`.
"Binary" only means each node has at most two kids.
Rule: do not assume any value order unless the problem says BST.

### Memorize this

1. `null` → `0`.
2. `left = maxDepth(root.left)`.
3. `right = maxDepth(root.right)`.
4. Return `Math.max(left, right) + 1`.

```java
public int maxDepth(TreeNode root) {
    if(root==null) return 0;
    int left=maxDepth(root.left);
    int right=maxDepth(root.right);

    return Math.max(left, right)+1 ;
}
```

Say in the interview: Each call returns the height of its own subtree, and a node's height is one plus the taller kid, so it is post-order DFS in O(n) time and O(h) stack.

### How it went

On-time, and he coded it himself.
The only miss was the complexity in the gate (O(n log n) / O(1) instead of O(n) / O(h)).
The cousin is BFS, counting levels.

---

## LC 226 Invert Binary Tree — on-time

https://leetcode.com/problems/invert-binary-tree/

### The problem

Swap the left and right child of every node, all the way down, and return the root.

```
  before        after
    4             4
   / \           / \
  2   7         7   2
 / \ / \       / \ / \
1  3 6  9     9  6 3  1
```

### The idea

Pattern: DFS pre-order (swap at the node, then recurse into both kids).

Each call swaps its own two kids, then trusts each kid to fix its own subtree.
After the swap, `root.left` is the old right, and that is fine because both sides get visited anyway.
Post-order (recurse, then swap) also works.

Time is O(n) because each node is swapped once.
Extra space is O(h) for the recursion stack, worst O(n) on a chain.

### How to solve it

1. If the node is `null`, return `null`.
2. Save `tmp = root.right`, set `root.right = root.left`, set `root.left = tmp`.
3. Invert the new left subtree.
4. Invert the new right subtree.
5. Return the node.

Trace on `1 / 2 3 / 4 5`:

| Call | kids before swap | kids after swap | then recurse into |
|---|---|---|---|
| 1 | 2, 3 | 3, 2 | 3, then 2 |
| 3 | null, null | null, null | nothing |
| 2 | 4, 5 | 5, 4 | 5, then 4 |
| 5 | null, null | null, null | nothing |
| 4 | null, null | null, null | nothing |

Result: `1 / 3 2 / _ _ 5 4`.

### Holes to patch

**Complexity said as O(n log n) / O(1)**

Same miss as Max Depth.
One visit per node is O(n), and the recursion stack is O(h).
Rule: every "visit each node once" DFS is O(n) time, O(h) extra space.

**`ArrayDeque` rejects `null`**

His solution was right, but the JUnit helper that rebuilt the tree used an `ArrayDeque` and blew up with an NPE.
After an invert, a node's left kid can be `null`, and `ArrayDeque.add(null)` throws `NullPointerException`.
Rule: if you must offer `null` kids into a queue, use `LinkedList`; otherwise check `!= null` before offering.

**Classic trap: swapping between the two calls (in-order)**

If you invert the left subtree, then swap, then invert `root.right`, the "right" you visit is the subtree you already inverted.
On `1 / 2 3 / 4 5`, node `2` gets inverted twice and ends back as `4 5`.
Rule: swap before both calls or after both calls, never between.

### Memorize this

1. `null` → return `null`.
2. Save right in `tmp`, move left to right, put `tmp` on the left.
3. Recurse into both kids.
4. Return the node.

```java
public TreeNode invertTree(TreeNode root) {
    if (root==null) return null;

    TreeNode tmp=root.right;
    root.right=root.left;
    root.left=tmp;
    invertTree( root.left);
    invertTree( root.right);
    return root;
}
```

Say in the interview: Each call swaps its two children and then inverts both subtrees, visiting every node once, so it is O(n) time and O(h) stack.

### How it went

On-time, and he coded it himself.
The swap plus both recursive calls was right on the first try.
The NPE came from the test helper, not from his code, and the gate complexity was wrong again.

---

## LC 102 Binary Tree Level Order Traversal — overtime, coach-fixed

https://leetcode.com/problems/binary-tree-level-order-traversal/

### The problem

Return the node values grouped by depth, each row left to right.

```
    3
   / \
  9   20
     /  \
    15   7
```

Answer: `[[3], [9, 20], [15, 7]]`.

### The idea

Pattern: BFS with a queue, one level per outer loop.

A queue is first in, first out, so nodes come out in the same order they went in, level by level.
The trick is knowing where one level ends.
At the start of each level, the queue holds exactly that level and nothing else.
So you freeze `size = queue.size()` and poll exactly `size` nodes into one row.
Kids you offer during those polls belong to the next level.

Time is O(n) because every node is offered and polled once.
Extra space is O(n) for the queue, which holds up to the widest level (about n / 2 on a full tree); the output list is not counted.

### How to solve it

1. If the root is `null`, return an empty list (not `null`).
2. Offer the root.
3. While the queue is not empty, read `size = queue.size()` once.
4. Make a new row `tmpArr`, and loop `size` times: `remove()` a node, add its value, offer the non-null left and right kids.
5. Add the row to `answer`.

Trace on a full tree `1 / 2 3 / 4 5 6 7`, where two parents feed one row:

| Level | size | polled | queue after | row |
|---|---|---|---|---|
| 1 | 1 | 1 | 2 3 | `[1]` |
| 2 | 2 | 2, 3 | 4 5 6 7 | `[2, 3]` |
| 3 | 4 | 4, 5, 6, 7 | empty | `[4, 5, 6, 7]` |

### Holes to patch

**A plain recursive walk is not level order**

The first idea was recursion with one list.
A DFS walk visits `3, 9, 20, 15, 7` but has no idea where one depth ends.
Rule: grouping by depth needs BFS, or a DFS that carries a `depth` index into `List<List<Integer>>`.

**`new Queue<>()` does not compile**

`Queue` is an interface, so it has no constructor.
Rule: pick a class that implements it.

```java
Queue<TreeNode> queue = new ArrayDeque<>();
```

**One row per parent**

The wrong thinking was "peel one parent, its kids are the next row".
On `1 / 2 3 / 4 5 6 7`, that gives rows `[4, 5]` and `[6, 7]` instead of `[4, 5, 6, 7]`.
Behind it was the idea that a binary tree has only two nodes per level.
Binary means at most two kids **per node**; a level can hold many nodes.
Rule: a row is everything polled during one frozen `size`, from all parents of that level.

**Looping on the live `queue.size()`**

If the inner loop is `for (i = 0; i < queue.size(); i++)`, the bound changes as you poll and offer.
On `3 / 9 20 / 15 7` you get `[[3, 9], [20, 15], [7]]`.
Rule: read `size` once, before the inner `for`.

```java
int size = queue.size();
ArrayList<Integer> tmpArr = new ArrayList<>();
for (int i = 0; i < size; i++) {
```

**Special cases that are not needed**

He returned `null` for an empty tree, then fixed it to an empty list.
He also tried a `rowCount = 2` counter and a special first row for the root.
The frozen `size` handles the root row (size `1`) and every other row with no special case.
Rule: empty input returns an empty list, and the loop needs no extra counters.

### Memorize this

1. `null` root → empty list.
2. Offer root.
3. While queue not empty: `size = queue.size()`.
4. New `tmpArr`; loop `size` times: `remove()`, add value, offer non-null kids.
5. Add `tmpArr` to `answer`.

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> answer = new ArrayList<List<Integer>>();
    if (root == null) return answer;

    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.add(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        ArrayList<Integer> tmpArr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode tmp = queue.remove();
            tmpArr.add(tmp.val);
            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
        }
        answer.add(tmpArr);
    }

    return answer;
}
```

Say in the interview: I run BFS and freeze the queue size at the start of each level, so exactly that many polls make one row, which is O(n) time and O(n) queue space.

### How it went

Overtime, and the coach wrote the size loop after he asked near the clock.
The one-row-per-parent idea and the missing frozen `size` cost the clock.
The cousin is DFS with a `depth` index.

---

## LC 98 Validate Binary Search Tree — overtime

https://leetcode.com/problems/validate-binary-search-tree/

### The problem

Is the tree a valid BST?
Every value in a node's left subtree must be strictly smaller than the node, every value in the right subtree strictly bigger, and both subtrees must also be BSTs.

```
    5
   / \
  3   7
     /
    4
```

Answer: `false`.
`4 < 7` is fine for its parent, but `4` sits in the right subtree of `5`, so it must be bigger than `5`.

### The idea

Pattern: DFS pre-order with a range `(low, high)` passed down as arguments.

Each node must sit strictly inside a window set by all its ancestors.
Going left, the node's value becomes the new upper bound.
Going right, the node's value becomes the new lower bound.
Because the bounds are arguments, each call has its own copy, and the left call can never spoil the right call's window.

Time is O(n) because each node is checked once.
Extra space is O(h) for the recursion stack, worst O(n) on a chain.

### How to solve it

1. Call `check(root, Long.MIN_VALUE, Long.MAX_VALUE)`.
2. `null` → `true`.
3. If `root.val <= low` or `root.val >= max`, return `false`.
4. Check the right kid with `(root.val, max)` and the left kid with `(low, root.val)`.
5. Return `right && left`.

Trace on the tree above (right kid first, as in his code):

| Call | window | fits? | returns |
|---|---|---|---|
| 5 | (−∞, +∞) | yes | depends on kids |
| 7 | (5, +∞) | yes | depends on kids |
| null (right of 7) | — | — | true |
| 4 | (5, 7) | no, `4 <= 5` | **false** |
| 7 | — | — | false |
| 5 | — | — | **false** (`&&` stops, so `3` is never checked) |

### Holes to patch

**Only comparing a node with its parent (or the root, or the parent plus the root)**

A local check sees `7 → 4` and says fine.
On the tree above that returns `true`, but the answer is `false`.
Rule: every ancestor narrows the window, so carry `(low, high)` all the way down.

**Bounds kept as locals or class fields**

Locals `minVal` / `maxVal` inside `isValidBST` reset on every call.
Class fields are one shared box, so the left call overwrites them before the right call runs.
Rule: bounds are method arguments, one private copy per call.

**`Integer.MIN_VALUE` / `Integer.MAX_VALUE` as the starting window**

A single node with value `Integer.MIN_VALUE` is a valid BST.
With `low = Integer.MIN_VALUE`, the check `val <= low` is true, so you return `false`.
Rule: start with `long` bounds, which are outside the `int` range.

```java
return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
```

**Boxed `Long` parameters do not take an `int`**

Passing `root.val` (an `int`) into a `Long` parameter is a compile error, because Java does not widen and box in one step.
Rule: use primitive `long` for the bounds.

**Equal values are invalid**

The rule is strictly less and strictly greater.
On `2 / 2`, a check with `<` / `>` returns `true`, but the answer is `false`.
Rule: reject with `<=` and `>=`.

### Memorize this

1. `check(root, low, max)`, first call with `long` min and max.
2. `null` → `true`.
3. `root.val <= low || root.val >= max` → `false`.
4. Right gets `(root.val, max)`, left gets `(low, root.val)`.
5. Return `right && left`.

```java
public boolean isValidBST(TreeNode root) {
    return check(root,Long.MIN_VALUE,Long.MAX_VALUE);
}

private boolean check(TreeNode root,long low,long max){

    if (root==null) return true;
    if (root.val <= low || root.val >= max) return false;
    return check(root.right,root.val, max) &&
        check(root.left,low, root.val) ;
}
```

Say in the interview: Each node must fit strictly inside a range set by its ancestors, so I pass `low` and `max` down, tightening one side per step, which is O(n) time and O(h) stack.

### How it went

Overtime; he kept going after the clock and coded it himself.
The shared-bounds idea and the `Integer.MIN_VALUE` edge cost the most time.
His version recurses right first, which is fine, because order does not change the bounds.
The cousin is an in-order walk where each value must be strictly bigger than the previous one.

---

## LC 230 Kth Smallest Element in a BST — overtime

https://leetcode.com/problems/kth-smallest-element-in-a-bst/

### The problem

Return the `k`th smallest value in a BST (1-based).
Do not change the tree.

```
    3
   / \
  1   4
   \
    2
```

`k = 2` → `2` (sorted order is `1 2 3 4`).

### The idea

Pattern: in-order DFS with a shared counter.

In-order (left, node, right) on a BST visits values in sorted order.
So the `k`th node you visit in in-order is the answer.
The counter must be **one shared box** (a field), because every call needs to see how many nodes were already visited anywhere in the tree.
This is the opposite of Validate BST, where the bounds must be private copies.

Time is O(h + k) because you walk down to the smallest value and then visit `k` nodes before stopping; worst case O(n).
Extra space is O(h) for the recursion stack.

### How to solve it

1. Keep a field `counter = 0`, shared by every call.
2. `inorder(node, k)`: `null` → `0` (nothing found here).
3. `left = inorder(node.left, k)`; if `counter == k`, the answer was found on the left, so return `left`.
4. `counter++`; if `counter == k`, return `node.val`.
5. `right = inorder(node.right, k)`; if `counter == k`, return `right`.
6. Otherwise return `0`.

Trace on the tree above, `k = 2`:

| Step | node | counter after | returns |
|---|---|---|---|
| go left from 3, then left from 1 hits null | — | 0 | 0 |
| visit 1 | 1 | 1 | not yet, go right |
| left of 2 is null | — | 1 | 0 |
| visit 2 | 2 | 2 | **2** (`counter == k`) |
| back at 1, `right = 2` | 1 | 2 | 2 |
| back at 3, `left = 2` | 3 | 2 | **2**, so 3 and 4 are never counted |

### Holes to patch

**"Go left `k` times from the root"**

The first idea mixed depth with rank.
On the tree above with `k = 2`, two lefts from `3` go `3 → 1 → null`.
The answer is `2`, which hangs off the right of `1`.
Rule: rank in a BST comes from in-order position, not from depth.

**Pre-order vs in-order**

He did not know the three orders by name at first.
Pre-order visits `3 1 2 4`, which is not sorted.
Rule: on a BST, sorted order is in-order: left, node, right.

**Counter as a local or a copied argument**

`int counter` inside the helper, or passed as an argument, is a private copy per call.
The child's `counter++` never reaches the parent.
On `2 / 1 3` with `k = 2`, the parent still has `0` after visiting `1`, counts itself as first, and returns `3` instead of `2`.
Rule: the count must be shared across calls, so it is a field.

**Ignoring the recursive return**

If you call `inorder(node.left)` and throw away its result, a hit on the left dies there.
The parent keeps counting past `k` and returns `0`.
On the tree above with `k = 2`, that returns `0` instead of `2`.
Rule: after each recursive call, if the answer is already found, return it right away.

**`return node.left.val` instead of `node.val`**

When the count hits `k`, the current node is the answer, not its kid.
Rule: return `node.val` at the visit.

**The field is never reset**

`counter` starts at `0` only when the object is created.
Calling `kthSmallest` twice on the same object starts the second call with the old count and returns `0`.
Rule: reset the shared counter at the top of the public method.

```java
counter = 0;
```

### Memorize this

1. Field `counter = 0`.
2. `null` → `0`.
3. `left = inorder(node.left, k)`; `counter == k` → return `left`.
4. `counter++`; `counter == k` → return `node.val`.
5. `right = inorder(node.right, k)`; `counter == k` → return `right`.
6. Return `0`.

```java
int counter=0;

public int kthSmallest(TreeNode root, int k) {
    return  inorder(root,k);
}
int inorder(TreeNode node,int k) {

    if (node == null) return 0;
    int left = inorder(node.left, k);
    if (counter == k) return left;// left
    counter++;
    if (counter==k) return node.val;
    int right =inorder(node.right,k);
    if (counter == k) return right;
    return 0;  // right
}
```

Say in the interview: In-order on a BST visits values in sorted order, so I count visits with a shared counter and stop at the `k`th, which is O(h + k) time and O(h) stack.

### How it went

Overtime; he kept going after the clock and coded it himself.
The coach explained the three DFS orders with the clock off, and the counter-as-copy plus dropped returns cost the clock.
The cousin is collecting in-order into a list and returning `get(k - 1)`, which costs O(n) extra space.

---

## LC 199 Binary Tree Right Side View — overtime, coach-fixed

https://leetcode.com/problems/binary-tree-right-side-view/

### The problem

Stand on the right side of the tree and list the values you can see, top to bottom.
That is the last node of each level, whichever side it hangs from.

```
    1
   / \
  2   3
 /
4
```

Answer: `[1, 3, 4]`.
`4` is visible because nothing on its level is to the right of it.

### The idea

Pattern: BFS level by level, keeping only the last node of each level.

This is Level Order with one change: instead of storing a whole row, you store the value at index `size - 1`.
Both kids are always offered, because a deeper left branch can be the only node on its level.

Time is O(n) because every node is polled once.
Extra space is O(n) for the queue at the widest level.

### How to solve it

1. `null` root → empty list.
2. Offer the root.
3. While the queue is not empty, freeze `size`.
4. Loop `size` times: `remove()` into `currNode`; if `i == size - 1`, add its value to `retList`; offer non-null left, then right.

Trace on the tree above:

| Level | size | polled (index) | last? | queue after | output |
|---|---|---|---|---|---|
| 1 | 1 | 1 (0) | yes | 2 3 | `[1]` |
| 2 | 2 | 2 (0) | no | 3 4 | `[1]` |
| 2 | 2 | 3 (1) | yes | 4 | `[1, 3]` |
| 3 | 1 | 4 (0) | yes | empty | `[1, 3, 4]` |

### Holes to patch

**Walking only the right spine**

The first idea was "go right until null".
On the tree above that returns `[1, 3]` and misses `4`.
Rule: you see the last node of each **level**, not the right edge of the tree.

**Prefer right, take left only when right is null**

This fixes a node with only a left kid, but not a deeper left branch under a node whose right sibling is alive.
On the tree above, at `1` you pick `3`, `3` has no kids, and you stop without ever seeing `4`.
Rule: explore both sides and let the level decide who is last.

**`while (nextNode != null)` without advancing**

The loop never moved `nextNode`, so it never ended.
Rule: every loop that walks a pointer must reassign it inside the body.

**`ArrayDeque` vs `LinkedList`**

`ArrayDeque` throws on `null`, `LinkedList` accepts it.
His final code checks `!= null` before offering, so either works.
Rule: check kids before offering, or use `LinkedList` if you offer nulls on purpose.

### Memorize this

1. `null` root → empty list.
2. Offer root.
3. Freeze `size` each level.
4. `remove()` `size` times; add the value to `retList` when `i == size - 1`.
5. Offer non-null left, then right.

```java
public List<Integer> rightSideView(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    ArrayList<Integer> retList = new ArrayList<>();
    if (root == null) return retList;

    queue.add(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode currNode = queue.remove();
            if (i == size - 1) {
                retList.add(currNode.val);
            }
            if (currNode.left != null) {
                queue.add(currNode.left);
            }
            if (currNode.right != null) {
                queue.add(currNode.right);
            }
        }
    }

    return retList;
}
```

Say in the interview: It is level-order BFS where I keep only the last node polled on each level, so a deeper left branch still shows up, in O(n) time and O(n) space.

### How it went

Overtime, and the coach wrote the size loop after he asked near the end.
The right-spine idea and the infinite `while` cost the clock.
The cousin is DFS right-first, recording the first node seen at each new depth.

---

## LC 100 Same Tree — overtime

https://leetcode.com/problems/same-tree/

### The problem

Are trees `p` and `q` identical: same shape and same values at every position?

```
  p      q
  1      1
 /        \
2          2
```

Answer: `false` (same values, different shape).

### The idea

Pattern: a paired walk over both trees at the same time.

You compare the two nodes that sit in the same position.
Both `null` means this position matches.
One `null`, or different values, means the trees differ.
Otherwise the kids must match pairwise: left with left, right with right.
The walk can be BFS with two queues (his version) or DFS recursion (shorter).

Time is O(n) because each position is compared once (n = the smaller tree, since you stop at the first difference).
Extra space is O(n) for the two queues in BFS, or O(h) for the stack in DFS.

### How to solve it

BFS version (his):

1. Both roots `null` → `true`; exactly one `null` → `false`.
2. Add `p` into `queuep` and `q` into `queueq` (both `LinkedList`, nulls are allowed).
3. Loop while either queue is not empty; if only one is empty → `false`.
4. Remove `cuurP` and `currQ`.
5. Both `null` → `continue`; one `null`, or values differ → `false`.
6. Pre-check the right kids, then the left kids: one `null` or different values → `false`.
7. Add `cuurP.left`, `cuurP.right` to `queuep` and `currQ.left`, `currQ.right` to `queueq`.
8. Loop ends → `true`.

Trace on the trees above:

| Step | removed pair | check | queuep after | queueq after |
|---|---|---|---|---|
| 1 | 1, 1 | pair matches | — | — |
| 1 | — | right kids: `null` vs `2` | — | — |
| — | — | return **false** before anything is added | — | — |

Without the kid pre-checks (step 6), the walk adds `2, null` and `null, 2`, removes the pair `2, null` next, and returns `false` there.

### Holes to patch

**Pre-checking the kids before offering them ("too many ifs")**

His code compares `p.left` with `q.left` and `p.right` with `q.right` (nulls and values) before it offers them.
That is the same check the next poll will do anyway, written a second time.
It gives the right answer but doubles the ifs and makes it easy to miss a case.
Rule: check only the pair you just polled, and offer the kids blind.

**Missing the both-`null` `continue`**

Once null kids are offered, you will poll a `(null, null)` pair.
Without `continue`, the next line reads `.val` on `null` or returns `false`.
On `p = 1 / 2`, `q = 1 / 2`, the missing right kids form a `(null, null)` pair, and the answer must still be `true`.
Rule: first line after the poll is `if (a == null && b == null) continue;`.

**Offering `null` into an `ArrayDeque`**

`ArrayDeque.add(null)` throws `NullPointerException`.
Rule: use `LinkedList` when the queue must hold nulls.

**Throwing on a mismatch**

A mismatch is a normal answer, not an error.
Rule: `return false`, do not throw.

**Shorter and less space: the recursive form**

The same two checks on the pair, then `isSameTree(p.left, q.left) && isSameTree(p.right, q.right)`, need no queues.
That is O(h) stack instead of O(n) queue space.

### Memorize this

1. Both roots `null` → `true`; one `null` → `false`.
2. Two `LinkedList` queues, add `p` and `q`.
3. While either is not empty: one empty → `false`.
4. Remove a pair: both `null` → `continue`; one `null` or values differ → `false`.
5. Pre-check right kids, then left kids (null mismatch or value mismatch → `false`).
6. Add left and right kids of both into their queues.
7. Loop ends → `true`.

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    if(p==null&&q==null) return true;
    if((p==null&&q!=null)||(p!=null&&q==null)) return false;
    Queue<TreeNode> queuep = new LinkedList<>();
    Queue<TreeNode> queueq = new LinkedList<>();
    queuep.add(p);
    queueq.add(q);
    while(!queuep.isEmpty()||!queueq.isEmpty()){
        if(queuep.isEmpty()||queueq.isEmpty()) return false;
        TreeNode cuurP=queuep.remove();
        TreeNode currQ=queueq.remove();
        if((cuurP==null && currQ==null )) continue;
        if((cuurP==null && currQ!=null )||(cuurP!=null && currQ==null )
            ||(cuurP.val!=currQ.val))  return false;

        if((cuurP.right!=null &&currQ.right==null)||(cuurP.right==null &&currQ.right!=null)
        ) return false;
        if((cuurP.right!=null &&currQ.right!=null)&&(cuurP.right.val!=currQ.right.val))return false;

        if((cuurP.left!=null &&currQ.left==null)||(cuurP.left==null &&currQ.left!=null)
        ) return false;
        if( (cuurP.left!=null &&currQ.left!=null)&&(cuurP.left.val!=currQ.left.val))return false;

        queuep.add(cuurP.left);
        queuep.add(cuurP.right);
        queueq.add(currQ.left);
        queueq.add(currQ.right);

    }

    return true;
}
```

Say in the interview: I walk both trees in lockstep with two queues, and at each position both null is a match while one null or different values is a mismatch, which is O(n) time and O(n) queue space.

### How it went

Overtime; he coded the two-queue BFS himself.
Tests went green after he added the both-null `continue`.
The pre-checking of kids stayed in his code and came back as the main hole in Symmetric Tree and Subtree.

---

## LC 112 Path Sum — overtime, coach-fixed

https://leetcode.com/problems/path-sum/

### The problem

Is there a root-to-**leaf** path whose values add up to `targetSum`?
A leaf is a node with no children.

```
      5
     / \
    4   8
   / \
  11  3
```

`targetSum = 12` → `true` (`5 + 4 + 3`).

### The idea

Pattern: DFS pre-order, passing `remaining` down as an argument.

Each call subtracts its own value from what the parent passed and hands the rest to its kids.
Only at a leaf do you check `remaining == 0`.
Because `remaining` is an `int` argument, each call has its own copy.
When the left branch fails, the right branch still starts from the parent's value, so nothing needs to be undone.

Time is O(n) because each node is visited at most once.
Extra space is O(h) for the recursion stack.

### How to solve it

1. `null` → `false`.
2. `remaining -= node.val`.
3. If the node is a leaf, return `remaining == 0`.
4. Otherwise return `dfs(left, remaining) || dfs(right, remaining)`.

Trace on the tree above, `targetSum = 12`:

| Call | remaining in | remaining after subtract | leaf? | returns |
|---|---|---|---|---|
| 5 | 12 | 7 | no | depends on kids |
| 4 | 7 | 3 | no | depends on kids |
| 11 | 3 | −8 | yes | false |
| 3 | 3 | 0 | yes | **true** |
| 4 | — | — | — | true |
| 5 | — | — | — | **true** (8 is never visited) |

### Holes to patch

**A shared `sum` field with the undo in the wrong place**

He kept one class field `sum` and undid the node's value before the right call.
Then the right branch starts without this node's value.
On `1` with only a right kid `3`, `targetSum = 4`: after the left call the undo leaves `sum = 0`, the right leaf makes `sum = 3`, and you return `false`.
The answer is `true`.
Rule: pass `remaining` as an argument so there is nothing to undo.

**Leaf check after the undo**

With the undo before the leaf check, a single node `1` with `targetSum = 0` ends at `sum = 0` and returns `true`.
The answer is `false`.
Rule: the leaf check uses the sum that includes this node.

**Checking the sum mid-path**

On `1 / 2` with `targetSum = 1`, the root alone reaches `1`, but the root is not a leaf.
The answer is `false`.
Rule: only compare at a node with no children.

```java
if (root.left == null && root.right == null) {
    return remaining == 0;
}
```

**Ignoring the recursive returns**

Calling `dfs(left)` and `dfs(right)` without using their results loses a `true` found below.
Rule: `return dfs(left, remaining) || dfs(right, remaining);`

**NPE on a `null` root**

He read `root.val` before checking `null`.
An empty tree has no path, so the answer is `false`.
Rule: first line is `if (node == null) return false;`.

### Memorize this

1. `null` → `false`.
2. `remaining -= node.val`.
3. Leaf → `remaining == 0`.
4. Else `left || right`, each with its own copy of `remaining`.

```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    return dfs(root, targetSum);
}

private boolean dfs(TreeNode root, int remaining) {
    if (root == null) return false;

    remaining -= root.val;

    if (root.left == null && root.right == null) {
        return remaining == 0;
    }

    return dfs(root.left, remaining) || dfs(root.right, remaining);
}
```

Say in the interview: I pass the remaining sum down as an argument and only test it at leaves, so each branch has its own copy and nothing needs undoing, which is O(n) time and O(h) stack.

### How it went

Overtime, and the coach wrote the remaining-as-argument version after he asked.
The gate talk ran long, and the shared `sum` field with a misplaced undo cost the clock.
The cousin is a field `sum` with one undo at the end of the frame, after both kids.

---

## LC 236 Lowest Common Ancestor of a Binary Tree — coach-fixed

https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

### The problem

Given two nodes `p` and `q` in a binary tree, return the deepest node that has both under it.
A node counts as its own descendant.
Both nodes exist and are different.

```
        3
      /   \
     5     1
    / \   / \
   6   2 0   8
      / \
     7   4
```

`p = 5, q = 1` → `3`.
`p = 5, q = 4` → `5`.
`p = 6, q = 4` → `5`.

### The idea

Pattern: DFS post-order.

Each call answers: did I find `p` or `q` in my subtree?
It returns the node it found (or the answer, once found), or `null`.
A node can only decide after both kids have reported, which is why it is post-order.
If both sides return non-null, `p` and `q` split at this node, so this node is the answer.
If only one side is non-null, pass that up unchanged.
If the node itself is `p` or `q`, return it right away; if the other one is below it, this node is still the answer.

Time is O(n) because each node is visited at most once.
Extra space is O(h) for the recursion stack, O(n) on a chain.

### How to solve it

1. `null` → `null`.
2. If the node is `p` or `q`, return the node.
3. `left = recurse(node.left)`, `right = recurse(node.right)`.
4. Both non-null → return the node.
5. Else return whichever is non-null (or `null`).

Trace on the tree above, `p = 6, q = 4` (the case that broke his list idea):

| Call | left returns | right returns | returns |
|---|---|---|---|
| 6 | — (it is `p`) | — | 6 |
| 7 | null | null | null |
| 4 | — (it is `q`) | — | 4 |
| 2 | null | 4 | 4 |
| 5 | 6 | 4 | **5** (split) |
| 0 | null | null | null |
| 8 | null | null | null |
| 1 | null | null | null |
| 3 | 5 | null | **5** |

### Holes to patch

**Calling it pre-order because of the walk order**

The reason given was the visiting order `3, 5, 6, 2`.
That walk is the same for pre-, in-, and post-order; only when the node does its work changes.
Here the node needs both kids' answers before it can decide.
Rule: needs answers from below → post-order.

**Post-order list, then "take the node after both"**

He listed nodes in post-order and picked the first node after both `p` and `q`.
Post-order here is `6 7 4 2 5 0 8 1 3`.
With `p = 6, q = 4`, the next node after both is `2`, but the answer is `5`.
Rule: a flat list loses the shape; use what each call returns.

**"How do I check that `p` and `q` are connected?"**

There is no separate connection check.
The connection is two non-null returns meeting at the same node.
Rule: `left != null && right != null` → this node is the answer.

**Not seeing that the method itself is the recursion**

He tried a `currRoot` field and a list instead of calling `lowestCommonAncestor` on the kids.
The method's own return value already carries "found `p`", "found `q`", or "found the answer".
Rule: no fields, no list; recurse and combine the two returns.

**What `left != null ? left : right` means**

It is a short if/else (ternary).
It means: I am not the split, so pass up whatever was found below, or `null`.

### Memorize this

1. `null` → `null`.
2. Node is `p` or `q` → return node.
3. Recurse left and right.
4. Both non-null → return node.
5. Else return the non-null one.

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    if (root == p || root == q) return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

Say in the interview: Each call returns `p`, `q`, or the answer found in its subtree, and the first node where both sides return non-null is the split point, which is post-order DFS in O(n) time and O(h) stack.

### How it went

Coach-fixed before the clock ran out; he asked for the code after the bottom-up trace.
He did not get the problem at first, and the pre- vs post-order mix-up cost time in the gate.
The cousin is LC 235 (LCA of a BST), where values tell you which side to go without asking both.

---

## LC 101 Symmetric Tree — overtime, coach-fixed

https://leetcode.com/problems/symmetric-tree/

### The problem

Is the tree a mirror image of itself around its center?

```
      1
    /   \
   2     2
  / \   / \
 3   4 4   3
```

Answer: `true`.

The trap: identical sides are not mirrored.

```
      1
    /   \
   2     2
  / \   / \
 3   4 3   4
```

Answer: `false`.

### The idea

Pattern: a paired walk, like Same Tree, but in mirror order.

Compare the left subtree with the right subtree.
Two nodes mirror each other when both are `null`, or when their values match and their kids mirror each other crosswise.
Crosswise means outer with outer (`a.left` with `b.right`) and inner with inner (`a.right` with `b.left`).
With BFS you keep two queues and offer those pairs in the same order.

Time is O(n) because each node is compared once.
Extra space is O(n) for the queues in BFS, or O(h) for the stack in DFS.

### How to solve it

BFS version (his, after the fix):

1. `null` root → `true`.
2. Add `root.left` into queue `left` and `root.right` into queue `right` (`LinkedList`).
3. While `left` is not empty, remove `leftNode` and `rightNode`.
4. Both `null` → `continue`.
5. One `null` → `false`; values differ → `false`.
6. Add `leftNode.left` into `left` and `rightNode.right` into `right` (outer pair).
7. Add `leftNode.right` into `left` and `rightNode.left` into `right` (inner pair).

Trace on the trap tree (identical sides):

| Step | removed pair | check | `left` after | `right` after |
|---|---|---|---|---|
| 1 | 2, 2 | match | 3, 4 | 4, 3 |
| 2 | 3, 4 | values differ | — | — |
| — | — | return **false** | — | — |

If `right` got `rightNode.left, rightNode.right` (Same Tree order), step 2 would compare `3` with `3` and the walk would wrongly return `true`.

### Holes to patch

**One stack plus one queue**

The idea was that a stack "reverses" one side.
A stack reverses across levels too, so the pairs from different depths get mixed.
Rule: two queues, and the mirror comes from the order you offer the kids.

**Offering the right side in Same Tree order**

He offered `rightNode.left, rightNode.right`.
On the trap tree that compares `3` with `3` and `4` with `4`, so it returns `true`.
Rule: offer outer then inner.

```java
left.add(leftNode.left);
right.add(rightNode.right);
left.add(leftNode.right);
right.add(rightNode.left);
```

**Pre-checking the kids instead of the polled pair**

He checked the four kids' nulls and values before offering them, and only compared values when all four kids were non-null.
That skips every case where one kid is `null`, which is exactly the shape of `1 / 2 2 / _ 3 _ 3` (both `3`s are right kids, answer `false`), and that test failed at the clock.
Rule: same as Same Tree; check the pair right after the poll, offer kids blind.

**`root.left.val` before a `null` check**

On `1 / 2` (no right kid), reading `root.right.val` throws an NPE.
The answer is simply `false`.
Rule: never read `.val` until the polled pair passed the null checks.

**Never handling a polled `null`**

Once kids are offered blind, `(null, null)` pairs come out of the queues.
Rule: `if (leftNode == null && rightNode == null) continue;` comes first.

**Less space: the recursive form**

A helper `mirror(a, b)` with the same checks, returning `mirror(a.left, b.right) && mirror(a.right, b.left)`, needs no queues.
That is O(h) stack instead of O(n) queue space.

### Memorize this

1. `null` root → `true`.
2. Queue `left` gets `root.left`, queue `right` gets `root.right`.
3. Remove a pair: both `null` → `continue`.
4. One `null` → `false`; values differ → `false`.
5. Add outer pair (`leftNode.left`, `rightNode.right`), then inner pair (`leftNode.right`, `rightNode.left`).
6. Loop ends → `true`.

```java
public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;

    Queue<TreeNode> left = new LinkedList<>();
    Queue<TreeNode> right = new LinkedList<>();
    left.add(root.left);
    right.add(root.right);

    while (!left.isEmpty()) {
        TreeNode leftNode = left.remove();
        TreeNode rightNode = right.remove();

        if (leftNode == null && rightNode == null) continue;
        if (leftNode == null || rightNode == null) return false;
        if (leftNode.val != rightNode.val) return false;

        left.add(leftNode.left);
        right.add(rightNode.right);
        left.add(leftNode.right);
        right.add(rightNode.left);
    }
    return true;
}
```

Say in the interview: I compare the left and right subtrees as pairs with two queues, where both null is a match and otherwise the values must match and I enqueue the outer pair and then the inner pair, which is O(n) time and O(n) queue space.

### How it went

Overtime, and the coach filled it after he asked.
At the clock, 4 of 6 tests were green; the Same Tree offer order and the pre-checked kids ("too many ifs") cost the clock.
His repo version is the fixed two-queue BFS.

---

## LC 103 Binary Tree Zigzag Level Order Traversal — on-time

https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/

### The problem

Return the values level by level, but alternate direction: the first row left to right, the next right to left, and so on.

```
    3
   / \
  9   20
     /  \
    15   7
```

Answer: `[[3], [20, 9], [15, 7]]`.

### The idea

Pattern: BFS level by level (Level Order), reversing every other row.

The queue order never changes: always offer left, then right.
Only the finished row is flipped on odd levels.
A boolean flag starts `false` at the root and flips once per level.

Time is O(n) because every node is polled once, and reversing all rows is O(n) in total.
Extra space is O(n) for the queue at the widest level.

### How to solve it

1. `null` root → empty list.
2. A root with no kids → `[[root.val]]` (his special case; the loop would handle it anyway).
3. `flag = false` (it means "reverse this row"), offer the root.
4. Freeze `size`, `remove()` `size` nodes into `tmpList`, offer left then right.
5. If `flag`, reverse `tmpList`, add it, set `flag = false`.
6. Else add `tmpList` as is, set `flag = true`.

Trace on `1 / 2 3 / 4 5 6 7`:

| Level | row as polled | direction | row added |
|---|---|---|---|
| 1 | `[1]` | left to right | `[1]` |
| 2 | `[2, 3]` | right to left | `[3, 2]` |
| 3 | `[4, 5, 6, 7]` | left to right | `[4, 5, 6, 7]` |

### Holes to patch

**Saying "BST" when meaning BFS**

BST is a kind of tree (ordered values), not a way to walk one.
Rule: the walk is BFS (queue, level by level) or DFS (recursion, depth first).

**Classic trap: flipping the queue order instead of the row**

If you offer kids right-then-left on reversed levels, the next level comes out scrambled.
On `1 / 2 3 / 4 5 6 7`, polling `2` then `3` and offering right first gives a queue `5 4 7 6`, which is neither `[4, 5, 6, 7]` nor `[7, 6, 5, 4]`.
Rule: always offer left then right; only reverse the finished row.

**Extra special case and double flag flips (nits)**

A single-node special case is not needed; the loop already returns `[[root]]`.
Flipping the flag in both branches of an if/else is the same as `flag = !flag` once after the row.
He also left unused imports.
Rule: let the general loop handle the root, and flip the flag in one line.

### Memorize this

1. Same as Level Order: offer root, freeze `size`, `remove()` `size` nodes into `tmpList`.
2. Always offer left, then right.
3. `flag` starts `false`; when it is `true`, reverse `tmpList`.
4. Add the row, flip `flag`.

```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res=new ArrayList<List<Integer>>();
    Queue<TreeNode> que=new LinkedList<>();
    if(root==null ) return res;
    if((root.left==null&&root.right==null)){
        ArrayList<Integer> tmpList=new ArrayList<>();
        tmpList.add(root.val);
        res.add(tmpList);
        return  res;
    }
    boolean flag=false;
    que.add(root);
    while(!que.isEmpty()){
        int size=que.size();

        ArrayList<Integer> tmpList=new ArrayList<>();
        for(int i=0;i<size;i++){
            TreeNode tmp=que.remove();
            tmpList.add(tmp.val);
            if(tmp.left!=null){
                que.add(tmp.left);
            }
            if(tmp.right!=null){
                que.add(tmp.right);
            }
        }
        if(flag) {
            Collections.reverse(tmpList);
            res.add(tmpList);
            flag=false;
        }
        else{
            res.add(tmpList);
            flag=true;
        }

    }

    return res;
}
```

Say in the interview: It is level-order BFS where the queue always goes left to right and I only reverse every other finished row, which is O(n) time and O(n) space.

### How it went

On-time; he coded it himself well inside the clock, and all tests were green.
The only gate miss was the name ("BST" for BFS).
The cousin builds each row in a `LinkedList` with `addLast` or `addFirst` by direction, so no reverse is needed.

---

## LC 572 Subtree of Another Tree — untimed

https://leetcode.com/problems/subtree-of-another-tree/

### The problem

Is there a node in `root` such that that node plus everything under it is exactly `subRoot`, down to the leaves?

```
  root         subRoot
    3             4
   / \           / \
  4   5         1   2
 / \
1   2
```

Answer: `true`.
If `2` in `root` had an extra child `0`, the answer would be `false`, because the match must go all the way to the leaves.

### The idea

Pattern: scan every node of `root`, and at each one run a Same Tree check against `subRoot`.

The outer scan can be BFS (his version) or recursion.
The inner check is exactly LC 100: both `null` is a match, one `null` or different values is a mismatch, then left pair and right pair.
A failed match is a normal `false`; keep scanning, because another node with the same value may match lower down.

Time is O(n · m), where n is the size of `root` and m the size of `subRoot`, because each of the n nodes may start a check that walks up to m nodes.
Extra space is O(n + m) in his version: the BFS queue plus the recursion of the inner check; the all-recursive version is O(h_root + h_sub).

### How to solve it

1. Both `null` → `true`; one `null` → `false`.
2. BFS over `root` with a queue `que`.
3. Remove `tmp`; if `tmp.val == subRoot.val`, run `pre(tmp, subRoot)`, and if it is `true`, return `true`.
4. Always offer the non-null kids of `tmp`, match or not.
5. Queue empty → return `flag` (`false`).
6. `pre` is Same Tree: both `null` → `true`, one `null` → `false`, values differ → `false`, then `left && right`.

Trace on the trees above:

| Removed `tmp` | value equals 4? | `pre(tmp, subRoot)` | continue? |
|---|---|---|---|
| 3 | no | not called | yes, enqueue 4 and 5 |
| 4 | yes | **true**: (1, 1) match, (2, 2) match, all kids null pairs | return **true** |

### Holes to patch

**Enqueueing kids only when the values match**

He offered kids only inside `if (tmp.val == subRoot.val)`.
On the example, `3 != 4`, so `3`'s kids were never enqueued and the scan ended with `false`.
Rule: the scan always visits every node; the value check only decides whether to run `same`.

**Returning `false` when a kid is `null`**

His inner check returned `false` if `node.left` or `node.right` was `null`.
Two matching leaves (`1` and `1`) both have `null` kids, so the check failed on a real match.
Rule: check only the pair you were handed; `(null, null)` is a match.

**Dropping the recursive results**

He called `pre(left)` and `pre(right)` and ignored what they returned, then returned `node.val == subRoot.val`, which is always `true` at that point.
On `root = 4 / 1 3`, `subRoot = 4 / 1 2`, that returns `true`, but the answer is `false`.
Rule: `return pre(node.left, subRoot.left) && pre(node.right, subRoot.right);`

**Throwing, or stopping, on the first mismatch**

He wanted to throw when a match failed.
On `root = 1 / _ 4 / 4 / 1 2` (a right `4` whose left child is another `4` with kids `1` and `2`), the first `4` fails and the second `4` matches.
Rule: a mismatch is `false` for that start node only; keep scanning.

**Time and space not stated before coding**

Rule: say O(n · m) time, because every node of `root` can start an O(m) check.

### Memorize this

1. Both `null` → `true`; one `null` → `false`.
2. BFS over `root`.
3. Value matches `subRoot.val` → run `pre`; `true` → return `true`.
4. Always offer non-null kids.
5. Queue empty → `false`.
6. `pre` is Same Tree: both null true, one null false, values differ false, then `left && right`.

```java
public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    if (subRoot== null && root==null) return true;
    if (subRoot== null || root==null) return false;
    boolean flag=false;

    Queue<TreeNode> que=new LinkedList<>();
    que.add(root);
    while (!que.isEmpty()){
        TreeNode tmp=que.remove();
        if(tmp.val==subRoot.val){
            flag=   pre(tmp, subRoot);
            if(flag) return flag;
        }
        if(tmp.left!=null){
            que.add(tmp.left);
        }
        if(tmp.right!=null){
            que.add(tmp.right);
        }

    }

    return flag;
}
boolean pre(TreeNode node,TreeNode subRoot) {
    if (node == null &&subRoot==null) return true;
    if ((node == null ||subRoot==null)) return false;
    if(node.val!=subRoot.val) return false;      // here

    return pre(node.left, subRoot.left)&& pre(node.right,subRoot.right);
}
```

Say in the interview: At every node of the big tree whose value matches the small tree's root I run a Same Tree check and keep scanning on a mismatch, which is O(n · m) time and O(n + m) space for the queue and the check's recursion.

### How it went

Untimed, because he started coding before the Memorize step; he coded it with where-hints.
All three bugs were the same holes as Same Tree and Symmetric Tree: checking kids instead of the pair, and dropping recursive returns.
He fixed each one himself after a hint.

---

## LC 222 Count Complete Tree Nodes — on-time (O(n) version)

https://leetcode.com/problems/count-complete-tree-nodes/

### The problem

The tree is complete: every level is full except maybe the last, and the last level fills from the left.
Return the number of nodes, in less than O(n) if you can.

```
      1
     / \
    2   3
   / \  /
  4  5 6
```

Answer: `6`.

### The idea

His version: BFS over the whole tree, collecting every value, and the list size is the count.
That is O(n) time because every node is removed once.
Extra space is O(n) for the queue plus the list `x` of values.

The follow-up asks for less than O(n); that version is under Holes to patch.

### How to solve it

1. `null` root → `0`.
2. Add the root to a `LinkedList` queue `que`.
3. While `que` is not empty: remove `tmp`, add `tmp.val` to the list `x`.
4. Offer the non-null left kid, then the non-null right kid.
5. Return `x.size()`.

Trace on the tree above:

| Removed `tmp` | `x` after | `que` after |
|---|---|---|
| 1 | `[1]` | 2 3 |
| 2 | `[1, 2]` | 3 4 5 |
| 3 | `[1, 2, 3]` | 4 5 6 |
| 4 | `[1, 2, 3, 4]` | 5 6 |
| 5 | `[1, 2, 3, 4, 5]` | 6 |
| 6 | `[1, 2, 3, 4, 5, 6]` | empty → return **6** |

### Holes to patch

**Under O(n): skip whole perfect subtrees (the fast version)**

He did not land this one, and it is the one the interviewer is after.
From a node, walk only the left edge for `l` and only the right edge for `r`.
Equal lengths mean the subtree is perfect, so return `(1 << l) - 1` without visiting it.
Different lengths mean return `1 + countNodes(left) + countNodes(right)`.
In a complete tree one kid is always perfect, so only one side per level goes deep.
Time is O(log² n): about log n deep calls, each with an edge walk of about log n steps.
Extra space is O(log n) for the recursion.
On the tree above, the call at `2` sees edges `2-4` and `2-5`, both length 2, and returns `3` without ever visiting `4` and `5`.

**"+2 if both kids, +1 if one" is still O(n)**

That recursion still visits every node, so it is O(n), the same as BFS.
Rule: under O(n) means some nodes are never visited; here, whole perfect subtrees are counted by formula.

**The perfect-tree formula was not on hand**

A perfect tree with `h` levels has `1 + 2 + 4 + ... + 2^(h−1) = 2^h − 1` nodes.
1 level → 1, 2 → 3, 3 → 7, 4 → 15.
Rule: `(1 << h) - 1`.

**Storing every value in an `ArrayList` just to call `size()`**

His BFS adds each value to a list and returns `list.size()`.
That wastes O(n) memory on values nobody reads.
Rule: keep an `int count` and increment it on each poll.

### Memorize this

1. `null` → `0`.
2. BFS with a queue from the root.
3. Each removed node: add its value to `x`, offer non-null kids.
4. Return `x.size()`.
5. Follow-up: edge heights equal → `(1 << l) - 1`, else `1 + left + right`, O(log² n) (see Holes to patch).

```java
public int countNodes(TreeNode root) {
    if (root==null) return 0;
    Queue<TreeNode> que=new LinkedList<>();
    ArrayList<Integer> x=new ArrayList<>();
    que.add(root);
    while (!que.isEmpty()){
        TreeNode tmp=que.remove();
        x.add(tmp.val);
        if(tmp.left!=null){
            que.add(tmp.left);
        }
        if(tmp.right!=null){
            que.add(tmp.right);
        }
    }

    return x.size();
}
```

Say in the interview: I count every node with a BFS in O(n), and for the follow-up I compare the left-edge and right-edge heights, because equal heights mean a perfect subtree with `2^h − 1` nodes, so only one side per level needs real work, which is O(log² n) time.

### How it went

On-time with the O(n) BFS; he coded it himself quickly.
He tried the fast version first but did not land the `2^h − 1` idea after two hints, and asked for it in the notes instead.
The fast version under Holes to patch is the one to have ready for an interview.
