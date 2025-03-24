package com.adel;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * InfluxDB, time-based database
 * https://www.baeldung.com/java-influxdb
 */
public class App {

    private static final String DB = "test_java_db";

    public static void main(String[] args) {
        try(final InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086")) {

            //ping server
            final Pong pong = influxDB.ping();
            if (!pong.isGood()) {
                System.out.println("Error ping influxDB server: " + pong.toString());
            }

            //create db with retention policy
            influxDB.createDatabase(DB);
            influxDB.createRetentionPolicy("defaultPolicy", DB, "1d",1, true);

            //log set
            influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);

            //INSERT
            final Point point1 = Point.measurement("market_price")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("symbol", "NVDA")
                    .addField("price", 12.2)
                    .addField("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .addField("version", 1)
                    .build();

            final Point point2 = Point.measurement("market_price")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("symbol", "AMD")
                    .addField("price", 5.9)
                    .addField("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .addField("version", 1)
                    .build();

            final MarketPrice marketPrice = new MarketPrice();
            marketPrice.setVersion(2);
            marketPrice.setPrice(7.1);
            marketPrice.setSymbol("AMD");
            final Point point3 = Point.measurementByPOJO(MarketPrice.class)
                    .addFieldsFromPOJO(marketPrice)
                    .build();

            //BATCH POINTS, wrtie efficency
            final BatchPoints batchPoints = BatchPoints
                    .database(DB)
                    .retentionPolicy("defaultPolicy")
                    .build();

            batchPoints.point(point1);
            batchPoints.point(point2);
            batchPoints.point(point3);

            influxDB.write(batchPoints);

            //enable batch 100 insert OR every 200ms
//            influxDB.enableBatch(100,200, TimeUnit.MILLISECONDS);
//            influxDB.setRetentionPolicy("defaultPolicy");
//            influxDB.setDatabase("test_java_db");

            //===================================================

            final QueryResult queryResult = influxDB.query(new Query("select * from market_price", DB));

            final InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
            final List<MarketPrice> marketPrices = resultMapper.toPOJO(queryResult, MarketPrice.class);

            System.out.println(marketPrices);
        }
    }
}
