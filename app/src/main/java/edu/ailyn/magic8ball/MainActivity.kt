/*
Ailyn Diaz
2/13/24
CSC Android Magic 8 Ball Game
 */

package edu.ailyn.magic8ball
import edu.ailyn.magic8ball.ui.theme.Magic8ballTheme // my theme.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// string array magic_8_ball_answers
val answers = R.array.magic_8_ball_answers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Magic8ballTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Magic8Ball()
                }
            }
        }
    }
}

@Composable
fun Magic8Ball() {
    var answer by remember { mutableStateOf("Begin...") }
    val context = LocalContext.current
    val scale = remember { Animatable(1f) } // For animating the Magic 8 Ball image
    var shakeBall by remember { mutableStateOf(false) }

    // moves the 8 ball up and down
    LaunchedEffect(shakeBall) {
        if (shakeBall) {
            scale.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(durationMillis = 300)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300)
            )
            shakeBall = false
        }
    }

    Column(     // Column for struct
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Text(   // main title
            text = "MAGIC \uD83E\uDE84 8 BALL  \uD83C\uDFB1",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(   // 2nd part
            text = "Make everyday decisions easier!",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Magic8BallAnswer(answer, scale.value)

        Spacer(modifier = Modifier.height(80.dp))

        // the entire ask button
        Button(
            onClick = {
                shakeBall = true
                answer = generateAnswer(context)
            },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .height(70.dp)
        ) {
            Text(
                text = "✨ ASK ✨",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp
                )
            )
        }
    }
}

//the magic 8 ball inside
@Composable
fun Magic8BallAnswer(answer: String, scale: Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(400.dp)
            .shadow(8.dp, CircleShape)
    ) {
        val magic8BallImage = painterResource(id = R.drawable.magic_8_ball) // image in res/drawable
        Image(
            painter = magic8BallImage,
            contentDescription = "Magic 8 Ball",
            modifier = Modifier
                .matchParentSize()
                .scale(scale)
        )

        if (answer.isNotEmpty()) {
            Text(
                text = answer,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

// my answers from the strings
fun generateAnswer(context: android.content.Context): String {
    val answersArray = context.resources.getStringArray(answers)
    return answersArray[Random.nextInt(answersArray.size)]
}

//run
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Magic8ballTheme {
        Magic8Ball()
    }
}


