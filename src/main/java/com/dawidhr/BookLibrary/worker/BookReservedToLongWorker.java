package com.dawidhr.BookLibrary.worker;

import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonNotificationDAO;
import com.dawidhr.BookLibrary.model.BookReserved;
import com.dawidhr.BookLibrary.model.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookReservedToLongWorker {
    @Autowired
    BookReservedDAO bookReservedDAO;
    @Autowired
    PersonNotificationDAO personNotificationDAO;

    public BookReservedToLongWorker(BookReservedDAO bookReservedDAO, PersonNotificationDAO personNotificationDAO) {
        this.bookReservedDAO = bookReservedDAO;
        this.personNotificationDAO = personNotificationDAO;
    }

    public void process() {
        // TODO checkIf Worker is enabled and get config for this worker
        Set<Long> personIds = getAllPersonIdsWhereSendNotification();
        for (long personId: personIds) {
            processSendNotification(personId);
        }
    }

    private void processSendNotification(long personId) {
        if (checkIfNotificationWasSend(personId)) {
            return;
        }
        sendNotification(personId);
    }

    protected Set<Long> getAllPersonIdsWhereSendNotification() {
        Set<Long> personIds = new HashSet<>();
        List<BookReserved> books = bookReservedDAO.getAllBooksBorrowedAtLeast2MonthsAgo();
        books.forEach(bookReserved -> personIds.add(bookReserved.getPerson().getPersonId()));
        return personIds;
    }

    protected void sendNotification(long personId) {
        List<BookReserved> bookReserves = bookReservedDAO.getAllBooksBorrowedAtLeast2MonthsAgoByPersonId(personId);
        String message = prepareMessage(new Person(), bookReserves);
        sendEmail(new Person(), message);
    }

    protected void sendEmail(Person person, String message) {
        //TODO add send email
    }

    protected String prepareMessage(Person person, List<BookReserved> bookReserves) {
        //TODO change message
        String message = "Witaj "+ person.getFirstName() + " "+person.getLastName()+"\n";
        message += "Prosimy o zwrócenie poniższych książek.\n";
        for (BookReserved book : bookReserves) {
            message += "- "+book.getBook().getBookInfo().getTitle()+"\n";
        }
        message += "Pozdrawiamy Biblioteka";
        return message;
    }

    protected boolean checkIfNotificationWasSend(long personId) {
        return personNotificationDAO.isPersonNotifiedIn7Days(personId);
    }
}
