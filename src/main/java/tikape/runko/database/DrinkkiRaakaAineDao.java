package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.DrinkkiRaakaAine;
import tikape.runko.domain.RaakaAine;

public class DrinkkiRaakaAineDao {

    private Database database;

    public DrinkkiRaakaAineDao(Database database) {
        this.database = database;
    }

    public DrinkkiRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DrinkkiRaakaAine WHERE raaka_aine_id = ?"
                + " AND drinkki_id = ?");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer raaka_aine_id = rs.getInt("raaka_aine_id");
        Integer drinkki_id = rs.getInt("drinkki_id");
        String jarjestys = rs.getString("nimi");
        String maara = rs.getString("maara");
        String ohje = rs.getString("ohje");

        DrinkkiRaakaAine dra = new DrinkkiRaakaAine(raaka_aine_id, drinkki_id, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return dra;
    }

    public List<DrinkkiRaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DrinkkiRaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<DrinkkiRaakaAine> drat = new ArrayList<>();
        while (rs.next()) {
            Integer raaka_aine_id = rs.getInt("raaka_aine_id");
            Integer drinkki_id = rs.getInt("drinkki_id");
            String jarjestys = rs.getString("nimi");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");

            drat.add(new DrinkkiRaakaAine(raaka_aine_id, drinkki_id, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return drat;
    }

    public void delete(Integer key, Integer key2) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM DrinkkiRaakaAine WHERE raaka_aine_id = ?"
                + " AND drinkki_id = ?");

        stmt.setInt(1, key);
        stmt.setInt(2, key2);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public DrinkkiRaakaAine saveOrUpdate(DrinkkiRaakaAine object) throws SQLException {
        if (object.getRaakaAineId() == null && object.getDrinkkiId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }

    private DrinkkiRaakaAine save(DrinkkiRaakaAine drinkkira) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO DrinkkiRaakaAine"
                + " (raaka_aine_id, drinkki_id, jarjestys, maara, ohje)"
                + " VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, drinkkira.getRaakaAineId());
        stmt.setInt(2, drinkkira.getDrinkkiId());
        stmt.setString(3, drinkkira.getJarjestys());
        stmt.setString(4, drinkkira.getMaara());
        stmt.setString(5, drinkkira.getOhje());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM DrinkkiRaakaAine"
                + " WHERE jarjestys = ?");
        stmt.setString(3, drinkkira.getJarjestys());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        DrinkkiRaakaAine dra = new DrinkkiRaakaAine(rs.getInt("raaka_aine_id"), rs.getInt("drinkki_id"),
                rs.getString("jarjestys"), rs.getString("maara"), rs.getString("ohje"));

        stmt.close();
        rs.close();

        conn.close();

        return dra;
    }

    private DrinkkiRaakaAine update(DrinkkiRaakaAine drinkkira) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE DrinkkiRaakaAine SET"
                + " jarjestys = ?, maara = ?, ohje = ?"
                + " WHERE raaka_aine_id = ? AND drinkki_id = ?");
        stmt.setString(1, drinkkira.getJarjestys());
        stmt.setString(2, drinkkira.getMaara());
        stmt.setString(3, drinkkira.getOhje());
        stmt.setInt(4, drinkkira.getRaakaAineId());
        stmt.setInt(5, drinkkira.getDrinkkiId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return drinkkira;
    }


}
