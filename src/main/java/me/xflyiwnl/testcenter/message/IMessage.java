package me.xflyiwnl.testcenter.message;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IMessage {

    String startWith();

    void onMessage(Update update, long chatId);

}
