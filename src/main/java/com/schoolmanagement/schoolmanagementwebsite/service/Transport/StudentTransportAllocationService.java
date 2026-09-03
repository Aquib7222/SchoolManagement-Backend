package com.schoolmanagement.schoolmanagementwebsite.service.Transport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StudentTransportAllocationRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StudentTransportAllocationResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.TransportStudentResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.RouteManagement;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.StudentTransportAllocation;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.TransportAllocationStatus;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleManagement;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleRouteMapping;
import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.TransportAllocationStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.RouteManagementRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.StudentTransportAllocationRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.VehicleManagementRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.VehicleRouteMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentTransportAllocationService {

    private final StudentRepository studentRepository;

    private final StudentTransportAllocationRepository allocationRepository;

    private final RouteManagementRepository routeRepository;

    private final VehicleRouteMappingRepository vehicleRouteMappingRepository;

    private final VehicleManagementRepository vehicleRepository;


    // =========================================================
    // GET STUDENTS
    // =========================================================

    public List<TransportStudentResponse> getStudents(
            Long schoolId,
            String academicYear,
            String studentClass,
            String section) {

        List<Student> students =
                studentRepository.findTransportRequiredStudents(
                        schoolId,
                        academicYear,
                        studentClass,
                        section
                );

        List<TransportStudentResponse> response =
                new ArrayList<>();

        for (Student student : students) {

            StudentTransportAllocation allocation =
                    allocationRepository
                            .findBySchoolIdAndStudentIdAndAcademicYear(
                                    schoolId,
                                    student.getId(),
                                    academicYear
                            )
                            .orElse(null);

            if (allocation == null) {

                response.add(
                        TransportStudentResponse.builder()
                                .studentId(student.getId())
                                .schoolId(schoolId)
                                .admissionNumber(
                                        student.getAdmissionNumber()
                                )
                                .studentName(
                                        student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName()
                                )
                                .rollNumber(
                                        student.getRollNumber()
                                )
                                .studentClass(
                                        student.getStudentClass()
                                )
                                .section(
                                        student.getSection()
                                )
                                .transportRequired(true)
                                .allocated(false)
                                .build()
                );

            } else {

                RouteManagement route =
                        routeRepository
                                .findById(allocation.getRouteId())
                                .orElse(null);

                String routeName =
                        route != null
                                ? route.getRouteName()
                                : null;

                String vehicleNumber = null;

                VehicleRouteMapping mapping =
                        vehicleRouteMappingRepository
                                .findBySchoolIdAndVehicleId(
                                        schoolId,
                                        getVehicleIdForRoute(
                                                schoolId,
                                                allocation.getRouteId()
                                        )
                                )
                                .orElse(null);

                Long vehicleId = null;

                if (mapping != null) {
                    vehicleId = mapping.getVehicleId();

                    VehicleManagement vehicle =
                            vehicleRepository
                                    .findById(vehicleId)
                                    .orElse(null);

                    if (vehicle != null) {
                        vehicleNumber =
                                vehicle.getVehicleNumber();
                    }
                }

                response.add(
                        TransportStudentResponse.builder()
                                .studentId(student.getId())
                                .schoolId(schoolId)
                                .admissionNumber(
                                        student.getAdmissionNumber()
                                )
                                .studentName(
                                        student.getFirstName()+" "+student.getMiddleName()+" "+student.getLastName()
                                )
                                .rollNumber(
                                        student.getRollNumber()
                                )
                                .studentClass(
                                        student.getStudentClass()
                                )
                                .section(
                                        student.getSection()
                                )
                                .transportRequired(true)
                                .allocated(true)
                                .allocationId(
                                        allocation.getId()
                                )
                                .routeId(
                                        allocation.getRouteId()
                                )
                                .routeName(routeName)
                                .stopName(
                                        allocation.getStopName()
                                )
                                .vehicleNumber(
                                        vehicleNumber
                                )
                                .allocationStatus(
                                        allocation.getStatus() != null
                                                ? allocation.getStatus().name()
                                                : null
                                )
                                .build()
                );
            }
        }

        return response;
    }


    // =========================================================
    // GET ROUTES
    // =========================================================

    public List<RouteManagement> getRoutes(
            Long schoolId) {

        return routeRepository.findBySchoolId(schoolId);
    }


    // =========================================================
    // ASSIGN MULTIPLE STUDENTS
    // =========================================================

    @Transactional
    public List<StudentTransportAllocationResponse> assignStudents(
            StudentTransportAllocationRequest request) {

        Long schoolId =
                request.getSchoolId();

        String academicYear =
                request.getAcademicYear();

        Long routeId =
                request.getRouteId();

        String stopName =
                request.getStopName();

        List<String> admissionNumbers =
                request.getAdmissionNumbers();


        if (schoolId == null) {
            throw new RuntimeException(
                    "School ID is required"
            );
        }

        if (academicYear == null ||
                academicYear.trim().isEmpty()) {

            throw new RuntimeException(
                    "Academic year is required"
            );
        }

        if (routeId == null) {
            throw new RuntimeException(
                    "Route is required"
            );
        }

        if (stopName == null ||
                stopName.trim().isEmpty()) {

            throw new RuntimeException(
                    "Stop is required"
            );
        }

        if (admissionNumbers == null ||
                admissionNumbers.isEmpty()) {

            throw new RuntimeException(
                    "Please select at least one student"
            );
        }


        // =====================================================
        // ROUTE CHECK
        // =====================================================

        RouteManagement route =
                routeRepository
                        .findById(routeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route not found"
                                )
                        );

        if (!schoolId.equals(
                route.getSchoolId())) {

            throw new RuntimeException(
                    "Route does not belong to this school"
            );
        }


        // =====================================================
        // STOP CHECK
        // =====================================================

        String validStop =
                findMatchingStop(
                        route.getStops(),
                        stopName
                );

        if (validStop == null) {

            throw new RuntimeException(
                    "Selected stop does not belong to this route"
            );
        }


        List<StudentTransportAllocationResponse>
                response = new ArrayList<>();


        // =====================================================
        // STUDENTS
        // =====================================================

        for (String admissionNumber :
                admissionNumbers) {

            Student student =
                    studentRepository
                            .findBySchool_IdAndAdmissionNumber(
                                    schoolId,
                                    admissionNumber
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Student not found: "
                                                    + admissionNumber
                                    )
                            );


            // =================================================
            // TRANSPORT REQUIRED
            // =================================================

           String transportRequired = student.getTransportRequired();

if (transportRequired == null ||
        !transportRequired.trim().equalsIgnoreCase("Yes")) {

    throw new RuntimeException(
            student.getFirstName() + " " +
            (student.getMiddleName() != null
                    ? student.getMiddleName() + " "
                    : "") +
            (student.getLastName() != null
                    ? student.getLastName()
                    : "")
            + " does not require transport"
    );
}


            // =================================================
            // EXISTING ALLOCATION
            // =================================================

            StudentTransportAllocation allocation =
                    allocationRepository
                            .findBySchoolIdAndStudentIdAndAcademicYear(
                                    schoolId,
                                    student.getId(),
                                    academicYear
                            )
                            .orElse(null);


            LocalDateTime now =
                    LocalDateTime.now();


            if (allocation == null) {

                allocation =
                        StudentTransportAllocation.builder()
                                .schoolId(schoolId)
                                .studentId(student.getId())
                                .admissionNumber(
                                        student.getAdmissionNumber()
                                )
                                .academicYear(academicYear)
                                .studentClass(
                                        student.getStudentClass()
                                )
                                .section(
                                        student.getSection()
                                )
                                .routeId(routeId)
                                .stopName(validStop)
                                .status(
                                        TransportAllocationStatus.ACTIVE
                                )
                                .assignedAt(now)
                                .updatedAt(now)
                                .build();

            } else {

                allocation.setRouteId(routeId);

                allocation.setStopName(validStop);

                allocation.setStatus(
                        TransportAllocationStatus.ACTIVE
                );

                allocation.setUpdatedAt(now);
            }


            StudentTransportAllocation saved =
                    allocationRepository.save(
                            allocation
                    );


            response.add(
                    buildResponse(
                            saved,
                            student,
                            route,
                            schoolId
                    )
            );
        }

        return response;
    }


    // =========================================================
    // GET ALL ALLOCATIONS
    // =========================================================

    public List<StudentTransportAllocationResponse>
    getAllAllocations(
            Long schoolId,
            String academicYear) {

        List<StudentTransportAllocation>
                allocations =
                allocationRepository
                        .findBySchoolIdAndAcademicYear(
                                schoolId,
                                academicYear
                        );

        List<StudentTransportAllocationResponse>
                response =
                new ArrayList<>();

        for (StudentTransportAllocation allocation :
                allocations) {

            Student student =
                    studentRepository
                            .findById(
                                    allocation.getStudentId()
                            )
                            .orElse(null);

            RouteManagement route =
                    routeRepository
                            .findById(
                                    allocation.getRouteId()
                            )
                            .orElse(null);

            if (student == null ||
                    route == null) {
                continue;
            }

            response.add(
                    buildResponse(
                            allocation,
                            student,
                            route,
                            schoolId
                    )
            );
        }

        return response;
    }


    // =========================================================
    // DELETE ALLOCATION
    // =========================================================

    @Transactional
    public void deleteAllocation(
            Long schoolId,
            Long id) {

        StudentTransportAllocation allocation =
                allocationRepository
                        .findBySchoolIdAndId(
                                schoolId,
                                id
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Transport allocation not found"
                                )
                        );

        allocationRepository.delete(
                allocation
        );
    }


    // =========================================================
    // RESPONSE
    // =========================================================

    private StudentTransportAllocationResponse
    buildResponse(
            StudentTransportAllocation allocation,
            Student student,
            RouteManagement route,
            Long schoolId) {

        Long vehicleId =
                getVehicleIdForRoute(
                        schoolId,
                        route.getId()
                );

        VehicleManagement vehicle = null;

        if (vehicleId != null) {

            vehicle =
                    vehicleRepository
                            .findById(vehicleId)
                            .orElse(null);
        }

        return StudentTransportAllocationResponse
                .builder()
                .id(allocation.getId())
                .schoolId(allocation.getSchoolId())
                .studentId(allocation.getStudentId())
                .admissionNumber(
                        allocation.getAdmissionNumber()
                )
                .studentName(
                        student.getFirstName()+" "+student.getMiddleName()+" "+student.getLastName()
                )
                .studentClass(
                        allocation.getStudentClass()
                )
                .section(
                        allocation.getSection()
                )
                .academicYear(
                        allocation.getAcademicYear()
                )
                .routeId(
                        allocation.getRouteId()
                )
                .routeName(
                        route.getRouteName()
                )
                .stopName(
                        allocation.getStopName()
                )
                .vehicleId(vehicleId)
                .vehicleNumber(
                        vehicle != null
                                ? vehicle.getVehicleNumber()
                                : null
                )
                .vehicleType(
                        vehicle != null
                                ? vehicle.getVehicleType()
                                : null
                )
                .status(
                        allocation.getStatus() != null
                                ? allocation.getStatus().name()
                                : null
                )
                .assignedAt(
                        allocation.getAssignedAt()
                )
                .updatedAt(
                        allocation.getUpdatedAt()
                )
                .build();
    }


    // =========================================================
    // GET VEHICLE ASSIGNED TO ROUTE
    // =========================================================

    private Long getVehicleIdForRoute(
            Long schoolId,
            Long routeId) {

        List<VehicleRouteMapping> mappings =
                vehicleRouteMappingRepository
                        .findBySchoolId(schoolId);

        for (VehicleRouteMapping mapping :
                mappings) {

            if (routeId.equals(
                    mapping.getRouteId())) {

                return mapping.getVehicleId();
            }
        }

        return null;
    }


    // =========================================================
    // FIND STOP
    // =========================================================

    private String findMatchingStop(
            String stops,
            String searchStop) {

        if (stops == null ||
                stops.trim().isEmpty()) {

            return null;
        }

        String search =
                searchStop.trim();

        String[] stopArray =
                stops.split(",");

        for (String stop :
                stopArray) {

            String current =
                    stop.trim();

            if (current.equalsIgnoreCase(search)) {
                return current;
            }
        }

        return null;
    }
}