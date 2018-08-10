package com.example.code.service;

import com.example.code.entity.TradeRecord;
import com.example.code.model.BuySellIndicator;
import com.example.code.model.StockDetails;
import com.example.code.repository.TradeDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TradeServiceTest {

    @Autowired
    TradeService tradeService;

    @Test
    public void getTrades() {
        File file = new File("/home/pranavankit/TradeData.dat");
        tradeService.processFile(file);

        TradeRecord tradeRecord = new TradeRecord();
        tradeRecord.setBrokerCode("CB");
        tradeRecord.setBrokerName("CitiBank");
        tradeRecord.setBuySellIndicator(BuySellIndicator.B);
        tradeRecord.setQuantity(48);
        tradeRecord.setStockName("DSP BlackRock");
        tradeRecord.setTradeDate("05/25/2018");
        tradeRecord.setSettlementDate("05/29/2018");
        tradeRecord.setTradeId("435");

        TradeRecord tradeRecord1 = new TradeRecord();
        tradeRecord1.setBrokerCode("CB");
        tradeRecord1.setBrokerName("CitiBank");
        tradeRecord1.setBuySellIndicator(BuySellIndicator.B);
        tradeRecord1.setQuantity(230);
        tradeRecord1.setStockName("Samsung");
        tradeRecord1.setTradeDate("02/15/2018");
        tradeRecord1.setSettlementDate("02/17/2018");
        tradeRecord1.setTradeId("287");

        TradeRecord tradeRecord2 = new TradeRecord();
        tradeRecord2.setBrokerCode("CB");
        tradeRecord2.setBrokerName("CitiBank");
        tradeRecord2.setBuySellIndicator(BuySellIndicator.B);
        tradeRecord2.setQuantity(50);
        tradeRecord2.setStockName("Unilever");
        tradeRecord2.setTradeDate("07/15/2018");
        tradeRecord2.setSettlementDate("07/19/2018");
        tradeRecord2.setTradeId("361");


        List<TradeRecord> expList = tradeService.getTrades("CB");

        Assert.assertEquals(tradeRecord.toString().trim(), expList.get(0).toString());
        Assert.assertEquals(tradeRecord1.toString().trim(), expList.get(1).toString());
        Assert.assertNotEquals(tradeRecord2.toString().trim(), expList.get(2).toString());
    }

    @Test
    public void getTopFiveStocks() {
        File file = new File("/home/pranavankit/TradeData.dat");;
        tradeService.processFile(file);

        StockDetails stockDetails = new StockDetails();
        stockDetails.setBrokerCode("IBM");
        stockDetails.setTotalCount(480);

        StockDetails stockDetails1 = new StockDetails();
        stockDetails1.setBrokerCode("Samsung");
        stockDetails1.setTotalCount(230);

        StockDetails stockDetails2 = new StockDetails();
        stockDetails2.setBrokerCode("Infosys");
        stockDetails2.setTotalCount(200);

        StockDetails stockDetails3 = new StockDetails();
        stockDetails3.setBrokerCode("Facebook");
        stockDetails3.setTotalCount(125);

        StockDetails stockDetails4 = new StockDetails();
        stockDetails4.setBrokerCode("Google");
        stockDetails4.setTotalCount(100);


        List<StockDetails> stockDetailsListAct = tradeService.getTopFiveStocks(BuySellIndicator.B);
        Assert.assertEquals(stockDetails.toString().trim(), stockDetailsListAct.get(0).toString());
        Assert.assertEquals(stockDetails1.toString().trim(), stockDetailsListAct.get(1).toString());
        Assert.assertEquals(stockDetails2.toString().trim(), stockDetailsListAct.get(2).toString());
        Assert.assertEquals(stockDetails3.toString().trim(), stockDetailsListAct.get(3).toString());
        Assert.assertNotEquals(stockDetails4.toString().trim(), stockDetailsListAct.get(4).toString());
    }
}