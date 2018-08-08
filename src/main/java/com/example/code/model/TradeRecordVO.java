package com.example.code.model;

/**
 * Created by ankitpranav on 5/8/18.
 */
public class TradeRecordVO {

  private String tradeId;

  private String stockName;

  private String brokerCode;

  private String brokerName;

  private String quantity;

  private String tradeDate;

  private String settlementDate;

  private String buySellIndicator;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TradeRecordVO)) return false;

    TradeRecordVO that = (TradeRecordVO) o;

    return getTradeId() != null ? getTradeId().equals(that.getTradeId()) : that.getTradeId() == null;

  }

  @Override
  public int hashCode() {
    return getTradeId() != null ? getTradeId().hashCode() : 0;
  }

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

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
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

  public String getBuySellIndicator() {
    return buySellIndicator;
  }

  public void setBuySellIndicator(String buySellIndicator) {
    this.buySellIndicator = buySellIndicator;
  }

  @Override
  public String toString() {
    return "TradeRecordVO{" +
            "tradeId='" + tradeId + '\'' +
            ", stockName='" + stockName + '\'' +
            ", brokerCode='" + brokerCode + '\'' +
            ", brokerName='" + brokerName + '\'' +
            ", quantity='" + quantity + '\'' +
            ", tradeDate='" + tradeDate + '\'' +
            ", settlementDate='" + settlementDate + '\'' +
            ", buySellIndicator='" + buySellIndicator + '\'' +
            '}';
  }
}
