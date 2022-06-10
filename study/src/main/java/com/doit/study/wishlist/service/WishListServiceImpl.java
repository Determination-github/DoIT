package com.doit.study.wishlist.service;

import com.doit.study.mapper.WishListMapper;
import com.doit.study.wishlist.domain.Wishlist;
import com.doit.study.wishlist.dto.WishlistDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListServiceImpl implements WishListService{

    private final WishListMapper wishListMapper;


    @Override
    public void save(WishlistDto wishlistDto) {
        log.info("wishlistDto={}", wishlistDto);

        wishListMapper.save(wishlistDto);
    }

    @Override
    public Integer getCountById(Integer id) {
        return wishListMapper.getCountById(id);
    }

    @Override
    public Integer getCountByIdAndStudyId(Integer id, Integer study_id) {
        log.info("id={}, study_id={}", id, study_id);

        return wishListMapper.getWishlistCount(id, study_id);
    }

    @Override
    public List<WishlistDto> getWishlist(Integer id) {
        List<Wishlist> wishlists = wishListMapper.getWishlist(id);
        List<WishlistDto> wishlistDtos = new ArrayList<>();

        if(wishlists != null) {
            for (Wishlist wishlist : wishlists) {
                WishlistDto wishlistDto = new WishlistDto().toDto(wishlist);
                wishlistDtos.add(wishlistDto);
            }
        }

        return wishlistDtos;
    }

    @Override
    public void deleteWishlist(Integer id, Integer study_id) {
        wishListMapper.deleteWishlist(id, study_id);
    }
}
