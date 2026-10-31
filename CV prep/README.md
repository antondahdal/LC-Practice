# CV prep

Java only. The screen is Thursday 5 November 2026 at 13:00. Tomorrow, Saturday 31 October, is the second session.

## Study checklist

- [x] CV story, 10 minutes. What you own now at Amdocs, in Java, and for how many years. Closed Friday 30 October. Arabic first, then English.
- [ ] Job line match, 10 minutes. One requirement, then the CV line that answers it.
- [ ] Linux, 5 minutes. Process versus thread. Where a log is. One machine red and the rest green.
- [ ] Network, 5 minutes. IP versus port. TCP versus UDP. A drop shows up as a timeout.
- [ ] Unique values with a `HashSet`. `["CPP", "PYTHON", "JAVA", "JAVA", "CPP"]` prints `[CPP, PYTHON, JAVA]`.
- [ ] Easy, already in this folder. Valid Anagram. Remove Duplicates from Sorted Array.
- [ ] Extra pair today. Both mediums, 25 minutes each. Rotate Array, then Longest Substring Without Repeating Characters. Rotate Array is in progress.

## CV story

Closed Friday 30 October. Arabic first, then the same story in English.

Backend Java engineer at Amdocs, 2021 to now. About five years on telecom billing and payment systems.

You investigate production failures, find the cause, and ship the fix. You build Java services, write scripts for production data changes, tune Oracle SQL, and support deploys on Linux.

Two bodies of work. Telefónica Hispam, running and extending the live system. A customer migration after another company acquired the product.

The migration reads customers from a file. Already-migrated customers are rejected. Locked customers go to a retry table. A second job reads that table and processes them once they are unlocked.

A customer is a Java object: open, locked, or already migrated. The file job and the retry job use the same object and the same checks.

Say "Spring-based batch jobs" unless you have seen `org.springframework.batch` in that code. The booking app is one Spring Boot app plus a gateway. It is not four separate services yet.

## Job line match

Requirement, then the CV line.

Bachelor's or Master's degree in Computer Science, Software Engineering, or a related field.
B.Sc. in Information Systems, Max Stern Academic College of Emek Yezreel, 2018–2022.

5+ years of experience as a software engineer.
Software engineer with about 5 years at Amdocs. Backend Java Engineer, Amdocs, 2021–Present.

Proficiency in programming languages such as Python, Java, or similar.
Developed and maintained Java backend services. Backend: Java, Spring Boot, Spring Batch, Spring Data JPA, Spring Security (JWT), Hibernate, Java EE (EJB), REST, SOAP.

Deep understanding of software development methodologies and engineering best practices.
Worked in Agile teams. Version control in Perforce. Also Git, from the Tsofen course.

Excellent problem solving skills and the ability to address complex technical challenges.
Investigated production defects on live telecom systems and traced each one to the root cause. Wrote and tuned Oracle SQL.

Strong communication and collaboration skills, with the ability to work effectively in a team environment.
Worked in Agile teams. Built a QA configuration tool for Dell test environments and worked with DevOps on environment setup.

Proven track record of delivering high quality work on time and meeting project deadlines.
Delivered SOAP web services and Java EE (EJB) integrations. Deployed and supported the applications on Linux in production.

Expert level knowledge of the Python programming language.
No CV line. Code in Java.

Experience with Kubernetes networking.
No CV line.

Strong knowledge of the Linux operating system.
Support deployments on Linux. Deployed and supported the applications on Linux in production. Platforms: Linux (production deploy and support), Docker, WebLogic, Maven, Git, Perforce.

## Linux

A process has its own memory. Threads in that process share it.

User code asks the kernel to do the privileged work.

Open the service log first. `tail` the end. `grep` the error.

`df` when the box is full. `ps` when a test is stuck.

One machine red and the rest green: compare that machine before you change the test.

A red nightly: which test, which machine, the first log line, what you would rerun.

## Network

An IP is the machine. A port is the program on it.

TCP keeps a connection and retries. UDP sends and moves on.

A drop shows up as a timeout or a missing reply. Look at the link before the test assertion.

BlueField takes networking work off the CPU. DOCA is the software other people build on it. This role is the harness that runs thousands of tests every night and shows what failed.

## Unique values and a HashSet

The list is `["CPP", "PYTHON", "JAVA", "JAVA", "CPP"]`.

Walk it once. A `HashSet` holds values already seen. `add` returns true only the first time, and that value goes into the result.

`main` prints `[CPP, PYTHON, JAVA]`.

The class is `src/main/java/questions/week11/uniquevalues/UniqueValues.java`.

## 242. Valid Anagram

https://leetcode.com/problems/valid-anagram/

`t` is an anagram of `s` when both strings use the same characters the same number of times.

`ValidAnagram` counts each character of `s` in a `HashMap`, then walks `t` and decrements that count. A character that was never in `s`, or a count that is already 0, returns false. If any count is still above 0 after `t` is finished, the method returns false.

No package declaration. File: `CV prep/ValidAnagram.java`.

| s | t | returned |
|---|---|---|
| anagram | nagaram | true |
| rat | car | false |
| (empty) | (empty) | true |
| a | ab | false |

## 26. Remove Duplicates from Sorted Array

https://leetcode.com/problems/remove-duplicates-from-sorted-array/

`nums` is sorted in non-decreasing order. Duplicates are removed in place. The method returns `k`, and the first `k` slots hold the unique values in order.

`RemoveDuplicates` keeps `slow` on the last unique value and lets `fast` walk the rest. Equal values only move `fast`. A new value moves `slow` forward one slot, copies `nums[fast]` into that slot, then moves `fast`. The return value is `slow + 1`.

No package declaration. File: `CV prep/RemoveDuplicates.java`.

| input | returned | first k elements |
|---|---|---|
| [1,1,2] | 2 | [1,2] |
| [0,0,1,1,1,2,2,3,3,4] | 5 | [0,1,2,3,4] |
| [1,2] | 2 | [1,2] |
| [] | 1 | array stayed empty, no exception |

## Medium problems

These are the mediums already named for this screen. Saturday 31 October is this pair. Rotate Array is in progress.

| When | Problem | Link |
|---|---|---|
| Saturday 31 Oct | Rotate Array | https://leetcode.com/problems/rotate-array/ |
| Saturday 31 Oct | Longest Substring Without Repeating Characters | https://leetcode.com/problems/longest-substring-without-repeating-characters/ |
| Monday 2 Nov | Maximum Subarray | https://leetcode.com/problems/maximum-subarray/ |
| Tuesday 3 Nov | Remove Nth Node From End of List | https://leetcode.com/problems/remove-nth-node-from-end-of-list/ |
| Wednesday 4 Nov | Container With Most Water | https://leetcode.com/problems/container-with-most-water/ |

Rotate Array pairs with the array work already on the CV. Longest Substring pairs with the `HashSet` scan from unique values: you keep the characters currently in the window. Remove Nth Node is the list walk that must stop on the right node. Container With Most Water is the Wednesday mock. Maximum Subarray is one pass that keeps a running total and drops it when it goes below zero.

Remove Duplicates II is already closed this week. Do not use it as tomorrow's new medium.
