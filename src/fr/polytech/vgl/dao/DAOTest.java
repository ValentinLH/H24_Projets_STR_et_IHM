package fr.polytech.vgl.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import fr.polytech.vgl.main.StubMain;
import fr.polytech.vgl.model.Company;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class DAOTest {

    public static void main(String[] args) {
        // Remplacez le placeholder par la chaîne de connexion de votre déploiement MongoDB
        String uri = "mongodb://localhost:27017";

        // Configuration du CodecRegistry avec le PojoCodecProvider
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        // Connexion à MongoDB
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Sélection de la base de données et configuration du CodecRegistry
            MongoDatabase database = mongoClient.getDatabase("projet_str").withCodecRegistry(pojoCodecRegistry);

            // Sélection de la collection avec le type Pojo
            MongoCollection<Company> collection = database.getCollection("company", Company.class);

            // Insertion de trois instances de Flower
            Company company = StubMain.miniStubCompany();
            
            
            //collection.insertMany(Arrays.asList(person1, person2));
            collection.insertOne(company);

            
            // Mise à jour d'un document
//            collection.updateOne(
//                    Filters.lte("height", 22),
//                    Updates.addToSet("colors", "pink")
//            );

            // Suppression d'un document
            
            //collection.deleteOne(Filters.eq("name", "Bob"));


            // Return and print all documents in the collection
            List<Company> companies = new ArrayList<>();
            collection.find().into(companies);
            System.out.println(companies);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
