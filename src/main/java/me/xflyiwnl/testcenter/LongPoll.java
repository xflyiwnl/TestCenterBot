package me.xflyiwnl.testcenter;

import me.xflyiwnl.testcenter.message.IMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LongPoll extends TelegramLongPollingBot {

    private String botName = "айди бота";
    private String token = "ключ";

    private Long chatId = -1L; // беседа

    private int lastMessage = -1;

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage()) return;
        if (update.getMessage().getText() == null) return;

        Message message = update.getMessage();
        long chatId = message.getChatId();

        IMessage iMessage = null;

        for (IMessage msg : TestCenter.getInstance().getMessages()) {
            if (message.getText().startsWith(msg.startWith())) {
                iMessage = msg;
                break;
            }
        }

        if (iMessage == null) {
            if (update.getMessage().isUserMessage()) return;
            DeleteMessage deleteMessage = DeleteMessage.builder()
                    .messageId(message.getMessageId())
                    .chatId(chatId)
                    .build();

            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            iMessage.onMessage(update, chatId);
        }

    }

    public int sendMessage(long chatId, List<String> message) {

        StringBuilder sb = new StringBuilder();
        for (String text : message) {
            sb.append(text).append("\n");
        }

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(sb.toString())
                .build();

        try {
            Message msg = execute(sendMessage);
            return msg.getMessageId();
        } catch (TelegramApiException e) {
            System.out.println("Чат: " + chatId + ", не существует!");
            return -1;
        }

    }

    public boolean broadcast(List<String> message) {

        StringBuilder sb = new StringBuilder();
        for (String text : message) {
            sb.append(text).append("\n");
        }

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(sb.toString())
                .build();
        try {
            execute(sendMessage);
            return true;
        } catch (TelegramApiException e) {
            System.out.println("Чат: " + chatId + ", не существует!");
            return false;
        }

    }

    public boolean delete(int msgId) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(msgId)
                .build();
        try {
            execute(deleteMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void changeLast(List<String> message) {
        StringBuilder sb = new StringBuilder();
        for (String text : message) {
            sb.append(text).append("\n");
        }

        int lastMessage = sendMessage(chatId, message);
        CompletableFuture.runAsync(() -> {
            delete(lastMessage - 1);
        });

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
