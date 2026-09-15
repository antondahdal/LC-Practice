# Week 5 Day 2 — 2026-09-15 — extra vs notes repo

Tuesday. Part 1 coding done. Auth LC-SD skipped (already Monday Part 3). Cache extra done after he parked it, then continued.

## Check-before-ask (new rule)

Coach asked Chapter 13 login-to-Book even though Monday Part 3 already covered JWT at gateway vs Booking. Anton corrected. Rule added: `.cursor/rules/lc-check-before-ask.mdc` plus `AI-TEACHING.md` / `lc-session.mdc`. Voice rule from this morning still applies.

## LC 143 Reorder List (Medium) — passed, coach wrote it

Blind 75, not Top Interview 150. Week 5 map lists it.

**Gate:** two workers at first and last, swap `f.next` with last. Four-node picture `1-4-3-2` is the wrong leftover. Five-node swap-and-stop fails. Singly list has no `prev`. Local `first = sec` does not rewire.

He asked for the code. File is split by count, reverse back half (#206 loop), then hook. Weak: did not land “cannot walk backward, so reverse the back half once.”

**Cousin:** #92 reverse a slice; merge-two-lists weave is the hook step.

## LC 141 Linked List Cycle (Easy) — passed

**Is** Top Interview 150.

Set of `ListNode` (the node, not `val`). O(n) / O(n). Then cousin: two walkers, O(1) extra. First cousin NPEd on `1-2-3` (`fast.next.next` with `fast.next == null`). Guard added. Tests include three-node no-cycle. Leftover unused `HashSet` imports.

## Part 1 Design

**Ch 13 Auth:** skip. Same talk as Monday Part 3.

**Cache extra (Monday was thin):** first GET Event is a miss (DB). Later GETs are hits until TTL, or until a real Book refreshes the copy so browse is not a fake ticket. Did not name key `event:{id}`. Heard 10× as “10 users” (miss then hits). 10× for the role = 10× **browse**, extra GETs are hits, Book volume small and still hits Event’s row. Trap unchanged: cache “1 left” is never 201.
