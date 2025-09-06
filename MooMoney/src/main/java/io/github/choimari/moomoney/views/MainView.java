package io.github.choimari.moomoney.views;

import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.util.InputReader;

/**
 *  프로그램 시작 화면
 */
public class MainView extends AbstractView {
		
	public MainView(InputReader reader) {
		super(reader);

	}
	
	private final String title = "한눈에 지출";
    private final String version = "1.0.0";
    private final String intro = "한눈에 지출(MooMoney)은 개인 지출을 쉽고 빠르게 관리할 수 있는 프로그램입니다.";
    private final String asciiArt = 
            "                               ,----.\n" +
            "                              ( WOW! )                         .-.\n" +
            "                               `----' _                         \\ \\\n" +
            "                                     (_)                         \\ \\\n" +
            "                                         O                       | |\n" +
            "                    |\\ /\\                  o                     | |\n" +
            "    __              |,\\(_\\_                  . /\\---/\\   _,---._ | |\n" +
            "   ( (              |\\,`   `-^.               /^   ^  \\,'       `. ;\n" +
            "    \\ \\             :    `-'   )             ( O   O   )           ;\n" +
            "     \\ \\             \\        ;               `.=o=__,'            \\\n" +
            "      \\ \\             `-.   ,'                  /         _,--.__   \\\n" +
            "       \\ \\ ____________,'  (                   /  _ )   ,'   `-. `-. \\\n" +
            "        ; '                ;                  / ,' /  ,'        \\ \\ \\ \\\n" +
            "        \\                 /___,-.            / /  / ,'          (,_)(,_)\n";
    
    private final String asciiArt2 = 
            "                        ┌─────────────────────────────┐\n" +
            "                        │        한눈에 지출          │\n" +
            "                        ├─────────────────────────────┤\n" +
            "                        │ 카테고리: 음식/교통/생활... │\n" +
            "                        │ 날짜: YYYY-MM-DD            │\n" +
            "                        │ 결제수단: 카드/현금         │\n" +
            "                        │ 금액: 12,345원              │\n" +
            "                        ├─────────────────────────────┤\n" +
            "                        │        환영합니다!          │\n" +
            "                        └─────────────────────────────┘\n";
    


	@Override
	public void showContent() {
		System.out.println("===================================================================================");
		System.out.println("                                " + title + " Ver." + version);
		System.out.println("===================================================================================");
		System.out.println(asciiArt);
		System.out.println("===================================================================================");
		System.out.println(asciiArt2);
		System.out.println(intro);
	    System.out.println("===================================================================================");
	}


}
