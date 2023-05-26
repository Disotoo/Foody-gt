package uvg.edu.gt;

import uvg.edu.gt.model.Restaurant;
import uvg.edu.gt.model.User;

import org.neo4j.driver.*;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.Map;
import java.util.List;



public class DBDriver implements IDBDriver{

    // Credenciales para acceder a la BD
    private final static String CONNECTION_URI = "bolt://44.215.127.186:7687";
    private final static String USER = "neo4j";
    private final static String PASSWORD = "elapse-career-realignments";
    // NEO4J Driver
    private final Driver _driver;
    private final SessionFactory _sessionFactory;               // Para iniciar llamadas con la BD
    private final String MODELS_PACKAGE = "uvg.edu.gt.model";   // En que carpeta estan las clases plantilla para los nodos
    
    
    public DBDriver(){
        // Inicializando Driver de NEO4J -> El que se comunica con la BD
        _driver = GraphDatabase.driver(
            this.CONNECTION_URI, 
            AuthTokens.basic(USER, PASSWORD),
            Config.defaultConfig());
        // Inicializando SesscionFactory -> Este el que sirve para hacer queries. EL BUENO
        _sessionFactory = new SessionFactory(new BoltDriver(_driver), MODELS_PACKAGE);
    }

    @Override
    public void close() throws Exception{
        _driver.close();
    }

    @Override
    public User getUser(String name){
        Session session = _sessionFactory.openSession();
        User user;

        // Load sirve para cargar UN SOLO NODO de la BD
        user = session.load(User.class, name);

        return user;
    }

    @Override
    public Restaurant getRestaurant(String name){
        Session session = _sessionFactory.openSession();
        Restaurant restaurant;

        // Load sirve para cargar UN SOLO NODO de la BD
        restaurant = session.load(Restaurant.class, name);

        return restaurant;
    }
    
    public List<Restaurant> getRecommendedRestaurant(User user){
        Session session = _sessionFactory.openSession();

        Iterable<Restaurant> restaurants = session.query(
            Restaurant.class,
            ""
            + "MATCH (r:restaurant) -[:foodType]-> (:$foodType)"
            + "WITH r"
            + "MATCH (r) -[:Price]-> (:$price)"
            + "WITH r"
            + "MATCH (r) -[:ambient]-> (:$ambient)"
            + "WITH r"
            + "MATCH (r) -[:score]-> (:$score)"
            + "RETURN r"
             ,
            Map.of(
                "foodType", user.getFoodType(),
                "Price", user.getPrice(),
                "ambient", user.getAmbient(),
                "score", user.getScore()
            )
        )
    }

}