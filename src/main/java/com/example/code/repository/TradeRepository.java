package com.example.code.repository;

import com.example.code.entity.TradeRecord;
import com.example.code.model.BuySellIndicator;
import com.example.code.model.StockDetails;
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

    @Query(value = " select t from TradeRecord t where t.brokerCode = :brokerCode")
    List<TradeRecord> getTrades(@Param("brokerCode") String brokerCode);

    @Query(value = "select t.stockName,sum(t.quantity) as totalCount from TradeRecord t " +
            "WHERE t.buySellIndicator = :buySellIndicator GROUP BY  stockName  ORDER BY totalCount desc ")
    List<Object[]> getTopFiveStocks(@Param("buySellIndicator") BuySellIndicator buySellIndicator);
}