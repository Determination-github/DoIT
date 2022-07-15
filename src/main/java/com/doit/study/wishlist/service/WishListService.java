package com.doit.study.wishlist.service;

import com.doit.study.wishlist.dto.WishlistDto;

import java.util.List;

public interface WishListService {

    //위시리스트 저장
    void save(WishlistDto wishlistDto);

    //위시리스트 개수 가져오기
    Integer getCountById(Integer id);

    //위시리스트 개수 가져오기(BY ID AND STUDY_ID)
    Integer getCountByIdAndStudyId(Integer id, Integer study_id);

    //위시리스트 목록 가져오기
    List<WishlistDto> getWishlist(Integer id);

    //위시리스트 삭제
    void deleteWishlist(Integer id, Integer study_id);
}
