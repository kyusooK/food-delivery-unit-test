package fooddelivery.domain;

import fooddelivery.ProcessOrderApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OrderMgmt_table")
@Data
//<<< DDD / Aggregate Root
public class OrderMgmt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;

    private String userId;

    private String storeId;

    private Float totalAmount;

    private String comment;

    @Embedded
    private Address address;

    @ElementCollection
    private List<OrderItem> orderItems;

    private Integer coupon;

    public static OrderMgmtRepository repository() {
        OrderMgmtRepository orderMgmtRepository = ProcessOrderApplication.applicationContext.getBean(
            OrderMgmtRepository.class
        );
        return orderMgmtRepository;
    }

    //<<< Clean Arch / Port Method
    public static void orderInfoTransfer(OrderPlaced orderPlaced) {
        //implement business logic here:

        /** Example 1:  new item 
        OrderMgmt orderMgmt = new OrderMgmt();
        repository().save(orderMgmt);

        CouponAdded couponAdded = new CouponAdded(orderMgmt);
        couponAdded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(orderMgmt->{
            
            orderMgmt // do something
            repository().save(orderMgmt);

            CouponAdded couponAdded = new CouponAdded(orderMgmt);
            couponAdded.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
