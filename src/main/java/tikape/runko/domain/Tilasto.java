
package tikape.runko.domain;

import java.sql.SQLException;
import tikape.runko.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.DrinkkiRaakaAineDao;

public class Tilasto {
    
    private RaakaAine raakaaine;
    private HashSet<Integer> drinkitIdt;
    private HashSet<Drinkki> drinkit;
    private Database database;
    private DrinkkiRaakaAineDao draDao;
    private DrinkkiDao drDao;
    

    public Tilasto(Database database, RaakaAine raakaaine) throws SQLException {
        this.raakaaine = raakaaine;
        this.database = database;
        this.draDao = new DrinkkiRaakaAineDao(this.database);
        for (DrinkkiRaakaAine draakaaine : draDao.findAll()) {
            if ((draakaaine.getRaakaAineId().equals(this.raakaaine.getId()))) {
                this.drinkitIdt.add(draakaaine.getDrinkkiId());
            }
        }
    }
    
    public int monessakoDrinkissa() {
        return this.drinkitIdt.size();
    }
    
    public HashSet<Drinkki> getDrinkit() throws SQLException {
        for (Integer id : this.drinkitIdt) {
            this.drinkit.add(drDao.findOne(id));
        }
        return this.drinkit;
    }
}
