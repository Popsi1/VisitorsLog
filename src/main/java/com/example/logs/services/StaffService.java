package com.example.logs.services;
import com.example.logs.constant.Constants;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.exceptions.IncorrectPasswordException;
import com.example.logs.exceptions.StaffNotFoundException;
import com.example.logs.exceptions.UsernameAlreadyExistException;
import com.example.logs.models.Staff;
import com.example.logs.models.Visitor;
import com.example.logs.repositories.StaffRepository;
import com.example.logs.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    public String addNewStaff(Staff staff) {
        Staff staffByUsername = staffRepository.findStaffByUserName(staff.getUserName());
        Visitor visitorByUsername =visitorRepository.findVisitorByUserName(staff.getUserName());
        try {
            if (staffByUsername.getUserName() != null) {
                throw new UsernameAlreadyExistException(staffByUsername.getUserName() + " already exist");
            }
        }catch (NullPointerException ex){

        }

        try {
            if (visitorByUsername.getUserName() != null) {
                throw new UsernameAlreadyExistException(visitorByUsername.getUserName() + " already exist");
            }
        }catch (NullPointerException ex){

        }

        staffRepository.save(staff);

        return Constants.SIGNUP;
    }



    public List<ServiceResponseDto> getAllStaff(){
        List<ServiceResponseDto> listOfServiceResponseDto = new ArrayList<>();
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        List<Staff> allStaffSaved = staffRepository.findAll();

        for(int i=0; i<allStaffSaved.size(); i++){
            Staff staff = allStaffSaved.get(i);
            serviceResponseDto.setName(staff.getName());
            serviceResponseDto.setEmail(staff.getEmail());
            serviceResponseDto.setPhoneNumber(staff.getPhoneNumber());
            serviceResponseDto.setAddress(staff.getAddress());

            listOfServiceResponseDto.add(serviceResponseDto);
        }

        return listOfServiceResponseDto;
    }



    public Staff getStaff(Long staffId){
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new StaffNotFoundException("staff not found"));

        return staff;
    }



    public Staff getStaffByUsernameAndPassword(String username, String password){
        Staff staffByUserName = staffRepository.findStaffByUserName(username);
        Staff staffByUserNameAndPassword = null;

        try {
            if (password.equals(staffByUserName.getPassword())) {
                staffByUserNameAndPassword = staffRepository.findStaffByUserNameAndPassword(username, password);
            } else {
                throw new IncorrectPasswordException("incorrect password");
            }
        }catch (NullPointerException ex){

        }

        return staffByUserNameAndPassword;
    }

}
