package io.github.choimari.moomoney.domain;
/**
 * 영수증 카테고리
 */
public enum Category {
	 FOOD("식비"),
	 TRANSPORT("교통"),
	 SHOPPING("쇼핑"),
	 LIVING("생활"),
	 CULTURE("문화"),
	 INSURANCE("보험"),
	 COMMUNICATION("통신료"),
	 ETC("기타");

	 private final String label; // 화면용 한글 라벨

	 private Category(String label) {
	     this.label = label;
	 }

	 public String getLabel() {
	     return label;
	 }
}
