# Week 05 — review sheet

Week 5 was the linked-list week: two pointers with a gap, fast/slow walkers, in-place reversal, a dummy node in front of the head, and merging by rewiring `next`.

The interview timer started on Day 4.
Days 1–3 have no clock, so those results say "passed (no timer)" when he coded it himself.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 19 | Remove Nth Node From End of List | Two pointers with a gap of `n` + dummy | O(n) / O(1) | passed (no timer) |
| 206 | Reverse Linked List | Iterative reversal (`prev`, `curr`, saved `next`) | O(n) / O(1) | passed (no timer) |
| 143 | Reorder List | Fast/slow middle + reverse back half + weave | O(n) / O(1) | coach-fixed |
| 141 | Linked List Cycle | HashSet of nodes, then fast/slow (Floyd) | O(n) / O(n) set, O(1) Floyd | passed (no timer) |
| 2 | Add Two Numbers | Two walkers + carry + dummy | O(max(m,n)) / O(1) extra | coach-fixed |
| 21 | Merge Two Sorted Lists | Two walkers + dummy, hook the smaller | O(m+n) / O(1) | passed (no timer) |
| 92 | Reverse Linked List II | Dummy + walk to `before` + reverse k nodes | O(n) / O(1) | overtime, coach-fixed |
| 160 | Intersection of Two Linked Lists | HashSet of nodes (cousin: switch-heads walkers) | O(m+n) / O(m) set, O(1) walkers | on-time |
| 82 | Remove Duplicates from Sorted List II | Dummy + skip whole equal runs | O(n) / O(1) | overtime |
| 234 | Palindrome Linked List | Fast/slow middle + reverse back half + compare | O(n) / O(1) | on-time |

---

## LC 19 Remove Nth Node From End of List — passed (no timer)

https://leetcode.com/problems/remove-nth-node-from-end-of-list/

### The problem

You get the head of a linked list and a number `n`.
Remove the node that is `n`-th counting from the tail, and return the head.

Example: `1 → 2 → 3 → 4 → 5`, `n = 2` removes `4` and gives `1 → 2 → 3 → 5`.

Edge example: `1 → 2`, `n = 2` removes the head and gives `2`.

### The idea

Pattern: two pointers with a fixed gap.

You cannot count from the tail of a singly list, but you can fake it.
Walk `front` ahead first so there is a gap between `front` and `back`.
Then move both one step at a time.
When `front` falls off the end, `back` sits exactly on the node before the victim.

A dummy node in front of the head means the head victim is not a special case.
`back` can always sit on "the node before", even when the victim is the head.

Time is O(n) because `front` walks the list once and `back` walks part of it.
Extra space is O(1) because you only keep two pointers and a dummy.

### How to solve it

1. Put a `dummy` in front of `head`.
2. Start `front` and `back` on `dummy`.
3. Move `front` forward `n + 1` times, so the gap is `n + 1` links.
4. Move both until `front` is `null`.
5. `back.next` is the victim, so set `back.next = back.next.next`.
6. Return `dummy.next`.

Trace on `1 → 2 → 3`, `n = 3` (the head is the victim):

| step | front | back | list so far |
|---|---|---|---|
| start | dummy | dummy | dummy → 1 → 2 → 3 |
| front 1 | 1 | dummy | same |
| front 2 | 2 | dummy | same |
| front 3 | 3 | dummy | same |
| front 4 | null | dummy | same, loop does not run |
| skip | null | dummy | dummy → 2 → 3 |
| return | | | 2 → 3 |

### Holes to patch

**Called it a sliding window**

A sliding window grows and shrinks over a range.
Here the gap between the two pointers never changes.
Rule: two pointers with a fixed gap of `n`, not a window.

**Head victim treated like a mid-list skip**

Without a dummy, `back` starts on the head, and the skip removes the node after `back`.
On `1 → 2`, `n = 2`, `front` walks to `null`, and `back.next = back.next.next` removes `2` instead of `1`.
On `1`, `n = 1`, `back.next` is `null`, so `back.next.next` throws a `NullPointerException`.
Rule: if `front` is already `null` after walking `n` steps, the victim is the head, so return `head.next`, or put a dummy in front so there is always a node before the victim.

```java
if (front == null) return head.next;
```

### Memorize this

