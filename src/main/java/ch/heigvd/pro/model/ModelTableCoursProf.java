package ch.heigvd.pro.model;

public class ModelTableCoursProf {
    private String acronyme;

    public ModelTableCoursProf(String acronyme) {
        this.acronyme = acronyme;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    @Override
    public String toString() {
        return acronyme;
    }
}
