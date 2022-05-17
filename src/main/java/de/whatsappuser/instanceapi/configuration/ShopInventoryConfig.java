package de.whatsappuser.instanceapi.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class ShopInventoryConfig {

    private int id, size;
    private String name;
}
