package fooddelivery.domain;

import fooddelivery.domain.*;
import fooddelivery.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderAccepted extends AbstractEvent {

    private Long id;
    private String orderId;
    private String userId;
    private String storeId;
    private Float totalAmount;
    private String comment;
    private List<OrderItem> orderItems;
    private Address address;
    private Status status;

    public OrderAccepted(OrderMgmt aggregate) {
        super(aggregate);
    }

    public OrderAccepted() {
        super();
    }
}
//>>> DDD / Domain Event
