package com.nimbleways.springboilerplate.services.impl;

import java.time.LocalDate;

import com.nimbleways.springboilerplate.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository pr;
    private final NotificationService ns;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void notifyDelay(int leadTime, Product p) {
        log.info("Notify delay to product : {} using lead time :{} ", p.getName(), leadTime);
        p.setLeadTime(leadTime);
        pr.save(p);
        ns.sendDelayNotification(leadTime, p.getName());
        log.info("Notified delay to product : {}  using lead time :{} ", p.getName(), leadTime);
    }

    @Transactional
    @Override
    public void handleSeasonalProduct(Product p) {
        log.info("Handle seasonal product : {}", p.getName());
        if ((LocalDate.now().isAfter(p.getSeasonStartDate()) && LocalDate.now().isBefore(p.getSeasonEndDate())
                && p.getAvailable() > 0)) {
            p.setAvailable(p.getAvailable() - 1);
            save(p);
        }  else if (LocalDate.now().plusDays(p.getLeadTime()).isAfter(p.getSeasonEndDate())) {
            ns.sendOutOfStockNotification(p.getName());
            p.setAvailable(0);
            save(p);
        } else if (p.getSeasonStartDate().isAfter(LocalDate.now())) {
            ns.sendOutOfStockNotification(p.getName());
            save(p);
        } else {
            notifyDelay(p.getLeadTime(), p);
        }
        log.info("Handled seasonal product : {}", p.getName());
    }

    @Transactional
    @Override
    public void handleExpiredProduct(Product p) {
        log.info("Handle expired product : {}", p.getName());
        if (p.getAvailable() > 0 && p.getExpiryDate().isAfter(LocalDate.now())) {
            p.setAvailable(p.getAvailable() - 1);
            save(p);
        } else {
            ns.sendExpirationNotification(p.getName(), p.getExpiryDate());
            p.setAvailable(0);
            save(p);
        }
        log.info("Handled expired product : {}", p.getName());
    }


    @Transactional
    @Override
    public void handleNormalProduct(Product p){
        log.info("Handle normal product : {}", p.getName());
        if (p.getAvailable() > 0) {
            p.setAvailable(p.getAvailable() - 1);
            save(p);
        } else {
            int leadTime = p.getLeadTime();
            if (leadTime > 0) {
                notifyDelay(leadTime, p);
            }
        }
        log.info("Handle normal product : {}", p.getName());
    }
    @Override
    public void save(Product p) {
        log.info("Saving product : {}", p);
        var saved =  productRepository.save(p);
        log.info("Saved product : {}", saved);
    }
}