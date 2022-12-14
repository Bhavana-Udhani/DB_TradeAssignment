package com.ofss.trade.service;

import com.ofss.trade.entity.TradeStore;
import com.ofss.trade.exception.TradeException;

import java.util.List;
import java.util.Optional;

public interface ITradeStoreService {
    TradeStore saveTradeStore(TradeStore tradeStore) throws TradeException;
    List<TradeStore> getAllTradeStore();
    Optional<TradeStore> getTradeStoreById(String tradeId);
    TradeStore updateTradeStore(TradeStore tradeStore);
    void deleteTradeStore(long id);
    void updateExpired();
}
