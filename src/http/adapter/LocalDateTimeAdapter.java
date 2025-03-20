package http.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static tasks.Task.START_TIME_FORMAT;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    // "01.03.2025 11:11"

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        jsonWriter.value(localDateTime.format(dtf));
//        if (localDateTime != null) {
//            jsonWriter.value(localDateTime.format(dtf));
//        }
//        else {
//            jsonWriter.value((String) null);
//        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString(), dtf);
//        if (jsonReader.nextString() != "null") {
//            return LocalDateTime.parse(jsonReader.nextString(), dtf);
//        } else {
//            return null;
//        }
    }
}
