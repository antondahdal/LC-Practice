# Week 05 — review sheet

Week 5 was the linked-list week: two pointers with a gap, fast/slow walkers, in-place reversal, a dummy node in front of the head, and merging by rewiring `next`.

The interview timer started on Day 4.
Days 1–3 have no clock, so those results say "passed (no timer)" when he coded it himself.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 19 | Remove Nth Node From End of List | Two pointers with a gap of `n` + head-victim check | O(n) / O(1) | passed (no timer) |
| 206 | Reverse Linked List | Iterative reversal (`first`, `sec`, saved `tmp`) | O(n) / O(1) | passed (no timer) |
| 143 | Reorder List | Fast/slow middle + reverse back half + weave | O(n) / O(1) | coach-fixed |
| 141 | Linked List Cycle | HashSet of nodes, then fast/slow (Floyd) | O(n) / O(n) set, O(1) Floyd | passed (no timer) |
| 2 | Add Two Numbers | Two walkers + carry + dummy | O(max(m,n)) / O(1) extra | coach-fixed |
| 21 | Merge Two Sorted Lists | Two walkers + dummy, copy the smaller | O(m+n) / O(m+n) copies (O(1) if you hook live nodes) | passed (no timer) |
| 92 | Reverse Linked List II | Dummy + walk to `before` + reverse k nodes | O(n) / O(1) | overtime, coach-fixed |
| 160 | Intersection of Two Linked Lists | HashSet of nodes, one loop over both (cousin: switch-heads walkers) | O(m+n) / O(m+n) set, O(1) walkers | on-time, still buggy |
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
Walk `first` ahead `n` steps so there is a gap of `n` links between `first` and `back`.
Then move both one step at a time.
When `first` sits on the last node, `back` sits exactly on the node before the victim.

If `first` is already `null` after the `n` steps, the list has exactly `n` nodes.
Then the victim is the head, and there is no node before it, so return `head.next`.

Time is O(n) because `first` walks the list once and `back` walks part of it.
Extra space is O(1) because you only keep two pointers.

### How to solve it

1. Start `first` and `back` on `head`.
2. Move `first` forward `n` times.
3. If `first` is `null`, the head is the victim, so return `head.next`.
4. Move both while `first.next != null`, so `first` stops on the last node.
5. `back.next` is the victim, so set `back.next = back.next.next`.
6. Return `head`.

Trace on `1 → 2 → 3 → 4 → 5`, `n = 2`:

| step | first | back | list so far |
|---|---|---|---|
| start | 1 | 1 | 1 → 2 → 3 → 4 → 5 |
| first 1 | 2 | 1 | same |
| first 2 | 3 | 1 | same, `first` is not null |
| both 1 | 4 | 2 | same |
| both 2 | 5 | 3 | same, `first.next` is null, stop |
| skip | 5 | 3 | 1 → 2 → 3 → 5 |
| return | | | 1 → 2 → 3 → 5 |

On `1 → 2 → 3`, `n = 3`, `first` walks to `null`, so step 3 returns `2 → 3`.

### Holes to patch

**Called it a sliding window**

A sliding window grows and shrinks over a range.
Here the gap between the two pointers never changes.
Rule: two pointers with a fixed gap of `n`, not a window.

**Head victim treated like a mid-list skip**

Without a dummy, `back` starts on the head, and the skip removes the node after `back`.
On `1 → 2`, `n = 2`, `first` walks to `null`, and `back.next = back.next.next` removes `2` instead of `1`.
On `1`, `n = 1`, `back.next` is `null`, so `back.next.next` throws a `NullPointerException`.
Rule: if `first` is already `null` after walking `n` steps, the victim is the head, so return `head.next`.

```java
if(first==null){
    return back.next;
}
```

Alternative without the special case: start both on a dummy in front of the head, walk `n + 1`, and return `dummy.next`.

### Memorize this

1. `first` and `back` on head.
2. `first` walks `n`.
3. `first == null`: the head is the victim, return `head.next`.
4. Both walk while `first.next != null`.
5. `back.next = back.next.next`.
6. Return `head`.

