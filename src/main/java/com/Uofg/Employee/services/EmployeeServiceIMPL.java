package com.Uofg.Employee.services;

import com.Uofg.Employee.entity.EmployeeEntity;
import com.Uofg.Employee.model.Employee;
import com.Uofg.Employee.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceIMPL implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeServiceIMPL(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity =new EmployeeEntity();
        BeanUtils.copyProperties(employee,employeeEntity);
        employeeRepository.save(employeeEntity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        List<Employee > employees=employeeEntities.stream().map(emp ->new Employee(emp.getId(),emp.getFirstName(),emp.getLastName(),emp.getEmailId())).collect(Collectors.toList());
        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        EmployeeEntity employee =employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employee =employeeRepository.findById(id).get();
        Employee emp = new Employee();
        BeanUtils.copyProperties(employee,emp);
        return emp;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeentity =employeeRepository.findById(id).get();
        employeentity.setEmailId(employee.getEmailId());
        employeentity.setFirstName(employee.getFirstName());
        employeentity.setLastName(employee.getLastName());
        employeeRepository.save(employeentity);
        return employee;

    }
}
