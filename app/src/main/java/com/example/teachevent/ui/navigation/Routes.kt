//creado por Edwin Mauricio Morales Rodriguez
package com.example.teachevent.ui.navigation

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Catalog : Routes("catalog")
    object Detail : Routes("detail/{eventId}") {
        fun createRoute(id: String) = "detail/$id"
    }
}