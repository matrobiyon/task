package tj.example.zavodteplic.profile.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.example.zavodteplic.R
import tj.example.zavodteplic.profile.presentation.model.ProfileDataItem
import tj.example.zavodteplic.profile.presentation.model.ZnakZodiac

@Composable
fun DrawProfileCardContent(text: String, title: String, type: Int) {
    Row(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = title, fontSize = 10.sp, color = Color.Gray)
        }
        Box(modifier = Modifier
            .size(35.dp)
            .drawBehind {
                drawCircle(Color((0xFF246EE9)))
            }
            .padding(8.dp)) {
            Icon(
                painterResource(id = if (type == 1) R.drawable.ic_at else if (type == 2) R.drawable.ic_phone else R.drawable.ic_user),
                contentDescription = "item",
                tint = Color.White
            )
        }
    }
}

@Composable
fun DrawProfileItems(item: ProfileDataItem) {
    Row(
        modifier = Modifier
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(35.dp)
            .drawBehind {
                drawCircle(Color(0xFFc5dffa))
            }
            .padding(8.dp),
            contentAlignment = Alignment.Center) {
            Icon(
                painterResource(id = item.icon),
                contentDescription = "item",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = item.data,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = item.title,
                color = Color.Gray,
                fontSize = 12.sp,

                )
        }

    }
}

fun getZodiacZnak(day: Int, month: Int): ZnakZodiac? {
    val zodiacSigns = listOf(
        ZnakZodiac("Козерог", 22, 1, 12, 1),
        ZnakZodiac("Водолей", 20, 18, 1, 2),
        ZnakZodiac("Рыбы", 19, 20, 2, 3),
        ZnakZodiac("Овен", 21, 20, 3, 4),
        ZnakZodiac("Телец", 21, 21, 4, 5),
        ZnakZodiac("Близнецы", 22, 21, 5, 6),
        ZnakZodiac("Рак", 22, 22, 6, 7),
        ZnakZodiac("Лев", 23, 23, 7, 8),
        ZnakZodiac("Дева", 24, 23, 8, 9),
        ZnakZodiac("Весы", 24, 23, 9, 10),
        ZnakZodiac("Скорпион", 24, 22, 10, 11),
        ZnakZodiac("Стрелец", 22, 12, 11, 12)
    )

    return zodiacSigns.firstOrNull { znak ->
        (day >= znak.startDate && month == znak.startMonth) ||
                (day <= znak.endDate && month == znak.endMonth)
    }
}