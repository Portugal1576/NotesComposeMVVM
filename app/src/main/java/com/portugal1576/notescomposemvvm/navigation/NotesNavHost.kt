package com.portugal1576.notescomposemvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.portugal1576.notescomposemvvm.MainViewModel
import com.portugal1576.notescomposemvvm.screens.*
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.ID
import com.portugal1576.notescomposemvvm.utils.Constans.Screen.ADD_SCREEN
import com.portugal1576.notescomposemvvm.utils.Constans.Screen.MAIN_SCREEN
import com.portugal1576.notescomposemvvm.utils.Constans.Screen.NOTE_SCREEN
import com.portugal1576.notescomposemvvm.utils.Constans.Screen.START_SCREEN

sealed class NavRoute(val route: String){
    object Start: NavRoute(START_SCREEN)
    object Main: NavRoute(MAIN_SCREEN)
    object Add: NavRoute(ADD_SCREEN)
    object Note: NavRoute(NOTE_SCREEN)
}


@Composable
fun NotesNavHost(mViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = NavRoute.Start.route){
        composable(NavRoute.Start.route){
            StartScreen(navController = navController, viewModel = mViewModel)}
        composable(NavRoute.Main.route){
            MainScreen(navController = navController, viewModel = mViewModel)}
        composable(NavRoute.Add.route){
            AddScreen(navController = navController, viewModel = mViewModel)}
        composable(NavRoute.Note.route + "/{$ID}"){backStackEntry ->
            NoteScreen(navController = navController, viewModel = mViewModel,
            noteId = backStackEntry.arguments?.getString(ID))}
    }
}