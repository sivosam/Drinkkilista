package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class DrinkkiDao implements Dao<Drinkki, Integer> {

    private Database database;

    public DrinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki d = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return d;
    }

    @Override
    public List<Drinkki> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki");

        ResultSet rs = stmt.executeQuery();
        List<Drinkki> drinkit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            drinkit.add(new Drinkki(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return drinkit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Drinkki WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    @Override
    public Drinkki saveOrUpdate(Drinkki object) throws SQLException {
        if (object.getId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }

    private Drinkki save(Drinkki drinkki) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Drinkki"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, drinkki.getNimi());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Drinkki"
                + " WHERE nimi = ?");
        stmt.setString(1, drinkki.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Drinkki d = new Drinkki(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return d;
    }

    private Drinkki update(Drinkki drinkki) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Drinkki SET"
                + " nimi = ? WHERE id = ?");
        stmt.setString(1, drinkki.getNimi());
        stmt.setInt(2, drinkki.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return drinkki;
    }

    public List<RaakaAine> findAllRaakaAine(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Raakaaine.nimi FROM Drinkkiraakaaine, Drinkki, Raakaaine"
                + " WHERE drinkki.id = drinkkiraakaaine.drinkki_id and raakaaine.id = drinkkiraakaaine.raaka_aine_id and drinkki.id = ?");

        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> ra = new ArrayList<>();
        while (rs.next()) {
            String raaka_aine_nimi = rs.getString("nimi");

            ra.add(new RaakaAine(null, raaka_aine_nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ra;
    }

    public List<String> findRaakaAineNimiMaaraOhje(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT raakaaine.nimi AS nimi,"
                + " drinkkiraakaaine.jarjestys AS jarjestys,"
                + " drinkkiraakaaine.maara AS maara,"
                + " drinkkiraakaaine.ohje AS ohje"
                + " FROM DrinkkiRaakaAine, RaakaAine, Drinkki WHERE drinkki.id = drinkkiraakaaine.drinkki_id"
                + " AND raakaaine.id = drinkkiraakaaine.raaka_aine_id"
                + " AND drinkki_id = ?");

        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        List<String> ra = new ArrayList<>();
        while (rs.next()) {
            String raaka_aine_nimi = rs.getString("nimi");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");
            //String jarjestys = Integer.toString(rs.getInt("jarjestys"));
            Integer jarjestysluku = rs.getInt("jarjestys");

//            if (maara.equals("") && ohje.equals("")) {
//                ra.add(raaka_aine_nimi);
//            } else if (ohje.equals("")) {
//                ra.add(raaka_aine_nimi + ", " + maara);
//            } else if (maara.equals("")) {
//                ra.add(raaka_aine_nimi + ". Ohje: " + ohje);
//            } else {
//                ra.add(raaka_aine_nimi + ", " + maara + ". Ohje: " + ohje);
//            }
            String kaikkiKohdat = "";
            // Jos järjestys-parametria ei syötetä sivulla niin laitetaan raaka-aine listan loppuun ja annetaan indeksin mukainen järjestys
            if (jarjestysluku - 1 < 0 || jarjestysluku - 1 > ra.size()) {
                jarjestysluku = ra.size() + 1;
            }
            
            kaikkiKohdat = kaikkiKohdat.concat(raaka_aine_nimi);
            if (!maara.isEmpty()) {
                kaikkiKohdat = kaikkiKohdat.concat(", " + maara);
            }
            if (!ohje.isEmpty()) {
                kaikkiKohdat = kaikkiKohdat.concat(". Ohje: " + ohje);
            }

            ra.add(jarjestysluku - 1, kaikkiKohdat);
            

        }

        rs.close();
        stmt.close();
        connection.close();

        return ra;
    }

}
