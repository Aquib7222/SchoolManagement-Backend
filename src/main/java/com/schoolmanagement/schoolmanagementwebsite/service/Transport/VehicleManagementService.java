package com.schoolmanagement.schoolmanagementwebsite.service.Transport;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleManagementRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleManagementResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleManagement;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.VehicleManagementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleManagementService {

    private final VehicleManagementRepository vehicleRepository;

  
    @Transactional
    public VehicleManagementResponse createVehicle(VehicleManagementRequest request) {

        
        if (vehicleRepository.existsBySchoolIdAndVehicleNumber(
                request.getSchoolId(),
                request.getVehicleNumber())) {

            throw new RuntimeException(
                    "Vehicle number already exists for this school"
            );
        }

        VehicleManagement vehicle = VehicleManagement.builder()
                .schoolId(request.getSchoolId())
                .vehicleNumber(request.getVehicleNumber())
                .vehicleType(request.getVehicleType())
                .vehicleModel(request.getVehicleModel())
                .vehicleCapacity(request.getVehicleCapacity())
                .status(request.getStatus())
                .build();

        VehicleManagement savedVehicle =
                vehicleRepository.save(vehicle);

        return mapToResponse(savedVehicle);
    }


   
    public VehicleManagementResponse getVehicleById(Long schoolId, Long id) {

        VehicleManagement vehicle = vehicleRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

       
        if (!vehicle.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Vehicle not found");
        }

        return mapToResponse(vehicle);
    }


   
    public VehicleManagementResponse getVehicleByNumber(
            Long schoolId,
            String vehicleNumber) {

        VehicleManagement vehicle = vehicleRepository
                .findBySchoolIdAndVehicleNumber(
                        schoolId,
                        vehicleNumber
                )
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

        return mapToResponse(vehicle);
    }


 
    public List<VehicleManagementResponse> getVehiclesBySchool(Long schoolId) {

        return vehicleRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Transactional
    public VehicleManagementResponse updateVehicle(
            Long schoolId,
            Long id,
            VehicleManagementRequest request) {

        VehicleManagement vehicle = vehicleRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

       
        if (!vehicle.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Vehicle not found");
        }

        
        if (!vehicle.getVehicleNumber()
                .equals(request.getVehicleNumber())) {

            if (vehicleRepository
                    .existsBySchoolIdAndVehicleNumber(
                            schoolId,
                            request.getVehicleNumber())) {

                throw new RuntimeException(
                        "Vehicle number already exists for this school"
                );
            }
        }

        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setVehicleModel(request.getVehicleModel());
        vehicle.setVehicleCapacity(request.getVehicleCapacity());
        vehicle.setStatus(request.getStatus());

        VehicleManagement updatedVehicle =
                vehicleRepository.save(vehicle);

        return mapToResponse(updatedVehicle);
    }


   
    @Transactional
    public void deleteVehicle(Long schoolId, Long id) {

        VehicleManagement vehicle = vehicleRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

        
        if (!vehicle.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Vehicle not found");
        }

        vehicleRepository.delete(vehicle);
    }


   
    private VehicleManagementResponse mapToResponse(
            VehicleManagement vehicle) {

        return VehicleManagementResponse.builder()
                .id(vehicle.getId())
                .schoolId(vehicle.getSchoolId())
                .vehicleNumber(vehicle.getVehicleNumber())
                .vehicleType(vehicle.getVehicleType())
                .vehicleModel(vehicle.getVehicleModel())
                .vehicleCapacity(vehicle.getVehicleCapacity())
                .status(vehicle.getStatus())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}