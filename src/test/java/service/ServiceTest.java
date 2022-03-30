package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

class ServiceTest {

    private StudentRepository studentXmlRepo = new StudentRepository(new StudentValidator());
    private TemaXMLRepository temaXmlRepo = new TemaXMLRepository(new TemaValidator(), "tema_mock.xml");
    private NotaXMLRepository notaXmlRepo = new NotaXMLRepository(new NotaValidator(), "nota_mock.xml");

    Service service = new Service(studentXmlRepo, temaXmlRepo, notaXmlRepo);
    // addStudent functionality

    // Group tests
    @Test
    void saveStudentOkay() {
        Assertions.assertEquals(0, service.saveStudent("student1", "Alex", 923));
    }
    @Test
    void saveStudentBadLower() {
        Assertions.assertEquals(1, service.saveStudent("student1", "Alex", 3));
    }
    @Test
    void saveStudentBadUpper() {
        Assertions.assertEquals(1, service.saveStudent("student1", "Alex", 1032));
    }

    // ID tests
    @Test
    void saveStudentIDNull() { Assertions.assertEquals(1, service.saveStudent(null, "Alex", 123)); }
    @Test
    void saveStudentIDEmpty() { Assertions.assertEquals(1, service.saveStudent("", "Alex", 123)); }

    // Name tests
    @Test
    void saveStudentNameNull() { Assertions.assertEquals(1, service.saveStudent("student1", null, 123)); }
    @Test
    void saveStudentNameEmpty() { Assertions.assertEquals(1, service.saveStudent("student1", "", 123)); }

    // addAssignment functionality
    @Test
    void saveAssignmentIDnull() { Assertions.assertEquals(1, service.saveTema(null, "desc1", 12, 12)); }

    @Test
    void saveAssignmentIDempty() { Assertions.assertEquals(1, service.saveTema("", "desc2", 10, 10)); }

}