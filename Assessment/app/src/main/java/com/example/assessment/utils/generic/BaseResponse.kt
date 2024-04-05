package com.example.assessment.utils.generic

class BaseResponse<T>(var status: Status, var data: T? = null, var error: String?) {


    enum class Status {
        LOADING, SUCCESS, ERROR, NO_INTERNET_AVAILABLE, TOKEN_EXPIRED, USER_NOT_VERIFIED, EMPTY, EXCEPTION
    }

    companion object {
        fun <T> loading(): BaseResponse<T> {
            return BaseResponse<T>(Status.LOADING, null, null)
        }
        fun <T> success(data: T): BaseResponse<T> {
            return BaseResponse(Status.SUCCESS, data, null)
        }
        fun <T> error(error: String?): BaseResponse<T> {
            return BaseResponse(Status.ERROR, null, error)
        }

        fun <T> tokenExpired(error: String?): BaseResponse<T> {
            return BaseResponse(Status.TOKEN_EXPIRED, null, error)
        }

        fun <T> onException(error: String?): BaseResponse<T> {
            return BaseResponse(Status.EXCEPTION, null, error)
        }


        fun <T> userNotVerified(error: String?): BaseResponse<T> {
            return BaseResponse(Status.USER_NOT_VERIFIED, null, error)
        }



        fun <T> empty(message: String?): BaseResponse<T> {
            return BaseResponse(Status.EMPTY, null, message)
        }



        fun <T> noInternetAvailable(): BaseResponse<T> {
            return BaseResponse(Status.NO_INTERNET_AVAILABLE, null, "No internet connection found")
        }
    }





}