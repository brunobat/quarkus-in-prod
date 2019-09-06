package org.acme.rest.json;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class Fruit {

    @NotEmpty
    @Schema(
            name = "name",
            description = "The name of the Fruit.",
            example = "orange")
    private String name;

    @Schema(
            name = "description",
            description = "The description of the Fruit.",
            example = "It's quite orange")
    private String description;

    public Fruit() {
    }

    public Fruit(String name,
                 String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Fruit)) {
            return false;
        }

        Fruit other = (Fruit) obj;

        return Objects.equals(other.name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
