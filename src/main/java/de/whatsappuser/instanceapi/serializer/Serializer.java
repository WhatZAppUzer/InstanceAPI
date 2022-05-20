package de.whatsappuser.instanceapi.serializer;

import de.whatsappuser.instanceapi.InstanceCore;

public class Serializer {

    public void save(Object instance) {
        InstanceCore.getInstance().getPersist().save(instance);
    }

    public <T> T load(T def, Class<T> tClass, String name) {
        return InstanceCore.getInstance().getPersist().loadOrSaveDefault(def, tClass, name);
    }
}
