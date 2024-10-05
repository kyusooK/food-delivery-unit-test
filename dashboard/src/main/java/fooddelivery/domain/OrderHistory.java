package fooddelivery.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "OrderHistory_table")
@Data
public class OrderHistory {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String userId;
    private String orderId;
    private String storeId;
    private Float totalAmount;
    private String riderId;
    private String status;
}
