package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.DrinkkiRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.DrinkkiRaakaAine;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Tilasto;
import tikape.runko.domain.Tilastot;

public class Main {

    public static void main(String[] args) throws Exception {

        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        DrinkkiRaakaAineDao drad = new DrinkkiRaakaAineDao(database);
        
        Tilastot tilastot = new Tilastot(database);

        //Alkusivu
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        //Drinkit-sivu
        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            map.put("raakaaineet", raakaAineDao.findAll());
            map.put("drad", drad.findAll());

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

        post("/drinkit/lisaa-raaka-aine", (req, res) -> {
            drad.save(new DrinkkiRaakaAine(Integer.parseInt(req.queryParams("lisaaraakaaine")), Integer.parseInt(req.queryParams("lisaadrinkki")),
                    req.queryParams("jarjestys"), req.queryParams("maara"), req.queryParams("ohje")));
            res.redirect("/drinkit");
            return "";
        });

        post("/delete/:id", (req, res) -> {
            drad.delete(Integer.parseInt(req.params("id")));
            drinkkiDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/drinkit");
            return "";
        });

        //Yksittäisen drinkin resepti-sivu
        get("/drinkit/:id/:name", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkki", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("drinkinRaakaaineet", drinkkiDao.findRaakaAineNimiJaMaara(Integer.parseInt(req.params(":id"))));
            
            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());
        
        //Raaka-aineet-sivu
        post("/raakaaineet", (req, res) -> {
            if (req.queryParams("nimi").equals("")) {
                res.redirect("/raakaaineet");
                return "";
            }
            raakaAineDao.saveOrUpdate(new RaakaAine(null, req.queryParams("nimi")));
            res.redirect("/raakaaineet");
            return "";
        });

        post("/deletera/:id", (req, res) -> {
            raakaAineDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/raakaaineet");
            return "";
        });

        get("/raakaaineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaAineDao.findAll());

            return new ModelAndView(map, "raakaaineet");
        }, new ThymeleafTemplateEngine());
        
        //Tilastot-sivu
        get("/tilastot", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaAineDao.findAll());
            map.put("tilastoja", tilastot.getTilastojoukko());

            return new ModelAndView(map, "tilastot");
        }, new ThymeleafTemplateEngine());
    }
}
