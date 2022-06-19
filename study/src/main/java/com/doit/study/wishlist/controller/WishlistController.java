package com.doit.study.wishlist.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.service.BoardService;
import com.doit.study.wishlist.dto.WishlistDto;
import com.doit.study.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    /**
     * 위시리스트 저장
     * @param id
     * @param wishlistDto
     * @return ResponseEntity
     * @throws Exception
     */
    @PostMapping("/like/save/{id}")
    public ResponseEntity like(@PathVariable("id") Integer id,
                                  @RequestBody WishlistDto wishlistDto) throws Exception {
        log.info("id={}, wishlistDto={}", id, wishlistDto);

        //중복으로 담지 않도록 체크
        Integer result = wishListService.getCountByIdAndStudyId(id, wishlistDto.getStudy_id());
        if(result == 0) {
            wishListService.save(wishlistDto);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 위시리스트 삭제
     * @param id
     * @param wishlistDto
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/like/delete/{id}")
    public ResponseEntity likeDelete(@PathVariable("id") Integer id,
                                     @RequestBody WishlistDto wishlistDto) throws Exception {
        //DB에서 정보 삭제하기
        wishListService.deleteWishlist(id, wishlistDto.getStudy_id());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 위시리스트 가져오기
     * @param model
     * @param currentPage
     * @param pageSize
     * @param id
     * @return String
     */
    @GetMapping("/like/{id}")
    public String wishlist(Model model,
                           @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                           @PathVariable Integer id) {

        //위시리스트 개수 가져오기
        Integer result = wishListService.getCountById(id);
        Integer totalRecordCount;
        if(result != null) { //위시리스트에 담기 게시글이 있다면
            totalRecordCount = result;
        } else { //위시리스트에 담기 게시글이 없다면
            totalRecordCount = 0;
        }

        if (totalRecordCount != 0) {
            //페이징 처리
            Pagination pagination = getPagination(currentPage, pageSize, totalRecordCount, model);

            //위시리스트에 담긴 스터디 정보 가져오기
            List<WishlistDto> wishlist = wishListService.getWishlist(id);
            boardService.getWishlistBoardListAll(id, wishlist, pagination);
            model.addAttribute("list", boardService.getWishlistBoardListAll(id, wishlist, pagination));
            model.addAttribute("id", id);
        } else { //NULL값 담기
            model.addAttribute("list", null);
        }

        return "board/wishlist";
    }



    /**
     * 위시리스트 페이징 처리하기
     * @param model
     * @param currentPage
     * @param pageSize
     * @param id
     * @return String
     */
    @GetMapping("/like")
    public String wishlistIndex(Model model,
                                @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                                @RequestParam Integer id) {

        Integer totalRecordCount = wishListService.getCountById(id);

        //페이징 처리
        Pagination pagination = getPagination(currentPage, pageSize, totalRecordCount, model);

        //위시리스트에 담긴 스터디 정보 가져오기
        List<WishlistDto> wishlist = wishListService.getWishlist(id);
        if(wishlist != null) {
            boardService.getWishlistBoardListAll(id, wishlist, pagination);
            model.addAttribute("list", boardService.getWishlistBoardListAll(id, wishlist, pagination));
        } else {
            model.addAttribute("list", null);
        }

        model.addAttribute("id", id);

        return "board/wishlist";
    }


    //------------------------------------extracted method-----------------------------------------

    //페이징
    private Pagination getPagination(int currentPage, int pageSize, Integer totalRecordCount, Model model) {
        Pagination pagination = new Pagination(currentPage, pageSize);
        pagination.setTotalRecordCount(totalRecordCount);
        model.addAttribute("pagination", pagination);
        return pagination;
    }
}
