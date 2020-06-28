package io.cutebot.markonimage.web

import io.cutebot.markonimage.service.manage.MarkManageService
import io.cutebot.markonimage.web.model.CreateMarkRequest
import io.cutebot.markonimage.web.model.GetMarkResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping(
        path = ["/api/manage/marks"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MarkManageResource(
        private val service: MarkManageService
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
            @Valid @ModelAttribute mark: CreateMarkRequest
    ): GetMarkResponse {
        val createdMark = service.add(mark.getCreateModel())
        return GetMarkResponse(createdMark)
    }

    @GetMapping("/{mark_id}")
    fun get(
            @PathVariable("mark_id") markId: Int
    ): GetMarkResponse {
        return GetMarkResponse(service.getById(markId))
    }

    @GetMapping("/findByBot")
    fun getByBot(
            @RequestParam("bot_id") botId: Int
    ): List<GetMarkResponse> {
        return service.getAll(botId).map { GetMarkResponse(it) }
    }

}