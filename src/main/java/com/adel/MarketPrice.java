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
    private Double price;

    @Column(name = "version")
    private Integer version;

    public MarketPrice() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
