package com.example.logs.services;
import com.example.logs.constant.Constants;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.exceptions.IncorrectPasswordException;
import com.example.logs.exceptions.StaffNotFoundException;
import com.example.logs.exceptions.UsernameAlreadyExistException;
import com.example.logs.exceptions.VisitorNotFoundException;
import com.example.logs.models.Staff;
import com.example.logs.models.Visitor;
import com.example.logs.models.VisitorLogs;
import com.example.logs.repositories.StaffRepository;
import com.example.logs.repositories.VisitorLogsRepository;
import com.example.logs.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private VisitorLogsRepository visitorLogsRepository;

    @Autowired
    private StaffService staffService;

    public String addNewVisitor(Visitor visitor) {
        Visitor visitorByUsername =visitorRepository.findVisitorByUserName(visitor.getUserName());
        Staff staffByUsername = staffRepository.findStaffByUserName(visitor.getUserName());

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

        visitorRepository.save(visitor);

        return Constants.SIGNUP;
    }

    public List<ServiceResponseDto> getAllVisitors(){
        List<ServiceResponseDto> listOfAllVisitors = new ArrayList<>();
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        List<Visitor> allVisitorsSaved = visitorRepository.findAll();

        for(int i=0; i<allVisitorsSaved.size(); i++){
            Visitor visitor = allVisitorsSaved.get(i);
            serviceResponseDto.setName(visitor.getName());
            serviceResponseDto.setEmail(visitor.getEmail());
            serviceResponseDto.setPhoneNumber(visitor.getPhoneNumber());
            serviceResponseDto.setAddress(visitor.getAddress());

            listOfAllVisitors.add(serviceResponseDto);
        }

        return listOfAllVisitors;
    }

    public Visitor getVisitor(Long visitorId){
        Visitor visitor = visitorRepository.findById(visitorId).orElseThrow(() -> new VisitorNotFoundException("visitor not found"));

        return visitor;
    }

    public Visitor getVisitorByUsernameAndPassword(String username, String password){
        Visitor visitorByUserName = visitorRepository.findVisitorByUserName(username);
        Visitor visitorByUserNameAndPassword = null;

        try {
            if (password.equals(visitorByUserName.getPassword())) {
                visitorByUserNameAndPassword = visitorRepository.findVisitorByUserNameAndPassword(username, password);
            } else {
                throw new IncorrectPasswordException("incorrect password");
            }
        }catch (NullPointerException ex){

        }

        return visitorByUserNameAndPassword;
    }

    public String logsNewVisit(Long visitorId, Long staffId, VisitorLogs visitorLogs){
        Visitor visitor = getVisitor(visitorId);
        Staff staff = staffService.getStaff(staffId);

        try {
            if (staff.getUserName() == null) {
                throw new StaffNotFoundException(staff + " not found");
            }
        }catch (NullPointerException ex){

        }

        try {
            if (visitor.getUserName() == null) {
                throw new VisitorNotFoundException(visitor + " not found");
            }
        }catch (NullPointerException ex){

        }

        visitorLogs.setVisitor(visitor);
        visitorLogs.setStaff(staff);
        visitorLogs.getVisitor().setStaff(staff);
        visitorLogsRepository.save(visitorLogs);

        return Constants.LOG_CREATED;
    }
}
