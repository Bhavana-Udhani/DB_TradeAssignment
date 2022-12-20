package com.ofss.trade.service;

import com.ofss.trade.repository.TradeStoreRepository;
import com.ofss.trade.entity.TradeStore;
import com.ofss.trade.exception.TradeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TradeStoreService implements ITradeStoreService {
    private final TradeStoreRepository tradeStoreRepository;

    @Autowired
    public TradeStoreService(TradeStoreRepository tradeStoreRepository) {
        this.tradeStoreRepository = tradeStoreRepository;
    }

    @Override
    @Transactional
    public TradeStore saveTradeStore(TradeStore tradeStore) throws TradeException {
        LocalDate localDate = LocalDate.now();
        if (tradeStore.getMaturityDate().compareTo(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) > 0) {
            throw new TradeException("Maturity Date is less than current date");
        }
        Optional<TradeStore> savedTradeStore = tradeStoreRepository.findByTradeId(tradeStore.getTradeId());
        if (savedTradeStore.isPresent()) {
            long version = tradeStoreRepository.findLatestVersion(tradeStore.getTradeId());
            if (version > tradeStore.getVersion()) {
                throw new TradeException("Lower Version Trade not allowed");
            } else if (version == tradeStore.getVersion()) {
                tradeStoreRepository.deleteById(tradeStore.getId());
                return tradeStoreRepository.save(tradeStore);
            }
        }
        return tradeStoreRepository.save(tradeStore);
    }

    @Override
    @Transactional
    public List<TradeStore> getAllTradeStore() {
        return tradeStoreRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<TradeStore> getTradeStoreById(String tradeId) {
        return tradeStoreRepository.findByTradeId(tradeId);
    }

    @Override
    @Transactional
    public TradeStore updateTradeStore(TradeStore tradeStore) {
        return tradeStoreRepository.save(tradeStore);
    }

    @Override
    @Transactional
    public void deleteTradeStore(long id) {
        tradeStoreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateExpired() {
        LocalDate localDate = LocalDate.now();
        List<TradeStore> maturedTrade = tradeStoreRepository.findMaturedTrade(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (null != maturedTrade) {
            for (TradeStore updateTrade : maturedTrade) {
                updateTrade.setExpired(true);
                tradeStoreRepository.save(updateTrade);
            }
        }
    }
}
