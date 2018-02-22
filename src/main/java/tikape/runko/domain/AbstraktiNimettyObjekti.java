package tikape.runko.domain;

public abstract class AbstraktiNimettyObjekti {
    
    private Integer id;
    private String nimi;

    public AbstraktiNimettyObjekti(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
}
