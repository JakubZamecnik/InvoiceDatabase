package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Arrays;
import java.util.List;
public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, Long>, JpaRepository<InvoiceEntity, Long> {

    @Query(value = "SELECT COALESCE(SUM(price), 0) FROM invoice", nativeQuery = true)
    Long getAllTimeSum();
    @Query(value = "SELECT COALESCE(SUM(price),0) FROM invoice WHERE issued > '2024-01-01'", nativeQuery = true)
    Long getCurrentYearSum();

    @Query(value = "SELECT COUNT(*) FROM invoice", nativeQuery = true)
    Long getInvoiceCount();

    @Query(value = "SELECT i FROM invoice i WHERE " +
            "(:#{#filter.buyerID} = -1 OR i.buyer.id = :#{#filter.buyerID}) " +
            "AND (:#{#filter.sellerID} = -1 OR i.seller.id = :#{#filter.sellerID}) " +
            "AND (:#{#filter.product} = '' OR i.product LIKE %:#{#filter.product}%) " +
            "AND (:#{#filter.minPrice} IS NULL OR i.price >= :#{#filter.minPrice}) " +
            "AND (:#{#filter.maxPrice} IS NULL OR i.price <= :#{#filter.maxPrice})")
    List<InvoiceEntity> getFilteredInvoices(InvoiceFilter filter, Pageable pageable);

}
