package com.doit.study.wishlist.service;

import com.doit.study.wishlist.dto.WishlistDto;

import java.util.List;

public interface WishListService {

    void save(WishlistDto wishlistDto);

    Integer getCountById(Integer id);

    Integer getCountByIdAndStudyId(Integer id, Integer study_id);

    List<WishlistDto> getWishlist(Integer id);

    void deleteWishlist(Integer id, Integer study_id);
}
