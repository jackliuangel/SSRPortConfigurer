package com.securingweb.vpn;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ComparableTestJsonDTO implements Comparable<ComparableTestJsonDTO> {

    TestJsonDTO testJsonDTO;

    @Override
    public int compareTo(ComparableTestJsonDTO o) {
        return this.testJsonDTO.id - o.testJsonDTO.id;
    }
}
