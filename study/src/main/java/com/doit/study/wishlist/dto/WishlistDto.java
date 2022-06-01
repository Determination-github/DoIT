package com.doit.study.wishlist.dto;

import com.doit.study.wishlist.domain.Wishlist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistDto {

    private Integer wish_id, study_id, id;

    public Wishlist toEntity(WishlistDto wishlistDto) {
        Wishlist wishlist = new Wishlist(wishlistDto.wish_id, wishlistDto.study_id, wishlistDto.id);

        return wishlist;
    }

    public WishlistDto toDto(Wishlist wishlist) {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setWish_id(wishlist.getWish_id());
        wishlistDto.setStudy_id(wishlist.getStudy_id());
        wishlistDto.setId(wishlist.getId());

        return wishlistDto;
    }

}
