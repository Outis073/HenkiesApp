import Logica.FileLogica;
import Logica.FileMethods;
import Logica.FileLogica;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Protocol {
    private static final int INIT = 0;
    private static final int SERVER = 1;
    private static final int WAITING = 2;
    private static final int ACTION = 3;
    private int state = INIT;
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public String processInput(String theInput) {
        String theOutput = null;


        switch (state) {
            case INIT:
                theOutput = "kies een client folder";
                state = WAITING;
                break;
            case WAITING:
                theOutput = "kies een van de volgende opties: create , delete, sync";
                state = ACTION;
                break;
            case ACTION:
                if (theInput.equalsIgnoreCase("create")) {
                    theOutput = "geef een filepath op voor create";
                    state = WAITING;
                    break;
                }
                 else if (theInput.equalsIgnoreCase("delete")) {
                    theOutput = "geef een filepath op voor het verwijderen";
                    state = WAITING;
                    break;
                }
                else if (theInput.equalsIgnoreCase("sync")) {
                    theOutput = "synchroniseren van bestanden";
                    state = WAITING;
                    break;
                }
                else {
                    theOutput = "Geen geldige optie, kies opnieuw";
                }

            default:
                break;
        }
        return theOutput;
    }
}