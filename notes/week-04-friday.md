# Week 4 Friday — 2026-09-11 — extra vs notes repo

Map leftover was **#202 Happy Number**. Anton skipped it. Stayed on Week 4 hash bank: **#290 Word Pattern** (Top Interview 150). **#76** still skipped. No LC-SD (Friday).

## LC 290 Word Pattern — passed

`pattern` letters vs space-separated `s`. 1-to-1 both ways. Length of letters must equal word count.

**Gate:** HashMap; key+value glue; O(n) / O(n).

**Weak:** `!=` on `String` (reference). Then `equals` true → `return false` (match is success, keep going). Fail only when mapped word is **different**. `containsValue` is O(n) per step (same note as #205).

**Cousin:** #205 (already Wed). Do not redo.

Solution: `questions.week04.WordPattern`.
