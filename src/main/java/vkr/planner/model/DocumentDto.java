package vkr.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto extends Plan{

    private String id;
    private List<Event> eventList = new ArrayList<>();
    private String description;


    public DocumentDto(String name) {
        super(name);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "DocumentDto{" +
                "id=" + id +
                ", eventList=" + eventList +
                ", description='" + description + '\'' +
                '}';
    }

    public void convertToDto(Map<Integer, List<String>> data){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        data.remove(0);
        data.values().forEach(value ->
                {
                    try {
                        this.eventList.add(new Event(
                                (int) Double.parseDouble(value.get(0)),
                                value.get(1),
                                Duration.ofDays((long) Double.parseDouble(value.get(2))),
                                Boolean.parseBoolean(value.get(3)),
                                (int) Double.parseDouble(value.get(4)),
                                formatter.parse(value.get(5)),
                                (int) Double.parseDouble(value.get(6))
                        )
                );
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
