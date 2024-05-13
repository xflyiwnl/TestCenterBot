package me.xflyiwnl.testcenter.message;

import me.xflyiwnl.testcenter.TestCenter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class DevMessage implements IMessage {

    @Override
    public String startWith() {
        return "/разработчик";
    }

    @Override
    public void onMessage(Update update, long chatId) {
        TestCenter.getInstance().getLongPoll().sendMessage(chatId, Arrays.asList(
                "Бот разработан - @xflyiwnl"
        ));
    }

}
