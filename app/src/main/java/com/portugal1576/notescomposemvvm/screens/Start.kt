package com.portugal1576.notescomposemvvm.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.portugal1576.notescomposemvvm.MainViewModel
import com.portugal1576.notescomposemvvm.MainViewModelFactory
import com.portugal1576.notescomposemvvm.navigation.NavRoute
import com.portugal1576.notescomposemvvm.ui.theme.NotesComposeMVVMTheme
import com.portugal1576.notescomposemvvm.utils.TYPE_FIREBASE
import com.portugal1576.notescomposemvvm.utils.TYPE_ROOM

@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

    //context и ViewModel нужны для обработки кликов, поэтому создаем их... У нас две кнопки
    val context = LocalContext.current
    val mViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))


    Scaffold(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "What will we use? ")
            Button(
                onClick = {
                    mViewModel.initDatabase(TYPE_ROOM){
                        navController.navigate(route = NavRoute.Main.route)
                    }

                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Room database")
            }
            Button(
                onClick = {
                    mViewModel.initDatabase(TYPE_FIREBASE){
                        navController.navigate(route = NavRoute.Main.route)
                    }

                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Firebase database")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun prevStartScreen() {
    NotesComposeMVVMTheme {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

        StartScreen(navController = rememberNavController(), viewModel = mViewModel)
    }
}