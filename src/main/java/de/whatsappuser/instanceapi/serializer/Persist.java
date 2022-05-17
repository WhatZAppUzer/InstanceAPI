package de.whatsappuser.instanceapi.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.serializer.typeadapter.EnumTypeAdapter;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class Persist {

    private final Gson gson = buildGson().create();
    private final InstanceCore instance;

    public Persist(InstanceCore instance) {
        this.instance = instance;
    }

    public static String getName(Object o) {
        return getName(o.getClass());
    }

    public static String getName(Type type) {
        return getName(type.getClass());
    }

    private GsonBuilder buildGson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY);
    }

    public File getFile(String name) {
        return new File(this.instance.getDataFolder(), name + ".json");
    }

    public File getFile(Class<?> clazz) {
        return getFile(getName(clazz));
    }

    public File getFile(Object obj) {
        return getFile(getName(obj));
    }

    public File getFile(Type type) {
        return getFile(getName(type));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> clazz) {
        return loadOrSaveDefault(def, clazz, getFile(clazz));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> clazz, String name) {
        return loadOrSaveDefault(def, clazz, getFile(name));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> clazz, File file) {
        if (!file.exists()) {
            this.instance.getLogger().info("Creating default: " + file);
            this.save(def, file);
            return def;
        }

        T loaded = this.load(clazz, file);

        if (loaded == null) {
            this.instance.getLogger().warning("Using default as I failed to load: " + file);

            // backup bad file, so user can attempt to recover their changes from it
            File backup = new File(file.getPath() + "_bad");
            if (backup.exists()) {
                backup.delete();
            }
            this.instance.getLogger().warning("Backing up copy of bad file to: " + backup);
            file.renameTo(backup);

            return def;
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
        return DiscUtil.writeCatch(file, gson.toJson(instance), true);
    }

    public <T> T load(Class<T> clazz) {
        return load(clazz, getFile(clazz));
    }

    public <T> T load(Class<T> clazz, String name) {
        return load(clazz, getFile(name));
    }

    public <T> T load(Class<T> clazz, File file) {
        String content = DiscUtil.readCatch(file);
        if (content == null) {
            return null;
        }

        try {
            return gson.fromJson(content, clazz);
        } catch (Exception ex) {    // output the error message rather than full stack trace; error parsing the file, most likely
            this.instance.getLogger().warning(ex.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T load(Type typeOfT, String name) {
        return (T) load(typeOfT, getFile(name));
    }

    @SuppressWarnings("unchecked")
    public <T> T load(Type typeOfT, File file) {
        String content = DiscUtil.readCatch(file);
        if (content == null) {
            return null;
        }

        try {
            return (T) gson.fromJson(content, typeOfT);
        } catch (Exception ex) {    // output the error message rather than full stack trace; error parsing the file, most likely
            this.instance.getLogger().warning(ex.getMessage());
        }

        return null;
    }
}
