package bonus;

import java.time.LocalDateTime;
import java.util.Set;

public class ReadingList {
    
    private String name;
    private LocalDateTime creationTimestamp;
    private Set<Book> books;

    public ReadingList(String name,LocalDateTime timestamp,Set<Book> books){
        this.name=name;
        this.creationTimestamp=timestamp;
        this.books=books;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("==="+name+"====\n\nTimeStamp : "+creationTimestamp+"\n\nBooks : ");
        books.stream().forEach(book -> sb.append("\nid:"+book.id+" Author:"+book.getAuthor()+" Genre:"+book.getGenre()));

        return sb.toString();
    }

}
