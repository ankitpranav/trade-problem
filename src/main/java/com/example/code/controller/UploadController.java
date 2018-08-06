package com.example.code.controller;

import java.io.IOException;
import java.util.List;

import com.example.code.TradeRecord;
import com.example.code.model.TradeRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.code.service.UploadService;

/**
 * Created by ankitpranav on 5/8/18.
 */

@RestController
@RequestMapping("/tradedata")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@Autowired
	UploadController(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public List<TradeRecordVO> uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile) throws IOException {
		return uploadService.uploadFile(multiPartFile);
	}

	@RequestMapping(value = "/getTrades", method = RequestMethod.GET)
	public List<TradeRecord> getTrades(@RequestParam("brokerCode") String brokerCode){
		return uploadService.getTrades(brokerCode);
	}

	@RequestMapping(value = "/getTopFiveStocks", method = RequestMethod.GET)
	public List<TradeRecord> getTopStocks(){
		return uploadService.getTopFiveStocks();
	}

}