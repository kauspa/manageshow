package com.demo.manageshow.service;

import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.data.Show;

import java.util.Optional;
import java.util.Set;

public interface BuyerService {
    public Buyer addBuyer(Buyer buyer);
    public Set<Buyer> getBuyers();

    public Optional<Buyer> getBuyerByMobile(String mobile);

}
