package com.dawidhr.BookLibrary.worker;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonNotificationDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookInfo;
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

    private final String messageExample = "Witaj TestName Kowlaski\n" +
            "Prosimy o zwrócenie poniższych książek.\n" +
            "- 5 zasad\n" +
            "- 10 zasad\n" +
            "- 15 zasad\n" +
            "Pozdrawiamy Biblioteka";

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

    @Test
    public void prepareMessageTest() {
        Person person = new Person();
        person.setFirstName("TestName");
        person.setLastName("Kowlaski");
        List<BookReserved> bookReserves = prepareBookBorrowed();
        String messageResult = bookReservedToLongWorkerTmp.prepareMessage(person, bookReserves);
        assertEquals(messageResult, messageExample);
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
        BookInfo bookInfo = new BookInfo();
        bookInfo.setTitle("5 zasad");
        Book book = new Book();
        book.setBookInfo(bookInfo);
        bookReserved.setBook(book);
        bookReserveds.add(bookReserved);

        BookReserved bookReserved2 = new BookReserved();
        Person person2 = new Person();
        person2.setPersonId(2L);
        bookReserved2.setPerson(person2);
        BookInfo bookInfo2 = new BookInfo();
        bookInfo2.setTitle("10 zasad");
        Book book2 = new Book();
        book2.setBookInfo(bookInfo2);
        bookReserved2.setBook(book2);
        bookReserveds.add(bookReserved2);

        BookReserved bookReserved3 = new BookReserved();
        Person person3 = new Person();
        person3.setPersonId(3L);
        bookReserved3.setPerson(person3);
        BookInfo bookInfo3 = new BookInfo();
        bookInfo3.setTitle("15 zasad");
        Book book3 = new Book();
        book3.setBookInfo(bookInfo3);
        bookReserved3.setBook(book3);
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
        protected String prepareMessage(Person person, List<BookReserved> bookReserves) {
            return super.prepareMessage(person, bookReserves);
        }
    }
}