1. Dummy in front of head.
2. `front` and `back` on dummy.
3. `front` walks `n + 1`.
4. Both walk until `front == null`.
5. `back.next = back.next.next`.
6. Return `dummy.next`.

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode front = dummy, back = dummy;
    for (int i = 0; i <= n; i++) front = front.next;
    while (front != null) {
        front = front.next;
        back = back.next;
    }
    back.next = back.next.next;
    return dummy.next;
}
```

Say in the interview: I keep two pointers `n + 1` links apart starting from a dummy, so when the front one falls off, the back one is right before the node to delete, in one pass and O(1) space.

### How it went

He coded it and it passed, before the timer existed.
The gate started as "sliding window" and he fixed the name to two pointers.
His version handles the head with `return head.next` instead of a dummy.

---

## LC 206 Reverse Linked List — passed (no timer)

https://leetcode.com/problems/reverse-linked-list/

### The problem

Reverse a singly linked list and return the new head.

Example: `1 → 2 → 3` becomes `3 → 2 → 1`.
An empty list returns `null`.

### The idea

Pattern: iterative reversal with `prev`, `curr`, and a saved `next`.

Walk the list once.
At each node, point its `next` back at the node behind it.
You must save the old `next` first, or you lose the rest of the list.
When `curr` falls off, `prev` is the old tail, which is the new head.

Time is O(n) because each node is flipped once.
Extra space is O(1) because you keep three pointers.

### How to solve it

1. `prev = null`, `curr = head`.
2. While `curr != null`: save `next = curr.next`.
3. Point `curr.next = prev`.
4. Move `prev = curr`, then `curr = next`.
5. Return `prev`.

Trace on `1 → 2 → 3`:

| step | prev | curr | next | links so far |
|---|---|---|---|---|
| start | null | 1 | | 1 → 2 → 3 |
| 1 | 1 | 2 | 2 | 1 → null |
| 2 | 2 | 3 | 3 | 2 → 1 → null |
| 3 | 3 | null | null | 3 → 2 → 1 → null |
| return | | | | head is 3 |

### Holes to patch

**Swapped neighbors instead of reversing**

Swapping each pair (`first.next = sec.next; sec.next = first`) is LC 24 Swap Nodes in Pairs.
On `1 → 2 → 3 → 4` it gives `2 → 1 → 4 → 3`, not `4 → 3 → 2 → 1`.
Rule: reverse points every node back at the one behind it, one node per step.

**Loop condition that reads `curr.next`**

`while (curr != null || curr.next != null)` still evaluates `curr.next` when `curr` is `null`.
On any list, the last step makes `curr` null and the check throws a `NullPointerException`.
Rule: the loop is exactly `while (curr != null)`.

**Both pointers start on the head**

If `prev = head` and `curr = head`, the first step sets `head.next = head`.
On `1 → 2`, node `1` now points at itself, which is a cycle.
Rule: `prev` starts at `null`, because the old head becomes the new tail.

### Memorize this

1. `prev = null`, `curr = head`.
2. Save `next`.
3. `curr.next = prev`.
4. `prev = curr`, `curr = next`.
5. Loop `while (curr != null)`, return `prev`.

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

Say in the interview: I walk once, save the next node, point the current node back at the previous one, and step forward, so the old tail becomes the head in O(n) time and O(1) space.

### How it went

He coded it and it passed, before the timer existed.
The gate first described a neighbor swap, which is LC 24.
This loop is the building block for LC 143, LC 92, and LC 234 later in the week.

---

## LC 143 Reorder List — coach-fixed

https://leetcode.com/problems/reorder-list/

### The problem

Reorder a singly linked list in place as first, last, second, second-last, and so on.
Change links, not values.

Example: `1 → 2 → 3 → 4` becomes `1 → 4 → 2 → 3`.
`1 → 2 → 3 → 4 → 5` becomes `1 → 5 → 2 → 4 → 3`.

### The idea

Pattern: fast/slow to find the middle, reverse the back half, then weave the two halves.

A singly list cannot walk backward, so "take the last node" is expensive every time.
Reverse the back half once, and the last nodes come to you in order from its head.
Then take one node from the front half and one from the reversed back half, alternating.

Time is O(n) because finding the middle, reversing, and weaving are each one pass.
Extra space is O(1) because you only rewire existing nodes.

### How to solve it

1. If the list has 0 or 1 nodes, return.
2. `slow` and `fast` start on `head`; move while `fast.next != null && fast.next.next != null`.
3. `slow` is the end of the front half; cut with `second = slow.next`, `slow.next = null`.
4. Reverse `second` with the LC 206 loop.
5. Weave: save both `next`s, hook `front.next = back`, `back.next = frontNext`, advance both.
6. Stop when the back half runs out.

Trace on `1 → 2 → 3 → 4 → 5`.
The middle step stops `slow` on `3`, so the front half is `1 → 2 → 3` and the reversed back half is `5 → 4`.

| step | front | back | frontNext | backNext | list so far |
|---|---|---|---|---|---|
| 1 | 1 | 5 | 2 | 4 | 1 → 5 → 2 → 3 |
| 2 | 2 | 4 | 3 | null | 1 → 5 → 2 → 4 → 3 |
| stop | 3 | null | | | 1 → 5 → 2 → 4 → 3 |

### Holes to patch

**Two workers at first and last, swapping**

The picture was one pointer at the head and one at the tail, moving toward each other.
A singly list has no `prev`, so the tail pointer cannot step backward.
Finding the new "last" each time means walking from the head again, which is O(n²).
Rule: you cannot walk backward, so reverse the back half once and then walk it forward.

**Moving a variable does not rewire the list**

Writing `first = sec` only moves your finger to another node.
The list stays exactly the same.
On `1 → 2 → 3 → 4`, reassigning locals leaves the list as `1 → 2 → 3 → 4`.
Rule: only an assignment to some `node.next` changes the list.

**Classic trap: forgetting to cut the front half**

If you skip `slow.next = null`, the front half still points into the back half.
On `1 → 2 → 3 → 4`, the weave ends with `3.next = 3`, a self-loop.
Rule: cut at the middle before reversing.

```java
ListNode second = slow.next;
slow.next = null;
```

### Memorize this

1. Empty or one node: return.
2. Middle: `while (fast.next != null && fast.next.next != null)`.
3. Cut: `second = slow.next`, `slow.next = null`.
4. Reverse `second` (LC 206 loop).
5. Weave one front, one back, until the back is `null`.

```java
public void reorderList(ListNode head) {
    if (head == null || head.next == null) return;
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    ListNode curr = slow.next, prev = null;
    slow.next = null;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    ListNode front = head, back = prev;
    while (back != null) {
        ListNode frontNext = front.next, backNext = back.next;
        front.next = back;
        back.next = frontNext;
        front = frontNext;
        back = backNext;
    }
}
```

Say in the interview: A singly list cannot walk backward, so I find the middle with fast/slow, reverse the back half once, and weave the two halves, which is O(n) time and O(1) space.

### How it went

He asked for the code, so the coach wrote it.
No timer yet.
The hole that blocked him was the "swap first and last" picture, which does not work without a `prev` pointer.

---

## LC 141 Linked List Cycle — passed (no timer)

https://leetcode.com/problems/linked-list-cycle/

### The problem

Return `true` if following `next` ever reaches a node you already visited.
Return `false` if the list ends at `null`.

Example: `1 → 2 → 3 → 4` with `4.next = 2` is `true`.
`1 → 2 → 3` is `false`.

### The idea

Pattern: HashSet of nodes (O(n) space), or fast/slow walkers (Floyd, O(1) space).

The set version stores each node as you visit it.
If you see a node that is already in the set, you went around a loop.

The fast/slow version moves `slow` one step and `fast` two steps.
If there is no cycle, `fast` hits `null`.
If there is a cycle, `fast` gains one step per turn on `slow` inside the loop, so it must land on `slow`.

Time is O(n) for both, because each node is visited a constant number of times.
Extra space is O(n) for the set, because it can hold every node, and O(1) for fast/slow, because it keeps two pointers.

### How to solve it

1. `slow = head`, `fast = head`.
2. While `fast != null && fast.next != null`: move `slow` one, `fast` two.
3. If `slow == fast`, return `true`.
4. If the loop ends, return `false`.

Trace on `1 → 2 → 3 → 4` with `4.next = 2`:

| step | slow | fast | same? |
|---|---|---|---|
| start | 1 | 1 | skip the check |
| 1 | 2 | 3 | no |
| 2 | 3 | 2 | no |
| 3 | 4 | 4 | yes, return `true` |

### Holes to patch

**`fast.next.next` without checking `fast.next`**

His first fast/slow version jumped `fast.next.next` without guarding `fast.next`.
On `1 → 2 → 3`, `fast` lands on `3`, `fast.next` is `null`, and `fast.next.next` throws a `NullPointerException`.
Rule: check both `fast != null` and `fast.next != null` before the double jump.

```java
while (fast != null && fast.next != null)
```

**Classic trap: storing values instead of nodes**

A set of `val` says "cycle" whenever two different nodes share a value.
On `1 → 1` (no cycle), a set of values sees `1` twice and wrongly returns `true`.
Rule: put the `ListNode` in the set, not `node.val`.

### Memorize this

1. `slow` and `fast` on head.
2. Loop while `fast != null && fast.next != null`.
3. `slow` one step, `fast` two steps.
4. Same node: `true`.
5. Loop ends: `false`.

```java
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

