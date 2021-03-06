package io.cutebot.telegram.handlers

import io.cutebot.telegram.exception.TgBotNotFoundException
import org.slf4j.LoggerFactory

class LongPollProcess(
        private val longPollTimeout: Int,
        private val botHandler: TgBotLongPollHandler,
        private val bot: BaseBot
) : Thread() {
    private var ok = true
    override fun run() {
        var offset = 0
        while (ok && !this.isInterrupted) {
            try {
                val tgResponseUpdate = botHandler.getMessages(bot.token, offset, longPollTimeout, 50)
                if (!ok) {
                    return
                }
                for (tgUpdate in tgResponseUpdate.result) {
                    offset = tgUpdate.updateId + 1
                    botHandler.handle(bot, tgUpdate)
                }
                if (interrupted()) {
                    ok = false
                }
            } catch (e: TgBotNotFoundException) {
                error("Bot not found via tg request. Dismissed from request pool. Bot {}. Token: {}",
                        bot.id, bot.token.subSequence(0, 15))
                return
            } catch (e: Exception) {
                error(e.message, e)
                try {
                    sleep(2000)
                } catch (e1: InterruptedException) {
                    ok = false
                }
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(LongPollProcess::class.java)

        fun error(message: String?, vararg o: Any) {
            try {
                log.error(message, o)
            } catch (e: Exception) {
                println(message ?: "Unknown error")
                println(e.message)
            }
        }
    }
}
