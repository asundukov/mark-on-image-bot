package io.cutebot.imagegenerator

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Service
class ImageGenerateExecutor {
    private val executor = ThreadPoolExecutor(2, 8, 1, TimeUnit.MINUTES,
            ArrayBlockingQueue<Runnable?>(100))


    fun execute(
            originalImageUrl: String,
            markImagePath: String,
            imageReceiver: ImageReceiver,
            markPosition: MarkPosition,
            scaleValue: BigDecimal
    ) {
        val task = ImageGenerateTask(
                originalImageUrl = originalImageUrl,
                markImagePath = markImagePath,
                imageReceiver = imageReceiver,
                markPosition = markPosition,
                scaleValue = scaleValue
        )

        executor.execute(task)

    }

}