package br.com.cincoquatro.locacaoespaco.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {

	// @formatter:off
    ROLE_ADMIN("Admin"),
    ROLE_USER("Usuários"),
    ROLE_CUSTOMER("Clientes"),
    ROLE_CONTRACT("Contratos"),
    ROLE_PLACE("Espaços"),
    ROLE_ADDITIONAL("Adicionais"),
    ROLE_LEASES("Locações"),
    ROLE_MONITOR("Monitor");
	// @formatter:on

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Role getByRoleName(String rolename) {
        for (Role name : Role.values())
            if (name.name().equals(rolename))
                return name;

        return null;
    }

}
