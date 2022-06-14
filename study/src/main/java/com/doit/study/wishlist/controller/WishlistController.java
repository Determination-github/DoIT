package com.doit.study.wishlist.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.service.BoardService;
import com.doit.study.wishlist.dto.WishlistDto;
import com.doit.study.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class WishlistController {

    private final WishListService wishListService;
    private final BoardService boardService;

    @PostMapping("/like/save/{id}")
    public ResponseEntity like(@PathVariable("id") Integer id,
                                  @RequestBody WishlistDto wishlistDto) throws Exception {
        log.info("id={}, wishlistDto={}", id, wishlistDto);

        //중복으로 담지 않도록 체크
        Integer result = wishListService.getCountByIdAndStudyId(id, wishlistDto.getStudy_id());
        log.info("result = {}", result);
        if(result == 0) {
            wishListService.save(wishlistDto);
        }

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/like/delete/{id}")
    public ResponseEntity likeDelete(@PathVariable("id") Integer id,
                                     @RequestBody WishlistDto wishlistDto) throws Exception {
        log.info("id={}, wishlistDto={}", id, wishlistDto);

        //DB에서 정보 삭제하기
        wishListService.deleteWishlist(id, wishlistDto.getStudy_id());

        return ResponseEntity.ok(id);
    }

    @GetMapping("/like/{id}")
    public String wishlist(Model model,
                           @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                           @PathVariable Integer id) {

        Integer result = wishListService.getCountById(id);
        Integer totalRecordCount;
        if(result != null) {
            totalRecordCount = result;
        } else {
            totalRecordCount = 0;
        }

        if (totalRecordCount != 0) {
            Pagination pagination = new Pagination(currentPage, pageSize);

            pagination.setTotalRecordCount(totalRecordCount);
            log.info("totalRecordCount = " + totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = " + pagination);

            //위시리스트에 담긴 스터디 정보 가져오기
            List<WishlistDto> wishlist = wishListService.getWishlist(id);
            log.info("wishlist = "+wishlist);
            if(wishlist != null) {
                boardService.getWishlistBoardListAll(id, wishlist, pagination);
                model.addAttribute("list", boardService.getWishlistBoardListAll(id, wishlist, pagination));
            } else {
                model.addAttribute("list", null);
            }


            log.info("list = " + boardService.getWishlistBoardListAll(id, wishlist, pagination));

            model.addAttribute("id", id);

            return "board/wishlist";
        } else {
            Pagination pagination = new Pagination(currentPage, pageSize);

            pagination.setTotalRecordCount(totalRecordCount);
            log.info("totalRecordCount = " + totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = " + pagination);

            model.addAttribute("list", null);
            return "board/wishlist";
        }
    }

    @GetMapping("/like")
    public String wishlistIndex(Model model,
                                @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                                @RequestParam Integer id) {

        Integer totalRecordCount = wishListService.getCountById(id);
        Pagination pagination = new Pagination(currentPage, pageSize);
        pagination.setTotalRecordCount(totalRecordCount);
        log.info("totalRecordCount = " + totalRecordCount);

        model.addAttribute("pagination", pagination);
        log.info("pagination = " + pagination);

        //위시리스트에 담긴 스터디 정보 가져오기
        List<WishlistDto> wishlist = wishListService.getWishlist(id);
        log.info("wishlist = "+wishlist);
        if(wishlist != null) {
            boardService.getWishlistBoardListAll(id, wishlist, pagination);
            model.addAttribute("list", boardService.getWishlistBoardListAll(id, wishlist, pagination));
        } else {
            model.addAttribute("list", null);
        }

        log.info("list = " + boardService.getWishlistBoardListAll(id, wishlist, pagination));

        model.addAttribute("id", id);

        return "board/wishlist";

    }
}
