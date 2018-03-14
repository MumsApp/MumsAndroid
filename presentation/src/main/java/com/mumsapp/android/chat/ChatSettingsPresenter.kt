package com.mumsapp.android.chat

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import javax.inject.Inject

class ChatSettingsPresenter : BasePresenter<ChatSettingsView> {

    @Inject
    constructor() {

    }

    override fun start() {
        val recipients =  createTemplateRecipients()
        view?.showItems(recipients)
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    private fun createTemplateRecipients(): List<TemplateChatRecipient> {
        val list = ArrayList<TemplateChatRecipient>()
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))
        list.add(TemplateChatRecipient("Layla Patrickson"))

        return list
    }
}