package com.example.enviroscan.serverresponse;

import java.util.ArrayList;

public class speciesidentification {
    private String description;
    private String ecological;
    private String fact;
    private String physical;
    private String filename;
    private String response;
    private String species;
    private String confidence1;
    private String confidence2;
    private String confidence3;
    private String species1;
    private String species2;
    private String species3;
    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public String getEcological() {
        return ecological;
    }

    public void setEcological(String ecological) {
        this.ecological = ecological;
    }
    public String getPhysical() {
        return physical;
    }

    public void setPhysical(String physical) {
        this.physical = physical;
    }

    public String getConfidence1() {
        return confidence1;
    }

    public String getConfidence2() {
        return confidence2;
    }

    public String getConfidence3() {
        return confidence3;
    }



    public String getSpecies1() {
        return species1;
    }

    public String getSpecies2() {
        return species2;
    }

    public String getSpecies3() {
        return species3;
    }

    // Setter Methods

    public void setConfidence1(String confidence1) {
        this.confidence1 = confidence1;
    }

    public void setConfidence2(String confidence2) {
        this.confidence2 = confidence2;
    }

    public void setConfidence3(String confidence3) {
        this.confidence3 = confidence3;
    }
    

    public void setSpecies1(String species1) {
        this.species1 = species1;
    }

    public void setSpecies2(String species2) {
        this.species2 = species2;
    }

    public void setSpecies3(String species3) {
        this.species3 = species3;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDescription() {
        return description;
    }

    public String getFilename() {
        return filename;
    }



    public String getSpecies() {
        return species;
    }

    // Setter Methods

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



    public void setSpecies(String species) {
        this.species = species;
    }

}
