# Asteroids

## Description

#### Project

An implementation of the classic game "Asteroids" in java for the course Software Engineering Methods (CSE2115).

#### Game

The objective of Asteroids is to destroy asteroids and saucers. The player controls a triangular ship that can rotate left and right, fire shots straight forward, and thrust forward. Once the ship begins moving in a direction, it will continue in that direction for a time without player intervention unless the player applies thrust in a different direction. The ship eventually comes to a stop when not thrusting. The player can also send the ship into hyperspace, causing it to disappear and reappear in a random location on the screen, at the risk of self-destructing or appearing on top of an asteroid.

Each level starts with a few large asteroids drifting in various directions on the screen. Objects wrap around screen edges – for instance, an asteroid that drifts off the top edge of the screen reappears at the bottom and continues moving in the same direction. As the player shoots asteroids, they break into smaller asteroids that move faster and are more difficult to hit. Smaller asteroids are also worth more points. Two flying saucers appear periodically on the screen; the "big saucer" shoots randomly and poorly, while the "small saucer" fires frequently at the ship. After reaching a score of 40,000, only the small saucer appears. As the player's score increases, the angle range of the shots from the small saucer diminishes until the saucer fires extremely accurately. Once the screen has been cleared of all asteroids and flying saucers, a new set of large asteroids appears, thus starting the next level. The game gets harder as the number of asteroids increases until after the score reaches a range between 40,000 and 60,000. The player starts with 3-5 lives upon game start and gains an extra life per 10,000 points. When the player loses all their lives, the game ends. Machine "turns over" at 99,990 points, which is the maximum high score that can be achieved. 

## Rules

Rules as determined by the lab manual

#### Planning 

Each iteration needs to be planned following the Scrum methodology. At the end of each iteration, in the retrospective, the plan is checked: which features have been finished, which tasks are still open. The results of the check are considered fr the backlog of the next iteration: you reflect on issues that occurred and you decide upon a way to prevent these issues from occurring again. 

#### Code versioning and GitLab 

The source code has to be versioned using git on the social platform GitLab (TUDelft distribution). GitLab repositories will be private, students cannot plagiarise code from other groups, but inter-group collaboration is possible. Please note that each team member has to commit his/her own code. It is not allowed to commit for other team members. Commit history will be considered to determine the final grade of the implementation part.

#### Test-driven, static analysis tools, and continuous integration 

The implementation must be test-driven: First, implement a test for a feature and then start implementing the feature. Make use of the JUnit test framework and the code coverage utility Cobertura.

The code has to be analysed with the support of three static analysis tools, namely CheckStyle, FindBugs and PMD. These tools will help keeping the quality of the source code high.Finally, each project must use continuous integration and include test outputs and static tools output in the building process. For this, the CI/CD infrastructure of GitLab can be used in conjunction with Gradle (or Maven) as the automated building tool.

#### Pull-based development model 

The development must follow the pull-based development model: Each new feature implementation, bug fix, etc. has to be created on a new branch and then the author must submit a pull request. The code then has to be reviewed by at least two other team-members, who must also consider the output of the continuous integration, testing, and static analysis tools in their reviews.

#### Always have a running version 

The implementation follows the concept of "always have a running system". After each iteration, each project group gives a 5-minute demonstration session of the most recent implemented feature(s) followed by a 20-minute discussion of the design, implementation, tests, and iteration planning.

*More information on the expected setup for the projects from a software engineering perspective were given in the starting lecture of the course. You can find the slides on Brightspace.*

#### Handing in your work

You need to hand in several documents throughout the quarter, including your lab assignments. Hand in your Scrum documents as **.md**-files in a folder called “doc” located in the root of the repository. For the assignments you are allowed to hand-in a **.pdf**-file as well, but these files in the same “doc” folder. Please name your files as “yyyymmdd-document-name.md”. For example, for a Sprint Retrospective handed in on November 1st 2019 the filename would be as follows:“20191101-sprint-retrospective.md”. Code should be on the **master** branch by the time of the deadline, including a recognisable tag on the commit.

## Course Schedule

[embed]https://gitlab.ewi.tudelft.nl/cse2115/2019-2020/AS/sem-group-49/template/tree/dev-refactor/docs/time_schedule.pdf[/embed]

