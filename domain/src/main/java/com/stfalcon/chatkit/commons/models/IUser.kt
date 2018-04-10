package com.stfalcon.chatkit.commons.models

interface IUser {

    /**
     * Returns the user's id
     *
     * @return the user's id
     */
    fun getId(): String

    /**
     * Returns the user's name
     *
     * @return the user's name
     */
    fun getName(): String

    /**
     * Returns the user's avatar image url
     *
     * @return the user's avatar image url
     */
    fun getAvatar(): String
}