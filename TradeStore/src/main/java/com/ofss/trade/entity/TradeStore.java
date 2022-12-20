package com.ofss.trade.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trade_store")
@NamedQueries({
        @NamedQuery(name = "TradeStore.findLatestVersion",
                query = "select max(v.version) from TradeStore v where v.tradeId = :tradeId"),
        @NamedQuery(name = "TradeStore.findMaturedTrade",
                query = "select v from TradeStore v where v.maturityDate < :today")
})
public class TradeStore {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "version")
    private long version;

    @Column(name = "counter_party_id")
    private String counterPartyId;

    @Column(name = "book_id")
    private String bookId;

    @Temporal(TemporalType.DATE)
    @Column(name = "maturity_date")
    private Date maturityDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expired")
    private boolean expired;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public TradeStore() {
    }

    public TradeStore(String tradeId, long version, String counterPartyId, String bookId, Date maturityDate, Date createdDate, boolean expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "TradeStore{" +
                "id=" + id +
                ", tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterPartyId='" + counterPartyId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", maturityDate=" + maturityDate +
                ", createdDate=" + createdDate +
                ", expired=" + expired +
                '}';
    }
}
