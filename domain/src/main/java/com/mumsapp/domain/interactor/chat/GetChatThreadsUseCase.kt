package com.mumsapp.domain.interactor.chat

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.chat.ChatThreadResponse
import com.mumsapp.domain.model.chat.TemplateChatMessage
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.chat.TemplateChatThread
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetChatThreadsUseCase(val schedulerProvider: SchedulerProvider) : BaseUseCase<BaseRequest, ChatThreadResponse>(schedulerProvider) {

    private var isOwnMessages: Boolean = true


    override fun createUseCaseObservable(param: BaseRequest): Observable<ChatThreadResponse> {
        val response = ChatThreadResponse(mockedThreadsList().toList())
        return Observable.defer { Observable.just(response) }
    }

    private fun mockedThreadsList() : Array<TemplateChatThread> = this[createThreadMock(), createThreadMock(), createThreadMock(),
            createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(),
            createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock(), createThreadMock()] as Array<TemplateChatThread>

    private fun createThreadMock() : TemplateChatThread {
        val recipient = TemplateChatRecipient("John Fade")

        return TemplateChatThread(recipient, "17:56", mockedMessagesList().asList())
    }

    private fun mockedMessagesList() : Array<TemplateChatMessage> = this[createMessageMock(), createMessageMock(), createMessageMock(),
            createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(),
            createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock(), createMessageMock()] as Array<TemplateChatMessage>

    private fun createMessageMock() : TemplateChatMessage {
        isOwnMessages = !isOwnMessages
        return TemplateChatMessage("Template message", isOwnMessages)
    }

    operator fun get(vararg array: TemplateChatThread) = array

    operator fun get(vararg array: TemplateChatMessage) = array
}