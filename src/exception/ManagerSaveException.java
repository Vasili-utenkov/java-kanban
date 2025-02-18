package exception;

import java.io.IOException;

public class ManagerSaveException extends Exception {

    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }
}
