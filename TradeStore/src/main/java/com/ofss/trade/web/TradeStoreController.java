package com.ofss.trade.web;

import com.ofss.trade.entity.TradeStore;
import com.ofss.trade.exception.TradeException;
import com.ofss.trade.service.TradeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeStoreController {
    @Autowired
    private TradeStoreService tradeStoreService;

    @PostMapping
    public ResponseEntity<TradeStore> saveTradeStore(@RequestBody TradeStore tradeStore) {
            return new ResponseEntity<>(tradeStoreService.saveTradeStore(tradeStore),HttpStatus.CREATED);
    }

    @PutMapping("{tradeId}")
    @ResponseStatus(HttpStatus.OK)
    public TradeStore updateTradeStore(@PathVariable("tradeId") long tradeId, @RequestBody TradeStore tradeStore) {
        return tradeStoreService.updateTradeStore(tradeStore);
    }

    @GetMapping
    public List<TradeStore> findAllTradeStore() {
        return tradeStoreService.getAllTradeStore();
    }

    @GetMapping("{tradeId}")
    public ResponseEntity<TradeStore> findTradeStoreById(@PathVariable("tradeId") String tradeId) {
        return tradeStoreService.getTradeStoreById(tradeId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{tradeId}")
    public ResponseEntity<String> removeTradeStore(@PathVariable("tradeId") long tradeId) {
        tradeStoreService.deleteTradeStore(tradeId);
        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
    }
}
