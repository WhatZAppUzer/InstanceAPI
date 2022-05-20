package de.whatsappuser.instanceapi.handler.handlers.mongo.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import de.whatsappuser.instanceapi.InstanceCore;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

public abstract class MongoRepository<T> {

    private final InstanceCore core;

    @Getter @Setter
    private MongoCollection<Document> collection;

    public MongoRepository(InstanceCore core) {
        this.core = core;
    }

    public CompletableFuture<T> getData(String id, Type type) {
        return CompletableFuture.supplyAsync(() -> {
            Document document = this.collection.find(Filters.eq("_id", id)).first();
            if(document == null) return null;

            return this.core.getPersist().getGson().fromJson(document.toJson(), type);
        });
    }

    public void saveData(String id, Type type) {
        CompletableFuture.supplyAsync(() -> this.collection.replaceOne(Filters.eq("_id", id),Document.parse(this.core.getPersist().getGson().toJson(type)),
                new UpdateOptions().upsert(true)));
    }
}
