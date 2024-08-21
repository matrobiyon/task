package tj.example.zavodteplic.auth.presentation.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawBottomSheet(sheetState: SheetState, onClick: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = {
            onClick()
        },
        sheetState = sheetState
    ) {
        // Sheet content
        Button(onClick = {
            onClick()
        }) {
            Text("Hide bottom sheet")
        }
    }
}