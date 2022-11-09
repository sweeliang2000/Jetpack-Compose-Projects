package com.example.movieapp.screens.home.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movieapp.model.getMovies
import com.example.movieapp.widgets.MovieRow

@Composable
fun DetailsScreen(navController: NavController, movieID:String?){

    val newMovieList = getMovies().filter {
        it.id == movieID
    }
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.LightGray,
            elevation = 5.dp) {
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(100.dp))
                Text(text = "Movies") }

        }
    }) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                MovieRow(newMovieList[0])
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Movie Images")
                LazyRow( ){
                    items(newMovieList[0].images){
                        Card(modifier = Modifier
                            .padding(12.dp)
                            .size(240.dp),
                            elevation = 5.dp) {
                            Image(painter = rememberImagePainter(data = it), contentDescription = "Movie Poster")
                            
                            
                        }
                    }
                }


            }
        }
    }
    
    
    
    

}