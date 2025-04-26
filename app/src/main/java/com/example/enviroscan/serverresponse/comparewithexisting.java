package com.example.enviroscan.serverresponse;

public class comparewithexisting {
    private String filename;
    private String presentcount;
    private String previouscount;
    private boolean Message;
    private String Status;
    public boolean isMessage() {
        return Message;
    }

    public void setMessage(boolean message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }




    // Getter Methods

    public String getFilename() {
        return filename;
    }

    public String getPresentcount() {
        return presentcount;
    }

    public String getPreviouscount() {
        return previouscount;
    }

    // Setter Methods

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPresentcount(String presentcount) {
        this.presentcount = presentcount;
    }

    public void setPreviouscount(String previouscount) {
        this.previouscount = previouscount;
    }
}
