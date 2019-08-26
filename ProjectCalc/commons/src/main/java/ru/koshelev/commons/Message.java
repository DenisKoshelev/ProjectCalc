package ru.koshelev.commons;

import java.io.Serializable;

/**
 * Класс для хранения сообщения, при помощи которого общаются клиент и сервер
 */
public class Message implements Serializable {
    private String topic;
    private String text;
    private Object object;


    public Message(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }

    public Message(String topic, Object object) {
        this.topic = topic;
        this.object = object;
    }

    public Message(String topic, String text, Object object) {
        this.topic = topic;
        this.text = text;
        this.object = object;
    }

    public String getTopic() {
        return topic;
    }

    public String getText() {
        return text;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Message{" +
                "topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                ", object=" + object +
                '}';
    }
}
