package com.aquariux.technical.test.refdata.client;

import com.aquariux.technical.test.model.BinanceTicker;
import com.aquariux.technical.test.model.HuobiData;
import com.aquariux.technical.test.model.HuobiTicker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PriceAggregationReferenceClient {
    @Value("${api.url.binance.ticker.book.ticker}")
    private String binanceTickerUrl;
    @Value("${api.url.huobi.ticker}")
    private String houbiTickerUrl;

    public List<BinanceTicker> getBinanceTickerSymbol() {
        WebClient webClient = WebClient.create(binanceTickerUrl);
        Mono<List<BinanceTicker>> tickerMono =
                webClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<BinanceTicker>>() {
                        });
        if (tickerMono.block() != null && !tickerMono.block().isEmpty()) {
            return tickerMono.block();
        } else {
            return null;
        }
    }

    public List<HuobiData> getHuobiTickerSymbol() {
        WebClient webClient = WebClient.create(houbiTickerUrl);
        Mono<HuobiTicker> tickerMono =
                webClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<HuobiTicker>() {
                        });
        if (tickerMono != null && !tickerMono.block().getData().isEmpty()) {
            return tickerMono.block().getData();
        } else {
            return null;
        }
    }
}
