package com.max.hsu.dartscoreboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.max.hsu.dartscoreboard.ui.theme.DartScoreBoardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootFrame()
        }
    }
}

@Composable
fun RootFrame() {
    DartScoreBoardTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                val (header, gameStart, gameReset, gameContinue) = createRefs()
                IconTitle(Modifier.constrainAs(header) {
                    top.linkTo(parent.top, margin = 80.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                })

                GameButton(Modifier.constrainAs(gameStart) {
                    bottom.linkTo(gameContinue.top, margin = 12.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }, R.string.gameStart) {

                }

                GameButton(Modifier.constrainAs(gameContinue) {
                    bottom.linkTo(gameReset.top, margin = 12.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }, R.string.gameContinue) {

                }

                GameButton(Modifier.constrainAs(gameReset) {
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }, R.string.gameReset) {

                }
            }
        }
    }
}

@Composable
fun IconTitle(modifier: Modifier) {
    Image(
        modifier = modifier.size(100.dp, 100.dp),
        painter = painterResource(id = R.drawable.ic_dart),
        contentDescription = null
    )
}

@Composable
fun GameSetting(modifier: Modifier, @StringRes actionRes: Int, buttonClick: () -> Unit) {
    Button(
        onClick = buttonClick,
        modifier = modifier
            .height(42.dp)
    ) {
        Text(stringResource(id = actionRes))
    }
}

@Composable
fun GameButton(modifier: Modifier, @StringRes actionRes: Int, buttonClick: () -> Unit) {
    Button(
        onClick = buttonClick,
        modifier = modifier
            .height(42.dp)
    ) {
        Text(stringResource(id = actionRes))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF, heightDp = 640, widthDp = 360)
@Composable
fun DefaultPreview() {
    RootFrame()
}