package com.example.calculargorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculargorjeta.ui.theme.CalcularGorjetaTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalcularGorjetaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CalculadoraGorjeta(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculadoraGorjeta(modifier: Modifier = Modifier) {
    var valorConta by remember { mutableStateOf("") }
    var porcentagemGorjeta by remember { mutableStateOf("") }
    var arredondarGorjeta by remember { mutableStateOf(false) }

    val gorjeta = calcularGorjeta(
        valorConta = valorConta,
        porcentagem = porcentagemGorjeta,
        arredondar = arredondarGorjeta
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Calculadora de Gorjeta",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = valorConta,
            onValueChange = { valorConta = it },
            label = { Text("Valor da conta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = porcentagemGorjeta,
            onValueChange = { porcentagemGorjeta = it },
            label = { Text("Porcentagem da gorjeta (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Arredondar gorjeta")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = arredondarGorjeta,
                onCheckedChange = { arredondarGorjeta = it }
            )
        }

        Text(
            text = "Gorjeta: $gorjeta",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

fun calcularGorjeta(
    valorConta: String,
    porcentagem: String,
    arredondar: Boolean
): String {
    val valor = valorConta.toDoubleOrNull() ?: 0.0
    val percentual = porcentagem.toDoubleOrNull() ?: 0.0

    var gorjeta = valor * (percentual / 100)

    if (arredondar) {
        gorjeta = ceil(gorjeta)
    }

    return NumberFormat.getCurrencyInstance().format(gorjeta)
}

@Preview(showBackground = true)
@Composable
fun CalculadoraGorjetaPreview() {
    CalcularGorjetaTheme {
        CalculadoraGorjeta()
    }
}
