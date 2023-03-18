import Logica.FileLogica;
import Logica.FileMethods;
import Logica.FileLogica;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Protocol {
    private static final int WAITING = 0;
    private static final int ACTION = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;
    private static final int NUMJOKES = 5;
    private static final int CREATE = 6;

    private int state = WAITING;
    private int currentJoke = 0;

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public String processInput(String theInput) {
        String theOutput = null;

        switch (state) {
            case WAITING:
                theOutput = "zeg create";
                state = ACTION;
                break;
            case ACTION:
                if (theInput.equalsIgnoreCase("create")) {
                    theOutput = "geef een filepath op";
                    state = CREATE;
                } else {
                    theOutput = "Geen geldige optie, kies opnieuw";
                }
                break;

            case CREATE:
                theOutput = "Geef een filepath op";

                if(theInput != null){

                    theOutput = "create file";
                }

                break;

            default:
                break;
        }
        return theOutput;
    }
}