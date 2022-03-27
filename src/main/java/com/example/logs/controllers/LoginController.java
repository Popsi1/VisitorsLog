package com.example.logs.controllers;
import com.example.logs.dtos.LoginDto;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.models.Staff;
import com.example.logs.models.Visitor;
import com.example.logs.services.StaffService;
import com.example.logs.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private VisitorService visitorService;

    @PostMapping("/login")
    public ServiceResponseDto login(@RequestBody LoginDto loginDto) throws Exception {
        Staff staff = staffService.getStaffByUsernameAndPassword(loginDto.getUserName(), loginDto.getPassword());
        Visitor visitor = visitorService.getVisitorByUsernameAndPassword(loginDto.getUserName(), loginDto.getPassword());
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();

        try {
            if (staff != null) {
                serviceResponseDto.setName(staff.getName());
                serviceResponseDto.setEmail(staff.getEmail());
                serviceResponseDto.setPhoneNumber(staff.getPhoneNumber());
                serviceResponseDto.setAddress(staff.getAddress());
            } else if (visitor != null) {
                serviceResponseDto.setName(visitor.getName());
                serviceResponseDto.setEmail(visitor.getEmail());
                serviceResponseDto.setPhoneNumber(visitor.getPhoneNumber());
                serviceResponseDto.setAddress(visitor.getAddress());
            }else
                throw new Exception("user not found");

        }catch (NullPointerException ex){

        }

        return serviceResponseDto;
    }
}
