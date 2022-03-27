package com.example.logs.dtos;
import com.example.logs.models.Staff;
import com.example.logs.models.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDto {

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    public static ServiceResponseDto fromStaff(Staff staff) {
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        serviceResponseDto.setName(staff.getName());
        serviceResponseDto.setEmail(staff.getEmail());
        serviceResponseDto.setPhoneNumber(staff.getPhoneNumber());
        serviceResponseDto.setAddress(staff.getAddress());

        return serviceResponseDto;
    }

    public static ServiceResponseDto fromVisitor(Visitor visitor) {
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        serviceResponseDto.setName(visitor.getName());
        serviceResponseDto.setEmail(visitor.getEmail());
        serviceResponseDto.setPhoneNumber(visitor.getPhoneNumber());
        serviceResponseDto.setAddress(visitor.getAddress());

        return serviceResponseDto;
    }
}
