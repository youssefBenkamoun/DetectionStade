package ma.project.detectionstade.model;

public class Maladie {

    private int id;
    private String code;
    private String nom;

    public Maladie() {
    }

    public Maladie(int id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
