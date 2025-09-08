package io.github.choimari.moomoney.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor @Getter @Builder @EqualsAndHashCode 
public class Receipt {
	 private LocalDate date;
	 private Category category;
	 private int price;
	 private String memo;
}
