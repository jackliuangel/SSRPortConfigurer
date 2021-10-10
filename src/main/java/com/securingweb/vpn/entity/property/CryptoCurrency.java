package com.securingweb.vpn.entity.property;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface CryptoCurrency {

    Map<String, CryptoCurrency> ALL = new HashMap<>(2048);

    public static CryptoCurrency ofCode(String id) {
        return ALL.get(id);
    }

    public static List<CryptoCurrency> values() {
        List<CryptoCurrency> list = new LinkedList();
        list.add(_1.BTC);
        list.add(_1.ETH);
        list.add(_2.CRO);
        list.add(_2.CRO_ORG);
        return list;

    }

    enum _1 implements CryptoCurrency {

        BTC("BTC", "Blockchain coin"),
        ETH("ETH", "Ether coin"),
        ;

        private String id;

        private String name;

        _1(String id, String name) {
            this.id = id;
            this.name = name;
            ALL.put(id, this);
        }

    }

    enum _2 implements CryptoCurrency {

        CRO("CRO", "Crypto token"),
        CRO_ORG("CRO_ORG", "Crypto org token"),
        ;

        private String id;

        private String name;

        _2(String id, String name) {
            this.id = id;
            this.name = name;
            ALL.put(id, this);
        }

    }

}
