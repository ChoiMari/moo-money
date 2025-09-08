package io.github.choimari.moomoney.dto;

import java.time.LocalDate;

import io.github.choimari.moomoney.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 영수증 요청 데이터 처리
 */
@AllArgsConstructor @Getter 
public class ReceiptRequest {
	 private LocalDate date;
	 private Category category;
	 private Integer price;
	 private String memo;
}
