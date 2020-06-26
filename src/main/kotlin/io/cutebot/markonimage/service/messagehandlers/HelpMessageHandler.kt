package io.cutebot.markonimage.service.messagehandlers

import io.cutebot.telegram.handlers.BaseBot
import io.cutebot.telegram.tgmodel.TgUser

class HelpMessageHandler : MessageHandler {

    private val defaultMessage = "/start - begin using bot\n" +
            "/help - summon this message\n" +
            "/marks - show all marks\n" +
            "/about - show about info\n"

    override fun handle(bot: BaseBot, params: String, chatId: Long, user: TgUser): String {
        return defaultMessage
    }
}