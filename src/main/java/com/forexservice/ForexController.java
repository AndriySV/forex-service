package com.forexservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// http://localhost:8000/currency-exchange/from/EUR/to/INR

@RestController
public class ForexController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
       logger.info(String.format("Start retrieveExchangeValue with parameters from=%s to=%s", from, to));
        ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
        exchangeValue.setPort(
                Integer.parseInt(environment.getProperty("local.server.port")));
        logger.info(String.format("End retrieveExchangeValue. The exchangeValue=%s", exchangeValue.getConversionMultiple()));
        return exchangeValue;
    }
}
