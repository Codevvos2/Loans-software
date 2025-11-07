package Model;

import DAO.EmprestimoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestEmprestimo {

    private EmprestimoDAO daoMock;
    private Emprestimo emprestimo;

    @BeforeEach
    void setUp() throws Exception {
        daoMock = Mockito.mock(EmprestimoDAO.class);
        emprestimo = new Emprestimo();

        Field daoField = Emprestimo.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(emprestimo, daoMock);
    }

    @Test
    void testGettersAndSetters() {
        emprestimo.setDataloc("2025-10-01");
        emprestimo.setDatadev("2025-11-01");
        emprestimo.setStatus("ativo");

        assertEquals("2025-10-01", emprestimo.getDataloc());
        assertEquals("2025-11-01", emprestimo.getDatadev());
        assertEquals("ativo", emprestimo.getStatus());
    }
}