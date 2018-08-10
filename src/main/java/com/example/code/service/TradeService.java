package com.example.code.service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.example.code.entity.TradeRecord;
import com.example.code.model.BuySellIndicator;
import com.example.code.model.StockDetails;
import com.example.code.repository.TradeDAO;
import com.example.code.model.TradeRecordVO;
import com.example.code.repository.TradeRepository;
import com.example.code.validator.Validator;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.dozer.DozerBeanMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;

@Service
public class TradeService {

  @Autowired
  private TradeDAO tradeDAO;

  @Autowired
  private TradeRepository tradeRepository;

  @Bean(name = "org.dozer.Mapper")
  public DozerBeanMapper dozerBean() {
    List<String> mappingFiles = Arrays.asList(
        "dozer-configration-mapping.xml");

    DozerBeanMapper dozerBean = new DozerBeanMapper();
    dozerBean.setMappingFiles(mappingFiles);
    return dozerBean;
  }

  @Autowired
  DozerBeanMapper dozerBeanMapper;

  @Autowired
  private Validator validator;

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(TradeService.class);

  List<TradeRecordVO> missedList = new ArrayList<TradeRecordVO>();

  public void uploadFile(MultipartFile multipartFile) {
    File file = null;
    try {
      file = convertMultiPartToFile(multipartFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    processFile(file);
  }

  public void processFile(File file) {
    ColumnPositionMappingStrategy<TradeRecordVO> strat = new ColumnPositionMappingStrategy<TradeRecordVO>();
    strat.setType(TradeRecordVO.class);
    /**
     * the fields to bind do in your JavaBean
     */
    String[] columns = new String[] { "tradeId", "stockName", "brokerCode", "brokerName",
            "quantity", "tradeDate", "settlementDate", "buySellIndicator" };
    strat.setColumnMapping(columns);

    CsvToBean<TradeRecordVO> csv = new CsvToBean<TradeRecordVO>();

    List<TradeRecordVO> list = null;
    try {
      list = csv.parse(strat, new CSVReader(new FileReader(file), '|'));
    } catch (FileNotFoundException e) {
      System.out.println("Exception was thrown! File Not Found or Permission to file not given");
      e.printStackTrace();
    }
    // removes the coloumn name from the list
    list.remove(0);
    /*removing whitespaces and printing the duplicates on the console*/
    List<TradeRecordVO> validList = new ArrayList<>();
    for (TradeRecordVO tradeRecordVO : list){
      tradeRecordVO.setBrokerCode(tradeRecordVO.getBrokerCode().trim());
      tradeRecordVO.setStockName(tradeRecordVO.getStockName().trim());
      tradeRecordVO.setQuantity(tradeRecordVO.getQuantity().trim());
      tradeRecordVO.setTradeDate(tradeRecordVO.getTradeDate().trim());
      tradeRecordVO.setSettlementDate(tradeRecordVO.getSettlementDate().trim());
      tradeRecordVO.setBrokerName(tradeRecordVO.getBrokerName().trim());
      if (!validList.contains(tradeRecordVO)){
        validList.add(tradeRecordVO);
      } else {
        System.out.println("Duplicate Records ----->"+ tradeRecordVO);
      }
    }
    List<TradeRecordVO> tradeRecords = validateData(validList);
    List<TradeRecord> tradeRecordList = new ArrayList<>();
    if (!tradeRecords.isEmpty()) {
      for (TradeRecordVO tradeRecordVO : tradeRecords) {
        tradeRecordList.add(dozerBeanMapper.map(tradeRecordVO, TradeRecord.class));
      }
      tradeDAO.storeValidData(tradeRecordList);
    }
    for(TradeRecordVO tradeRecordVO : missedList){
      System.out.println("Valiadtion Failed Records ----->"+ tradeRecordVO);
    }
  }

  /*validates the data from the csv file*/
  private List<TradeRecordVO> validateData(List<TradeRecordVO> tradeRecords) {

    List<TradeRecordVO> clonTradeRecordVOs = new ArrayList<>(tradeRecords);

    Iterator<TradeRecordVO> _tradeRecord = tradeRecords.iterator();

    while (_tradeRecord.hasNext()) {

      TradeRecordVO tradeRecord = _tradeRecord.next();
      if (!validator.validateAlphaNumneric(tradeRecord.getTradeId()) ||
          !validator.validateAlphaNumneric(tradeRecord.getStockName()) ||
          !validator.validateAlphaNumneric(tradeRecord.getBrokerCode()) ||
          !validator.validateAlphaNumneric(tradeRecord.getBrokerName()) ||
          !validator.validateInteger(tradeRecord.getQuantity()) ||
          !validator.validateEnum(tradeRecord.getBuySellIndicator()) ||
          !validator.isValidDateFormat(tradeRecord.getTradeDate()) ||
          tradeRecord.getSettlementDate().compareTo(tradeRecord.getTradeDate()) < 0) {
          missedList.add(tradeRecord);
          clonTradeRecordVOs.remove(tradeRecord);
      }
    }
    return clonTradeRecordVOs;
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(convFile);
      fos.write(file.getBytes());
      fos.close();
    } catch (FileNotFoundException e) {
      System.out.println("Exception was thrown! File Not Found");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Exception was thrown! Unable to open/write to file");
      e.printStackTrace();
    }

    return convFile;
  }

  public List<TradeRecord> getTrades(String brokerCode){
    if (brokerCode != null) {
      return tradeRepository.getTrades(brokerCode);
    }
    System.out.print("Broker Code cannot be null");
    return null;
  }

  public List<StockDetails> getTopFiveStocks(BuySellIndicator buySellIndicator){
    if (buySellIndicator != null) {
      List<Object[]> _stockCounts = tradeRepository.getTopFiveStocks(buySellIndicator);
      List<StockDetails> stockCounts = new ArrayList<>();
      for (Object[] object : _stockCounts){
        if(object != null){
          StockDetails stockDetails = new StockDetails();
          stockDetails.setBrokerCode(object[0].toString());
          stockDetails.setTotalCount((((Number) object[1]).intValue()));
          stockCounts.add(stockDetails);
        }
      }
      return stockCounts.stream().limit(5).collect(Collectors.toList());
    } else{
      System.out.print("BuySell Indicator cannot be null");
      return null;
    }
  }
}