package com.ecommerce.app.warehouse.dataaccess.stock.adapter;

import com.ecommerce.app.warehouse.dataaccess.stock.mapper.StockDataAccessMapper;
import com.ecommerce.app.warehouse.dataaccess.stock.repository.StockJpaRepository;
import com.ecommerce.app.warehouse.domain.core.entity.Stock;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseStockRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockRepositoryImpl implements WarehouseStockRepository {

    private final StockJpaRepository stockJpaRepository;
    private final StockDataAccessMapper stockDataAccessMapper;

    public StockRepositoryImpl(StockJpaRepository stockJpaRepository, StockDataAccessMapper stockDataAccessMapper) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockDataAccessMapper = stockDataAccessMapper;
    }

    @Override
    public Stock save(Stock stock) {
        return stockDataAccessMapper.stockEntityToStock(stockJpaRepository
                .save(stockDataAccessMapper.stockToStockEntity(stock)));
    }

    @Override
    public Optional<Stock> findById(StockId stockId) {
        return stockJpaRepository.findById(stockId.getValue()).map(stockDataAccessMapper::stockEntityToStock);
    }
}
