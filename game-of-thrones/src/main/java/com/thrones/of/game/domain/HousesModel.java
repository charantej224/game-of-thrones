
package com.thrones.of.game.domain;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "houseName",
        "tagLines",
        "members",
        "weapons"
})
/**
 * Name : HousesModel
 * implements: Cloneable
 * Model required to deep clone to create an instance in the running session.
 * implements: Serializable
 * Model required to be written into file, hence, serialization is required.
 */
public class HousesModel implements Cloneable, Serializable {

    @JsonProperty("houseName")
    private String houseName;
    @JsonProperty("tagLines")
    private String tagLines;
    @JsonProperty("members")
    private List<Member> members = null;
    @JsonProperty("weapons")
    private List<Weapon> weapons = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("houseName")
    public String getHouseName() {
        return houseName;
    }

    @JsonProperty("houseName")
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    @JsonProperty("tagLines")
    public String getTagLines() {
        return tagLines;
    }

    @JsonProperty("tagLines")
    public void setTagLines(String tagLines) {
        this.tagLines = tagLines;
    }

    @JsonProperty("members")
    public List<Member> getMembers() {
        return members;
    }

    @JsonProperty("members")
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @JsonProperty("weapons")
    public List<Weapon> getWeapons() {
        return weapons;
    }

    @JsonProperty("weapons")
    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
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
        return
                "\t\t\t\t\t houseName=" + houseName + "\n" +
                        "\t\t\t\t\t tagLines=" + tagLines + "\n" +
                        "\t\t\t\t\t members=" + members + "\n" +
                        "\t\t\t\t\t weapons=" + weapons;
    }

    @Override
    public HousesModel clone() {
        try {
            HousesModel houseModelCloned = (HousesModel) super.clone();
            houseModelCloned.setTagLines(tagLines);
            houseModelCloned.setMembers(new ArrayList<Member>());
            houseModelCloned.setWeapons(new ArrayList<Weapon>());
            members.forEach(member -> houseModelCloned.getMembers().add(member.clone()));
            weapons.forEach(weapon -> houseModelCloned.getWeapons().add(weapon.clone()));
            return houseModelCloned;
        } catch (CloneNotSupportedException exception) {
            throw new RuntimeException(exception.getMessage());
        }

    }
}
