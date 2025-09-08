package io.github.choimari.moomoney.service;

import java.io.IOException;

import io.github.choimari.moomoney.dto.ReceiptRequest;
import io.github.choimari.moomoney.repository.ReceiptRepository;

public class ReceiptService {
	private final ReceiptRepository receiptRepo;
    public ReceiptService(ReceiptRepository receiptRepo) {
        this.receiptRepo = receiptRepo;
    }
    
    /**
     * 영수증 저장
     * @param dto 영수증 DTO
     * @return 저장 성공 여부
     */
    public boolean saveReceipt(ReceiptRequest dto, String email) {
        try {
            // 레포지토리에 실제 저장 처리
            receiptRepo.save(dto, email);
            return true;
        } catch (IOException e) {
            System.out.println("[서버 오류] 영수증 저장 실패. 잠시 후 다시 시도해주세요.");
            e.printStackTrace();
            return false;
        }
    }
    
    
}
