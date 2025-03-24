package com.adel;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * @author Adel.Albediwy
 */

@Measurement(name = "market_price")
public class MarketPrice {

    @Column(name = "time")
    private Instant time;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "price")
    private Integer price;


    public MarketPrice() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