Say in the interview: A HashSet of nodes works in O(n) space, but with a slow and a fast pointer the fast one either hits `null` or laps the slow one inside the cycle, so it is O(n) time and O(1) space.

### How it went

He coded the node set first, then the fast/slow cousin, before the timer existed.
The cousin first threw a `NullPointerException` on `1 → 2 → 3`, and the guard fixed it.
His repo version starts `fast` at `head.next.next`, which also works.

---

## LC 2 Add Two Numbers — coach-fixed

https://leetcode.com/problems/add-two-numbers/

### The problem

Two lists each store a non-negative number, one digit per node, ones digit first.
Return their sum as a list in the same order.

Example: `2 → 4 → 3` plus `5 → 6 → 4` is `342 + 465 = 807`, so the answer is `7 → 0 → 8`.

### The idea

Pattern: two walkers plus a carry, building the answer behind a dummy node.

The ones digits are already at the heads, so you add column by column like on paper.
Keep one `carry` while you walk.
A list that ran out counts as digit `0`, and it does not stop the loop.

Time is O(max(m, n)) because each step consumes one digit from each list.
Extra space is O(1) besides the output list, because you only keep `carry` and a few pointers.

### How to solve it

1. `dummy` and `tail = dummy`, `carry = 0`.
2. Loop while `l1 != null || l2 != null || carry != 0`.
3. `sum = carry` plus `l1.val` if present plus `l2.val` if present; advance the lists you used.
4. Append `new ListNode(sum % 10)`, move `tail`.
5. `carry = sum / 10`.
6. Return `dummy.next`.

