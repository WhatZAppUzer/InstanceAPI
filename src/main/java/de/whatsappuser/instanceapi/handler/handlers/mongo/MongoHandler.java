package de.whatsappuser.instanceapi.handler.handlers.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.whatsappuser.instanceapi.handler.handlers.IHandler;
import lombok.Getter;

import java.util.Collections;

@Getter
public class MongoHandler implements IHandler {

    private MongoDatabase mongoDatabase;
    private MongoClient client;
    private String host, user, password, authdatabase, database;
    private int port;
    private boolean auth;

    public MongoHandler(String host, String user, String password, String authdatabase, String database, int port, boolean auth) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.authdatabase = authdatabase;
        this.database = database;
        this.port = port;
        this.auth = auth;
    }

    @Override
    public void onLoad() {
        if(this.auth) {
            this.client = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(MongoCredential.createCredential(this.user, this.authdatabase, this.password.toCharArray())));
        } else {
            this.client = new MongoClient(new ServerAddress(this.host, this.port));
        }
        this.mongoDatabase = this.client.getDatabase(this.database);
        this.client.startSession();
    }

    @Override
    public void onUnLoad() {
        this.client.close();
    }
}
