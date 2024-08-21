package tj.example.zavodteplic.auth.presentation.event

import androidx.compose.ui.text.input.OffsetMapping

class TextFieldOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return offset + 1
    }

    override fun transformedToOriginal(offset: Int): Int {
        return offset - 1
    }
}

