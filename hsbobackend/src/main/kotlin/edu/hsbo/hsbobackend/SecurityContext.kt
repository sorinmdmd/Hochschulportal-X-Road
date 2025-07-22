package edu.hsbo.hsbobackend

import org.json.JSONObject
import java.util.Base64

object SecurityContext {
    private val subId: ThreadLocal<String> = ThreadLocal()
    private val role: ThreadLocal<String> = ThreadLocal()

    fun setSub(subId: String) {
        this.subId.set(subId)
    }

    fun getSub(): String? {
        return subId.get()
    }

    fun decodeAndSetRole(token: String) {
        val parts = token.split(".")
        require(parts.size == 3)

        val payloadJson = String(Base64.getUrlDecoder().decode(parts[1]))
        val payload = JSONObject(payloadJson)
        val roles = payload.getJSONObject("realm_access").getJSONArray("roles")

        setRole(
            when {
                "admin" in roles -> "admin"
                "student" in roles -> "student"
                else -> throw IllegalStateException("No roles found in token")
            }
        )
    }

    private fun setRole(role: String){
        this.role.set(role)
    }

    fun getRole(): String? {
        return role.get()
    }

    fun clear() {
        subId.remove()
    }
}