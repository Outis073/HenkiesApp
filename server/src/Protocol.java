import Logica.FileLogica;
import Logica.FileMethods;
import Logica.FileLogica;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Protocol {
    private static final int WAITING = 0;
    private static final int ACTION = 1;
    private static final int SYNC = 2;
    private int state = WAITING;
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public String processInput(String theInput) {
        String theOutput = null;

        switch (state) {
            case WAITING:
                theOutput = "kies een van de volgende opties: create , delete, update, sync";
                state = ACTION;
                break;
            case ACTION:
                if (theInput.equalsIgnoreCase("create")) {
                    theOutput = "geef een filepath op voor create";
                    state = WAITING;
                }
                 else if (theInput.equalsIgnoreCase("delete")) {
                    theOutput = "geef een filepath op voor het verwijderen";
                    state = WAITING;
                }
                else if (theInput.equalsIgnoreCase("update")) {
                    theOutput = "geef een filepath op voor het updaten";
                    state = WAITING;
                }
                else if (theInput.equalsIgnoreCase("sync")) {
                    theOutput = "synchroniseren van bestanden";
                    state = WAITING;
                }
                else if (theInput.equalsIgnoreCase("server is syncing")) {
                    theOutput = "synchroniseren van bestanden";
                    //state = WAITING;
                }

                else {
                    theOutput = "Geen geldige optie, kies opnieuw";
                }
                break;

            default:
                break;
        }
        return theOutput;
    }
}