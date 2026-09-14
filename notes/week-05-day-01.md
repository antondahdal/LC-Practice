# Week 5 Day 1 — 2026-09-14 — extra vs notes repo

`week-05.md` in the notes repo still says **not started**. Part 1 **coding + Ch 4** done here.

Bank: #206 Reverse, #141 Cycle, #21 Merge, #19 Remove Nth, #143 Reorder. Monday = Medium **#19** then Easy **#206**.

## LC 19 Remove Nth Node From End — passed

`n` from the **tail**. `1 → 2`, `n = 2` drops **1**, leftover `{2}` (not value 2, not “2nd from head”).

**Gate:** first said sliding window (wrong). Picture was two walkers with gap **n**. Name: two pointers. O(n) / O(1).

**Memorize:** front walks `n`. Then both until front has no next. Back is **before** the victim. Rewire `back.next`. Head victim: front is already `null` after the `for` → `return back.next` (do **not** `back.next = back.next.next` while sitting on head). Dummy before head = one path, no `if`.

**Weak:** treated head-drop like a mid-list skip. Single node `n = 1` → NPE on `back.next.next`. Long list with `n = length` is the same head case.

**Cousin:** dummy; or two-pass count length.

## LC 206 Reverse Linked List — passed

Flip every `next`. New head = old tail.

**Gate:** two pointers, but neighbor swap (`first.next = sec.next; sec.next = first`) is **#24**, not reverse.

**Memorize:** behind starts **null**, current = head. Save `current.next`, point current at behind, behind = current, current = saved. Loop `while (current != null)`. `|| current.next` NPEs. Both on head → `1.next = 1` cycle.

**Cousin:** recursion; #92 reverse a slice.

## Part 1 Design — Chapter 4 Cache / TTL — done

Anton: cache on **Event** because change/truth is there. Gospel = cache is not the real DB. Did not name a key. Did not get **10×**.

**Sharpen:** Event **DB** is truth, not Event’s cache. Cache in front of Event’s **read** (or Booking’s GET) is fine. `book()` still hits the row. Key = `event:{id}`. Shared store, not a per-pod map.

**10×:** 10× more **browse** than Book. Cache exists so 10× GETs do not 10× the DB. Book volume stays small and still skips cache.

**Trap:** Redis “1 seat left” → allow Book / return **201**. That is gospel. Sold out is Event **409**. Stale browse is OK; stale **take** is not.

**Interview sentence:** Cache GET with TTL; Book is Event’s row. Never treat “1 left” in cache as a ticket.
