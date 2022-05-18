package de.whatsappuser.instanceapi.serializer;

import de.whatsappuser.instanceapi.InstanceCore;

public class Serializer {

    public void save(Object instance) {
        InstanceCore.getPlugin(InstanceCore.class).getPersist().save(instance);
    }

    public <T> T load(T def, Class<T> clazz, String name) {
        return InstanceCore.getPlugin(InstanceCore.class).getPersist().loadOrSaveDefault(def, clazz, name);
    }
}
