import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen()
        }
    }
}

@Composable
fun GameScreen() {
    var targetValue by remember { mutableStateOf(Random.nextInt(0, 101)) }
    var sliderValue by remember { mutableStateOf(50f) }
    var score by remember { mutableStateOf(0) }
    var round by remember { mutableStateOf(1) }
    var feedbackText by remember { mutableStateOf("") }

    fun calculateScore() {
        val guess = sliderValue.toInt()
        val difference = kotlin.math.abs(targetValue - guess)
        when {
            difference <= 3 -> {
                score += 5
                feedbackText = "Perfect! You got all the points."
            }
            difference <= 8 -> {
                score += 1
                feedbackText = "Almost perfect! Try again."
            }
            else -> {
                feedbackText = "So close! Try again."
            }
        }

        targetValue = Random.nextInt(0, 101)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bull's Eye Game",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Move the slider as close as you can to : $targetValue",
            style = TextStyle(fontSize = 18.sp)
        )

        Slider(
            value = sliderValue,
            onValueChange = { newValue ->
                sliderValue = newValue
            },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Button(
            onClick = {
                calculateScore()
                round++
                sliderValue = 50f // Reset slider to the middle
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Hit Me!")
        }

        Text(
            text = "Your Score: $score",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Blue),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = feedbackText,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameScreen() {
    GameScreen()
}
