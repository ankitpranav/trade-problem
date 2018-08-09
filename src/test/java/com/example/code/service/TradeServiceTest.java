package com.example.code.service;

import com.example.code.entity.TradeRecord;
import com.example.code.model.StockDetails;
import com.example.code.repository.TradeDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class TradeServiceTest {

    @Autowired
    TradeService tradeService;

    @MockBean
    TradeDAO tradeDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTrades() throws Exception{
        TradeRecord tradeRecord = new TradeRecord();
        tradeRecord.setBrokerCode("CB");
        tradeRecord.setBrokerName("CitiBank");
        tradeRecord.setBuySellIndicator(TradeRecord.BuySellIndicator.B);
        tradeRecord.setQuantity(48);
        tradeRecord.setStockName("DSP BlackRock");
        tradeRecord.setTradeDate("05/25/2018");
        tradeRecord.setSettlementDate("05/29/2018");
        tradeRecord.setTradeId("435");

        TradeRecord tradeRecord1 = new TradeRecord();
        tradeRecord1.setBrokerCode("CB");
        tradeRecord1.setBrokerName("CitiBank");
        tradeRecord1.setBuySellIndicator(TradeRecord.BuySellIndicator.B);
        tradeRecord1.setQuantity(230);
        tradeRecord1.setStockName("Samsung");
        tradeRecord1.setTradeDate("07/15/2018");
        tradeRecord1.setSettlementDate("02/17/2018");
        tradeRecord1.setTradeId("287");

        TradeRecord tradeRecord2 = new TradeRecord();
        tradeRecord2.setBrokerCode("CB");
        tradeRecord2.setBrokerName("CitiBank");
        tradeRecord2.setBuySellIndicator(TradeRecord.BuySellIndicator.B);
        tradeRecord2.setQuantity(50);
        tradeRecord2.setStockName("Unilever");
        tradeRecord2.setTradeDate("07/15/2018");
        tradeRecord2.setSettlementDate("07/19/2018");
        tradeRecord2.setTradeId("367");

        List<TradeRecord> tradeRecordsTest = new ArrayList<>();
        tradeRecordsTest.add(tradeRecord);
        tradeRecordsTest.add(tradeRecord1);
        tradeRecordsTest.add(tradeRecord2);

        List<TradeRecord> expList = tradeService.getTrades("CB");

        Assert.assertArrayEquals(tradeRecordsTest.toArray(), expList.toArray());

    }

    @Test
    public void getTopFiveStocks() {

        StockDetails stockDetails = new StockDetails();
        stockDetails.setBrokerCode("IBM");
        stockDetails.setTotalCount(480);

        StockDetails stockDetails1 = new StockDetails();
        stockDetails1.setBrokerCode("Samsung");
        stockDetails1.setTotalCount(230);

        StockDetails stockDetails2 = new StockDetails();
        stockDetails2.setBrokerCode("Infosys ");
        stockDetails2.setTotalCount(200);

        StockDetails stockDetails3 = new StockDetails();
        stockDetails3.setBrokerCode("Facebook");
        stockDetails3.setTotalCount(125);

        StockDetails stockDetails4 = new StockDetails();
        stockDetails4.setBrokerCode("Google");
        stockDetails4.setTotalCount(90);

        List<StockDetails> stockDetailsList = new ArrayList<>();

        stockDetailsList.add(stockDetails);
        stockDetailsList.add(stockDetails1);
        stockDetailsList.add(stockDetails2);
        stockDetailsList.add(stockDetails3);
        stockDetailsList.add(stockDetails4);

        given(tradeService.getTopFiveStocks(TradeRecord.BuySellIndicator.B)).willReturn(stockDetailsList);
    }
}