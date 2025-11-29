package com.turingalan.pokemon.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage


@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PokemonDetailScreen(
        modifier = modifier,
        name = uiState.name,
        artwork =  uiState.artwork,
    )


}

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    name: String,
    artwork: String?,
    )
{

    Column(modifier = modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally) {
        if (artwork != null)  {
            AsyncImage(
                model = artwork,
                contentScale = ContentScale.Crop,
                contentDescription = name
            )
        }
    }

}

//@Preview
//@Composable
//fun PokemonDetailScreenPreview() {
//    Surface {
//        PokemonDetailScreen(name = "Eeve", artwork = R.drawable.artwork_133)
//
//    }
//
//}