package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.service.BuyerService;
import com.demo.manageshow.service.DuplicateException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemBuyerService implements BuyerService {
    private final ConcurrentHashMap<String, Buyer> buyerMap = new ConcurrentHashMap<>();

    @Override
    public Buyer addBuyer(Buyer buyer) {
        Buyer existingBuyer = buyerMap.putIfAbsent(buyer.getMobile(), buyer);
        if (existingBuyer != null) {
            throw new DuplicateException(String.format("User's %s mobile already exists!", buyer.getMobile()));
        }
        return buyer;
    }

    @Override
    public Set<Buyer> getBuyers() {
        return new HashSet<>(buyerMap.values());
    }

    @Override
    public Optional<Buyer> getBuyerByMobile(String mobile) {
        return Optional.ofNullable(buyerMap.get(mobile));
    }
}
