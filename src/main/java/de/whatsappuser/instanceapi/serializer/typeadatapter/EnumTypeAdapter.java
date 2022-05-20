package de.whatsappuser.instanceapi.serializer.typeadatapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {

    public static final TypeAdapterFactory ENUM_FACTORY = newEnumTypeHierarchyFactory();
    private final Map<String, T> nameToConstant = new HashMap<>();
    private final Map<T, String> constantToName = new HashMap<>();

    public EnumTypeAdapter(Class<T> classOfT) {
        try {
            for (T constant : classOfT.getEnumConstants()) {
                String name = constant.name();
                SerializedName annotation = classOfT.getField(name).getAnnotation(SerializedName.class);
                if(annotation != null) name = annotation.value();

                this.nameToConstant.put(name, constant);
                this.constantToName.put(constant, name);
            }
        } catch (NoSuchFieldException e) {}
    }

    @Override
    public void write(JsonWriter writer, T value) throws IOException {
        writer.value(value == null ? null : this.constantToName.get(value));
    }

    @Override
    public T read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return this.nameToConstant.get(reader.nextString());
    }

    public static <TT> TypeAdapterFactory newEnumTypeHierarchyFactory() {
        return new TypeAdapterFactory() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                if(!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class)
                    return null;
                if(!rawType.isEnum())
                    rawType = rawType.getSuperclass();
                return (TypeAdapter<T>) new EnumTypeAdapter(rawType);
            }
        };
    }
}
