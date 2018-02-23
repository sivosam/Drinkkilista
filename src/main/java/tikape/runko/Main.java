package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {

        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        post("/drinkit", (req, res) -> {
            if (req.queryParams("nimi").equals("")) {
                res.redirect("/drinkit");
                return "";
            }
            drinkkiDao.saveOrUpdate(new Drinkki(null, req.queryParams("nimi")));
            res.redirect("/drinkit");
            return "";
        });
        
        post("/raakaaineet", (req, res) -> {
            if (req.queryParams("nimi").equals("")) {
                res.redirect("/raakaaineet");
                return "";
            }
            raakaAineDao.saveOrUpdate(new RaakaAine(null, req.queryParams("nimi")));
            res.redirect("/raakaaineet");
            return "";
        });

        post("/delete/:id", (req, res) -> {
            drinkkiDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/drinkit");
            return "";
        });

        post("/deletera/:id", (req, res) -> {
            raakaAineDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/raakaaineet");
            return "";
        });

        get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkki", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());

        get("/raakaaineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaAineDao.findAll());

            return new ModelAndView(map, "raakaaineet");
        }, new ThymeleafTemplateEngine());
    }
}