```java
public ListNode removeNthFromEnd(ListNode head, int n) {

    ListNode first=head;

    ListNode back=head;
    for(int i=0;i<n;i++){
        first=first.next;
    }
    if(first==null){

        return back.next;
    }
    while(first!=null && first.next!=null){
        first=first.next;
        back=back.next;
    }
    back.next=back.next.next;
    return head;
}
```

Say in the interview: I walk one pointer `n` steps ahead, return `head.next` if it already fell off, and otherwise move both until the front one is on the last node, so the back one is right before the node to delete, in one pass and O(1) space.

### How it went

He coded it and it passed, before the timer existed.
The gate started as "sliding window" and he fixed the name to two pointers.
His version handles the head with `return head.next` instead of a dummy.
The `first!=null` in his `while` is always true at that point, so it is harmless but not needed.

---

## LC 206 Reverse Linked List — passed (no timer)

https://leetcode.com/problems/reverse-linked-list/

### The problem

Reverse a singly linked list and return the new head.

Example: `1 → 2 → 3` becomes `3 → 2 → 1`.
An empty list returns `null`.

### The idea

Pattern: iterative reversal with `first` (the node behind), `sec` (the current node), and a saved `tmp`.

Walk the list once.
At each node, point its `next` back at the node behind it.
You must save the old `next` in `tmp` first, or you lose the rest of the list.
When `sec` falls off, `first` is the old tail, which is the new head.

Time is O(n) because each node is flipped once.
Extra space is O(1) because you keep three pointers.

### How to solve it

1. Empty or one node: return `head`.
2. `first = null`, `sec = head`.
3. While `sec != null`: save `tmp = sec.next`.
4. Point `sec.next = first`.
5. Move `first = sec`, then `sec = tmp`.
6. Return `first`.

Trace on `1 → 2 → 3`:

| step | first | sec | tmp | links so far |
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

**Loop condition that reads `sec.next`**

`while (sec != null || sec.next != null)` still evaluates `sec.next` when `sec` is `null`.
On any list, the last step makes `sec` null and the check throws a `NullPointerException`.
Rule: the loop is exactly `while (sec != null)`.

**Both pointers start on the head**

If `first = head` and `sec = head`, the first step sets `head.next = head`.
On `1 → 2`, node `1` now points at itself, which is a cycle.
Rule: `first` starts at `null`, because the old head becomes the new tail.

### Memorize this

1. Empty or one node: return `head`.
2. `first = null`, `sec = head`.
3. Save `tmp = sec.next`.
4. `sec.next = first`.
5. `first = sec`, `sec = tmp`.
6. Loop `while (sec != null)`, return `first`.

```java
public ListNode reverseList(ListNode head) {
    if(head==null||head.next==null) return head;

    ListNode first=null;
    ListNode sec=head;
    while(sec!=null ){
        ListNode tmp=sec.next;
        sec.next=first;
        first=sec;
        sec=tmp;
    }
    return first;
}
```

Say in the interview: I walk once, save the next node, point the current node back at the previous one, and step forward, so the old tail becomes the head in O(n) time and O(1) space.

### How it went

