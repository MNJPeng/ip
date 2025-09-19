# Mael User Guide

![alt text](image.png)

Your new local ☠️~~Virus~~☠️ task tracker.


## Adding Todo tasks

Enter `todo` and the task to be done.

Example: 
`todo Wash Dishes`

The task will be added to the running list of tasks.

```
    >>> [T][] Wash Dishes
        -Mael Acknowledged-
```

## Adding Deadline tasks

Enter `deadline`, the task to be done, `/by`, and the deadline in *ddmmyyyy hhmm* format.

Example: 
`deadline Project submission /by 19092025 2359`

The task will be added to the running list of tasks.

```
    >>> [D][] Project submission (Imminent: 19 Sep 2025 2359)
        -Mael Acknowledged-
```

## Adding Event tasks

Enter `event`, the task to be done, `/from`, the start date in *ddmmyyyy hhmm* format, `/to`, the end date in *ddmmyyyy hhmm* format.

Example: 
`event hackathon /from 21092025 0900 /to 22092025 2300`

The task will be added to the running list of tasks.

```
    >>> [E][] hackathon (alpha: 21 Sep 2025 0900, delta: 22 Sep 2025 2300)
        -Mael Acknowledged-
```

## Listing tasks

Enter `list` or `ls`.

Example: 
`ls`

All added tasks will be displayed with their completion status.

```
        -Outstanding Missions-
    1: [T][] Wash Dishes
    2: [D][] Project submission (Imminent: 19 Sep 2025 2359)
    3: [E][] hackathon (alpha: 21 Sep 2025 0900, delta: 22 Sep 2025 2300)
```

## Marking tasks as complete

Enter `mark` or `m` and the task number to mark.

Example: 
`m 3`

The task will be marked as completed on all future displays of the task.

```
    [E][X] hackathon (alpha: 21 Sep 2025 0900, delta: 22 Sep 2025 2300)
        -Mission Completed-
```

## Marking tasks as incomplete

Enter `unmark` or `um` and the task number to mark.

Example: 
`um 3`

The task will be marked as incomplete on all future displays of the task.

```
    [E][] hackathon (alpha: 21 Sep 2025 0900, delta: 22 Sep 2025 2300)
        -Mission Unsuccessful-
```

## Finding tasks

Enter `find` or `f` and the keyword to search for.

Example: 
`f Dishes`

Displays the list of tasks that contain the specified keyword anywhere in the displayed string (Case-sensitive).

```
        -Missions matching: "Dishes"-
    1: [T][] Wash Dishes
```

## Checking task deadlines or event periods

Enter `check` or `ch` and the date to check for in *ddmmyyyy hhmm* format

Example: 
`ch 21092025 1200`

Displays the list of incomplete deadline and event tasks, where the date input is either after the deadline of the deadline task, or in the period of the event task.

```
        -Missions by 21 Sep 2025 1200-
    1: [D][] Project submission (Imminent: 19 Sep 2025 2359)
    2: [E][] hackathon (alpha: 21 Sep 2025 0900, delta: 22 Sep 2025 2300)
```

## Deleting tasks

Enter `delete` or `del` and the task number to be deleted.

Example:
`del 2`

The task will be removed from the running list of tasks.

```
    [D][] Project submission (Imminent: 19 Sep 2025 2359)
        -Mission Terminated-
```

## Undoing commands

Enter `undo`

Example: `undo`

This will undo the last of any of the following commands performed, even after closing and relaunching Mael *(Provided it was closed via a normal exit command)*.
- Add Todo Task
- Add Deadline Task
- Add Event Task
- Mark Task
- Ummark Task
- Delete Task

```
        -Rewinding Time: "Readding Mission..."-
```

## Exiting Mael

Enter `bye`

Example:
`bye`

This closes Mael and saves all tasks and their current states, as well as retaining the ability to undo commands the next time Mael is launched.

## Saving and Loading Tasks and Commands

When exiting normally via the `bye` command, tasks are saved and are loaded back into Mael on the next launch.
This also applies to the commands input from the previous instance.

## Command summary:

- `todo Wash Dishes`
- `deadline Project submission /by 19092025 2359`
- `event hackathon /from 21092025 0900 /to 22092025 2300`
- `ls`
- `m 3`
- `um 3`
- `f Dishes`
- `ch 21092025 1200`
- `del 2`
- `undo`
- `bye`
