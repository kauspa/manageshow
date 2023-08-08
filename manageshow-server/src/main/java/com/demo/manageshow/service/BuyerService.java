package com.demo.manageshow.service;

import com.demo.manageshow.data.Buyer;

import java.util.Optional;
import java.util.Set;

public interface BuyerService {
    Buyer addBuyer(Buyer buyer);
    Set<Buyer> getBuyers();

    Optional<Buyer> getBuyerByMobile(String mobile);

}
