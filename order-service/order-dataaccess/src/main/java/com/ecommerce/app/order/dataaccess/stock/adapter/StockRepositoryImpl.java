package com.ecommerce.app.order.dataaccess.stock.adapter;

import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.order.application.service.ports.output.StockRepository;
import com.ecommerce.app.order.dataaccess.stock.mapper.StockDataAccessMapper;
import com.ecommerce.app.order.dataaccess.stock.repository.StockJpaRepository;
import com.ecommerce.app.order.domain.core.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;
    private final StockDataAccessMapper stockDataAccessMapper;

    public StockRepositoryImpl(StockJpaRepository stockJpaRepository, StockDataAccessMapper stockDataAccessMapper) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockDataAccessMapper = stockDataAccessMapper;
    }

    @Override
    public Optional<Stock> findById(StockId stockId) {
        return stockJpaRepository.findById(stockId.getValue()).map(stockDataAccessMapper::stockEntityToStockEntity);
    }

    @Override
    public Stock save(Stock stock) {
        return stockDataAccessMapper.stockEntityToStockEntity(
                stockJpaRepository.save(stockDataAccessMapper.stockToStockEntity(stock))
        );
    }
}
