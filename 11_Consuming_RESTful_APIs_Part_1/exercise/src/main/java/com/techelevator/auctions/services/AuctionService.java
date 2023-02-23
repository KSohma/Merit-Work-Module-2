package com.techelevator.auctions.services;

import org.springframework.web.client.RestTemplate;

import com.techelevator.auctions.model.Auction;

public class AuctionService {

    public static final String API_BASE_URL = "http://localhost:3000/auctions/";
    private RestTemplate restTemplate = new RestTemplate();


    public Auction[] getAllAuctions() {
        // call api here
        Auction[] response =  restTemplate.getForObject(API_BASE_URL, Auction[].class);
        return response;
    }

    public Auction getAuction(int id) {
        // call api here
        Auction response =  restTemplate.getForObject(API_BASE_URL + id, Auction.class);
        return response;
    }

    public Auction[] getAuctionsMatchingTitle(String title) {
        // call api here
        String apiWithTitle = API_BASE_URL + "?title_like=" + title;
        Auction[] response =  restTemplate.getForObject(apiWithTitle, Auction[].class);
        return response;
    }

    public Auction[] getAuctionsAtOrBelowPrice(double price) {
        // call api here
        String apiWithPrice = API_BASE_URL + "?currentBid_lte=" + price;
        Auction[] response =  restTemplate.getForObject(apiWithPrice, Auction[].class);
        return (Auction[]) response;
    }

}
