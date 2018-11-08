package br.com.augustoigor.http.response;

public enum Status implements IStatus {

    OK(200, "OK"),
    REDIRECT(301, "Moved Permanently"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),

    INTERNAL_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");

    private final int requestStatus;

    private final String description;

    Status(int requestStatus, String description) {
        this.requestStatus = requestStatus;
        this.description = description;
    }

    public static Status lookup(int requestStatus) {
        for (Status status : Status.values()) {
            if (status.getRequestStatus() == requestStatus) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "" + this.requestStatus + " " + this.description;
    }

    @Override
    public int getRequestStatus() {
        return this.requestStatus;
    }

}
