package me.xflyiwnl.testcenter;

import me.xflyiwnl.starter.App;
import me.xflyiwnl.testcenter.entity.University;
import me.xflyiwnl.testcenter.message.DevMessage;
import me.xflyiwnl.testcenter.message.IMessage;
import me.xflyiwnl.testcenter.message.StatusMessage;
import me.xflyiwnl.testcenter.task.RequestTask;
import me.xflyiwnl.testcenter.util.AuthUtil;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class TestCenter implements App {

    private static TestCenter instance;
    private LongPoll longPoll;
    private long threadTime = 60L;

    private List<University> universities = new ArrayList<>();
    private List<IMessage> messages = new ArrayList<>();

    @Override
    public void start() {
        instance = this;

        AuthUtil.setupFile();

        setupMessages();

        startPolling();
        setupUniversities();

        startThread();

    }

    public void setupMessages() {
        messages.add(new StatusMessage());
        messages.add(new DevMessage());
    }

    public void startThread() {
        while (true) {

            RequestTask task = new RequestTask();
            task.execute();

            try {
                Thread.sleep(threadTime * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setupUniversities() {
        universities.add(new University("(г. Алматы) Әл-Фараби ат. ҚазҰУ", "https://app.testcenter.kz/ent/student/app/api/v1/app/season/items/29/app-type/items/1/test-org/items/1118/test-period/items?student-test-id=2964997&is-emergency-school=false"));
        universities.add(new University("(Алматинская обл.) село Шамалған", "https://app.testcenter.kz/ent/student/app/api/v1/app/season/items/29/app-type/items/1/test-org/items/1131/test-period/items?student-test-id=2964997&is-emergency-school=false"));
    }

    public void startPolling() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            longPoll = new LongPoll();
            botsApi.registerBot(longPoll);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IMessage> getMessages() {
        return messages;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public LongPoll getLongPoll() {
        return longPoll;
    }

    public static TestCenter getInstance() {
        return instance;
    }
}
