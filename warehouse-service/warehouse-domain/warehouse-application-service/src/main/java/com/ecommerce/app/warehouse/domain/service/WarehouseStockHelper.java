package com.ecommerce.app.warehouse.domain.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.WarehouseDomainService;
import com.ecommerce.app.warehouse.domain.core.entity.*;
import com.ecommerce.app.warehouse.domain.core.event.StockCreatedEvent;
import com.ecommerce.app.warehouse.domain.core.event.StockUpdatedEvent;
import com.ecommerce.app.warehouse.domain.core.exception.ProductNotFoundException;
import com.ecommerce.app.warehouse.domain.core.exception.UserForbidden;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseNotFoundException;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockId;
import com.ecommerce.app.warehouse.domain.service.dto.create.ProductIdQuery;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockIdQuery;
import com.ecommerce.app.warehouse.domain.service.dto.create.StockRequestCommand;
import com.ecommerce.app.warehouse.domain.service.dto.create.WarehouseIdQuery;
import com.ecommerce.app.warehouse.domain.service.mapper.WarehouseDataMapper;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockCreatedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockUpdatedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.WarehouseStockTransferedMessagePublisher;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.StockJournalRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.StockTransferRepository;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.WarehouseStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class WarehouseStockHelper {

    private final WarehouseDomainService warehouseDomainService;
    private final WarehouseStockRepository warehouseStockRepository;
    private final StockTransferRepository stockTransferRepository;
    private final StockJournalRepository stockJournalRepository;
    private final WarehouseDataMapper warehouseDataMapper;
    private final UserHelper userHelper;
    private final WarehouseHelper warehouseHelper;
    private final ProductHelper productHelper;
    private final StockUpdatedMessagePublisher stockUpdatedMessagePublisher;
    private final StockCreatedMessagePublisher stockCreatedMessagePublisher;

    public WarehouseStockHelper(WarehouseDomainService warehouseDomainService,
                                WarehouseStockRepository warehouseStockRepository,
                                StockTransferRepository stockTransferRepository,
                                StockJournalRepository stockJournalRepository,
                                WarehouseDataMapper warehouseDataMapper,
                                UserHelper userHelper,
                                WarehouseHelper warehouseHelper,
                                ProductHelper productHelper,
                                StockUpdatedMessagePublisher stockUpdatedMessagePublisher,
                                StockCreatedMessagePublisher stockCreatedMessagePublisher) {
        this.warehouseDomainService = warehouseDomainService;
        this.warehouseStockRepository = warehouseStockRepository;
        this.stockTransferRepository = stockTransferRepository;
        this.stockJournalRepository = stockJournalRepository;
        this.warehouseDataMapper = warehouseDataMapper;
        this.userHelper = userHelper;
        this.warehouseHelper = warehouseHelper;
        this.productHelper = productHelper;
        this.stockUpdatedMessagePublisher = stockUpdatedMessagePublisher;
        this.stockCreatedMessagePublisher = stockCreatedMessagePublisher;
    }

    /**
     * This method is used to get the stock by id
     * @param stockIdQuery StockIdQuery
     * @param authorizationHeader AuthorizationHeader
     * @return stock
     */
    @Transactional(readOnly = true)
    public Stock findStockById(StockIdQuery stockIdQuery, AuthorizationHeader authorizationHeader) {

        Optional<Stock> stock = warehouseStockRepository.findById(new StockId(stockIdQuery.getStockId()));
        if (stock.isEmpty()) {
            throw new WarehouseNotFoundException("Stock not found for id: " + stockIdQuery.getStockId());
        }

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !user.getWarehouseId().equals(stock.get().getWarehouseId())) {
            throw new UserForbidden("User is not authorized to view stock for this warehouse");
        }

        return stock.get();
    }

    /**
     * This method is used to create a stock for a product in a warehouse.
     * It first checks if the user is authorized to create a stock for the warehouse.
     * @param stockRequestCommand StockRequestCommand
     * @param authorizationHeader AuthorizationHeader
     * @return StockCreatedEvent
     */
    @Transactional
    public StockCreatedEvent createStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader) {
        Stock stockCommand = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !user.getWarehouseId().equals(stockCommand.getWarehouseId())) {
            throw new UserForbidden("User is not authorized to create stock for this warehouse");
        }

        Optional<Stock> stockOptional = warehouseStockRepository.findByProductIdAndWarehouseId(stockCommand.getProductId(), stockCommand.getWarehouseId());
        if (stockOptional.isPresent()) {
            throw new WarehouseDomainException("Stock already exists for product id: " +
                    stockCommand.getProductId().getValue() + " and warehouse id: " + stockCommand.getWarehouseId().getValue());
        }

        Warehouse warehouse =  warehouseHelper.findWarehouse(stockCommand.getWarehouseId());
        Product product = productHelper.findProduct(stockCommand.getProductId());

        Stock stock = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);

        StockCreatedEvent stockCreatedEvent = warehouseDomainService.stockCreated(warehouse, product, stock, stockJournal,
                stockCommand.getQuantity(), stockCreatedMessagePublisher);
        saveStockJournal(stockCreatedEvent.getStockJournal());
        saveStock(stockCreatedEvent.getStock());
        return stockCreatedEvent;
    }

    /**
     * This method is used to update the stock for a product in a warehouse.
     * It first checks if the user is authorized to update the stock for the warehouse.
     * @param stockRequestCommand StockRequestCommand
     * @param authorizationHeader AuthorizationHeader
     * @return StockUpdatedEvent
     */
    @Transactional
    public StockUpdatedEvent updateStock(StockRequestCommand stockRequestCommand, AuthorizationHeader authorizationHeader) {
        Stock stockCommand = warehouseDataMapper.stockRequestCommandToStock(stockRequestCommand);

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !user.getWarehouseId().equals(stockCommand.getWarehouseId())) {
            throw new UserForbidden("User is not authorized to create stock for this warehouse");
        }

        Stock stock = findStockByProductIdAndWarehouseId(stockCommand.getProductId(), stockCommand.getWarehouseId());
        StockJournal stockJournal = warehouseDataMapper.stockToStockJournal(stock);

        Product product = productHelper.findProduct(stockCommand.getProductId());

        StockUpdatedEvent stockUpdatedEvent = warehouseDomainService.stockUpdated(product, stock,
                stockJournal, stockCommand.getQuantity(), stockUpdatedMessagePublisher);
        saveStockJournal(stockUpdatedEvent.getStockJournal());
        saveStock(stockUpdatedEvent.getStock());
        return stockUpdatedEvent;
    }

    /**
     * This method is used to find the stock journals for a product in a warehouse.
     * It first checks if the user is authorized to view the stock journals for the warehouse.
     * @param productIdQuery ProductIdQuery
     * @param warehouseIdQuery WarehouseIdQuery
     * @param authorizationHeader AuthorizationHeader
     * @return List<StockJournal>
     */
    @Transactional(readOnly = true)
    public List<StockJournal> findStockJournalsByWarehouseId(ProductIdQuery productIdQuery,
                                                             WarehouseIdQuery warehouseIdQuery,
                                                             AuthorizationHeader authorizationHeader) {

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !user.getWarehouseId().equals(new WarehouseId(warehouseIdQuery.getWarehouseId()))) {
            throw new UserForbidden("User is not authorized to view stock journals for this warehouse");
        }

        return stockJournalRepository.findAllByProductIdWarehouseId(new ProductId(productIdQuery.getProductId()), new WarehouseId(warehouseIdQuery.getWarehouseId()));
    }

    /**
     * This method is used to find the stock transfers for a warehouse.
     * It first checks if the user is authorized to view the stock transfers for the warehouse.
     * @param warehouseIdQuery WarehouseIdQuery
     * @param authorizationHeader AuthorizationHeader
     * @return List<StockTransfer>
     */
    @Transactional(readOnly = true)
    public List<StockTransfer> findStockTransfersBySourceWarehouseId(WarehouseIdQuery warehouseIdQuery,
                                                                     AuthorizationHeader authorizationHeader) {

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !user.getWarehouseId().equals(new WarehouseId(warehouseIdQuery.getWarehouseId()))) {
            throw new UserForbidden("User is not authorized to view stock transfers for this warehouse");
        }

        return stockTransferRepository.findAllBySourceWarehouseId(new WarehouseId(warehouseIdQuery.getWarehouseId()));
    }

    /**
     * This method is used to find the stock transfers by destination warehouse.
     * It first checks if the user is authorized to view the stock transfers for the warehouse.
     * @param warehouseIdQuery WarehouseIdQuery
     * @param authorizationHeader AuthorizationHeader
     * @return List<StockTransfer>
     */
    @Transactional(readOnly = true)
    public List<StockTransfer> findStockTransfersByDestinationWarehouseId(WarehouseIdQuery warehouseIdQuery,
                                                                          AuthorizationHeader authorizationHeader) {

        User user = userHelper.getUserFromLoggedIn(authorizationHeader);
        if (user.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !user.getWarehouseId().equals(new WarehouseId(warehouseIdQuery.getWarehouseId()))) {
            throw new UserForbidden("User is not authorized to view stock transfers for this warehouse");
        }

        return stockTransferRepository.findAllByDestinationWarehouseId(new WarehouseId(warehouseIdQuery.getWarehouseId()));
    }

    /**
     * This method is used to save a stock transfer.
     * @param stockTransfer StockTransfer
     */
    public void saveStockTransfer(StockTransfer stockTransfer) {
        StockTransfer stockTransferResult = stockTransferRepository.save(stockTransfer);
        if (stockTransferResult != null) {
            log.info("Stock transfer saved successfully with id: {}", stockTransferResult.getId());
        } else {
            log.error("Error saving stock transfer with id: {}", stockTransfer.getId());
            throw new WarehouseDomainException("Error saving stock transfer with id: " + stockTransfer.getId());
        }
    }

    /**
     * This method is used to save a stock journal.
     * @param stockJournal StockJournal
     */
    public void saveStockJournal(StockJournal stockJournal) {
        StockJournal stockJournalResult = stockJournalRepository.save(stockJournal);
        if (stockJournalResult != null) {
            log.info("Stock journal saved successfully with id: {}", stockJournalResult.getId());
        } else {
            log.error("Error saving stock journal with id: {}", stockJournal.getId());
            throw new WarehouseDomainException("Error saving stock journal with id: " + stockJournal.getId());
        }
    }

    /**
     * This method is used to find a stock by product id and warehouse id.
     * @param productId ProductId
     * @param warehouseId WarehouseId
     * @return Stock
     */
    public Stock findStockByProductIdAndWarehouseId(ProductId productId, WarehouseId warehouseId) {
        Optional<Stock> stock = warehouseStockRepository.findByProductIdAndWarehouseId(productId, warehouseId);
        if (stock.isEmpty()) {
            throw new WarehouseNotFoundException("Stock not found for product id: " + productId.getValue() + " and warehouse id: " + warehouseId.getValue());
        }
        return stock.get();
    }

    /**
     * This method is used to save a stock.
     * @param stock Stock
     * @return Stock
     */
    public Stock saveStock(Stock stock) {
        Stock stockResult = warehouseStockRepository.save(stock);
        if (stockResult != null) {
            log.info("Stock saved successfully with id: {}", stockResult.getId());
        } else {
            log.error("Error saving stock with id: {}", stock.getId());
            throw new WarehouseDomainException("Error saving stock with id: " + stock.getId());
        }
        return stockResult;
    }
}
