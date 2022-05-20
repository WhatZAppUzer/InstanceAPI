package de.whatsappuser.instanceapi.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.serializer.typeadatapter.EnumTypeAdapter;
import de.whatsappuser.instanceapi.serializer.typeadatapter.InventoryTypeAdapter;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class Persist {

    private final Gson gson = buildGson().create();

    public static String getName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    public static String getName(Object o) {
        return getName(o.getClass());
    }

    public static String getName(Type type) {
        return getName(type.getClass());
    }

    private GsonBuilder buildGson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(Inventory.class, new InventoryTypeAdapter())
                //TODO register LocationTypeAdapter
                .registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY);

    }

    public File getFile(String name) {
        return new File(InstanceCore.getInstance().getDataFolder(), name + ".json");
    }

    public File getFile(Class<?> clazz) {
        return getFile(getName(clazz));
    }

    public File getFile(Object object) {
        return getFile(getName(object));
    }

    public File getFile(Type type) {
        return getFile(getName(type));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> tClass) {
        return loadOrSaveDefault(def, tClass, getFile(tClass));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> tClass, String name) {
        return loadOrSaveDefault(def, tClass, getFile(name));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> tClass, File file) {
        if(!file.exists()) {
            InstanceCore.getInstance().getLogger().info("Creating default: " + file);
            this.save(def, file);
            return def;
        }

        T loaded = this.load(tClass, file);

        if(loaded == null) {
            InstanceCore.getInstance().getLogger().warning("Using default as I failed to load: " + file);

            File backUp = new File(file.getPath() + "_bad");
            if(backUp.exists())
                backUp.delete();

            InstanceCore.getInstance().getLogger().warning("Backing up copy of bad file to: " + backUp);
            file.renameTo(backUp);
        }
        return loaded;
    }

    public boolean save(Object instance) {
        return save(instance, getFile(instance));
    }

    public boolean save(Object instance, String name) {
        return save(instance, getFile(name));
    }

    public boolean save(Object instance, File file) {
        return DiscUtil.writeCatch(file, this.gson.toJson(instance), true);
    }

    public <T> T load(Class<T> tClass) {
        return load(tClass, getFile(tClass));
    }

    public <T> T load(Class<T> tClass, String name) {
        return load(tClass, getFile(name));
    }

    public <T> T load(Class<T> tClass, File file) {
        String content = DiscUtil.readCatch(file);

        if(content == null)
            return null;

        try {
            return (T) this.gson.fromJson(content, tClass);
        } catch (Exception e) {
            InstanceCore.getInstance().getLogger().warning(e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T load(Type typeOft, String name) {
        return (T) load(typeOft, getFile(name));
    }

    @SuppressWarnings("unchecked")
    public <T> T load(Type typeOft, File file) {
        String content = DiscUtil.readCatch(file);

        if(content == null)
            return null;

        try {
            return (T) this.gson.fromJson(content, typeOft);
        } catch (Exception e) {
            InstanceCore.getInstance().getLogger().warning(e.getMessage());
        }
        return null;
    }
}
