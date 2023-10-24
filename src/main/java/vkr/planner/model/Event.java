package vkr.planner.model;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.util.Date;
public class Event extends Task{
    private int id;
    @DateTimeFormat
    private Date date; // дата начала
    private boolean passed; // признак завершения события
    public Event(int id, String name, Duration duration, boolean isBlocker, int costs, Date date, int order) {
        super(name, order, duration, isBlocker, costs);
        this.id = id;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", passed=" + passed +
                ", name='" + super.getName() +
                ", order=" + super.getOrder() +
                ", duration=" + super.getDuration() +
                ", isBlocker=" + super.isBlocker() +
                ", costs=" + super.getCosts() +
                '}';
    }
}
