package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServiceTest {

    private StudentRepository studentXmlRepo = new StudentRepository(new StudentValidator());
    private TemaXMLRepository temaXmlRepo = new TemaXMLRepository(new TemaValidator(), "tema_mock.xml");
    private NotaXMLRepository notaXmlRepo = new NotaXMLRepository(new NotaValidator(), "nota_mock.xml");

    private StudentRepository mockedStudentRepo = mock(StudentRepository.class);
    private TemaXMLRepository mockedTemaXmlRepo = mock(TemaXMLRepository.class);
    private NotaXMLRepository mockedNotaXmlRepo = mock(NotaXMLRepository.class);

    Service service = new Service(studentXmlRepo, temaXmlRepo, notaXmlRepo);

    Service mockedService = new Service(mockedStudentRepo, mockedTemaXmlRepo, mockedNotaXmlRepo);
    // addStudent functionality

    @Test
    void testMockAddStudent() {
        mockedService.saveStudent("2", "nume", 922);
        verify(mockedStudentRepo).save(any());
    }
    @Test
    void testMockAddAssignement() {
        mockedService.saveTema("2", "descriere", 2, 4);
        verify(mockedTemaXmlRepo).save(any());
    }
    @Test
    void testMockAddGradet() {
        when(mockedStudentRepo.findOne("2")).thenReturn(new Student("2", "mock", 922));
        when(mockedTemaXmlRepo.findOne("2")).thenReturn(new Tema("2", "desc", 2, 4));
        mockedService.saveNota("2", "2", 8, 2, "FEED");
        verify(mockedNotaXmlRepo).save(any());
    }

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

    @Test
    void saveGradeNoStudent() {
        Assertions.assertEquals(-1, service.saveNota("studentNuExista","temaNuExista", 8, 2, "NU"));
    }

    @Test
    void saveGradeGood() {
        service.saveStudent("student", "nume", 933);
        service.saveTema("tema", "tema", 4, 2);
        Assertions.assertEquals(0, service.saveNota("student","tema", 8, 2, "NU"));
    }

    @Test
    void saveGradeDeadlineMissedByOne() {
        service.saveStudent("student", "nume", 933);
        service.saveTema("tema", "tema", 4, 2);
        Assertions.assertEquals(0, service.saveNota("student","tema", 8, 5, "NU"));
        service.findAllNote().forEach(nota -> Assertions.assertEquals(5.5, nota.getNota()));
    }

    @Test
    void saveGradeDeadlineMissedByOneDeadlineInvalid() {
        service.saveStudent("student", "nume", 933);
        service.saveTema("tema", "tema", 1, 1);
        Assertions.assertEquals(1, service.saveNota("student","tema", 8, 0, "NU"));
    }

    @Test
    void saveGradeDeadlineMissedByThree() {
        service.saveStudent("student", "nume", 933);
        service.saveTema("tema", "tema", 4, 2);
        Assertions.assertEquals(0, service.saveNota("student","tema", 8, 7, "NU"));
        service.findAllNote().forEach(nota -> Assertions.assertEquals(1, nota.getNota()));
    }

    @Test
    void saveGradeDeadlineMissedByThreeDeadlineInvalid() {
        service.saveStudent("student", "nume", 933);
        service.saveTema("tema", "tema", 1, 1);
        Assertions.assertEquals(1, service.saveNota("student","tema", 8, -1, "NU"));
    }

    @Test
    void saveGradeGoodDeadlineInvalid() {
        service.saveStudent("student", "nume", 933);
        service.saveTema("tema", "tema", 4, 2);
        Assertions.assertEquals(1, service.saveNota("student","tema", 8, -1, "NU"));
    }

    public void addStudent() {
        Assertions.assertEquals(0, service.saveStudent("student", "nume", 933));
    }

    public void addAssignment() {
        Assertions.assertEquals(0, service.saveTema("tema", "tema", 4, 2));
    }

    public void addGrade() {
        Assertions.assertEquals(0, service.saveNota("student","tema", 8, 2, "NU"));
    }

    @Test
    public void addStudent_integration() {
        addStudent();
    }

    @Test
    public void addAssignment_integration() {
        addStudent();
        addAssignment();
    }

    @Test
    public void addGrade_integration() {
        addStudent();
        addAssignment();
        addGrade();
    }
}