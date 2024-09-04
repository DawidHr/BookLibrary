package com.dawidhr.BookLibrary.worker;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonNotificationDAO;
import com.dawidhr.BookLibrary.model.BookReserved;
import com.dawidhr.BookLibrary.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BookReservedToLongWorkerTest {

    BookReservedToLongWorkerTmp bookReservedToLongWorkerTmp;
    @Mock
    BookReservedDAO bookReservedDAO;
    @Mock
    PersonNotificationDAO personNotificationDAO;

    @BeforeEach
    void setUp() {
        bookReservedToLongWorkerTmp = new BookReservedToLongWorkerTmp(bookReservedDAO, personNotificationDAO);
    }

    @Test
    public void checkIfNotificationWasSendTest() {
        long personId = 1;
        when(personNotificationDAO.isPersonNotifiedIn7Days(personId)).thenReturn(Boolean.FALSE);
        assertFalse(bookReservedToLongWorkerTmp.checkIfNotificationWasSend(personId));
        when(personNotificationDAO.isPersonNotifiedIn7Days(personId)).thenReturn(Boolean.TRUE);
        assertTrue(bookReservedToLongWorkerTmp.checkIfNotificationWasSend(personId));
    }

    @Test
    public void getAllPersonIdsWhereSendNotificationTest() {
        when(bookReservedDAO.getAllBooksBorrowedAtLeast2MonthsAgo()).thenReturn(new ArrayList<>());
        assertTrue(bookReservedToLongWorkerTmp.getAllPersonIdsWhereSendNotification().size() == 0);

        when(bookReservedDAO.getAllBooksBorrowedAtLeast2MonthsAgo()).thenReturn(prepareBookBorrowed());
        assertTrue(bookReservedToLongWorkerTmp.getAllPersonIdsWhereSendNotification().size() == 3);

        when(bookReservedDAO.getAllBooksBorrowedAtLeast2MonthsAgo()).thenReturn(prepareBookBorrowedWithDuplicat());
        assertTrue(bookReservedToLongWorkerTmp.getAllPersonIdsWhereSendNotification().size() == 2);

    }

    private List<BookReserved> prepareBookBorrowedWithDuplicat() {
        List<BookReserved> bookReserveds = new ArrayList<>();

        BookReserved bookReserved = new BookReserved();
        Person person = new Person();
        person.setPersonId(1L);
        bookReserved.setPerson(person);
        bookReserveds.add(bookReserved);

        BookReserved bookReserved2 = new BookReserved();
        Person person2 = new Person();
        person2.setPersonId(2L);
        bookReserved2.setPerson(person2);
        bookReserveds.add(bookReserved2);

        BookReserved bookReserved3 = new BookReserved();
        Person person3 = new Person();
        person3.setPersonId(1L);
        bookReserved3.setPerson(person3);
        bookReserveds.add(bookReserved3);
        return bookReserveds;
    }

    private List<BookReserved> prepareBookBorrowed() {
        List<BookReserved> bookReserveds = new ArrayList<>();

        BookReserved bookReserved = new BookReserved();
        Person person = new Person();
        person.setPersonId(1L);
        bookReserved.setPerson(person);
        bookReserveds.add(bookReserved);

        BookReserved bookReserved2 = new BookReserved();
        Person person2 = new Person();
        person2.setPersonId(2L);
        bookReserved2.setPerson(person2);
        bookReserveds.add(bookReserved2);

        BookReserved bookReserved3 = new BookReserved();
        Person person3 = new Person();
        person3.setPersonId(3L);
        bookReserved3.setPerson(person3);
        bookReserveds.add(bookReserved3);
        return bookReserveds;
    }


    class BookReservedToLongWorkerTmp extends BookReservedToLongWorker {

        public BookReservedToLongWorkerTmp(BookReservedDAO bookReservedDAO, PersonNotificationDAO personNotificationDAO) {
            super(bookReservedDAO, personNotificationDAO);
        }

        protected Set<Long> getAllPersonIdsWhereSendNotification() {
            return super.getAllPersonIdsWhereSendNotification();
        }

        protected boolean checkIfNotificationWasSend(long personId) {
            return super.checkIfNotificationWasSend(personId);
        }
    }
}