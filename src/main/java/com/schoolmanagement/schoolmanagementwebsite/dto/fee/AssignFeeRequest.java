package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignFeeRequest {

    private List<Long> feeStructureIds;

    private List<Long> studentIds;

}
