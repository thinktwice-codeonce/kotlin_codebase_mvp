package com.bss.codebase.exception

class ErrorCodes {

    companion object {
        val UNAUTHORIZED = 401
        val GENERAL_ERROR = 999
        val NOT_AUTHENTICATED_ERROR = 1000
        val NETWORK_NOT_AVAILABLE_ERROR = 1001
        val HTTP_IO_ERROR = 1002
        val HTTP_TIMEOUT_ERROR = 1003
        val HTTP_JSON_SYNTAX_ERROR = 1004
        val HTTP_RESPONSE_JSON_ERROR = 1005
    }
}