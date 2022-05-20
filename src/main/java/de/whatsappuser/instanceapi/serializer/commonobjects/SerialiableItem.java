package de.whatsappuser.instanceapi.serializer.commonobjects;

import de.whatsappuser.instanceapi.version.MultiversionMaterials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SerialiableItem {

    private MultiversionMaterials material;
    private String name;
    private List<String> lore;
    private int amount;
}
