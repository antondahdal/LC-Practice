# DFS orders — pre / in / post

Look-up sheet.
Anton keeps mixing the three (asked 2026-09-24).

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
