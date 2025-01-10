package com.ecommerce.app.order.application.service;

import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.StockId;
import com.ecommerce.app.order.application.service.dto.message.StockCreated;
import com.ecommerce.app.order.application.service.dto.message.StockShippedUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockTransferredUpdate;
import com.ecommerce.app.order.application.service.dto.message.StockUpdated;
import com.ecommerce.app.order.application.service.mapper.OrderDataMapper;
import com.ecommerce.app.order.application.service.mapper.StockDataMapper;
import com.ecommerce.app.order.application.service.ports.output.ProductRepository;
import com.ecommerce.app.order.application.service.ports.output.StockRepository;
import com.ecommerce.app.order.domain.core.entity.Product;
import com.ecommerce.app.order.domain.core.entity.Stock;
import com.ecommerce.app.order.domain.core.exception.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class StockHelper {

    private final StockDataMapper stockDataMapper;
    private final StockRepository stockRepository;
    private final ProductHelper productHelper;

    public StockHelper(StockDataMapper stockDataMapper, StockRepository stockRepository, ProductHelper productHelper) {
        this.stockDataMapper = stockDataMapper;
        this.stockRepository = stockRepository;
        this.productHelper = productHelper;
    }

    /**
     * update stock in order service when stock is transferred in warehouse service it will be called by message listener
     * @param stockTransferredUpdate stock transferred update
     */
    @Transactional
    public void updateStockFromTransfer(StockTransferredUpdate stockTransferredUpdate) {
        Stock stockWarehouseFrom = stockDataMapper.stockTransferredUpdateToStockWarehouseFrom(stockTransferredUpdate);
        Stock stockWarehouseTo = stockDataMapper.stockTransferredUpdateToStockWarehouseTo(stockTransferredUpdate);

        Stock stockFrom = findStock(stockWarehouseFrom.getId());
        Stock stockTo = findStock(stockWarehouseTo.getId());

        stockFrom.setQuantity(stockWarehouseFrom.getQuantity());
        stockTo.setQuantity(stockWarehouseTo.getQuantity());

        saveStock(stockFrom);
        saveStock(stockTo);
    }

    /**
     * update stock in order service when stock is shipped in warehouse service it will be called by message listener
     * @param stockShippedUpdate stock shipped update
     */
    @Transactional
    public void updateStockShipped(StockShippedUpdate stockShippedUpdate) {
        List<Stock> stocks = stockDataMapper.stockShippedUpdateToStock(stockShippedUpdate);
        stocks.forEach(stock -> {
            Stock stockToUpdate = findStock(stock.getId());
            stockToUpdate.setQuantity(stock.getQuantity());
            saveStock(stockToUpdate);
        });
    }

    /**
     * update stock in order service when stock is updated in warehouse service it will be called by message listener
     * @param stockUpdated stock updated
     */
    @Transactional
    public void updateStock(StockUpdated stockUpdated) {
        Stock stock = stockDataMapper.stockUpdatedToStock(stockUpdated);
        Stock stockToUpdate = findStock(stock.getId());
        stockToUpdate.setQuantity(stock.getQuantity());
        saveStock(stockToUpdate);
    }

    /**
     * create stock in order service when stock is created in warehouse service it will be called by message listener
     * @param stockCreated stock created
     */
    @Transactional
    public void createStock(StockCreated stockCreated) {
        Stock stock = stockDataMapper.stockCreatedToStock(stockCreated);
        Product product = productHelper.findProduct(new ProductId(UUID.fromString(stockCreated.getProductId())));
        stock.createStock(product);
        saveStock(stock);
    }

    /**
     * find stock by id
     * @param stockId stock id
     * @return stock
     */
    public Stock findStock(StockId stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isEmpty()) {
            log.error("Stock not found with id: {}", stockId);
            throw new RuntimeException("Stock not found with id: " + stockId);
        }
        return stock.get();
    }

    /**
     * save stock
     * @param stock stock
     * @return stock
     */
    public Stock saveStock(Stock stock) {
        Stock stockResult = stockRepository.save(stock);
        if (stockResult == null) {
            log.error("Error occurred while saving stock with id: {}", stock.getId());
            throw new OrderException("Error occurred while saving stock with id: " + stock.getId());
        }
        return stockResult;
    }
}
