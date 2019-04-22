
package com.thrones.of.game.domain;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "strength"
})
/**
 * Name : Weapon
 * implements: Cloneable
 * Model required to deep clone to create an instance in the running session.
 * implements: Serializable
 * Model required to be written into file, hence, serialization is required.
 */
public class Weapon implements Cloneable, Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("strength")
    private Integer strength;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("strength")
    public Integer getStrength() {
        return strength;
    }

    @JsonProperty("strength")
    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "\n \t\t\t\t\t  Weapon = " + name + " - cost required (in strength units) = " + strength;
    }

    @Override
    public Weapon clone() {
        try {
            return (Weapon) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
