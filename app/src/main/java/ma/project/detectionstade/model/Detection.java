package ma.project.detectionstade.model;

import java.util.Date;

public class Detection {

    private int id;
    private String photo;
    private String code;
    private boolean validation;
    private Date date;
    private String description;
    private Stade stade;

    public Stade getStade() {
        return stade;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    private Patient patient;


    public Detection() {
    }

    public Detection(String photo, Patient patient) {
        this.photo = photo;
        this.patient = patient;
    }

    public Detection(String photo, Patient patient, Stade stade) {
        this.photo = photo;
        this.patient = patient;
        this.stade = stade;
    }


    public Detection(int id, String photo, String code, boolean validatio, Date date, String description, Patient patient) {
        this.id = id;
        this.photo = photo;
        this.code = code;
        this.validation = validation;
        this.date = date;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCode() {
        return code;
    }

    public boolean isValidation() {
        return validation;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

