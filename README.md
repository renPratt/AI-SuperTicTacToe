# AI-SuperTicTacToe

This is a project that plays a professor's version of SuperTicTacToe against you (or other robots) and should win every time. It is an AI that I built for a course that I took when I was studying abroad in Greece. It went in a tournament against other bots and ultimately I got 2nd place, despite the bug in it at the time. 

**Here is a quick video of me playing against it:** (link here in future)

### How It Was Made

For the SuperTicTacToe assignment, I was asked to write four classes. One was for the greedy
algorithm, one was for the minimax algorithm, one was for the minimax algorithm with alpha
beta pruning, and one was for the heuristic.

First, I wrote the **heuristic**. This was an easy class to write the general structure of, but I
am still not confident now that it is ideal. I believe that I put a weight on all possible board cases, but
for some reason my AIs do not reflect that when playing against each other or when playing
against me. I wrote my **greedy algorithm** next, and it was extremely straight forward. It does the
move that will earn it the highest heuristic score in that moment, with no regard for the future,
what board it will lead the opponent to, etc. I then wrote the **minimax
algorithm**. This had me stumped for a while, as it isn’t something I have written before, but
eventually I was able to figure it out. It can see into the future of what the board might look like,
and makes decisions based on that. From there, **adding alpha beta pruning** was easy. 

When pitting my algorithms against each other, my greedy algorithm, of course, lost
consistently. This was because it had no ability to look into the future, and didn't have an understanding of the larger
board at all. My minimax algorithm and my alpha beta algorithm were fairly well tied. My alpha beta algorithm was
theoretically able to see further into the future, and takes less time to process than my normal
minimax one, but it seems to have some kind of error, because it was making mistakes that my
normal minimax doesn’t really make. As I said, they were fairly evenly tied, which has made it
difficult to decide which one to put into the tournament, because I was not sure how other
people’s algorithms will be different from mine, and having two nearly identical algorithms
might be part of why alpha beta wasn’t winning.

Eventually, I found the error in my code. The “eligible” function in the original
game did not work for me, so I had to check for “space” and then calculate what the current
board was, and I realized I had not factored in the function of “if the board chosen has been won,
any board may be picked” element of the game. Since factoring that in, both of my minimax’s
have been working much smoother, and alpha beta has been winning by a few points. That is
because I am able to give the alpha beta function a higher depth than the normal minimax
function.

### Optimizations?

### What I Learned

### My Professor's SuperTicTacToe Rules

### How You Can Play Against The AI
