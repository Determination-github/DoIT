package com.doit.study.wishlist.service;

import com.doit.study.mapper.WishListMapper;
import com.doit.study.wishlist.domain.Wishlist;
import com.doit.study.wishlist.dto.WishlistDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WishListServiceImpl implements WishListService{

    private final WishListMapper wishListMapper;

    /**
     * 위시리스트 저장
     * @param wishlistDto
     * @throws Exception
     */
    @Override
    public void save(WishlistDto wishlistDto) throws Exception {
        log.info("wishlistDto={}", wishlistDto);

        wishListMapper.save(wishlistDto);
    }

    /**
     * 위시리스트 개수 가져오기
     * @param id
     * @return Integer
     */
    @Override
    public Integer getCountById(Integer id) {
        return wishListMapper.getCountById(id);
    }

    /**
     * 위시리스트 개수 가져오기(BY ID AND STUDY_ID)
     * @param id
     * @param study_id
     * @return
     */
    @Override
    public Integer getCountByIdAndStudyId(Integer id, Integer study_id) {
        log.info("id={}, study_id={}", id, study_id);

        return wishListMapper.getWishlistCount(id, study_id);
    }

    /**
     * 위시리스트 목록 가져오기
     * @param id
     * @return List<WishlistDto>
     */
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

    /**
     * 위시리스트 삭제
     * @param id
     * @param study_id
     * @throws Exception
     */
    @Override
    public void deleteWishlist(Integer id, Integer study_id) throws Exception {
        wishListMapper.deleteWishlist(id, study_id);
    }
}
