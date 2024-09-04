package com.dawidhr.BookLibrary.worker;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonNotificationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



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