package com.stfalcon.chatkit.commons.models

import java.util.*

interface IMessage {

    /**
     * Returns message identifier
     *
     * @return the message id
     */
    fun getId(): String

    /**
     * Returns message text
     *
     * @return the message text
     */
    fun getText(): String

    /**
     * Returns message author. See the [IUser] for more details
     *
     * @return the message author
     */
    fun getUser(): IUser

    /**
     * Returns message creation date
     *
     * @return the message creation date
     */
    fun getCreatedAt(): Date
}