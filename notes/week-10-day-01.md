# Week 10 Day 1 — 2026-10-19 — extra vs notes repo

Monday.
Redo, then a fresh Top Interview 150, then two Spring questions, then a design recap.
Part 1 is closed.

## LC 215 Kth Largest Element in an Array — on-time, retired

Full write-up stays in `notes/review/week-07.md`.

The clock version put every value in a max-heap and removed `k` times.
6 of 6.
That is O(n log n) time and O(n) extra space.
A heap that stays at size `k` is O(n log k) time and O(k) extra space.
Without `reverseOrder`, the top is the smallest of the `k` largest.
`findKthLargest` in `questions.week10.KthLargest` is that size-k loop.
He put it there after the clock.

## LC 55 Jump Game — overtime, coach-patched

Full write-up: `notes/review/week-10.md`.

You keep the farthest index you can land on.
`i + nums[i]` is that index.
If `i` moves past it, you are stuck.

## Spring — S1 and S2 closed

S1 was auto-configuration, on the booking `DataSource`.
An entity is one row, such as `Event`.
The `DataSource` is the pool of connections to Postgres.
Boot creates that pool when the driver and the URL are present, and it uses yours if you declare one.

S2 was configuration and profiles.
`application.properties` turns on `dev`.
`application-dev.properties` sets `app.jwt.secret` to `${JWT_SECRET}`.
He said the environment variable.
That value wins over the file.
The secret stays out of the repo.
Trap: writing the secret into the properties file, or copying `@Value` through ten classes.
Do not re-ask S5.
He already said a bad token dies in the filter.

## Design recap — Ch 11 token bucket closed

Jar of 4, one token every 15 seconds.
Maria spends all 4 at 9:00:00.
At 9:00:15 one token is back.
He first said **200**, then `ClickIdMismatchException` for the second tap.
The first tap spends the new token and a successful Book is **201**.
The second tap is immediate, the jar is empty, and the gate answers **429**.
Booking does not see that tap.
Ch 10 consistent hashing stays closed from Week 8.

## Hosting — say this

Two repos, two pipelines. Not Jenkins.

Jenkins is a server you run, with a Jenkinsfile of stages and an agent that runs them.

The page repo is `event-booking-demo` on `main`. It is static HTML and JavaScript. A push runs the GitHub Actions workflow `Publish demo`: checkout, pack the folder, deploy to GitHub Pages. That workflow is the part that looks like a Jenkins job. Live page: https://antondahdal.github.io/event-booking-demo/ On localhost the page calls `http://localhost:8081`. Hosted, it calls `https://event-booking-gateway.onrender.com`. The browser talks only to the gateway.

The API repo is `SpringBoot-bookingApp` on `master`. `render.yaml` is the blueprint: one free Postgres, one free Redis, four web services. A push makes Render build each Dockerfile and start the container. Maven compiles inside the image. Render injects the port, database URL, Redis URL, the shared JWT secret, and the other services' public URLs. Free services on Render cannot call each other privately, so Auth, Event, and Booking use those public URLs. The page still never calls Booking directly.

Interview line: Actions publishes the page the way a Jenkins job would. Render is the host, not a CI server. The blueprint plus a push builds the image and rolls the service.

Trap: calling Render Jenkins, calling the Pages site a Java app, or saying the browser calls Booking.

The first Render image build failed because `.dockerignore` dropped `src`. The fix is on `master`.

## Calendar

Day 1 Part 1 closed (#215 on-time, retired; #55 overtime, coach-patched; S1 closed; S2 closed; Ch 11 token bucket closed).
