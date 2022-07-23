package com.basar.moviehunter.extension

import com.basar.moviehunter.util.ConstantsHelper.DOC_FORMAT

fun getDocumentLink(url: String) = String.format(DOC_FORMAT, url)