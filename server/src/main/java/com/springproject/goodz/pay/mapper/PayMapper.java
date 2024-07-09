package com.springproject.goodz.pay.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.springproject.goodz.pay.dto.Purchase;
import com.springproject.goodz.pay.dto.Sales;
import com.springproject.goodz.product.dto.ProductOption;

@Mapper
public interface PayMapper {
    
    // 구매 조회
    public Purchase selectPurchase(int purchaseNo) throws Exception;

    // 구매 등록
    public int insertPurchase(Purchase purchase) throws Exception;

    // 구매 업데이트
    public int updatePurchase(Purchase purchase) throws Exception;

    // 판매
    public int insertSale(Sales sales) throws Exception;

    // 유저별 구매 내역 조회
    public List<Purchase> findPurchasesByUserId(@Param("userId") String userId) throws Exception;

    // 유저별 판매 내역 조회
    public List<Sales> findSalesByUserId(@Param("userId") String userId) throws Exception;

    // 유저별 구매 내역 조회 (페이징)
    List<Purchase> findPurchasesByUserIdWithPage(@Param("userId") String userId, @Param("start") int start, @Param("rows") int rows) throws Exception;

    // 미결제시 취소
    public void cancelPurchase(int purchaseNo) throws Exception;

    ProductOption selectProductWithOption(@Param("pNo") int pNo, @Param("optionId") int optionId) throws Exception;
}
