package com.example.code.repository;

import com.example.code.TradeRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ankitpranav on 4/8/18.
 */
@Repository
public interface TradeRepository extends CrudRepository<TradeRecord,Long> {

    @Query(value = " select t from TradeRecord t where t.brokerCode like %?1% ")
    List<TradeRecord> getTrades(@Param("brokerCode") String brokerCode);

    @Query(value = "select t.stockName,sum(t.quantity) from TradeRecord t " +
            "WHERE t.buySellIndicator like '%B%'  GROUP BY  stockName,quantity  ORDER BY quantity desc ")
    List<TradeRecord> getTopFiveStocks();
}