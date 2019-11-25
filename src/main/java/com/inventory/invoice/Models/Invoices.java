package com.inventory.invoice.Models;

import com.inventory.invoice.Models.Enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoices {

    @Id
    @Column(name = "invoice_no")
    private String invoiceNumber;

    @Column(name = "date_issued")
    private LocalDateTime dateIssued;

    @Column(name = "total_cost")
    private int totalCost;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name ="customer_id")
    private String customerId;
}
