package com.example.code.entity;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;

/**
 * Created by ankitpranav on 4/8/18.
 */
@Entity
public class TradeRecord {

  public enum BuySellIndicator {
    B,S
  }

  @Id
  @CsvBindByName
  @Column(name="tradeId", unique=true)
  private String tradeId;

  @CsvBindByName
  private String stockName;

  @CsvBindByName
  private String brokerCode;

  @CsvBindByName
  private String brokerName;

  @CsvBindByName
  private Integer quantity;

  @CsvBindByName
  private String tradeDate;

  @CsvBindByName
  private String settlementDate;

  @CsvBindByName
  @Enumerated(EnumType.STRING)
  private BuySellIndicator buySellIndicator;

  public String getTradeId() {
    return tradeId;
  }

  public void setTradeId(String tradeId) {
    this.tradeId = tradeId;
  }

  public String getStockName() {
    return stockName;
  }

  public void setStockName(String stockName) {
    this.stockName = stockName;
  }

  public String getBrokerCode() {
    return brokerCode;
  }

  public void setBrokerCode(String brokerCode) {
    this.brokerCode = brokerCode;
  }

  public String getBrokerName() {
    return brokerName;
  }

  public void setBrokerName(String brokerName) {
    this.brokerName = brokerName;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getTradeDate() {
    return tradeDate;
  }

  public void setTradeDate(String tradeDate) {
    this.tradeDate = tradeDate;
  }

  public String getSettlementDate() {
    return settlementDate;
  }

  public void setSettlementDate(String settlementDate) {
    this.settlementDate = settlementDate;
  }

  public BuySellIndicator getBuySellIndicator() {
    return buySellIndicator;
  }

  public void setBuySellIndicator(BuySellIndicator buySellIndicator) {
    this.buySellIndicator = buySellIndicator;
  }

  @Override
  public String toString() {
    return "TradeRecord{" +
            ", tradeId='" + tradeId + '\'' +
            ", stockName='" + stockName + '\'' +
            ", brokerCode='" + brokerCode + '\'' +
            ", brokerName='" + brokerName + '\'' +
            ", quantity='" + quantity + '\'' +
            ", tradeDate='" + tradeDate + '\'' +
            ", settlementDate='" + settlementDate + '\'' +
            ", buySellIndicator=" + buySellIndicator +
            '}';
  }
}
