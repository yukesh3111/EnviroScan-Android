package com.example.enviroscan.serverresponse;

public class GreenSpace {
    private String bot;
    private String filename;
    private String greenpercentage;
    private String landpercentage;
    private String recommentation;

    public String getRecommentation() {
        return recommentation;
    }

    public void setRecommentation(String recommentation) {
        this.recommentation = recommentation;
    }




    // Getter Methods

    public String getBot() {
        return bot;
    }

    public String getFilename() {
        return filename;
    }

    public String getGreenpercentage() {
        return greenpercentage;
    }

    public String getLandpercentage() {
        return landpercentage;
    }

    // Setter Methods

    public void setBot(String bot) {
        this.bot = bot;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setGreenpercentage(String greenpercentage) {
        this.greenpercentage = greenpercentage;
    }

    public void setLandpercentage(String landpercentage) {
        this.landpercentage = landpercentage;
    }
}
