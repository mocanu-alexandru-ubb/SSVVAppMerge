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
}