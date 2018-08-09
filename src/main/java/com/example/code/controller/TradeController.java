package com.example.code.controller;

import java.io.IOException;
import java.util.List;

import com.example.code.entity.TradeRecord;
import com.example.code.model.StockDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.code.service.TradeService;

/**
 * Created by ankitpranav on 5/8/18.
 */

@RestController
@RequestMapping("/tradedata")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@Autowired
	TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	/*will take csv file as an input, parses it to object and persists in  the in-memory DB*/
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile) throws IOException {
		tradeService.uploadFile(multiPartFile);
	}

	/*will return the trades of a given broker as an input*/
	@RequestMapping(value = "/getTrades", method = RequestMethod.GET)
	public List<TradeRecord> getTrades(@RequestParam("brokerCode") String brokerCode){
		return tradeService.getTrades(brokerCode);
	}

	/*will return the top 5 stocks based on either buy or sell criteria*/
	@RequestMapping(value = "/getTopFiveStocks", method = RequestMethod.GET)
	public List<StockDetails> getTopStocks(@RequestParam("buySellIndicator") String buySellIndicator ){
		return tradeService.getTopFiveStocks(TradeRecord.BuySellIndicator.valueOf(buySellIndicator));
	}

}