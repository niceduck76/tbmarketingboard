package TBMarketing;

import TBMarketing.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TbMarketingBoardViewHandler {


    @Autowired
    private TbMarketingBoardRepository tbMarketingBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTbPurchased_then_CREATE_1 (@Payload TbPurchased tbPurchased) {
        try {
            if (tbPurchased.isMe()) {
                // view 객체 생성
                TbMarketingBoard tbMarketingBoard = new TbMarketingBoard();

                // view 객체에 이벤트의 Value 를 set 함
                tbMarketingBoard.setPurchaseId(tbPurchased.getPurchaseId());
                tbMarketingBoard.setPrdNm(tbPurchased.getPrdNm());
                tbMarketingBoard.setCustNm(tbPurchased.getCustNm()); //전화번호
                tbMarketingBoard.setPurchaseStatus(tbPurchased.getPurchaseStatus());

                // view 레파지 토리에 save
                tbMarketingBoardRepository.save(tbMarketingBoard);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReceiverChanged_then_UPDATE_1(@Payload ReceiverChanged receiverChanged) {
        try {
            if (receiverChanged.isMe()) {
                // view 객체 조회
                List<TbMarketingBoard> tbMarketingBoardList = tbMarketingBoardRepository.findByPurchaseId(receiverChanged.getPurchaseId());
                for(TbMarketingBoard tbMarketingBoards : tbMarketingBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    tbMarketingBoards.setPurchaseStatus(receiverChanged.getPurchaseStatus());
                    // view 레파지 토리에 save
                    tbMarketingBoardRepository.save(tbMarketingBoards);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenTbdmSent_then_UPDATE_2(@Payload TbdmSent tbdmSent) {
        try {
            if (tbdmSent.isMe()) {
                // view 객체 조회
                List<TbMarketingBoard> tbmarketingboardList = tbMarketingBoardRepository.findByPurchaseId(tbdmSent.getPurchaseId());
                for(TbMarketingBoard tbmarketingboard : tbmarketingboardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    tbmarketingboard.setDmStatus(tbdmSent.getDmStatus());
                    // view 레파지 토리에 save
                    tbMarketingBoardRepository.save(tbmarketingboard);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}