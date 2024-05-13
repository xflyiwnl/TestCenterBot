package me.xflyiwnl.testcenter.task;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.xflyiwnl.testcenter.TestCenter;
import me.xflyiwnl.testcenter.entity.Seat;
import me.xflyiwnl.testcenter.entity.University;
import me.xflyiwnl.testcenter.request.GetRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestTask implements Task {

    private TestCenter instance;

    public RequestTask() {
        instance = TestCenter.getInstance();
    }

    @Override
    public void execute() {

        Map<University, List<Seat>> seats = new HashMap<>();

        for (University university : instance.getUniversities()) {

            List<Seat> seatList = parseUniversity(university);

            if (!seatList.isEmpty()) {
                seats.put(university, seatList);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        if (seats.isEmpty()) return;

        LocalDateTime time = LocalDateTime.now();
        ZonedDateTime zonedUTC = time.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Asia/Almaty")).minusHours(1);

        List<String> broadcast = new ArrayList<>();
        broadcast.add("Свободные места на момент " + DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy").format(zonedIST));

        seats.forEach((university, seatList) -> {
            broadcast.add(" ");
            broadcast.add(university.getName() + ":");
            seatList.forEach(seat -> {
                broadcast.add(" > " + seat.getDate() + "    |    " + seat.getFreeSeats());
            });
        });

        instance.getLongPoll().changeLast(broadcast);

    }

    public List<Seat> parseUniversity(University university) {

        List<Seat> seatList = new ArrayList<>();

        try {
            GetRequest request = new GetRequest()
                    .url(university.getUrl());

            String response = request.send().body();
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();


            for (JsonElement jsonElement : jsonObject.getAsJsonArray("items")) {
                JsonObject asObject = jsonElement.getAsJsonObject();

                String day = asObject.get("testDate").getAsString();
                int freeSeats = asObject.get("freePlaceCount").getAsInt();

                if (freeSeats <= 0) continue;

                seatList.add(new Seat(university, day, freeSeats));

            }

            System.out.println("parsing: " + university.getName() + " (" + seatList.size() + ")");

        } catch (Exception e) {
            System.out.println("i can't parse " + university.getName());
        }

        return seatList;
    }

}
