package fooddelivery.domain;

import fooddelivery.domain.*;
import fooddelivery.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CouponAdded extends AbstractEvent {

    private Long id;
    private String orderId;
    private String userId;
    private String storeId;
    private Float totalAmount;
    private String comment;
    private List<OrderItem> orderItems;
    private Status status;
    private Address address;
    private Integer coupon;

    public CouponAdded(OrderMgmt aggregate) {
        super(aggregate);
    }

    public CouponAdded() {
        super();
    }
}
//>>> DDD / Domain Event
