package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_dsy1105.viewmodel.FeriadosViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FeriadosSection(feriadosViewModel: FeriadosViewModel = viewModel()) {
    val estado = feriadosViewModel.estado
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Feriados de Chile",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Próximas fechas de referencia para planificar actividades. " +
                    "Información externa de Nager.Date.",
                style = MaterialTheme.typography.bodyMedium
            )

            when {
                estado.cargando -> CircularProgressIndicator()
                estado.error != null -> {
                    Text(estado.error)
                    Button(onClick = feriadosViewModel::cargar) {
                        Text("Reintentar")
                    }
                }
                estado.feriados.isEmpty() -> Text("No se encontraron fechas disponibles.")
                else -> estado.feriados.forEach { feriado ->
                    Text(
                        text = "${LocalDate.parse(feriado.date).format(formato)} · ${feriado.localName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