Trace on `9 → 9` plus `1` (carry runs past both lists):

| step | l1 digit | l2 digit | carry in | sum | written | carry out | answer so far |
|---|---|---|---|---|---|---|---|
| 1 | 9 | 1 | 0 | 10 | 0 | 1 | 0 |
| 2 | 9 | 0 (empty) | 1 | 10 | 0 | 1 | 0 → 0 |
| 3 | 0 (empty) | 0 (empty) | 1 | 1 | 1 | 0 | 0 → 0 → 1 |

### Holes to patch

**Converting to a string or `Integer` and adding**

Lists can be 100 digits long.
An `int` holds about 10 digits and a `long` about 19, so the conversion overflows.
Rule: never build the number; add digit by digit with a carry.

**Reaching for a stack**

A stack is for LC 445, where the most significant digit is at the head.
Here the ones digit is already first, so you can add in the order you walk.
Rule: digits least-significant first means walk forward with a carry, no stack.

**Splicing the leftover list when one side runs out**

Hooking the rest of the longer list directly ignores the carry.
On `9 → 9` plus `1`, splicing after the first digit gives `0 → 9` and loses the carry, but the answer is `0 → 0 → 1`.
Rule: a missing node is digit `0`; keep adding until both lists and the carry are gone.

**Classic trap: forgetting the carry in the loop condition**

`while (l1 != null || l2 != null)` stops before the last carry.
On `5` plus `5` it returns `0` instead of `0 → 1`.
Rule: loop while either list or the carry is alive.

### Memorize this

