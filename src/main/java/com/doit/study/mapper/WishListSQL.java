package com.doit.study.mapper;

public class WishListSQL {

    public static final String save =
            "INSERT INTO WISH_TB(study_id, id) " +
                    "VALUES(#{wishlistDto.study_id}, #{wishlistDto.id})";

    public static final String getWishlistCount =
            "SELECT COUNT(*) FROM WISH_TB " +
                    "WHERE study_id = #{study_id} AND id = #{id}";

    public static final String getCountById =
            "SELECT COUNT(*) FROM WISH_TB WHERE id = #{id}";

    public static final String getWishlist =
            "SELECT * FROM WISH_TB WHERE id = #{id}";

    public static final String deleteWishlist =
            "DELETE FROM WISH_TB " +
                    "WHERE study_id = #{study_id} AND id = #{id}";
}
