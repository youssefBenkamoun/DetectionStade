package ma.project.detectionstade.model;

import java.util.List;

public class Stade {

    private int id;
    private String code;
    private String level;
    private String description;

    private Maladie maladie;


    private List<Detection> detection;


    public Stade() {
    }

    public Stade(int id, String code, String level, String description, Maladie maladie) {
        this.id = id;
        this.code = code;
        this.level = level;
        this.description = description;
        this.maladie = maladie;

    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }





    public Maladie getMaladie() {
        return maladie;
    }




    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaladie(Maladie maladie) {
        this.maladie = maladie;
    }


}