1. Dummy + tail, `carry = 0`.
2. Loop while `l1`, `l2`, or `carry`.
3. `sum = carry + (l1 or 0) + (l2 or 0)`.
4. Write `sum % 10`, `carry = sum / 10`.
5. Return `dummy.next`.

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(), tail = dummy;
    int carry = 0;
    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) { sum += l1.val; l1 = l1.next; }
        if (l2 != null) { sum += l2.val; l2 = l2.next; }
        tail.next = new ListNode(sum % 10);
        tail = tail.next;
        carry = sum / 10;
    }
    return dummy.next;
}
```

Say in the interview: The digits are ones-first, so I add column by column with a carry, treat a finished list as zero, and keep going while the carry is non-zero, in O(max(m, n)) time.

### How it went

He asked for the code, so the coach wrote it.
No timer yet.
The holes were the string/`Integer` idea in the gate and splicing the leftover list without the carry.

---

## LC 21 Merge Two Sorted Lists — passed (no timer)

https://leetcode.com/problems/merge-two-sorted-lists/

### The problem

Merge two sorted linked lists into one sorted list and return its head.

Example: `1 → 2 → 4` and `1 → 3 → 4` give `1 → 1 → 2 → 3 → 4 → 4`.

### The idea

Pattern: two walkers plus a dummy node, hooking the smaller head each step.

Both lists are sorted, so the smallest remaining value is always one of the two heads.
Hook that node onto the tail of the answer and advance that list.
When one list runs out, the other one is already sorted, so hook the rest in one assignment.

Time is O(m + n) because every node is hooked once.
Extra space is O(1) because you rewire the existing nodes instead of copying them.

### How to solve it

1. `dummy`, `tail = dummy`.
2. While both lists are non-null, hook the smaller head onto `tail.next` and advance that list.
3. Move `tail = tail.next`.
4. After the loop, `tail.next = list1 != null ? list1 : list2`.
5. Return `dummy.next`.

Trace on `1 → 2 → 4` and `1 → 3 → 4` (equal values take two steps):

| step | list1 head | list2 head | hook | answer so far |
|---|---|---|---|---|
| 1 | 1 | 1 | list1's 1 | 1 |
| 2 | 2 | 1 | list2's 1 | 1 → 1 |
| 3 | 2 | 3 | 2 | 1 → 1 → 2 |
| 4 | 4 | 3 | 3 | 1 → 1 → 2 → 3 |
| 5 | 4 | 4 | list1's 4 | 1 → 1 → 2 → 3 → 4 |
| end | null | 4 | hook the rest | 1 → 1 → 2 → 3 → 4 → 4 |

### Holes to patch

**Took the larger head**

The first version picked the bigger of the two heads.
On `1 → 3` and `2`, that hooks `2` before `1`, so the answer starts `2 → …` and is not sorted.
Rule: always hook the smaller head, one node per step.

**Copying values into new nodes**

He built the answer with `new ListNode(list1.val)`, and his repo version still does.
That allocates m + n new nodes, so the extra space is O(m + n), not O(1).
Rule: hook the live node itself.

```java
tail.next = list1;
list1 = list1.next;
```

**Leftover checks inside `while (both)`**

He put "one list is empty" checks inside `while (list1 != null && list2 != null)`.
Inside that loop both lists are non-null, so those branches never run and the leftover is lost.
On `1` and `2 → 3`, the loop stops after hooking `1`, and `2 → 3` is lost.
Rule: handle the leftover after the loop, in one assignment.

**Returned the dummy**

Returning `dummy` puts a fake `0` at the front.
On `1` and `2`, it returns `0 → 1 → 2`.
Rule: return `dummy.next`.

### Memorize this

1. Dummy + tail.
2. While both: hook the smaller live node, advance it.
3. Move tail.
4. After the loop: `tail.next = list1 != null ? list1 : list2`.
5. Return `dummy.next`.

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(), tail = dummy;
    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            tail.next = list1;
            list1 = list1.next;
        } else {
            tail.next = list2;
            list2 = list2.next;
        }
        tail = tail.next;
    }
    tail.next = (list1 != null) ? list1 : list2;
    return dummy.next;
}
```

Say in the interview: The smallest remaining value is always one of the two heads, so I hook the smaller one behind a dummy, then attach whatever list is left, in O(m + n) time and O(1) extra space by rewiring.

### How it went

He coded it and it passed, before the timer existed.
Holes on the way were taking the larger head, leftover checks that could never run, and returning the dummy.
His final version still copies nodes and uses `while (either)` with side checks, which is correct but uses O(m + n) extra space.

---

## LC 92 Reverse Linked List II — overtime, coach-fixed

https://leetcode.com/problems/reverse-linked-list-ii/

### The problem

Reverse only the nodes from position `left` to position `right` (1-indexed).
Leave the rest of the list as it is and return the head.

Example: `1 → 2 → 3 → 4 → 5`, `left = 2`, `right = 4` gives `1 → 4 → 3 → 2 → 5`.

### The idea

Pattern: dummy node, walk to the node before the slice, reverse exactly `right - left + 1` nodes, then reattach both ends.

The slice reversal is the LC 206 loop with a fixed count.
Two nodes matter for reattaching.
`before` is the node just before position `left`.
`start` is the first node of the slice, which becomes the slice's tail after reversing.
The dummy makes `left = 1` work, because the head also has a node before it.

Time is O(n) because you walk to `left` and then reverse at most n nodes.
Extra space is O(1) because you only rewire pointers.

### How to solve it

1. `dummy.next = head`, `before = dummy`.
2. Move `before` forward `left - 1` times.
3. `start = before.next`, `prev = null`, `curr = start`.
4. Run the LC 206 loop exactly `right - left + 1` times.
5. Now `prev` is the new slice head and `curr` is the first node after the slice.
6. `before.next = prev`, `start.next = curr`.
7. Return `dummy.next`.

Trace on `1 → 2 → 3 → 4 → 5`, `left = 2`, `right = 4`.
After step 2, `before` is `1` and `start` is `2`.

| step | prev | curr | next | slice links |
|---|---|---|---|---|
| start | null | 2 | | 2 → 3 → 4 |
| 1 | 2 | 3 | 3 | 2 → null |
| 2 | 3 | 4 | 4 | 3 → 2 |
| 3 | 4 | 5 | 5 | 4 → 3 → 2 |
| reattach | | | | `1.next = 4`, `2.next = 5` gives 1 → 4 → 3 → 2 → 5 |

### Holes to patch

**Compared values instead of positions**

He walked with `first.val != left`.
`left` and `right` are positions, not values.
On `5 → 6 → 7`, `left = 1`, `right = 2`, no node has value `1`, so the walk never stops at the right node.
Rule: count steps, `for (int i = 1; i < left; i++)`.

