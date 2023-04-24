package com.example.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CuteDogPicturesApp()
                }
            }
        }
    }
}

@Composable
fun CuteDogPicturesApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Feed.route) {
        composable(route = Screen.Feed.route) {
            FeedScreen(showAdoptionPage = {dogId ->
                navController.navigate(Screen.Adopt.createRoute(dogId))

            })
        }
        composable(route = Screen.Adopt.route, arguments = listOf(navArgument("dogID") {
            type = NavType.StringType
            nullable = true
        })) { backStackEntry ->
            val dogID = backStackEntry.arguments?.getString("dogID")
            AdoptionScreen(navController, dogID)
        }
    }
}

@Composable
fun FeedScreen(showAdoptionPage: (dogId: String) -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(modifier = Modifier.padding(10.dp), text = "Feed")

        Button(
            modifier = Modifier.padding(10.dp),
            onClick = { showAdoptionPage(null.toString()) }) {
            Text(text = "Click me to adopt!")
        }

        val dogs = remember { DataProvider.puppyList }
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(dogs) {dog ->
                PuppyListItem(
                    dog = dog,
                    onClick = { selectedDog ->
                        showAdoptionPage(selectedDog.id.toString())
                    })
            }
        }
    }
}

@Composable
fun PuppyListItem(dog: Puppy, onClick: (dog: Puppy) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(Modifier.clickable { onClick(dog) }) {
            puppyImage(puppy = dog)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = dog.title, style = MaterialTheme.typography.titleLarge)
                Text(text = "VIEW DETAIL", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun puppyImage(puppy: Puppy) {
    Image(
        painter = painterResource(id = puppy.imageId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

@Composable
fun AdoptionScreen(navController: NavController, dogID: String?) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(modifier = Modifier.padding(10.dp), text = "Adopt")

        Button(onClick = { navController.popBackStack(Screen.Adopt.route, inclusive = true) }) {
            Text(text = "Click me to go back!")

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeNavigationTheme {
//        Greeting("Android")
    }
}