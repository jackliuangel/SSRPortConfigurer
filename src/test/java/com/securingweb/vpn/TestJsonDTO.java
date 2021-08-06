package com.securingweb.vpn;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TestJsonDTO {

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("isGraduated")
    Boolean isGraduated;

    @JsonIgnore
    Boolean getGraduated() {
        return isGraduated;
    }

    void setGraduated(Boolean isGraduated) {
        this.isGraduated = isGraduated;
    }
}
