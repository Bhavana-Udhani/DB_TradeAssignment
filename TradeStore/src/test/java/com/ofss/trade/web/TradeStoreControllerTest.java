package com.ofss.trade.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofss.trade.entity.TradeStore;
import com.ofss.trade.service.TradeStoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TradeStoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeStoreService tradeStoreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetByTradeStoreId() throws Exception {
        // given - precondition or setup
        String tradeId = "T1";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        TradeStore trade = new TradeStore("T1", 1, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false);
        given(tradeStoreService.getTradeStoreById(tradeId)).willReturn(Optional.of(trade));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/trade/{id}", tradeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void saveNewTrade() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        TradeStore trade = new TradeStore("T1", 1, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false);
        String jsonRequest = objectMapper.writeValueAsString(trade);
        ResultActions result = mockMvc.perform(post("/trade").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        result.andDo(print()).
                andExpect(status().isCreated());

    }

    @Test
    public void saveTradewithOldVersion() throws Exception {
        String tradeId = "T1";
        List<TradeStore> tradeList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tradeList.add(new TradeStore("T1", 2, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false));
        given(tradeStoreService.getTradeStoreById(tradeId)).willReturn(Optional.empty());
        given(tradeStoreService.saveTradeStore(any(TradeStore.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        TradeStore trade2 = new TradeStore("T1", 1, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false);
        String jsonRequest2 = objectMapper.writeValueAsString(trade2);
        ResultActions result2 = mockMvc.perform(post("/trade").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trade2)));
        result2.andDo(print()).
                andExpect(status().isCreated());

    }

    @Test
    public void saveTradewithSameVersion() throws Exception {
        String tradeId = "T1";
        List<TradeStore> tradeList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tradeList.add(new TradeStore("T1", 3, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false));
        given(tradeStoreService.getTradeStoreById(tradeId)).willReturn(Optional.empty());
        given(tradeStoreService.saveTradeStore(any(TradeStore.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        TradeStore trade2 = new TradeStore("T1", 3, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false);
        String jsonRequest2 = objectMapper.writeValueAsString(trade2);
        ResultActions result2 = mockMvc.perform(post("/trade").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trade2)));
        result2.andDo(print()).
                andExpect(status().isCreated());

    }

    @Test
    public void saveTradewithOlderMaturityDate() throws Exception {
        String tradeId = "T1";
        List<TradeStore> tradeList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tradeList.add(new TradeStore("T1", 3, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false));
        given(tradeStoreService.getTradeStoreById(tradeId)).willReturn(Optional.empty());
        given(tradeStoreService.saveTradeStore(any(TradeStore.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        TradeStore trade2 = new TradeStore("T1", 3, "CP-1", "B1", dateFormat.parse("20/05/2022"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false);
        String jsonRequest2 = objectMapper.writeValueAsString(trade2);
        ResultActions result2 = mockMvc.perform(post("/trade").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trade2)));
        result2.andDo(print()).
                andExpect(status().isCreated());

    }

    @Test
    public void updateExpired() throws Exception {
        String tradeId = "T1";
        List<TradeStore> tradeList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tradeList.add(new TradeStore("T1", 1, "CP-1", "B1", dateFormat.parse("20/05/2023"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false));
        tradeList.add(new TradeStore("T1", 2, "CP-1", "B1", dateFormat.parse("20/05/2022"), dateFormat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()), false));
        given(tradeStoreService.getAllTradeStore()).willReturn(tradeList);
        tradeStoreService.updateExpired();
        ResultActions response = mockMvc.perform(get("/trade"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }

}
