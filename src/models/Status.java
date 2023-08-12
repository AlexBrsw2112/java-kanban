package models;

public enum Status {
    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private String statusPrint;

    Status(String statusPrint) {
        this.statusPrint = statusPrint;
    }

}
