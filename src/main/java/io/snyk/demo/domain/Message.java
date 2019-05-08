package io.snyk.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Message {

    @Id
    private String id;
    private String poster;
    private String body;

    public Message(){
        this.id = UUID.randomUUID().toString();
    }

    public Message(String poster, String text) {
        this.id = UUID.randomUUID().toString();
        this.poster = poster;
        this.body = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String message) {
        this.body = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", poster='" + poster + '\'' +
                ", message='" + body + '\'' +
                '}';
    }
}
