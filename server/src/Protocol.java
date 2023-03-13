public class Protocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private final String[] clues = {
            "Turnip",
            "Little Old Lady",
            "Atch",
            "Who",
            "Who" };
    private final String[] answers = {
            "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?" };

    public String processInput(String theInput) {
        String theOutput = null;

        switch (state) {
            case WAITING:
                theOutput = "Knock! Knock!";
                state = SENTKNOCKKNOCK;
                break;
            case SENTKNOCKKNOCK:
                if (theInput.equalsIgnoreCase("Who's there?")) {
                    theOutput = clues[currentJoke];
                    state = SENTCLUE;
                } else {
                    theOutput = "You're supposed to say \"Who's there?\"! " +
                            "Try again. Knock! Knock!";
                }
                break;
            case SENTCLUE:
                if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                    theOutput = answers[currentJoke] + " Want another? (y/n)";
                    state = ANOTHER;
                } else {
                    theOutput = "You're supposed to say \"" +
                            clues[currentJoke] +
                            " who?\"" +
                            "! Try again.\nKnock! Knock!";
                    state = SENTKNOCKKNOCK;
                }
                break;
            case ANOTHER:
                if (theInput.equalsIgnoreCase("y")) {
                    theOutput = "Knock! Knock!";
                    if (currentJoke == (NUMJOKES - 1))
                        currentJoke = 0;
                    else
                        currentJoke++;
                    state = SENTKNOCKKNOCK;
                } else {
                    theOutput = "Bye.";
                    state = WAITING;
                }
                break;
            default:
                break;
        }
        return theOutput;
    }
}