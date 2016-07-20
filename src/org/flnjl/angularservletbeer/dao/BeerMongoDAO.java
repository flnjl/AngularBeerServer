package org.flnjl.angularservletbeer.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.flnjl.angularservletbeer.model.Beer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class BeerMongoDAO {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
     
    private BeerMongoDAO() {
    	this.client = new MongoClient("localhost");
    	this.database = this.client.getDatabase("angularBeers");
    	this.collection = this.database.getCollection("beers");
    }
    
    private static BeerMongoDAO beerMongoDAO = null;
    
    public void insertBeer(Beer beer) {
    	if (null == getBeer(beer.getId())) {
	    	Document doc = generateBeerDocument(beer);
	    	collection.insertOne(doc);
    	}
    }
    
    public void updateBeer(Beer beer) {
    	if (null != beer.getOldId()) {
    		Beer oldBeer = Beer.getBeer(beer.getOldId());
    		deleteBeer(oldBeer);
    	}
    	else {
    		deleteBeer(beer);
    	}
    	insertBeer(beer);
    }
    
    public void deleteBeer(Beer beer) {
    	Document doc = generateBeerDocument(beer);
    	collection.deleteOne(doc);
    }
    
    private Document generateBeerDocument(Beer beer) {
    	Document doc = new Document("name", beer.getName())
    						.append("id", beer.getId())
    						.append("alcohol", beer.getAlcohol())
    						.append("description", beer.getDescription())
    						.append("img", beer.getImg())
    						.append("label", beer.getLabel())
    						.append("availability", beer.getAvailability())
    						.append("serving", beer.getServing())
    						.append("style", beer.getStyle())
    						.append("brewery", beer.getBrewery())
    			;
    	
    	return doc;
    }
    
    public static BeerMongoDAO getBeerMongoDAO() {
    	if (null == beerMongoDAO) {
    		beerMongoDAO = new BeerMongoDAO();
    	}
    	return beerMongoDAO;
    }
    
    public Beer getBeer(String id) {
    	MongoCursor<Document> cursor = this.collection.find(new Document("id", id)).iterator();
    	while (cursor.hasNext()) {
    		Document doc = cursor.next();
    		
    		Beer beer = new Beer();
    		//beer.setId(doc.getString("id"));
    		beer.setName(doc.getString("name"));
    		beer.setAlcohol(doc.getDouble("alcohol"));
    		beer.setDescription(doc.getString("description"));
    		beer.setImg(doc.getString("img"));
    		beer.setLabel(doc.getString("label"));
    		beer.setAvailability(doc.getString("availability"));
    		beer.setBrewery(doc.getString("brewery"));
    		beer.setServing(doc.getString("serving"));
    		beer.setStyle(doc.getString("style"));
    		
    		beer.setOldId(doc.getString("id"));
    		
    		return beer;
    	}
    		
    	return null;
    }
    
    public List<Beer> getBeerList() {
    	List<Beer> beers = new ArrayList<Beer>();
    	MongoCursor<Document> cursor = this.collection.find().iterator();
    	while (cursor.hasNext()) {
    		Document doc = cursor.next();
    		
    		Beer beer = new Beer();
    		//beer.setId(doc.getString("id"));
    		beer.setName(doc.getString("name"));
    		beer.setAlcohol(doc.getDouble("alcohol"));
    		beer.setDescription(doc.getString("description"));
    		beer.setImg(doc.getString("img"));
    		
    		beer.setOldId(doc.getString("id"));
    		
    		beers.add(beer);
    	}
    		
    	return beers;
    }
}