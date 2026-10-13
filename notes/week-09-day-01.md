# Week 9 Day 1 — 2026-10-12 — extra vs notes repo

Monday.
Two LCs, then two Spring Boot questions.
Week 9 families: backtracking / trie.

Today: #17 Letter Combinations, #637 Average of Levels.
Left in the bank: #22, #108, #46, #392, #39, #14, #208, #530.
Spring left: S3–S8, then Friday R1–R8.

## LC 17 Letter Combinations of a Phone Number — overtime, coach patched

Full write-up: `notes/review/week-09.md`.

Key idea: backtracking.
One partial string, one letter per digit so far.
When it is as long as the input, save it, drop the last letter, and try the next letter on that key.

**Holes:**
The keypad is a handwritten `String` array.
There is no library call that maps a digit to its letters.
`KEYS[button + 1]` is the next slot on the keypad, not the next digit in the string.
`"22"` then reads `"def"`.
The inner loop stopped at 3, so a second key of 7 or 9 dropped its fourth letter.
Each step `addAll`s a fresh pair of keys.
`"234"` comes back as two-letter pairs, not one letter from 2 and 3 and 4.
The `HashSet` checks a sum and stores only the button, so it does not mean "this pair is done".

**How it went:**
Overtime.
Coach patched the next-digit index and the loop bound.
5 of 5 on the tests, which are all length 0, 1, or 2.
A third digit is still wrong.

## LC 637 Average of Levels in Binary Tree — on-time, he coded

Full write-up: `notes/review/week-09.md`.

Key idea: BFS.
The queue's current size is one level.
Sum only those nodes, then divide.

**Holes:**
A null root returns null.
An empty list is the safer return.
The single-node branch does the same work the loop already does.
The sum is a `double`, so two `2147483647` values do not overflow an `int`.

**How it went:**
On-time, about 8 minutes, 5 of 5, he coded it.

## Part 1 Spring — S1 + S2 — Done

S1 auto-configuration.
He did not know it.
`@SpringBootApplication` is `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`.
A starter brings jars.
A bean is created only when a condition matches, and his own bean wins (`@ConditionalOnMissingBean`).
`--debug` shows the report.
Trap: Boot does not make a bean out of every class in every jar.

S2 configuration and profiles.
He said the property file, and did not know what wins over it.
`application.yml` is the default.
`application-prod.yml` loads when `spring.profiles.active=prod`.
An environment variable or the command line beats the file.
That is how Kubernetes sets the database URL without a new build.
`@Value` is one string in one field.
`@ConfigurationProperties` is a typed group you can validate.
The JWT secret stays out of the repo.
Trap: `@Value` copied through ten classes, or the secret committed in `application.yml`.

**Interview sentences:**
Boot creates a bean only when its conditions match, and it backs off when I define my own.
The yaml file is the default.
An env var beats the file, so Kubernetes overrides the database URL, and the JWT secret never sits in the repo.

## Calendar

Day 1 Part 1 closed (#17 overtime, coach patched; #637 on-time; S1 taught; S2 taught).
Not pushed.
Push after Part 3 when Anton says.
