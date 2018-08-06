package com.example.code.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.code.TradeRecord;
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
public class UploadService {

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

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(UploadService.class);

  List<TradeRecordVO> missedList = new ArrayList<TradeRecordVO>();

  public List<TradeRecordVO> uploadFile(MultipartFile multipartFile) throws IOException {

    File file = convertMultiPartToFile(multipartFile);

    ColumnPositionMappingStrategy<TradeRecordVO> strat = new ColumnPositionMappingStrategy<TradeRecordVO>();
    strat.setType(TradeRecordVO.class);
    /**
     * the fields to bind do in your JavaBean
     */
    String[] columns = new String[] { "tradeId", "stockName", "brokerCode", "brokerName",
        "quantity", "tradeDate", "settlementDate", "buySellIndicator" };
    strat.setColumnMapping(columns);

    CsvToBean<TradeRecordVO> csv = new CsvToBean<TradeRecordVO>();

    List<TradeRecordVO> list = csv.parse(strat, new CSVReader(new FileReader(file), '|'));
    list.remove(0);
    Set<TradeRecordVO> tradeRecordVOSet = new HashSet<>();
    tradeRecordVOSet.addAll(list);
    List<TradeRecordVO> tradeRecords = validateData(tradeRecordVOSet);
    List<TradeRecord> tradeRecordList = new ArrayList<>();
    if (!tradeRecords.isEmpty()) {
      for (TradeRecordVO tradeRecordVO : tradeRecords) {
        tradeRecordList.add(dozerBeanMapper.map(tradeRecordVO, TradeRecord.class));
      }
      tradeDAO.storeValidData(tradeRecordList);
    }
    return missedList;
  }

  public List<TradeRecordVO> validateData(Set<TradeRecordVO> tradeRecords) {

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
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  public List<TradeRecord> getTrades(String brokerCode){
    return tradeRepository.getTrades(brokerCode);
  }

  public List<TradeRecord> getTopFiveStocks(){
    List<TradeRecord> tradeRecordList = tradeRepository.getTopFiveStocks();
    return tradeRecordList.stream().limit(5).collect(Collectors.toList());
  }
}