package tikape.runko.domain;

public class DrinkkiRaakaAine {

    private int raaka_aine_id;
    private int drinkki_id;
    private String jarjestys;
    private String maara;
    private String ohje;

    public DrinkkiRaakaAine(int raaka_aine_id, int drinkki_id, String jarjestys, String maara, String ohje) {
        this.raaka_aine_id = raaka_aine_id;
        this.drinkki_id = drinkki_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public int getRaakaAineId() {
        return this.raaka_aine_id;
    }

    public int getDrinkkiId() {
        return this.drinkki_id;
    }

    public String getJarjestys() {
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
    
    public void setJarjestys (String jarjestys) {
        this.jarjestys = jarjestys;
    }
    
    public void setMaara (String maara) {
        this.maara = maara;
    }
    
    public void setOhje (String ohje) {
        this.ohje = ohje;
    }
}
