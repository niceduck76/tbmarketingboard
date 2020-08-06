package TBMarketing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TbMarketingBoardRepository extends CrudRepository<TbMarketingBoard, Long> {

    List<TbMarketingBoard> findByPurchaseId(String purchaseId);
}