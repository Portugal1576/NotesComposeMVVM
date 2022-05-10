package com.portugal1576.notescomposemvvm.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.portugal1576.notescomposemvvm.MainViewModel
import com.portugal1576.notescomposemvvm.MainViewModelFactory
import com.portugal1576.notescomposemvvm.model.Note
import com.portugal1576.notescomposemvvm.navigation.NavRoute
import com.portugal1576.notescomposemvvm.ui.theme.NotesComposeMVVMTheme
import com.portugal1576.notescomposemvvm.utils.Constans
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.ADD_NEW_NOTE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.DELETE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.EDIT_NOTE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.EMPTY
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.NAV_BACK
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.NONE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.SUBTITLE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.TITLE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.UPDATE
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.UPDATE_NOTE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?){
    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = notes.firstOrNull{it.id == noteId?.toInt()} ?: Note(title = NONE, subtitle = NONE)
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf(EMPTY)}
    var subtitle by remember { mutableStateOf(EMPTY)}

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp)
                ) {
                    Text(
                        text = EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = {title = it},
                        label = {Text(text = TITLE)},
                        isError = title.isEmpty()
                    )
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = {subtitle = it},
                        label = {Text(text = SUBTITLE)},
                        isError = subtitle.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                        viewModel.updateNote(note =
                        Note(id = note.id, title = title, subtitle = subtitle)
                        ){
                            navController.navigate(NavRoute.Main.route)
                        }
                        }
                    ) {
                        Text(text = UPDATE_NOTE)
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ){
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                        Text(
                            text = note.subtitle,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround)
                {
                    Button(onClick = {
                        coroutineScope.launch {
                            title = note.title
                            subtitle = note.subtitle
                            bottomSheetState.show()
                        }
                    }) {
                        Text(text = UPDATE)
                    }
                    Button(onClick = {
                        viewModel.deleteNote(note = note) {
                            navController.navigate(NavRoute.Main.route)
                        }
                    }) {
                        Text(text = DELETE)
                    }

                }
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate(NavRoute.Main.route)
                    }) {
                    Text(text = NAV_BACK)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun prevNoteScreen(){
    NotesComposeMVVMTheme {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

        NoteScreen(
            navController = rememberNavController(),
            viewModel = mViewModel,
            noteId = "1"
        )
    }
}