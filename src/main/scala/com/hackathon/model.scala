package com.hackathon

object model {

  sealed trait ApiError extends Product with Serializable
  case class UserNotFound(username: String) extends ApiError

  case class User(id: String,
                  username: String,
                  email: String)

}
