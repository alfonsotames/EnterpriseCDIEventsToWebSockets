package events;

/**
 *
 * @author alfonso
 */
public class TestEvent {
    private String message;
    
    public TestEvent(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}