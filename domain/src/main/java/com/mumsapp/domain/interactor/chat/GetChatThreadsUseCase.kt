package com.mumsapp.domain.interactor.chat

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.chat.ChatThreadResponse
import com.mumsapp.domain.model.chat.TemplateChatMessage
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.chat.TemplateChatThread
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable
import java.util.*

class GetChatThreadsUseCase(val schedulerProvider: SchedulerProvider) : BaseUseCase<BaseRequest, ChatThreadResponse>(schedulerProvider) {

    private var lastId = "0"


    override fun createUseCaseObservable(param: BaseRequest): Observable<ChatThreadResponse> {
        val response = ChatThreadResponse(mockedThreadsList().toList())
        return Observable.defer { Observable.just(response) }
    }

    private fun mockedThreadsList() : Array<TemplateChatThread> = this[createThreadMock(), createThreadMock(), createThreadMock(),
            createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(),
            createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock()] as Array<TemplateChatThread>

    private fun createThreadMock() : TemplateChatThread {
        lastId = if (lastId == "0") "1" else "0" 
        val recipient = TemplateChatRecipient(lastId, "John Space")

        return TemplateChatThread(recipient, "17:56", mockedMessagesList(recipient).asList())
    }

    private fun mockedMessagesList(recipient: TemplateChatRecipient) : Array<TemplateChatMessage> = this[createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient),
            createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient),
            createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient), createMessageMock(recipient)] as Array<TemplateChatMessage>

    private fun createMessageMock(recipient: TemplateChatRecipient) : TemplateChatMessage {
        return TemplateChatMessage("1", "Template message", Date(), recipient)
    }

    operator fun get(vararg array: TemplateChatThread) = array

    operator fun get(vararg array: TemplateChatMessage) = array
}