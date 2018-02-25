package tikape.runko.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.DrinkkiRaakaAineDao;

public class Tilasto {

    private RaakaAine raakaaine;
    private HashSet<Integer> drinkitIdt;
    private HashSet<Drinkki> drinkit;
    private ArrayList<String> drinkkiNimet;
    private Database database;
    private DrinkkiRaakaAineDao draDao;
    private DrinkkiDao drDao;

    
    // Tilasto-olio luo tilastoa parametrinä syötetyn tietokannan parametrinä annetusta yhdestä raaka-aineesta
    public Tilasto(Database database, RaakaAine raakaaine) throws SQLException {
        this.raakaaine = raakaaine;
        this.database = database;
        this.draDao = new DrinkkiRaakaAineDao(this.database);
        this.drDao = new DrinkkiDao(database);
        this.drinkitIdt = new HashSet<>();
        this.drinkit = new HashSet<>();
        this.drinkkiNimet = new ArrayList<>();
        
        for (DrinkkiRaakaAine draakaaine : this.draDao.findAll()) {
            // null-tarkistus ja isEmtpy-tarkistus saattavat olla turhia, mutta jätän tähän
            if (this.draDao.findAll() == null || this.draDao.findAll().isEmpty()) {
                break;
            }
            if ((draakaaine.getRaakaAineId().equals(this.raakaaine.getId()))) {
                this.drinkitIdt.add(draakaaine.getDrinkkiId());
            }
        }
        
        
        for (Integer id : this.drinkitIdt) {
            this.drinkit.add(drDao.findOne(id));
        }

        for (Integer id : this.drinkitIdt) {
            this.drinkkiNimet.add(drDao.findOne(id).getNimi());
        }
    }
    
    public String getRaakaAineNimi() {
        return this.raakaaine.getNimi();
    }

    public int getMonessakoDrinkissa() {
        return this.drinkitIdt.size();
    }

    public HashSet<Drinkki> getDrinkit() throws SQLException {
        return this.drinkit;
    }
    
    // Luetteloi kaikki drinkit missä tietty raaka-aine esiintyy. 
    // ArrayListana, koska tietokanta saattaa sisältää useita samannimisiä drinkkejä.
    public ArrayList<String> getDrinkkienNimet() throws SQLException {
        return this.drinkkiNimet;
    }
}
