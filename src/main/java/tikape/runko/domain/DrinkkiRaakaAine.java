package tikape.runko.domain;

public class DrinkkiRaakaAine {

    private Integer raaka_aine_id;
    private Integer drinkki_id;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public DrinkkiRaakaAine(Integer raaka_aine_id, Integer drinkki_id, Integer jarjestys, String maara, String ohje) {
        this.raaka_aine_id = raaka_aine_id;
        this.drinkki_id = drinkki_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getRaakaAineId() {
        return this.raaka_aine_id;
    }

    public Integer getDrinkkiId() {
        return this.drinkki_id;
    }

    public Integer getJarjestys() {
        return this.jarjestys;
    }

    public String getMaara() {
        return this.maara;
    }

    public String getOhje() {
        return this.ohje;
    }
    
    public void setRaakaAineId(Integer id) {
        this.raaka_aine_id = id;
    }
    
    public void setDrinkkiId(Integer id) {
        this.drinkki_id = id;
    }
    
    public void setJarjestys (Integer jarjestys) {
        this.jarjestys = jarjestys;
    }
    
    public void setMaara (String maara) {
        this.maara = maara;
    }
    
    public void setOhje (String ohje) {
        this.ohje = ohje;
    }
}
