package com.doit.study.mapper;

import com.doit.study.wishlist.domain.Wishlist;
import com.doit.study.wishlist.dto.WishlistDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WishListMapper {

    //위시리스트 저장
    @Insert(WishListSQL.save)
    void save(@Param("wishlistDto") WishlistDto wishlistDto);

    //위시리스트 개수 가져오기(BY BOARD_ID, ID)
    @Select(WishListSQL.getWishlistCount)
    Integer getWishlistCount(@Param("id") Integer id,
                             @Param("study_id") Integer study_id);

    //아이디로 위시리스트 가져오기
    @Select(WishListSQL.getCountById)
    Integer getCountById(@Param("id") Integer id);

    //회원 별 위시리스트 객체 가져오기
    @Select(WishListSQL.getWishlist)
    List<Wishlist> getWishlist(@Param("id") Integer id);

    //위시리스트에서 삭제
    @Delete(WishListSQL.deleteWishlist)
    void deleteWishlist(@Param("id") Integer id,
                        @Param("study_id") Integer study_id);
}
