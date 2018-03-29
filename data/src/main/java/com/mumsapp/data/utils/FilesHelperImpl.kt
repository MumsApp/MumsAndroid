package com.mumsapp.data.utils

import android.content.Context
import android.os.Environment
import android.support.v4.content.FileProvider
import com.mumsapp.android.di.qualifiers.ApplicationId
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class FilesHelperImpl : FilesHelper {

    private val appContext: Context
    private val applicationId: String

    @Inject
    constructor(appContext: Context, @ApplicationId applicationId: String) {
        this.appContext = appContext
        this.applicationId = applicationId
    }

    override fun createTemporaryFile(name: String, extWithDot: String): File {
        val tmpDir = Environment.getExternalStorageDirectory()
        if(!tmpDir.exists()) {
            tmpDir.mkdirs()
        }

        return File.createTempFile(name, extWithDot, tmpDir)
    }

    override fun getExportedUri(file: File): String {
        val uri = FileProvider.getUriForFile(appContext, "$applicationId.provider", file)
        return uri.toString()
    }
}