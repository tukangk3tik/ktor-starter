package felix_in_ktor.utils

class StringConfig {
    companion object {
        @Volatile
        private var INSTANCE: StringConfig? = null

        fun getInstance(): StringConfig = INSTANCE ?: synchronized(this) {
            val instance = StringConfig()
            INSTANCE = instance
            instance
        }
    }

    var jwtSecret = ""
    var jwtIssuer = ""
    var jwtAudience = ""
    var jwtMyRealm = ""
}