**Dummy was the head, not in front of it**

With `dummy = head`, there is no node before position `1`.
On `1 → 2 → 3`, `left = 1`, `right = 2`, nothing can point at the new slice head `2`, so the result cannot become `2 → 1 → 3`.
Rule: `dummy = new ListNode(0, head)`, and `before` starts on the dummy.

**Started the reversal one node too late**

He put the "behind" pointer on `2` and the current pointer on `3`, so `2` was treated as outside the slice.
The step `3.next = 2` ran while `2.next` was still `3`, which makes a `2 ⇄ 3` cycle.
Rule: `prev = null` and `curr = start`, same as LC 206.

**Reversed the whole tail**

`while (curr != null)` reverses to the end of the list, not to `right`.
On `1 → 2 → 3 → 4 → 5`, `left = 2`, `right = 3`, it also flips `4` and `5`.
Rule: loop a fixed `right - left + 1` times.

**Moving a pointer does not move a node**

`first = sec` moves your finger from node `2` to node `3`.
It does not turn node `2` into node `3`, and the list does not change.
Rule: only `node.next = ...` rewires the list.

**Swapped time and space in the gate**

He said O(1) time and O(n) extra space.
Rule: O(n) time because you walk the list, and O(1) extra because you rewire in place.

### Memorize this

1. Dummy in front of head.
2. `before` walks `left - 1`.
3. `start = before.next`, `prev = null`, `curr = start`.
4. LC 206 loop, `right - left + 1` times.
5. `before.next = prev`, `start.next = curr`.
6. Return `dummy.next`.

```java
public ListNode reverseBetween(ListNode head, int left, int right) {
    ListNode dummy = new ListNode(0, head), before = dummy;
    for (int i = 1; i < left; i++) before = before.next;
    ListNode start = before.next, prev = null, curr = start;
    for (int i = 0; i <= right - left; i++) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    before.next = prev;
    start.next = curr;
    return dummy.next;
}
```

Say in the interview: I use a dummy so the head is not special, walk to the node before `left`, reverse exactly `right - left + 1` nodes with the standard loop, and reconnect both ends, in one pass and O(1) space.

### How it went

Overtime: the 25-minute clock ran out.
He kept going for learning, then asked for the code, so the coach wrote it.
The clock went to positions-versus-values and to starting the reversal on the wrong nodes, which created a cycle.

---

## LC 160 Intersection of Two Linked Lists — on-time

https://leetcode.com/problems/intersection-of-two-linked-lists/

### The problem

Two lists may join at one shared node and share the rest of the tail.
Return that first shared node, or `null` if they never join.
"Shared" means the same node object, not an equal value.

Example: A is `4 → 1 → 8 → 4 → 5`, B is `5 → 6 → 1 → 8 → 4 → 5`, and they join at the node with value `8`.

### The idea

Pattern: HashSet of nodes, or two walkers that switch heads.

Set version: walk all of A into a set of nodes.
Then walk B, and the first node already in the set is the join.

Walker version: `a` walks A then B, and `b` walks B then A.
Both travel m + n steps in total, so they line up at the join, or both reach `null` together if there is no join.

Time is O(m + n) for both, because each list is walked a constant number of times.
Extra space is O(m) for the set, because it holds all of A, and O(1) for the walkers.

### How to solve it

Set version:

1. Walk A and add every node to a `HashSet<ListNode>`.
2. Walk B; return the first node the set contains.
3. Return `null` if B ends.

Walker version:

1. `a = headA`, `b = headB`.
2. While `a != b`: `a` steps, or jumps to `headB` when it is `null`; same for `b` with `headA`.
3. Return `a`.

Walker trace on A = `1 → 8`, B = `4 → 5 → 8` (node `8` is shared):

| step | a | b | same? |
|---|---|---|---|
| start | 1 | 4 | no |
| 1 | 8 | 5 | no |
| 2 | null | 8 | no |
| 3 | 4 (switched to B) | null | no |
| 4 | 5 | 1 (switched to A) | no |
| 5 | 8 | 8 | yes, return 8 |

### Holes to patch

**One loop over both lists that adds `null` to the set**

He walked A and B in the same loop and added both current nodes each turn.
When the shorter list ends, its current node is `null`, and `null` goes into the set.
The next `contains(null)` on that side is `true`, so it returns `null` even though the other list has not reached the join yet.
On A = `1 → 8`, B = `4 → 5 → 6 → 8` (node `8` shared), it returns `null` instead of `8`.
This bug is still in his repo version, and the current tests do not catch it.
Rule: walk A into the set first, then walk B.

