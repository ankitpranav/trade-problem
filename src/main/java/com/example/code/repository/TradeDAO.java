package com.example.code.repository;

import com.example.code.entity.TradeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ankitpranav on 4/8/18.
 */
@Repository
public class TradeDAO {

    @Autowired
    private TradeRepository tradeRepository;

    public void storeValidData(List<TradeRecord> tradeRecords){
        for (TradeRecord tr : tradeRecords){
            tradeRepository.save(tr);
        }
    }
}
