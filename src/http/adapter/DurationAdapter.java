package http.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationAdapter extends TypeAdapter<Duration> {

    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        jsonWriter.value(duration.toMinutes());

//        if (duration != null) {
//            jsonWriter.value(duration.toMinutes());
//        } else {
//            jsonWriter.value((String) null);
//        }

    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        return Duration.ofMinutes(jsonReader.nextInt());

//        if (jsonReader.nextString() != "null") {
//            return Duration.ofMinutes(jsonReader.nextInt());
//        } else {
//            return null;
//        }

    }
}
