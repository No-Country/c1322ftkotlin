package com.nocuntry.c1322ftkotlin.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerMenu(
    onMenuItemClick: (MenuItem) -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Opciones del menú
        Text(
            text = "Profile",
            modifier = Modifier
                .clickable {
                    onMenuItemClick(
                        MenuItem.Profile
                    )
                }
        )
        Text(
            text = "Notifications",
            modifier = Modifier
                .clickable {
                    onMenuItemClick(
                        MenuItem.notifications
                    )
                }
        )

        Divider()
        // Logout
        Text(
            text = "Logout",
            modifier = Modifier
                .clickable {
                    onLogoutClick()
                }
        )
    }
}
