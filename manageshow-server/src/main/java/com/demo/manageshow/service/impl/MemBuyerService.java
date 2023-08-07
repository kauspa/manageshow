package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.BuyerService;
import com.demo.manageshow.service.DuplicateException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class MemBuyerService implements BuyerService {
    ConcurrentHashMap<String, Buyer> buyerMap = new ConcurrentHashMap<>();
    @Override
    public Buyer addBuyer(Buyer buyer) {
        Buyer existingBuyer=buyerMap.putIfAbsent(buyer.getMobile(),buyer);
        if(existingBuyer!=null){
            throw new DuplicateException("User with %s mobile number exist".format(buyer.getMobile()));
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
