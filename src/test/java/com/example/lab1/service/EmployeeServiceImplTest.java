package com.example.lab1.service;

import com.example.lab1.dto.EmployeeRequest;
import com.example.lab1.dto.EmployeeResponse;
import com.example.lab1.entity.Employee;
import com.example.lab1.exception.ResourceNotFoundException;
import com.example.lab1.mapper.EmployeeMapper;
import com.example.lab1.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock private EmployeeRepository employeeRepository;
    @Mock private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employeeAlice;
    private Employee employeeSon;

    private EmployeeRequest reqAlice;
    private EmployeeRequest reqSon;

    private EmployeeResponse resAlice;
    private EmployeeResponse resSon;

    private static final LocalDate DOB_ALICE = LocalDate.of(1999, 1, 1);
    private static final LocalDate DOB_SON = LocalDate.of(2000, 5, 2);
    private static final LocalDate DOB_NEW = LocalDate.of(2003, 15, 2);

    @BeforeEach
    void setUp() {
        employeeAlice = new Employee();
        employeeAlice.setId(1);
        employeeAlice.setFirstName("Alice");
        employeeAlice.setLastName("Smith");
        employeeAlice.setBirthDate(DOB_ALICE);
        employeeAlice.setSupervisorId(1);

        employeeSon = new Employee();
        employeeSon.setId(2);
        employeeSon.setFirstName("Son");
        employeeSon.setLastName("Goku");
        employeeSon.setBirthDate(DOB_SON);
        employeeSon.setSupervisorId(1);

        EmployeeRequest reqAlice = new EmployeeRequest("Smith", "Alice", DOB_ALICE, null);
        EmployeeRequest reqBob   = new EmployeeRequest("Goku", "Bob",DOB_SON, 1);

        EmployeeResponse resAlice = new EmployeeResponse(1, "Nguyen", "Alice", DOB_ALICE, null);
        EmployeeResponse resBob   = new EmployeeResponse(2, "Tran", "Bob", DOB_SON, 1);

    }

    //test getAllEmployees
    @Test
    @DisplayName("getAllEmployees: trả về danh sách Response khi có dữ liệu")
    void getAllEmployees_returnsList_whenEmployeesExist() {
        // Arrange
        when(employeeRepository.findAll())
                .thenReturn(List.of(employeeAlice, employeeSon));
        when(employeeMapper.toResponse(employeeAlice)).thenReturn(resAlice);
        when(employeeMapper.toResponse(employeeSon)).thenReturn(resSon);

        // Act
        List<EmployeeResponse> actual = employeeService.getAllEmployees();

        // Assert
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getFirstName()).isEqualTo("Alice");
        assertThat(actual.get(1).getLastName()).isEqualTo("Tran");

        verify(employeeRepository).findAll();
        verify(employeeMapper, times(2)).toResponse(any(Employee.class));
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }
    //                   getEmployeeById
// ===================================================
    @Test
    @DisplayName("getEmployeeById: trả về Response khi tìm thấy")
    void getEmployeeById_returnsResponse_whenFound() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeAlice));
        when(employeeMapper.toResponse(employeeAlice)).thenReturn(resAlice);

        // Act
        EmployeeResponse actual = employeeService.getEmployeeById(1);

        // Assert
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getFirstName()).isEqualTo("Alice");
        verify(employeeRepository).findById(1);
        verify(employeeMapper).toResponse(employeeAlice);
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("getEmployeeById: ném ResourceNotFoundException khi không tồn tại")
    void getEmployeeById_throwsNotFound_whenEmployeeMissing() {
        // Arrange
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.getEmployeeById(999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found 999");

        verify(employeeRepository).findById(999);
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    //                  updateEmployee
// ===============================================
    @Test
    @DisplayName("updateEmployee: cập nhật đúng các field và trả về Response khi tìm thấy")
    void updateEmployee_returnsUpdatedResponse_whenFound() {
        // Arrange
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employeeSon));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeSon);
        when(employeeMapper.toResponse(employeeSon)).thenReturn(resSon);

        EmployeeRequest updateRequest = new EmployeeRequest("Tran-New", "Bob-New", DOB_NEW, 123);

        // Captured = Employee object
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        // Act
        EmployeeResponse updated = employeeService.updateEmployee(2, updateRequest);

        // Assert
        assertThat(updated.getId()).isEqualTo(2);

        verify(employeeRepository).findById(2);
        verify(employeeRepository).save(employeeCaptor.capture()); // lấy parameter thật sự từ save(...)
        verify(employeeMapper).toResponse(employeeSon);
        verifyNoMoreInteractions(employeeRepository, employeeMapper);

        Employee captured = employeeCaptor.getValue();
        assertThat(captured.getLastName()).isEqualTo("Tran-New");
        assertThat(captured.getFirstName()).isEqualTo("Bob-New");
        assertThat(captured.getBirthDate()).isEqualTo(DOB_NEW);
        assertThat(captured.getSupervisorId()).isEqualTo(123);
    }

    @Test
    @DisplayName("updateEmployee: ném ResourceNotFoundException khi không tìm thấy")
    void updateEmployee_throwsNotFound_whenEmployeeMissing() {
        // Arrange
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        EmployeeRequest updateRequest = new EmployeeRequest("A", "B", DOB_NEW, null);

        // Act & Assert
        assertThatThrownBy(() -> employeeService.updateEmployee(999, updateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found 999");

        verify(employeeRepository).findById(999);
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

// ===============================================
//                  deleteEmployee
// ===============================================

    @Test
    @DisplayName("deleteEmployee: xoá khi tồn tại")
    void deleteEmployee_deletes_whenExists() {
        // Arrange
        when(employeeRepository.existsById(1)).thenReturn(true);

        // Act
        employeeService.deleteEmployee(1);

        // Assert
        verify(employeeRepository).existsById(1);
        verify(employeeRepository).deleteById(1);
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("deleteEmployee: ném ResourceNotFoundException khi không tồn tại")
    void deleteEmployee_throwsNotFound_whenMissing() {
        // Arrange
        when(employeeRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> employeeService.deleteEmployee(999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found 999");

        verify(employeeRepository).existsById(999);
        verify(employeeRepository, never()).deleteById(anyInt());
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }
}
