package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_fee_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeeSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long schoolId;
    private Long studentId;

    private String admissionNumber;
    private String studentName;
    private String studentClass;
    private String section;
    private String mobileNumber;

    private String session;

    private Long studentFeeId;
    private Long feeStructureId;
    private Long feeMasterId;

    private String feeCode;
    private String feeName;
    private String feeCategory;
    private String feeBatch;

    private String month;

    private Double amount;
    private Double paidAmount;
    private Double dueAmount;
    private Double fineAmount;
    private Double discountAmount;

    

    private String status;

    private LocalDate dueDate;
     private LocalDate generateDate;
  
  private LocalDate paymentDate;
}