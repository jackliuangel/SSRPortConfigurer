package com.securingweb.vpn;

import com.securingweb.vpn.entity.property.CryptoCurrency;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptoCurrencyTest {

    @Test
    public void testEnum() {
        System.out.println(CryptoCurrency.values());
        assertThat(CryptoCurrency.ofCode("BTC")).isNotNull();
    }
}
