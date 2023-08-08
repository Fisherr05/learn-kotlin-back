package com.z.product.utils

import org.slf4j.LoggerFactory

class Logger {
    val log = LoggerFactory.getLogger(this.javaClass)
    fun info(message:String)= log.info(message)
    fun debug(message:String)= log.debug(message)
    fun warn(message:String)= log.warn(message)
    fun error(message:String)= log.error(message)
}