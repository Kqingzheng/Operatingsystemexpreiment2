package com.example.operatingsystemexpreiment2;

import java.io.Serializable;

public class Weight implements Serializable {
    int pagename;
    int weight;

    public Weight() {
    }

    public Weight(int pagename) {
        this.pagename = pagename;
        weight=1000;
    }

    public Weight(int pagename, int weight) {
        this.pagename = pagename;
        this.weight = weight;
    }
}
