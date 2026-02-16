package io.github.hjle.settlement.dto;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SettlementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private Long feeAmount;

    @Column(nullable = false)
    private Long settlementAmount;

    @Column(nullable = false)
    private String status;

    private LocalDateTime settlementDate;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "READY";
        }
    }

    public void completeSettlement() {
        this.status = "COMPLETED";
        this.settlementDate = LocalDateTime.now();
    }
}