# DFS orders (pre / in / post) and BFS

Look-up sheet.
Anton keeps mixing the three (asked 2026-09-24).
BFS added 2026-09-25.
Traced examples for every Week 6 problem: `notes/week-06-walkthroughs.md`.

## Same tree for all three

```
        1
       / \
      2   3
     / \
    4   5
```

The walk is the same every time: go down left, come back, go down right, come back.

Only **when the node does its own work** changes.

## Pre-order — node, left, right

Work on the node **before** its kids.

```
1  2  4  5  3
```

```java
void pre(TreeNode node) {
    if (node == null) return;
    visit(node);          // here
    pre(node.left);
    pre(node.right);
}
```

The node uses what the **parent** passed down.

Example: Path Sum #112 — subtract `node.val` from `remaining`, then hand `remaining` to the kids.

## In-order — left, node, right

Work on the node **between** its kids.

```
4  2  5  1  3
```

```java
void in(TreeNode node) {
    if (node == null) return;
    in(node.left);
    visit(node);          // here
    in(node.right);
}
```

On a BST this gives values in sorted order.

Example: Kth Smallest #230, Validate BST #98.

## Post-order — left, right, node

Work on the node **after** both kids are done.

```
4  5  2  3  1
```

```java
int post(TreeNode node) {
    if (node == null) return 0;
    int left = post(node.left);
    int right = post(node.right);
    return combine(node, left, right);   // here
}
```

The node uses what the **kids returned**.

Example: Max Depth #104 — `1 + max(left, right)`.

## Memory trick

The name tells you where the **node** sits.

Pre = node first.

In = node in the middle.

Post = node last.

Left always comes before right.

## How to pick

Ask one question: does the node need something from **above**, or an answer from **below**?

From above (a running sum, a range, a depth passed down) → pre-order.

From below (both kids must report first) → post-order.

BST and you want sorted values → in-order.

## BFS — level by level

Same tree:

```
        1
       / \
      2   3
     / \
    4   5
```

BFS does not go deep first.
It finishes a whole level, then moves to the next.

```
1  |  2  3  |  4  5
```

It uses a **queue** (first in, first out).
The node that went in first comes out first, so levels stay in order.

```java
Queue<TreeNode> queue = new ArrayDeque<>();
queue.add(root);
while (!queue.isEmpty()) {
    int size = queue.size();          // freeze this level
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.remove();
        visit(node);                  // i == size - 1 → last in level
        if (node.left != null) queue.add(node.left);
        if (node.right != null) queue.add(node.right);
    }
}
```

Queue after each level on the tree above:

| Level | polled | queue after |
|---|---|---|
| 1 | 1 | 2 3 |
| 2 | 2 3 | 4 5 |
| 3 | 4 5 | empty |

Freeze `size` before the `for`.
Kids added during the loop belong to the next level.

`ArrayDeque` does not accept `null`.
If you need to offer null kids (Same Tree, Symmetric Tree), use `LinkedList`.

Stack vs queue: a stack is last in, first out.
That gives a DFS order, not levels.
It is also why one stack plus one queue broke Symmetric Tree (Day 4).

Examples: Level Order #102, Right Side View #199.
Same Tree #100 and Symmetric Tree #101 can be done with two queues, polled in pairs.

## DFS or BFS?

Answer depends on **levels** (per-level list, last per level, shortest depth) → BFS.

Answer depends on a **path** or on what the kids return (sum to a leaf, height, split point) → DFS.

Both work (Same Tree, Symmetric, Invert) → pick the one you can write without bugs.
DFS is usually shorter.

Space: DFS keeps O(h), one call per level of depth.
BFS keeps O(w), the widest level, up to about n / 2 on a full tree.
