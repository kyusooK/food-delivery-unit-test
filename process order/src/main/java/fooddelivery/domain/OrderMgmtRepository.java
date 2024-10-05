package fooddelivery.domain;

import fooddelivery.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel="orderMgmts", path="orderMgmts")
public interface OrderMgmtRepository extends PagingAndSortingRepository<OrderMgmt, >{
}