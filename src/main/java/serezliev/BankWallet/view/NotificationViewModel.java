package serezliev.BankWallet.view;

public class NotificationViewModel {

    private String header;

    private String message;


    public String getHeader() {
        return header;
    }

    public NotificationViewModel setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public NotificationViewModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