```java
for (ListNode a = headA; a != null; a = a.next) set.add(a);
for (ListNode b = headB; b != null; b = b.next) if (set.contains(b)) return b;
return null;
```

**Classic trap: matching values instead of nodes**

Two separate lists can both contain `1 → 2 → 3` without sharing a node.
Comparing `val` returns the first `1`, but the correct answer is `null`.
Rule: compare node references (`==` or a set of `ListNode`), never `val`.

### Memorize this

1. `a = headA`, `b = headB`.
2. While `a != b`, step both.
3. A walker that hits `null` jumps to the other head.
4. They meet at the join, or both at `null`.
5. Return `a`.

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode a = headA, b = headB;
    while (a != b) {
        a = (a == null) ? headB : a.next;
        b = (b == null) ? headA : b.next;
    }
    return a;
}
```

Say in the interview: A set of A's nodes finds the join in O(m + n) time and O(m) space, and for O(1) space I let each walker switch to the other head so both travel m + n steps and meet at the join or at `null`.

### How it went

On-time: he coded the set version and finished before the Easy clock ran out.
The clock was armed late, with about 9 minutes left.
The one-loop `add(null)` hole was named in the notes but is still in the code, so fix it before relying on this file.

---

## LC 82 Remove Duplicates from Sorted List II — overtime

https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/

### The problem

The list is sorted.
Delete every value that appears more than once, including its first copy.
Keep only values that showed up exactly once.

Example: `1 → 2 → 3 → 3 → 4 → 4 → 5` becomes `1 → 2 → 5`.
`1 → 1 → 1 → 2 → 3` becomes `2 → 3`.

### The idea

Pattern: two pointers behind a dummy, skipping whole runs of equal values.

The list is sorted, so equal values sit next to each other in one run.
`left` is the last node you decided to keep.
`right` scans ahead.
If `right` starts a run, walk `right` past the entire run and hook `left.next` to whatever comes after.
If `right` is alone, keep it by moving `left` onto it.
The dummy handles a run at the head, because `left` can sit before the head.

Time is O(n) because `right` only moves forward, so each node is visited once even with the inner loop.
Extra space is O(1) because you only keep two pointers and a dummy.

### How to solve it

1. `dummy.next = head`, `left = dummy`, `right = head`.
2. While `right != null`:
3. If `right.next` has the same value, walk `right` until it is `null` or a new value, then `left.next = right`.
4. Otherwise `left = right`, then `right = right.next`.
5. Return `dummy.next`.

Trace on `1 → 1 → 2 → 3 → 3` (runs at both the head and the tail):

| step | left | right before | run? | action | list so far |
|---|---|---|---|---|---|
| 1 | dummy | first 1 | yes | skip to 2, `dummy.next = 2` | dummy → 2 → 3 → 3 |
| 2 | dummy | 2 | no | `left = 2`, `right = 3` | same |
| 3 | 2 | first 3 | yes | skip to null, `2.next = null` | dummy → 2 |
| return | | | | | 2 |

### Holes to patch

**Called it a HashSet problem**

A set keeps the first copy of each value.
On `1 → 1 → 2`, a set gives `1 → 2`, but the answer is `2`.
Rule: this problem drops every copy; sorted input means equals are neighbors, so scan with two pointers.

**`right` did not move every turn**

When `left.val == right.val` and `right` was the last node of a run, no branch moved `right`.
The loop checked the same node forever.
On `1 → 1`, the loop never ends.
Rule: every turn of the outer loop must move `right` forward.

**Used the dummy as the first kept node**

He wrote the first value into `dummy` itself, then returned `dummy.next`.
On `1 → 2 → 3`, that drops `1` and returns `2 → 3`.
Rule: the dummy is a fake node in front of the head; real nodes start at `dummy.next`.

**`left` only moved inside `if (left == null)`**

`left = right` only ran once, so `left` sat on `1` for the whole scan.
On `1 → 2 → 3 → 3 → 4`, skipping the `3` run hooks `1.next = 4` and drops `2`.
Rule: every unique node moves `left` onto it.

**Inner loop stopped before the end of a tail run**

Stopping the skip when `right.next == null` leaves `right` on the last duplicate.
On `1 → 2 → 2`, `right` stops on the second `2`, and `left.next = right` keeps a `2`.
Rule: walk `right` until it is `null` or a new value.

```java
while (right != null && right.val == val) right = right.next;
```

**Hook inside the unique branch**

He put `left.next = right` in the `else` for unique values.
On `1 → 1`, the `else` never runs, so `dummy.next` still points at the first `1`.
Rule: the hook belongs right after the inner skip.

**Worried the inner loop makes it O(n²)**

Nested loops are only O(n²) when the inner one restarts.
Here `right` never moves backward, so the two loops share one walk.
Rule: count total moves of `right`; it is at most n.

### Memorize this

1. Dummy in front; `left` on dummy, `right` on head.
2. If `right` equals its next: walk `right` past the whole run.
3. Then `left.next = right`.
4. Else: `left = right`, `right = right.next`.
5. Return `dummy.next`.

```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode dummy = new ListNode(0, head), left = dummy, right = head;
    while (right != null) {
        if (right.next != null && right.val == right.next.val) {
            int val = right.val;
            while (right != null && right.val == val) right = right.next;
            left.next = right;
        } else {
            left = right;
            right = right.next;
        }
    }
    return dummy.next;
}
```

Say in the interview: Because the list is sorted, duplicates form runs, so I keep a pointer to the last kept node behind a dummy and skip each whole run in one forward pass, which is O(n) time and O(1) space.

### How it went

Overtime: the 25-minute clock ran out.
He kept going, asked for the fixes, then rewrote it himself, and his rewrite passed.
The clock went to the infinite loop (`right` not moving) and `left` stuck on the first node.

---

## LC 234 Palindrome Linked List — on-time

https://leetcode.com/problems/palindrome-linked-list/

### The problem

Return `true` if the list reads the same forward and backward.

Example: `1 → 2 → 2 → 1` is `true`.
`1 → 2` is `false`.

### The idea

Pattern: fast/slow to find the middle, reverse the back half, then compare the two halves.

A stack of values works but costs O(n) extra space.
For O(1), reverse the second half in place so you can walk it from the tail end.
Then walk `head` and the reversed half together and compare values.
With an odd length, the middle node is never compared, which is correct.

Time is O(n) because finding the middle, reversing, and comparing are each one pass.
Extra space is O(1) because you rewire in place and keep a few pointers.

### How to solve it

1. Empty or one node: return `true`.
2. `slow` and `fast` on head; move while `fast.next != null && fast.next.next != null`.
3. Start the back half at `slow.next`.
4. Reverse it with the LC 206 loop (`prev = null`, `while (curr != null)`).
5. `prev` is the back head; compare `head` and `prev` step by step until `prev` is `null`.
6. Any mismatch returns `false`; otherwise `true`.

Trace on `1 → 2 → 3 → 2 → 1`.
The middle step stops `slow` on `3`, so the back half is `2 → 1`, reversed to `1 → 2`.

| step | head side | back side | equal? |
|---|---|---|---|
| 1 | 1 | 1 | yes |
| 2 | 2 | 2 | yes |
| stop | 3 | null | back half done, return `true` |

### Holes to patch

**`fast.next.next` without checking `fast.next`**

The middle loop tested only `fast.next.next`.
On `1 → 2 → 3`, `fast` reaches `3`, `fast.next` is `null`, and `fast.next.next` throws a `NullPointerException`.
Rule: test `fast.next != null` first.

```java
while (fast.next != null && fast.next.next != null)
```

**Reverse started with "behind" on `slow` instead of `null`**

Starting `prev` on `slow` makes the back half point back into the front half.
On `1 → 2 → 2 → 1`, this creates a cycle between the two `2`s, and the compare loop never reaches `null`.
Rule: `prev = null`, same as LC 206.

**Reverse loop stopped one node early**

`while (slow.next != null)` stops before the last node is flipped.
On `1 → 2 → 2 → 1`, the back head ends up as `2` instead of `1`, and the first compare `1` vs `2` wrongly returns `false`.
Rule: the reverse loop is `while (curr != null)`, and `prev` is the new back head.

**Looked for the middle again after the flip**

After reversing, you do not need the middle.
`prev` is the start of the reversed back half, and `head` is the start of the front half.
Rule: compare `head` and `prev`; stop when `prev` is `null`.

**Wanted to weave the halves**

Connecting the halves and running again is LC 143 Reorder List.
Here you only read the two halves side by side.
Rule: compare values, do not rewire the halves together.

### Memorize this

1. Middle: `while (fast.next != null && fast.next.next != null)`.
2. Back half starts at `slow.next`.
3. Reverse it: `prev = null`, `while (curr != null)`.
4. Walk `head` and `prev` together.
5. Mismatch: `false`; back half done: `true`.

```java
public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    ListNode prev = null, curr = slow.next;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    for (ListNode a = head, b = prev; b != null; a = a.next, b = b.next) {
        if (a.val != b.val) return false;
    }
    return true;
}
```

Say in the interview: A stack works in O(n) space, but I can find the middle with fast/slow, reverse the back half in place, and compare it with the front, which is O(n) time and O(1) space.

### How it went

On-time: he coded it and finished right at the 15-minute mark.
The gate first went toward weaving the halves, which is LC 143.
The code holes were the missing `fast.next` guard and the reverse loop that started on `slow` and stopped one node early.
