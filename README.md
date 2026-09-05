# The Last Shift — Java OOP Mini Project

A console-based, story-driven hospital mystery game for your Java OOP
project, built from the design doc you shared: a 7-day narrative with
branching choices, persistent stats/flags, a hospital triage mini-game,
and 5 possible endings.

## How to run

Requires JDK 17+ (uses `switch` expressions and text blocks).

```bash
cd src
javac laststory/*.java -d ../out
cd ..
java -cp out laststory.Main
```

Or just double check `javac -version` / `java -version` first if it
complains.

## How it plays

- Choose a difficulty (changes starting money / doctor count — never
  the story itself, per the design doc).
- Each day: a **hospital shift** — you assign your limited doctors to
  incoming patients (numbered menu, `0` to stop early). Untreated
  serious/critical patients have a chance of dying, which costs
  reputation and counts against your "patients saved" stat.
- Then a **story scene** with a numbered choice. Choices set flags and
  nudge stats (Maya's trust, Carter's trust, investigation progress,
  Blackwood's relationship, reputation, money).
- Day 7 ends with the big final choice, and `Ending.resolve()` looks at
  everything you did across all 7 days to decide which of the 5
  endings (HERO / TRUTH / DEAL / SILENCE / COLLAPSE) you get.

## Class overview (maps directly to the design doc's OOP diagram)

| Class | Responsibility |
|---|---|
| `Main` | Entry point, just boots `GameManager`. |
| `GameManager` | The `StoryManager` from the doc — owns the day-by-day scenes, choice menus, and story text. |
| `StoryState` | Flags (`Map<String, Boolean>`) + hospital-wide counters (patients saved/lost, evidence collected, current day). This is what lets Day 6/7 react to Day 1 choices. |
| `Player` | Alex Morgan's stats: money, reputation, investigation, Maya trust, Carter trust, Blackwood relationship. Clamped 0–100 where relevant. |
| `Hospital` | Owns the `Doctor` list (= treatment capacity) and runs the triage mini-game (`runShift`). |
| `Doctor` | Simple data class: name + specialty. |
| `Patient` | Name, condition, `Severity`, `PatientStatus`, and a `storyImportant` flag for plot-relevant patients like Patient 103. |
| `Severity` / `PatientStatus` | Enums used by `Patient`. |
| `Difficulty` | Enum holding the per-difficulty starting money / doctor count. |
| `Ending` | Enum of the 5 endings, each with title + ending text, and a static `resolve(player, story, finalChoice)` that implements the requirement table from the design doc. |

## Extending it

This is intentionally left as a solid skeleton rather than the full
150-scene story, so you can build on it for your assignment:

- **More scenes / branches**: add more `printScene(...)` + `choice(...)`
  calls inside each `dayN()` method, gated on `story.hasFlag(...)`.
- **More patients / conditions**: just construct more `Patient` objects
  — nothing else needs to change.
- **Smarter triage**: `Hospital.runShift` currently lets the player pick
  patients directly; you could add a `Treatment` class and require
  matching `Doctor` specialties to `Patient` conditions for a harder
  version.
- **Save/Load**: add a `SaveManager` that serializes `Player` +
  `StoryState` to a file (both are plain data holders already, so this
  is a small addition).
- **GUI**: the `GameManager` only talks to the console through
  `System.out` / `Scanner` — swapping those calls for a Swing or
  JavaFX UI later wouldn't require touching `Player`, `Hospital`,
  `Patient`, or `Ending` at all, which is the point of keeping the
  logic and the UI separate.

## Endings implemented

- **HERO** — investigation ≥ 80, evidence collected, Maya & Carter
  trust ≥ 70, ≥ 90% patients saved, reputation ≥ 80, final choice =
  authorities/public.
- **TRUTH** — exposed the truth (authorities/public) without hitting
  every HERO threshold.
- **DEAL** — gave the evidence to Blackwood.
- **SILENCE** — destroyed the evidence.
- **COLLAPSE** — reputation < 20, or money hits $0, or you lost more
  than 60% of your patients (overrides everything else).
