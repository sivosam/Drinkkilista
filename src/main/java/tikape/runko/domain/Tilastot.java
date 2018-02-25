
package tikape.runko.domain;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaAineDao;



public class Tilastot {
    
    private Set<Tilasto> tilastojoukko;
    private Database database;
    private RaakaAineDao raDao;

    public Tilastot(Database database) throws SQLException {
        this.database = database;
        this.tilastojoukko = new HashSet<>();
        this.raDao = new RaakaAineDao(database);
        
        for (RaakaAine raakaaine : this.raDao.findAll()) {
            this.tilastojoukko.add(new Tilasto(database, raakaaine));
        }
    }
    
    public Set<Tilasto> getTilastojoukko() {
        return this.tilastojoukko;
    }
}