He coded it and it passed, before the timer existed.
The gate first described a neighbor swap, which is LC 24.
His early `return head` is optional, because the loop already handles 0 and 1 nodes.
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
3. `slow` is the end of the front half; cut with `sec = slow.next`, `slow.next = null`.
4. Reverse `sec` with the LC 206 loop (`first = null`, save `tmp`); `first` is the reversed back head.
5. Weave with `front = head`, `back = first`: save `frontNext` and `backNext`, hook `front.next = back`, `back.next = frontNext`, advance both.
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
ListNode sec = slow.next;
slow.next = null;
```

### Memorize this

1. Empty or one node: return.
2. Middle: `while (fast.next != null && fast.next.next != null)`.
3. Cut: `sec = slow.next`, `slow.next = null`.
4. Reverse `sec` (LC 206 loop); `first` is the back head.
5. Weave `front = head`, `back = first`, one front, one back, until the back is `null`.

```java
public void reorderList(ListNode head) {
    if (head == null || head.next == null) {
        return;
    }

    // 1) Find the middle. slow stops at the end of the front half.
    ListNode slow = head;
    ListNode fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    // 2) Cut, then reverse the back half. Same loop as #206.
    ListNode sec = slow.next;
    slow.next = null;
    ListNode first = null;
    while (sec != null) {
        ListNode tmp = sec.next;
        sec.next = first;
        first = sec;
        sec = tmp;
    }

    // 3) Hook: one from the front half, one from the reversed back half.
    ListNode front = head;
    ListNode back = first;
    while (back != null) {
        ListNode frontNext = front.next;
        ListNode backNext = back.next;
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

1. Empty or one node: return `false`.
2. `slow = head`, `fast = head.next.next`, so `fast` already has a two-step head start.
3. While `fast != null`: if `slow == fast`, return `true`.
4. Move `slow` one step.
5. If `fast.next` or `fast.next.next` is `null`, the list ends, so return `false`.
6. Move `fast` two steps.
7. If the loop ends, return `false`.

Trace on `1 → 2 → 3 → 4` with `4.next = 2`:

| step | slow | fast | same? |
|---|---|---|---|
| start | 1 | 3 | no |
| 1 | 2 | 2 (3 → 4 → 2) | checked next turn |
| 2 | 2 | 2 | yes, return `true` |

### Holes to patch

**`fast.next.next` without checking `fast.next`**

His first fast/slow version jumped `fast.next.next` without guarding `fast.next`.
On `1 → 2 → 3`, `fast` lands on `3`, `fast.next` is `null`, and `fast.next.next` throws a `NullPointerException`.
Rule: check `fast.next` before the double jump.

```java
if(fast.next == null|| fast.next.next==null )return false;
```

The common form puts the guard in the loop: `while (fast != null && fast.next != null)`, with both starting on `head`.

**Classic trap: storing values instead of nodes**

A set of `val` says "cycle" whenever two different nodes share a value.
On `1 → 1` (no cycle), a set of values sees `1` twice and wrongly returns `true`.
Rule: put the `ListNode` in the set, not `node.val`.

### Memorize this

1. Empty or one node: `false`.
2. `slow = head`, `fast = head.next.next`.
3. Loop while `fast != null`; same node: `true`.
4. `slow` one step.
5. `fast.next` or `fast.next.next` is `null`: `false`.
6. `fast` two steps.

```java
public boolean hasCycle(ListNode head) {
    if(head==null||head.next==null) return false;
    ListNode slow=head;
    ListNode fast=head.next.next;
    while(fast!=null){
        if(slow==fast) return true;
        slow=slow.next;
        if(fast.next == null|| fast.next.next==null )return false;
        fast=fast.next.next;
    }
    return false;
}
```

Say in the interview: A HashSet of nodes works in O(n) space, but with a slow and a fast pointer the fast one either hits `null` or laps the slow one inside the cycle, so it is O(n) time and O(1) space.

### How it went

He coded the node set first, then the fast/slow cousin, before the timer existed.
The cousin first threw a `NullPointerException` on `1 → 2 → 3`, and the guard fixed it.
His repo version keeps only the fast/slow cousin, with `fast` starting at `head.next.next` and the guard inside the loop.

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
    ListNode dummy = new ListNode();
    ListNode tail = dummy;
    int carry = 0;

    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
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

Pattern: two walkers plus a dummy node, copying the smaller head each step.

Both lists are sorted, so the smallest remaining value is always one of the two heads.
Copy that value into a new node at the tail of the answer and advance that list.
When one list runs out, keep copying the other one, which is already sorted.

Time is O(m + n) because every node is copied once.
Extra space is O(m + n) because each value goes into a new node.
Hooking the live nodes instead of copying would make it O(1), see the holes.

### How to solve it

1. Both lists empty: return `null`.
2. `finalListTmp` is the dummy, and `finalList` is the tail.
3. Loop while `list1 != null || list2 != null`.
4. If both are non-null, copy the smaller head (`list1.val < list2.val` takes `list1`, ties take `list2`) and advance that list.
5. Then, if only `list2` is left, copy its head and advance it.
6. Then, if only `list1` is left, copy its head and advance it.
7. Return `finalListTmp.next`.

Trace on `1 → 2 → 4` and `1 → 3 → 4` (one turn can copy twice when a list just ran out):

| turn | list1 head | list2 head | copied | answer so far |
|---|---|---|---|---|
| 1 | 1 | 1 | list2's 1 (tie) | 1 |
| 2 | 1 | 3 | list1's 1 | 1 → 1 |
| 3 | 2 | 3 | 2 | 1 → 1 → 2 |
| 4 | 4 | 3 | 3 | 1 → 1 → 2 → 3 |
| 5 | 4 | 4 | list2's 4, then list2 is empty, so list1's 4 | 1 → 1 → 2 → 3 → 4 → 4 |
| end | null | null | loop stops | 1 → 1 → 2 → 3 → 4 → 4 |

### Holes to patch

**Took the larger head**

The first version picked the bigger of the two heads.
On `1 → 3` and `2`, that hooks `2` before `1`, so the answer starts `2 → …` and is not sorted.
Rule: always hook the smaller head, one node per step.

**Copying values into new nodes**

He built the answer with `new ListNode(list1.val)`, and his repo version still does.
That allocates m + n new nodes, so the extra space is O(m + n), not O(1).
Rule: hook the live node itself.

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

1. Both empty: `null`.
2. Dummy `finalListTmp`, tail `finalList`.
3. Loop while either list is alive.
4. Both alive: copy the smaller head, advance it.
5. Only one alive: copy its head, advance it.
6. Return `finalListTmp.next`.

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    if(list1==null&&list2==null) return null;
    ListNode finalListTmp=new ListNode();
    ListNode finalList=finalListTmp;
    while(list1!=null||list2!=null){


        if(list1!=null&&list2!=null){
            if(list1.val<list2.val){
                finalList.next=new ListNode(list1.val);
                finalList=finalList.next;
            list1=list1.next;}
            else {
                finalList.next=new ListNode(list2.val);
                finalList=finalList.next;
                list2=list2.next;
            }

        }

        if(list1==null&&list2!=null){
            finalList.next=new ListNode(list2.val);
            finalList=finalList.next;
            list2=list2.next;
        }
        if(list2==null&&list1!=null){
            finalList.next=new ListNode(list1.val);
            finalList=finalList.next;
            list1=list1.next;
        }


    }
    return finalListTmp.next;
}
```

Say in the interview: The smallest remaining value is always one of the two heads, so I copy the smaller one behind a dummy and keep copying whichever list is left, in O(m + n) time, and if they want O(1) extra space I hook the live nodes instead of copying.

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

1. Empty or one node: return `head`.
2. `dummy = new ListNode(0, head)`, `before = dummy`.
3. Move `before` forward `left - 1` times.
4. `start = before.next`, `first = null`, `sec = start`.
5. Run the LC 206 loop (save `tmp`) exactly `right - left + 1` times.
6. Now `first` is the new slice head and `sec` is the first node after the slice.
7. `before.next = first`, `start.next = sec`.
8. Return `dummy.next`.

Trace on `1 → 2 → 3 → 4 → 5`, `left = 2`, `right = 4`.
After step 3, `before` is `1` and `start` is `2`.

| step | first | sec | tmp | slice links |
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
Rule: `first = null` and `sec = start`, same as LC 206.

**Reversed the whole tail**

`while (sec != null)` reverses to the end of the list, not to `right`.
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

1. Empty or one node: return `head`.
2. Dummy in front of head, `before` on dummy.
3. `before` walks `left - 1`.
4. `start = before.next`, `first = null`, `sec = start`.
5. LC 206 loop, `right - left + 1` times.
6. `before.next = first`, `start.next = sec`.
7. Return `dummy.next`.

```java
public ListNode reverseBetween(ListNode head, int left, int right) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode dummy = new ListNode(0, head);
    ListNode before = dummy;
    for (int i = 1; i < left; i++) {
        before = before.next;
    }

    ListNode start = before.next;
    ListNode first = null;
    ListNode sec = start;
    for (int i = 0; i < right - left + 1; i++) {
        ListNode tmp = sec.next;
        sec.next = first;
        first = sec;
        sec = tmp;
    }

    before.next = first;
    start.next = sec;
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

Pattern: HashSet of nodes.

The join is the first node that belongs to both lists.
A set of `ListNode` remembers which nodes you already saw, by reference, not by value.
His version walks A and B in the same loop, adds both current nodes each turn, and returns the first current node that is already in the set.

Time is O(m + n) because each list is walked once.
Extra space is O(m + n) because the set can hold nodes from both lists.

### How to solve it

His one-loop version:

1. Either list empty: return `null`.
2. Loop while `headA != null || headB != null`.
3. If the set contains `headA`, return it; if it contains `headB`, return it.
4. Otherwise add both current nodes.
5. Advance each list that is not `null` yet.
6. Return `null` when both end.

Trace on A = `1 → 8`, B = `4 → 5 → 8` (node `8` is shared), where it works:

| turn | headA | headB | found? | set after |
|---|---|---|---|---|
| 1 | 1 | 4 | no | 1, 4 |
| 2 | 8 | 5 | no | 1, 4, 5, 8 |
| 3 | null | 8 | `8` is in the set | return 8 |

The holes below show inputs where this shape breaks.

### Holes to patch

**One loop over both lists that adds `null` to the set**

He walked A and B in the same loop and added both current nodes each turn.
When the shorter list ends, its current node is `null`, and `null` goes into the set.
The next `contains(null)` on that side is `true`, so it returns `null` even though the other list has not reached the join yet.
On A = `1 → 8`, B = `4 → 5 → 6 → 8` (node `8` shared), it returns `null` instead of `8`.
This bug is still in his repo version, and the current tests do not catch it.
Rule: walk A into the set first, then walk B.

**Both walkers reach the join on the same turn**

The check runs before the add, so a node that both sides reach on the same turn is not in the set yet.
Both then step past it, and the join is never seen again.
On A = `1 → 8`, B = `4 → 8` (node `8` shared), it returns `null` instead of `8`.
The same happens when `headA == headB`.
Rule: the two-pass version above fixes this too.

**O(1) space cousin: switch-heads walkers**

`a` walks A then B, and `b` walks B then A, so both travel m + n steps and meet at the join or at `null`.

**Classic trap: matching values instead of nodes**

Two separate lists can both contain `1 → 2 → 3` without sharing a node.
Comparing `val` returns the first `1`, but the correct answer is `null`.
Rule: compare node references (`==` or a set of `ListNode`), never `val`.

### Memorize this

1. Either empty: `null`.
2. One loop while either list is alive.
3. Set has `headA` or `headB`: return it.
4. Else add both.
5. Advance the ones that are not `null`.
6. Both end: `null`.

His repo code, with the two bugs above still in it:

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if(headA==null || headB==null) return null;
    HashSet<ListNode> set=new HashSet<>();
    while (headA!=null||headB!=null){
        if(set.contains(headA)){
            return headA;
        }if(set.contains(headB)){
            return headB;
        }
        else{
            set.add(headB);
            set.add(headA);

        }
        if(headA!=null)
            headA=headA.next;

        if(headB!=null)
            headB=headB.next;
    }
    return null;
}
```

Say in the interview: I put every node of A into a set of node references, then walk B and return the first node already in the set, in O(m + n) time and O(m) space, and for O(1) space I let each walker switch to the other head so both travel m + n steps and meet at the join or at `null`.

### How it went

On-time: he coded the set version and finished before the Easy clock ran out.
The clock was armed late, with about 9 minutes left.
The one-loop `add(null)` hole and the same-turn join hole are still in the code, so fix it with the two-pass version before relying on this file.

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

1. Empty or one node: return `head`.
2. `dummy = new ListNode(0, head)`, `left = dummy`, `right = head`.
3. While `right != null`: save `val = right.val`.
4. If `right.next` has the same `val`, walk `right` until it is `null` or a new value, then `left.next = right`.
5. Otherwise `left = right`, then `right = right.next`.
6. Return `dummy.next`.

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
while(right!=null &&right.val==val){
    right=right.next;
}
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

1. Empty or one node: return `head`.
2. Dummy in front; `left` on dummy, `right` on head.
3. Save `val = right.val`.
4. If `right.next` has the same `val`: walk `right` past the whole run, then `left.next = right`.
5. Else: `left = right`, `right = right.next`.
6. Return `dummy.next`.

```java
public ListNode deleteDuplicates(ListNode head) {
    if (head==null || head.next==null) return head;
    ListNode dummy=new ListNode(0, head);
    ListNode left=dummy;
    ListNode right=head;
    while(right!=null){
        int val=right.val;
        if(right.next!=null&&val==right.next.val){

            while(right!=null &&right.val==val){
                right=right.next;
            }
            left.next = right;
        }
        else{

            left=right;
            right=right.next;
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
3. Move `slow = slow.next`, so `slow` is the start of the back half.
4. Reverse it with the LC 206 loop (`before = null`, save `tmp`, `while (slow != null)`).
5. `before` is the back head; compare `head` and `before` step by step until `before` is `null`.
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
while(fast.next != null && fast.next.next != null){
```

**Reverse started with "behind" on `slow` instead of `null`**

Starting `before` on `slow` makes the back half point back into the front half.
On `1 → 2 → 2 → 1`, this creates a cycle between the two `2`s, and the compare loop never reaches `null`.
Rule: `before = null`, same as LC 206.

**Reverse loop stopped one node early**

`while (slow.next != null)` stops before the last node is flipped.
On `1 → 2 → 2 → 1`, the back head ends up as `2` instead of `1`, and the first compare `1` vs `2` wrongly returns `false`.
Rule: the reverse loop is `while (slow != null)`, and `before` is the new back head.

**Looked for the middle again after the flip**

After reversing, you do not need the middle.
`before` is the start of the reversed back half, and `head` is the start of the front half.
Rule: compare `head` and `before`; stop when `before` is `null`.

**Wanted to weave the halves**

Connecting the halves and running again is LC 143 Reorder List.
Here you only read the two halves side by side.
Rule: compare values, do not rewire the halves together.

### Memorize this

1. Empty or one node: `true`.
2. Middle: `while (fast.next != null && fast.next.next != null)`.
3. `slow = slow.next` is the back half.
4. Reverse it: `before = null`, `while (slow != null)`.
5. Walk `head` and `before` together.
6. Mismatch: `false`; back half done: `true`.

```java
public boolean isPalindrome(ListNode head) {
    if(head==null||head.next==null) return true;
    ListNode slow=head;
    ListNode fast=head;
    while(fast.next != null && fast.next.next != null){
        slow=slow.next;
        fast=fast.next.next;
    }
    ListNode before=null;
    slow=slow.next;
    while(slow!=null){
        ListNode tmp=slow.next;
        slow.next=before;
        before=slow;
        slow=tmp;
    }
    while(before!=null){
        if (head.val!=before.val){
            return false;
        }
        before=before.next;
        head=head.next;
    }

    return true;
}
```

Say in the interview: A stack works in O(n) space, but I can find the middle with fast/slow, reverse the back half in place, and compare it with the front, which is O(n) time and O(1) space.

### How it went

On-time: he coded it and finished right at the 15-minute mark.
The gate first went toward weaving the halves, which is LC 143.
The code holes were the missing `fast.next` guard and the reverse loop that started on `slow` and stopped one node early.
