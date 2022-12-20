package com.ofss.trade.repository;

import com.ofss.trade.entity.TradeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TradeStoreRepository extends JpaRepository<TradeStore, Long> {
    Optional<TradeStore> findByTradeId(String tradeId);
    long findLatestVersion(String tradeId);
    List<TradeStore> findMaturedTrade(Date today);
}
