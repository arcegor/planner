package vkr.planner.model.type1;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import vkr.planner.model.type2.Task;

import java.time.Duration;
import java.util.Date;
@Getter
public class Event extends Task {
    private int id;
    @DateTimeFormat
    private Date date; // дата начала
    private boolean passed; // признак завершения события
    public Event(int id, String name, Duration duration, boolean isBlocker, int costs, Date date, int order) {
        super(name, order, duration, isBlocker, costs);
        this.id = id;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
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
