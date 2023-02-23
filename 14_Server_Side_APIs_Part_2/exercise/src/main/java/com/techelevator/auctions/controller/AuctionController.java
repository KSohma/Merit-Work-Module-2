package com.techelevator.auctions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.techelevator.auctions.dao.AuctionDao;
import com.techelevator.auctions.exception.AuctionNotFoundException;
import com.techelevator.auctions.model.Auction;

import javax.validation.Valid;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

	@Autowired
    private AuctionDao dao;

    public AuctionController(AuctionDao dao) {
        this.dao = dao;
    }

    @RequestMapping( path = "", method = RequestMethod.GET)
    public List<Auction> list(@RequestParam(defaultValue = "") String title_like, @RequestParam(defaultValue = "0") double currentBid_lte) {

        if( !title_like.equals("") ) {
            return dao.searchByTitle(title_like);
        }
        if(currentBid_lte > 0) {
            return dao.searchByPrice(currentBid_lte);
        }

        return dao.list();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Auction get(@PathVariable int id) throws AuctionNotFoundException {
        return dao.get(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Auction create(@Valid @RequestBody Auction auction) {
        return dao.create(auction);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Auction update(@Valid @RequestBody Auction auction, @Valid @PathVariable int id)
            throws AuctionNotFoundException{
        return dao.update(auction, id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable int id)
            throws AuctionNotFoundException{
        dao.delete(id);
    }



}
